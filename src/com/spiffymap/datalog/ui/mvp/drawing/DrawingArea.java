package com.spiffymap.datalog.ui.mvp.drawing;

import com.spiffymap.geo.shared.geom.BoundingBoxInt;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.Scrollable;

/**
 *
 * @author steve
 * @version 30-Dec-2015
 */
public class DrawingArea extends JComponent implements MouseListener, MouseMotionListener,
        Scrollable {

    private static final Logger LOG = Logger.getLogger(DrawingArea.class.getName());

    private Image image;
    private boolean dragging = false;
    private volatile boolean selectionMode = false;
    private boolean selectingRectangle = false;
    private boolean selectingRectangleDrawn = false;
    private int offsetX;
    private int offsetY;
    private double scale;
    private int startDragX;
    private int startDragY;
    private int lastDragX;
    private int lastDragY;
    private int minDrawingX;
    private int minDrawingY;
    private int maxDrawingX;
    private int maxDrawingY;
    private BiConsumer<Integer, Integer> shiftConsumer;
    private BiConsumer<Integer, Integer> clickConsumer;
    private BoundingBoxInt selectedRectangle;
    private Optional<Consumer<BoundingBoxInt>> selectedRectangleConsumer = Optional.empty();
    private Optional<Runnable> rectangleSelectionEndCode = Optional.empty();
    private final Deque<DrawingState> drawingStateStack = new ArrayDeque<>();

    public DrawingArea() {
        addListeners();
    }

    private void addListeners() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void saveCurrentState() {
        drawingStateStack.push(new DrawingState(scale, offsetX, offsetY));
    }

    public void popState() {
        if (drawingStateStack.isEmpty()) {
            return;
        }
        DrawingState state = drawingStateStack.pop();
        offsetX = state.getxOffset();
        offsetY = state.getyOffset();
        scale = state.getScale();
    }

    public void reset() {
        saveCurrentState();
        if (shiftConsumer != null) {
            shiftConsumer.accept(0, 0);
        }
        offsetX = 0;
        offsetY = 0;
    }

    private BoundingBoxInt getSelectedRectangle(int currentX, int currentY) {
        int minX = Math.min(currentX, startDragX);
        int minY = Math.min(currentY, startDragY);
        int maxX = Math.max(currentX, startDragX);
        int maxY = Math.max(currentY, startDragY);
        return new BoundingBoxInt(minX, minY, maxX, maxY);
    }

    private void drawSelectingRectangle(int currentX, int currentY) {
        selectedRectangle = getSelectedRectangle(currentX, currentY);
        int width = selectedRectangle.getMaxX() - selectedRectangle.getMinX();
        int height = selectedRectangle.getMaxY() - selectedRectangle.getMinY();
        Graphics g = getGraphics();
        g.setXORMode(Color.gray);
        g.drawRect(selectedRectangle.getMinX(), selectedRectangle.getMinY(), width, height);
        g.setPaintMode();
    }

    private void clearSelectingRectangle() {
        // Clear old dragging rectangle
        if (selectingRectangleDrawn) {
            drawSelectingRectangle(lastDragX, lastDragY);
            selectingRectangleDrawn = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        if (selectionMode) {
            return;
        }
        if (selectingRectangle) {
            LOG.info("Selecting rectangle");
            // Clear old dragging rectangle
            clearSelectingRectangle();
        } else {
            LOG.info("Dragging");
        }
        int currentX = event.getX();
        int currentY = event.getY();
        if (!dragging) {
            dragging = true;
        } else {
            if (currentX != lastDragX || currentY != lastDragY) {
                int xShift = currentX + offsetX - startDragX;
                int yShift = currentY + offsetY - startDragY;
                if (selectingRectangle) {
                    LOG.log(Level.FINE, "Drawing selection rectangle to {0},{1}",
                            new Object[]{currentX, currentY});
                    drawSelectingRectangle(currentX, currentY);
                    selectingRectangleDrawn = true;
                } else if (shiftConsumer != null) {
                    shiftConsumer.accept(xShift, yShift);
                }
            }
        }
        lastDragX = currentX;
        lastDragY = currentY;
    }

    @Override
    public void mouseMoved(MouseEvent event) {

    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (!selectionMode) {
            return;
        }
        if (clickConsumer != null) {
            clickConsumer.accept(event.getX(), event.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (selectionMode) {
            return;
        }
        startDragX = event.getX();
        startDragY = event.getY();
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (selectionMode) {
            return;
        }
        saveCurrentState();
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (selectingRectangle) {
            endSelectingRectangle();
        } else {
            // End of drag - note current drag coordinates
            offsetX += lastDragX - startDragX;
            offsetY = lastDragY - startDragY;
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image == null) {
            return;
        }
        g.drawImage(image, 0, 0, this);
    }

    /**
     * @param image the image to set
     */
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    /**
     * @param shiftConsumer the shiftConsumer to set
     */
    public void setShiftConsumer(BiConsumer<Integer, Integer> shiftConsumer) {
        this.shiftConsumer = shiftConsumer;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double scale) {
        saveCurrentState();
        this.scale = scale;
    }

    /**
     * @return the scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * @param selectionMode the selectionMode to set
     */
    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
    }

    /**
     * @param clickConsumer the clickConsumer to set
     */
    public void setClickConsumer(BiConsumer<Integer, Integer> clickConsumer) {
        this.clickConsumer = clickConsumer;
    }

    public void startSelectingRectangle(Optional<Consumer<BoundingBoxInt>> rectangleConsumer,
            Optional<Runnable> rectangleSelectionEndCode) {
        selectedRectangleConsumer = rectangleConsumer;
        this.rectangleSelectionEndCode = rectangleSelectionEndCode;
        selectingRectangle = true;
    }

    public void cancelSelectingRectangle() {
        clearSelectingRectangle();
        selectingRectangle = false;
        selectingRectangleDrawn = false;
        selectedRectangle = null;
        rectangleSelectionEndCode.ifPresent(Runnable::run);
    }

    public void endSelectingRectangle() {
        clearSelectingRectangle();
        selectingRectangle = false;
        selectingRectangleDrawn = false;
        selectedRectangleConsumer.ifPresent(code -> code.accept(selectedRectangle));
        rectangleSelectionEndCode.ifPresent(Runnable::run);
    }

    /**
     * @param selectedRectangleConsumer the selectedRectangleConsumer to set
     */
    public void setSelectedRectangleConsumer(Optional<Consumer<BoundingBoxInt>> selectedRectangleConsumer) {
        this.selectedRectangleConsumer = selectedRectangleConsumer;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        int width = maxDrawingX - minDrawingX;
        int height = maxDrawingY - minDrawingY;
        return new Dimension(width, height);

    }

    public void updateDrawingBounds(int minDrawingX, int minDrawingY, int maxDrawingX, int maxDrawingY) {
        this.minDrawingX = minDrawingX;
        this.minDrawingY = minDrawingY;
        this.maxDrawingX = maxDrawingX;
        this.maxDrawingY = maxDrawingY;
        setPreferredSize(new Dimension(maxDrawingX - minDrawingX, maxDrawingY - minDrawingY));
        invalidate();
    }
}
