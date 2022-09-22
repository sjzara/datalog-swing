package com.spiffymap.datalog.ui.mvp.drawing;

import com.spiffymap.geo.shared.geom.BoundingBoxDouble;
import com.spiffymap.geo.shared.geom.BoundingBoxInt;
import com.spiffymap.geo.shared.geom.CoordinateDouble;
import com.spiffymap.mvp.graphics.Java2DPen;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import javax.swing.JPanel;
import net.spiffymap.datalog.client.drawing.InvertingScaler;
import net.spiffymap.datalog.client.drawing.Plotter;
import net.spiffymap.datalog.client.drawing.Scale;
import net.spiffymap.datalog.client.drawing.Scaler;
import net.spiffymap.datalog.client.drawing.ScalingPlotter;
import net.spiffymap.datalog.shared.drawing.SpiffyPenPlotter;
import net.spiffymap.datalog.client.model.DatalogFile;
import net.spiffymap.datalog.client.model.Detail;
import net.spiffymap.datalog.client.io.DrawingSettings;
import net.spiffymap.datalog.shared.ObjectSupplier;
import net.spiffymap.datalog.shared.mvp.drawing.DrawingPresenter;
import net.spiffymap.datalog.shared.mvp.drawing.DrawingView;

/**
 *
 * @author steve
 */
public class DrawingPanel extends JPanel implements DrawingView {

    private double drawingAreaWidth;
    private double drawingAreaHeight;
    private double newDrawingAreaWidth;
    private double newDrawingAreaHeight;
    final private Timer timer = new Timer();
    private DatalogFile file;
    private Scaler scaler;
    private BoundingBoxDouble mapBounds;
    private Scale scale;
    private double grid;
    private int shiftX;
    private int shiftY;
    private DrawingSettings drawingSettings;
    private DrawingPresenter presenter;
    private final DrawingArea drawingArea;
    private boolean isGoingBack;
    private final Map<CoordinateDouble, Detail> detailPositionMap = new HashMap<>();
    private final Set<Integer> selectedDetailNumbers = new HashSet<>();
    private ObjectSupplier objectSupplier;

    /**
     * Creates new form DrawingPanel
     */
    public DrawingPanel() {
        initComponents();
        drawingArea = new DrawingArea();
        drawingArea.setShiftConsumer(this::setShift);
        drawingArea.setClickConsumer(this::click);
        scrollPane.getViewport().add(drawingArea);
        contoursCheckBox.setVisible(false);
        groundModelCheckBox.setVisible(false);
        editDetailButton.setVisible(false);
        // Initial button states
        selectionToggleButton.setSelected(false);
        dragButton.setSelected(true);
        selectionToggleButton.addActionListener(this::handleToggleButtons);
        dragButton.addActionListener(this::handleToggleButtons);
    }

    @Override
    public void hideView() {
        // No action needed
    }

    @Override
    public void showView() {
        // Set up the drawing settings
        drawLevelsCheckBox.setSelected(presenter.isDrawingLevels());
        drawNumbersCheckBox.setSelected(presenter.isDrawingNumbers());
    }

    /**
     * Handle clicks on toggle buttons so that they act like radio buttons
     *
     * @param event
     */
    private void handleToggleButtons(ActionEvent event) {
        if (event.getSource().equals(selectionToggleButton)) {
            selectionToggleButton.setSelected(true);
            dragButton.setSelected(false);
        } else {
            selectionToggleButton.setSelected(false);
            dragButton.setSelected(true);
        }
        drawingArea.setSelectionMode(selectionToggleButton.isSelected());
        // Toggling clears selection
        clearSelectedDetail();
    }

    /**
     * Calculate the multiplier if there is none.
     */
    @Override
    public void calculateInitialScale() {
        InvertingScaler invertingScaler = new InvertingScaler();
        invertingScaler.setViewMaxY(drawingArea.getHeight());
        scaler = invertingScaler;
        Rectangle drawingBounds = scrollPane.getViewport().getBounds();
        // Work out multiplier
        Plotter plotter = new ScalingPlotter(scaler);
        plotter.clear();
        plotter.draw(file);
        grid = scaler.estimateGridSize();
        plotter.drawGrid(grid);
        // The scaling plotter will have got the extents of the drawn data.
        scaler.calcScale(drawingBounds.getMinX(), drawingBounds.getMaxX(),
                drawingBounds.getMinY(), drawingBounds.getMaxY());
        scale = scaler.getScale();
    }

    private TimerTask getTrackSizeChangesTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if (newDrawingAreaWidth != drawingAreaWidth || newDrawingAreaHeight != drawingAreaHeight) {
                    // Check again in another 100ms
                    drawingAreaWidth = newDrawingAreaWidth;
                    drawingAreaHeight = newDrawingAreaHeight;
                    timer.schedule(getTrackSizeChangesTask(), 100);
                }
            }
        };
    }


    /*
     * Erase any drawing
     */
    @Override
    public void clearDrawing() {
        Image drawing = null;
        drawingArea.setImage(drawing);
    }

    @Override
    public void resetScale() {
        scaler = null;
    }

    /**
     * Prepare the scaler.
     */
    @Override
    public void setupScaler() {
        if (scaler == null) {
            calculateInitialScale();
        } else {
            if (scale != null) {
                scaler.setScale(scale);
            }
        }
    }

    /**
     * Draw the file and note the detail positions.
     */
    @Override
    public void doDrawing() {
        clearDrawing();
        if (file == null) {
            detailPositionMap.clear();
            return;
        }
        setupScaler();
        calculateMapExtents();
        // Draw
        int height = (int) drawingArea.getHeight();
        int width = (int) drawingArea.getWidth();
        BufferedImage drawing = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = (Graphics2D) drawing.getGraphics();
        Java2DPen pen = new Java2DPen(graphics, drawingArea.getBounds());
        // Clear background to white.  We use the foreground colour for this, as suggested in Swing JavaDocs.
        pen.setColourDirectly("#FFFFFF");
        pen.clear();
        pen.setShift(shiftX, shiftY);
        SpiffyPenPlotter plotter = new SpiffyPenPlotter(pen, objectSupplier);
        plotter.setDebugCode(() -> {
            drawingArea.setImage(drawing);
            drawingArea.paint(drawingArea.getGraphics());
        });
        plotter.clear();
        plotter.setSelectedDetailNumbers(selectedDetailNumbers);
        plotter.setDrawingSettings(drawingSettings);
        plotter.setDrawLevels(presenter.isDrawingLevels());
        plotter.setDrawNumbers(presenter.isDrawingNumbers());
        plotter.setDrawGroundModel(presenter.isDrawGroundModel());
        plotter.setDrawContours(presenter.isDrawContours());
        plotter.setScaler(scaler);
        // Set scheduler for animation
        //plotter.setAnimationScheduler(code -> SwingUtilities.invokeLater(code));
        // Set redrawing action for use during animation
        //plotter.setRefreshDisplayCode(() -> drawingArea.setImage(drawing));
        plotter.draw(file);
        plotter.drawGrid(grid);
        drawingArea.setImage(drawing);
        // Record settings if not going back to previous state
        if (!isGoingBack) {
            // Record scale
            drawingArea.setScale(scaler.getScale().getMultiplier());
            drawingArea.saveCurrentState();
            // Now possible to go back
            backButton.setEnabled(true);
        }
        isGoingBack = false;
        // Note positions, including drawing shift
        detailPositionMap.clear();
        Map<CoordinateDouble, Detail> plotterPositionDetailMap = plotter.getPositionDetailMap();
        for (CoordinateDouble position : plotterPositionDetailMap.keySet()) {
            CoordinateDouble screenPosition = new CoordinateDouble(position.getX() + shiftX, position.getY() + shiftY);
            detailPositionMap.put(screenPosition, plotterPositionDetailMap.get(position));
        }
    }

    /**
     * Respond to mouse x and y value when dragging
     *
     * @param shiftX
     * @param shiftY
     */
    public void setShift(int shiftX, int shiftY) {
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        doDrawing();
    }

    private void calculateMapExtents() {
        double visibleMinX = mapBounds.getMinX() * scale.getMultiplier() + scale.getxOffset();
        double visibleMinY = mapBounds.getMinY() * scale.getMultiplier() + scale.getyOffset();
        double visibleMaxX = mapBounds.getMaxX() * scale.getMultiplier() + scale.getxOffset();
        double visibleMaxY = mapBounds.getMaxY() * scale.getMultiplier() + scale.getyOffset();
        drawingArea.setBounds((int) visibleMinX, (int) visibleMinY, (int) visibleMaxX,
                (int) visibleMaxY);
        drawingArea.updateDrawingBounds((int) visibleMinX, (int) visibleMinY, (int) visibleMaxX,
                (int) visibleMaxY);
    }

    /**
     * Respond to mouse x and y value when selecting details
     *
     * @param clickX
     * @param clickY
     */
    public void click(int clickX, int clickY) {
        presenter.attemptSelectDetail(new CoordinateDouble(clickX, clickY));
    }

    /**
     * Set and show the selected detail.
     *
     * @param number
     */
    @Override
    public void setSelectedDetail(int number) {
        selectedDetailNumbers.clear();
        selectedDetailNumbers.add(number);
        editDetailButton.setText("Edit detail " + number);
        editDetailButton.setVisible(true);
        doDrawing();
    }

    /**
     * Clear any selected detail and redraw.
     */
    @Override
    public void clearSelectedDetail() {
        selectedDetailNumbers.clear();
        editDetailButton.setVisible(false);
        doDrawing();
    }

    private void toggleSelection() {
        drawingArea.setSelectionMode(selectionToggleButton.isSelected());
        // Toggling clears selection
        clearSelectedDetail();
    }

    @Override
    public void resetDrawingArea() {
        drawingArea.reset();
    }

    /**
     * @param file the file to set
     */
    @Override
    public void setFile(DatalogFile file) {
        this.file = file;
        drawButton.setEnabled(true);
        zoomInButton.setEnabled(true);
        zoomOutButton.setEnabled(true);
        zoomExtentsButton.setEnabled(true);
        mapBounds = file.dataRange();
    }

    public CoordinateDouble getDisplayCentre() {
        Rectangle bounds = drawingArea.getBounds();
        double centreX = (bounds.getMaxX() - bounds.getMinX()) / 2;
        double centreY = (bounds.getMaxY() - bounds.getMinY()) / 2;
        return new CoordinateDouble(centreX, centreY);
    }

    /**
     * @param drawingSettings the drawingSettings to set
     */
    @Override
    public void setDrawingSettings(DrawingSettings drawingSettings) {
        this.drawingSettings = drawingSettings;
    }

    /**
     * Go back to a previous view, if any.
     */
    public void goBack() {
        isGoingBack = true;
        drawingArea.popState();
        scale.setMultiplier(drawingArea.getScale());
        doDrawing();
    }

    @Override
    public void setPresenter(DrawingPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void endAllDrawing() {
        timer.cancel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        drawButton = new javax.swing.JButton();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        zoomToRectangleButton = new javax.swing.JToggleButton();
        zoomExtentsButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        drawLevelsCheckBox = new javax.swing.JCheckBox();
        drawNumbersCheckBox = new javax.swing.JCheckBox();
        groundModelCheckBox = new javax.swing.JCheckBox();
        contoursCheckBox = new javax.swing.JCheckBox();
        selectionToggleButton = new javax.swing.JToggleButton();
        dragButton = new javax.swing.JToggleButton();
        editDetailButton = new javax.swing.JButton();
        gotoDetailButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        drawButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/draw.png"))); // NOI18N
        drawButton.setText("Draw");
        drawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(drawButton);

        zoomInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/zoomIn.png"))); // NOI18N
        zoomInButton.setText("Zoom in");
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(zoomInButton);

        zoomOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/zoomOut.png"))); // NOI18N
        zoomOutButton.setText("Zoom out");
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(zoomOutButton);

        zoomToRectangleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/box.png"))); // NOI18N
        zoomToRectangleButton.setText("Zoom rectangle");
        zoomToRectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomToRectangleButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(zoomToRectangleButton);

        zoomExtentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/zoomExtents.png"))); // NOI18N
        zoomExtentsButton.setText("Zoom extents");
        zoomExtentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomExtentsButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(zoomExtentsButton);

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/goback.png"))); // NOI18N
        backButton.setText("Back to last view");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(backButton);

        drawLevelsCheckBox.setText("Levels");
        drawLevelsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawLevelsCheckBoxActionPerformed(evt);
            }
        });
        buttonPanel.add(drawLevelsCheckBox);

        drawNumbersCheckBox.setText("Numbers");
        drawNumbersCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawNumbersCheckBoxActionPerformed(evt);
            }
        });
        buttonPanel.add(drawNumbersCheckBox);

        groundModelCheckBox.setText("Ground model");
        groundModelCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groundModelCheckBoxActionPerformed(evt);
            }
        });
        buttonPanel.add(groundModelCheckBox);

        contoursCheckBox.setText("Contours");
        contoursCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contoursCheckBoxActionPerformed(evt);
            }
        });
        buttonPanel.add(contoursCheckBox);

        selectionToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/select_detail.png"))); // NOI18N
        selectionToggleButton.setText("Select details");
        buttonPanel.add(selectionToggleButton);

        dragButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/drag.png"))); // NOI18N
        dragButton.setText("Drag");
        buttonPanel.add(dragButton);

        editDetailButton.setText("Edit selected detail");
        editDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editDetailButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(editDetailButton);

        gotoDetailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/goto.png"))); // NOI18N
        gotoDetailButton.setText("Go to detail");
        gotoDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gotoDetailButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(gotoDetailButton);

        add(buttonPanel, java.awt.BorderLayout.NORTH);
        add(scrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void drawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawButtonActionPerformed
        presenter.draw();
    }//GEN-LAST:event_drawButtonActionPerformed

    private void drawLevelsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawLevelsCheckBoxActionPerformed
        presenter.setDrawingLevels(drawLevelsCheckBox.isSelected());
        doDrawing();
    }//GEN-LAST:event_drawLevelsCheckBoxActionPerformed

    private void drawNumbersCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawNumbersCheckBoxActionPerformed
        presenter.setDrawingNumbers(drawNumbersCheckBox.isSelected());
        doDrawing();
    }//GEN-LAST:event_drawNumbersCheckBoxActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        zoomIn();
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        zoomOut();
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void zoomExtentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomExtentsButtonActionPerformed
        zoomFull();
    }//GEN-LAST:event_zoomExtentsButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        goBack();
    }//GEN-LAST:event_backButtonActionPerformed

    private void groundModelCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groundModelCheckBoxActionPerformed
        presenter.setDrawGroundModel(groundModelCheckBox.isSelected());
        doDrawing();
    }//GEN-LAST:event_groundModelCheckBoxActionPerformed

    private void contoursCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contoursCheckBoxActionPerformed
        presenter.setDrawContours(contoursCheckBox.isSelected());
        doDrawing();
    }//GEN-LAST:event_contoursCheckBoxActionPerformed

    private void editDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editDetailButtonActionPerformed
        if (!selectedDetailNumbers.isEmpty()) {
            presenter.editSelectedDetail(selectedDetailNumbers.iterator().next());
        }
    }//GEN-LAST:event_editDetailButtonActionPerformed

    private void gotoDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gotoDetailButtonActionPerformed
        presenter.chooseDetail();
    }//GEN-LAST:event_gotoDetailButtonActionPerformed

    private void zoomToRectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomToRectangleButtonActionPerformed
        if (zoomToRectangleButton.isSelected()) {
            drawingArea.startSelectingRectangle(Optional.of(presenter::zoomRectangle),
                    Optional.of(() -> zoomToRectangleButton.setSelected(false)));
        } else {
            drawingArea.cancelSelectingRectangle();
        }
    }//GEN-LAST:event_zoomToRectangleButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JCheckBox contoursCheckBox;
    private javax.swing.JToggleButton dragButton;
    private javax.swing.JButton drawButton;
    private javax.swing.JCheckBox drawLevelsCheckBox;
    private javax.swing.JCheckBox drawNumbersCheckBox;
    private javax.swing.JButton editDetailButton;
    private javax.swing.JButton gotoDetailButton;
    private javax.swing.JCheckBox groundModelCheckBox;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JToggleButton selectionToggleButton;
    private javax.swing.JButton zoomExtentsButton;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    private javax.swing.JToggleButton zoomToRectangleButton;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the detailPositionMap
     */
    @Override
    public Map<CoordinateDouble, Detail> getDetailPositionMap() {
        return detailPositionMap;
    }

    /**
     * @param objectSupplier the objectSupplier to set
     */
    @Override
    public void setObjectSupplier(ObjectSupplier objectSupplier) {
        this.objectSupplier = objectSupplier;
    }

    @Override
    public Scale getScale() {
        return scale;
    }

    @Override
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    @Override
    public BoundingBoxDouble getDrawingBounds() {
        Rectangle rect = drawingArea.getBounds();
        BoundingBoxDouble bounds = new BoundingBoxDouble(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY());
        return bounds;
    }

    /**
     * @return the scaler
     */
    @Override
    public Scaler getScaler() {
        return scaler;
    }

    /**
     * @param scaler the scaler to set
     */
    @Override
    public void setScaler(Scaler scaler) {
        this.scaler = scaler;
    }

    @Override
    public void zoomRectangle(Consumer<BoundingBoxInt> rectangleConsumer) {
        drawingArea.startSelectingRectangle(Optional.of(rectangleConsumer),
                Optional.of(() -> zoomToRectangleButton.setSelected(false)));
    }

    /**
     * @return the mapBounds
     */
    @Override
    public BoundingBoxDouble getMapBounds() {
        return mapBounds;
    }

    /**
     * @param mapBounds the mapBounds to set
     */
    @Override
    public void setMapBounds(BoundingBoxDouble mapBounds) {
        this.mapBounds = mapBounds;
    }
}
