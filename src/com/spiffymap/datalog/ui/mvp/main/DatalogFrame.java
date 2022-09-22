package com.spiffymap.datalog.ui.mvp.main;

import com.spiffymap.mvp.desktop.ui.SwingDialogs;
import com.spiffymap.datalog.SwingObjectSupplier;
import com.spiffymap.datalog.client.mvp.help.HelpDialog;
import com.spiffymap.datalog.ui.mvp.details.DetailCoordinatePanel;
import com.spiffymap.datalog.ui.mvp.details.DetailDataPanel;
import com.spiffymap.datalog.ui.mvp.EditGuideDialog;
import com.spiffymap.datalog.ui.mvp.details.SelectionDialog;
import com.spiffymap.datalog.ui.mvp.drawing.DrawingPanel;
import com.spiffymap.datalog.ui.mvp.history.EditHistoryDialog;
import com.spiffymap.datalog.ui.mvp.information.CodesDialog;
import com.spiffymap.datalog.ui.mvp.information.InformationPanel;
import com.spiffymap.datalog.ui.mvp.information.NewsDialog;
import com.spiffymap.datalog.ui.mvp.logo.LogoPanel;
import com.spiffymap.datalog.ui.mvp.printing.PrinterSetupDialog;
import com.spiffymap.datalog.ui.mvp.problems.DXFCompleteDialog;
import com.spiffymap.datalog.ui.mvp.problems.ProblemsDialog;
import com.spiffymap.datalog.ui.mvp.settings.DXFSettingsDialog;
import com.spiffymap.datalog.ui.mvp.settings.SettingsPanel;
import com.spiffymap.datalog.ui.mvp.stations.StationPanel;
import com.spiffymap.datalog.ui.mvp.stations.TraverseDisplayDialog;
import com.spiffymap.datalog.ui.mvp.stations.TraverseInputDialog;
import com.spiffymap.datalog.ui.mvp.stations.TraversesDialog;
import com.spiffymap.datalog.ui.utils.FocussingView;
import com.spiffymap.datalog.ui.utils.Settings;
import com.spiffymap.datalog.ui.utils.ToolbarManager;
import com.spiffymap.server.ui.UIUtils;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import net.spiffymap.datalog.client.mvp.help.HelpView;
import net.spiffymap.datalog.shared.modules.MainModule;
import net.spiffymap.datalog.shared.ObjectSupplier;
import net.spiffymap.datalog.shared.mvp.HelpDisplayingView;
import com.spiffymap.mvp.client.ui.View;
import net.spiffymap.datalog.shared.mvp.details.DetailCoordinateView;
import net.spiffymap.datalog.shared.mvp.details.DetailDataView;
import net.spiffymap.datalog.shared.mvp.details.DetailSelectionView;
import net.spiffymap.datalog.shared.mvp.dialogs.DXFSettingsView;
import net.spiffymap.datalog.shared.mvp.dialogs.printing.PrinterSetupView;
import net.spiffymap.datalog.shared.mvp.drawing.DrawingView;
import net.spiffymap.datalog.shared.mvp.history.EditHistoryView;
import net.spiffymap.datalog.shared.mvp.information.CodesView;
import net.spiffymap.datalog.shared.mvp.information.InformationView;
import net.spiffymap.datalog.shared.mvp.information.NewsView;
import net.spiffymap.datalog.shared.mvp.logo.LogoView;
import net.spiffymap.datalog.shared.mvp.main.DatalogPresenter;
import net.spiffymap.datalog.shared.mvp.main.DatalogView;
import net.spiffymap.datalog.shared.mvp.problems.DXFCompleteView;
import net.spiffymap.datalog.shared.mvp.problems.ProblemsView;
import net.spiffymap.datalog.shared.mvp.settings.SettingsView;
import net.spiffymap.datalog.shared.mvp.stations.StationView;
import net.spiffymap.datalog.shared.mvp.stations.TraverseDisplayView;

/**
 *
 * @author steve
 * @version 8-Jan-2016
 */
public class DatalogFrame extends JFrame implements DatalogView {

    private DatalogPresenter presenter;
    private DrawingPanel drawingView;
    private DetailDataPanel detailDataView;
    private final DetailSelectionView detailSelectionView;
    private DetailCoordinatePanel detailCoordinateView;
    private StationPanel stationView;
    private final TraversesDialog traversesView;
    private final TraverseInputDialog traverseInputView;
    private SettingsPanel settingsView;
    private InformationPanel informationPanel;
    private final DXFSettingsView dxfSettingsView;
    private final NewsView newsView;
    private final ProblemsView problemsView;
    private LogoPanel logoPanel;
    private final CodesView codesView;
    private final TraverseDisplayView traverseDisplayView;
    private final DXFCompleteView dxfCompleteView;
    private final EditHistoryView editHistoryView;
    private final EditGuideDialog detailEditGuide = new EditGuideDialog(this);
    private final PrinterSetupView printerSetupDialog = new PrinterSetupDialog(this);

    /**
     * Creates new form DatalogFrame
     */
    public DatalogFrame() {
        initComponents();
        UIUtils.centreOnScreen(DatalogFrame.this);
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(Math.min(1600, size.width), 800);
        setTitle("Datalog X");
        drawingView = new DrawingPanel();
        detailDataView = new DetailDataPanel();
        detailSelectionView = new SelectionDialog(this);
        detailCoordinateView = new DetailCoordinatePanel();
        stationView = new StationPanel();
        traversesView = new TraversesDialog(this);
        traverseInputView = new TraverseInputDialog(this);
        settingsView = new SettingsPanel();
        informationPanel = new InformationPanel();
        newsView = new NewsDialog(this);
        problemsView = new ProblemsDialog(this);
        codesView = new CodesDialog(this);
        traverseDisplayView = new TraverseDisplayDialog(this);
        logoPanel = new LogoPanel();
        dxfCompleteView = new DXFCompleteDialog(this);
        dxfSettingsView = new DXFSettingsDialog(this);
        editHistoryView = new EditHistoryDialog(this);
        HelpView helpView = new HelpDialog(this);
        detailDataView.setHelpDialog(detailEditGuide);
        stationView.setHelpDialog(detailEditGuide);
        ObjectSupplier supplier = new SwingObjectSupplier(this, drawingView, detailDataView, detailSelectionView,
                detailCoordinateView, stationView, traversesView, traverseInputView, settingsView, dxfSettingsView,
                informationPanel, newsView, problemsView, logoPanel, dxfCompleteView, editHistoryView,
                new SwingDialogs(this), codesView, traverseDisplayView, helpView);
        MainModule.load(supplier);
        // Setup toolbars
        setupToolbars();
        // Hide both editing menus
        detailsMenu.setVisible(false);
        stationsMenu.setVisible(false);
        // Handle tab selection (needed to change menus)
        tabbedPane.addChangeListener(this::tabChanged);
        // Set frame icon
        ImageIcon image = new ImageIcon(getClass().getResource("/resources/appicon.png"));
        setIconImage(image.getImage());
        UIUtils.centreOnScreen(DatalogFrame.this);
        setVisible(true);
    }

    private void setupToolbars() {
        ToolbarManager.setup(buttonPanel, "/resources", new Settings("loadButton", "fileopen.png", "Load file", this::load));
    }

    @Override
    public void setupTabs() {
        // Add the panels
        tabbedPane.add(TAB.LOGO.getName(), logoPanel);
        tabbedPane.add(TAB.DETAIL_DATA.getName(), detailDataView);
        tabbedPane.add(TAB.DETAIL_COORDINATES.getName(), detailCoordinateView);
        tabbedPane.add(TAB.STATION.getName(), stationView);
        tabbedPane.add(TAB.DRAWING.getName(), drawingView);
        tabbedPane.add(TAB.INFORMATION.getName(), informationPanel);
        tabbedPane.addChangeListener(event -> {
            // Send hide to all editing views
            detailDataView.hideView();
            stationView.hideView();
            // Show a selected editing view
            Component component = tabbedPane.getSelectedComponent();
            if (component instanceof HelpDisplayingView) {
                ((View) component).showView();
            }
        });
    }

    /**
     * Enable or disable controls based on file status
     *
     * @param enable
     */
    @Override
    public void enableControls(boolean enable) {
        List<Component> controls = Arrays.asList(saveButton, DXFButton,
                saveMenuItem, saveAsMenuItem, detailsMenu, detailDataView,
                detailCoordinateView, stationView, drawingView);
        for (Component control : controls) {
            control.setEnabled(enable);
        }
    }

    /**
     * Respond to tab changes
     *
     * @param event
     */
    public void tabChanged(ChangeEvent event) {
        detailsMenu.setVisible(false);
        stationsMenu.setVisible(false);
        Component component = tabbedPane.getSelectedComponent();
        if (component == null) {
            return;
        }
        if (component.equals(detailDataView)) {
            detailEditGuide.setVisible(true);
            detailsMenu.setVisible(true);
        } else {
            detailEditGuide.setVisible(false);
            if (component.equals(stationView)) {
                stationsMenu.setVisible(true);
            } else if (component.equals(drawingView)) {
                // Setup settings buttons
                drawingView.showView();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        topPanel = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        loadButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        checkButton = new javax.swing.JButton();
        DXFButton = new javax.swing.JButton();
        printCoordinatesButton = new javax.swing.JButton();
        reportProblemButton = new javax.swing.JButton();
        editHistoryButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loadMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        saveAsDXFItem = new javax.swing.JMenuItem();
        separator1 = new javax.swing.JPopupMenu.Separator();
        plotItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        printSetupItem = new javax.swing.JMenuItem();
        printCoordinatesItem = new javax.swing.JMenuItem();
        separator2 = new javax.swing.JPopupMenu.Separator();
        checkMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        detailsMenu = new javax.swing.JMenu();
        addDetailItem = new javax.swing.JMenuItem();
        editStationItem = new javax.swing.JMenuItem();
        editLineCodeItem = new javax.swing.JMenuItem();
        editBearingItem = new javax.swing.JMenuItem();
        editDistanceItem = new javax.swing.JMenuItem();
        editVerticalAngleItem = new javax.swing.JMenuItem();
        editPrismHeightItem = new javax.swing.JMenuItem();
        editDeletedItem = new javax.swing.JMenuItem();
        editLevelKilledItem = new javax.swing.JMenuItem();
        editACodeItem = new javax.swing.JMenuItem();
        editBCodeItem = new javax.swing.JMenuItem();
        editCCodeItem = new javax.swing.JMenuItem();
        stationsMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        codesMenuItem = new javax.swing.JMenuItem();
        newsItem = new javax.swing.JMenuItem();
        aboutItem = new javax.swing.JMenuItem();
        helpItem = new javax.swing.JMenuItem();
        lineCodeUseItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Datalog");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tabbedPane.setName("tabbedPane"); // NOI18N
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });
        getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);

        topPanel.setVerifyInputWhenFocusTarget(false);
        topPanel.setLayout(new java.awt.BorderLayout());

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        loadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/fileopen.png"))); // NOI18N
        loadButton.setName("loadButton"); // NOI18N
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(loadButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/filesave.png"))); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(saveButton);

        checkButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/check.png"))); // NOI18N
        checkButton.setToolTipText("Save as DXF");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(checkButton);

        DXFButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/exportdx.png"))); // NOI18N
        DXFButton.setToolTipText("Save as DXF");
        DXFButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DXFButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(DXFButton);

        printCoordinatesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/print.png"))); // NOI18N
        printCoordinatesButton.setToolTipText("Print coordinates");
        printCoordinatesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printCoordinatesButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(printCoordinatesButton);

        reportProblemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/report_problem.png"))); // NOI18N
        reportProblemButton.setToolTipText("Report problem");
        reportProblemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportProblemButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(reportProblemButton);

        editHistoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/report.png"))); // NOI18N
        editHistoryButton.setMnemonic('E');
        editHistoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editHistoryButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(editHistoryButton);

        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/old/exit.png"))); // NOI18N
        exitButton.setToolTipText("Exit");
        exitButton.setName("exitButton"); // NOI18N
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(exitButton);

        topPanel.add(buttonPanel, java.awt.BorderLayout.NORTH);

        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);

        fileMenu.setText("File");

        loadMenuItem.setText("Load");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save as");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        saveAsDXFItem.setText("Save as DXF");
        saveAsDXFItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsDXFItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsDXFItem);
        fileMenu.add(separator1);

        plotItem.setText("Plot");
        fileMenu.add(plotItem);
        fileMenu.add(jSeparator1);

        printSetupItem.setText("Print setup");
        printSetupItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printSetupItemActionPerformed(evt);
            }
        });
        fileMenu.add(printSetupItem);

        printCoordinatesItem.setText("Print coordinates");
        printCoordinatesItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printCoordinatesItemActionPerformed(evt);
            }
        });
        fileMenu.add(printCoordinatesItem);
        fileMenu.add(separator2);

        checkMenuItem.setText("Check");
        checkMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(checkMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        detailsMenu.setText("Details");

        addDetailItem.setText("Add");
        detailsMenu.add(addDetailItem);

        editStationItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        editStationItem.setText("Station");
        detailsMenu.add(editStationItem);

        editLineCodeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, 0));
        editLineCodeItem.setText("Line code");
        detailsMenu.add(editLineCodeItem);

        editBearingItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, 0));
        editBearingItem.setText("Bearing");
        detailsMenu.add(editBearingItem);

        editDistanceItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, 0));
        editDistanceItem.setText("Distance");
        detailsMenu.add(editDistanceItem);

        editVerticalAngleItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, 0));
        editVerticalAngleItem.setText("Vertical angle");
        detailsMenu.add(editVerticalAngleItem);

        editPrismHeightItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, 0));
        editPrismHeightItem.setText("Prism height");
        detailsMenu.add(editPrismHeightItem);

        editDeletedItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, 0));
        editDeletedItem.setText("Deleted");
        detailsMenu.add(editDeletedItem);

        editLevelKilledItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, 0));
        editLevelKilledItem.setText("Level killed");
        detailsMenu.add(editLevelKilledItem);

        editACodeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, 0));
        editACodeItem.setText("A code");
        detailsMenu.add(editACodeItem);

        editBCodeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, 0));
        editBCodeItem.setText("B code");
        detailsMenu.add(editBCodeItem);

        editCCodeItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, 0));
        editCCodeItem.setText("C code");
        detailsMenu.add(editCCodeItem);

        menuBar.add(detailsMenu);

        stationsMenu.setText("Stations");
        menuBar.add(stationsMenu);

        helpMenu.setText("Help");

        codesMenuItem.setText("Line codes");
        codesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codesMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(codesMenuItem);

        newsItem.setText("News");
        newsItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newsItemActionPerformed(evt);
            }
        });
        helpMenu.add(newsItem);

        aboutItem.setText("About");
        aboutItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutItem);

        helpItem.setText("Help....");
        helpItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpItem);

        lineCodeUseItem.setText("Line code use");
        lineCodeUseItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineCodeUseItemActionPerformed(evt);
            }
        });
        helpMenu.add(lineCodeUseItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        load();
    }//GEN-LAST:event_loadMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        hideView();
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        presenter.save();
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        presenter.saveAs();
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        presenter.open();
    }//GEN-LAST:event_loadButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        shutdown();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        presenter.save();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void DXFButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DXFButtonActionPerformed
        presenter.saveDxf(Optional.of(presenter::afterDXF));
    }//GEN-LAST:event_DXFButtonActionPerformed

    private void saveAsDXFItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsDXFItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_saveAsDXFItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        shutdown();
    }//GEN-LAST:event_formWindowClosing

    private void codesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codesMenuItemActionPerformed
        presenter.showCodes();
    }//GEN-LAST:event_codesMenuItemActionPerformed

    private void aboutItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutItemActionPerformed
        JOptionPane.showMessageDialog(this, "Datalog X - version " + presenter.getVersion(), "About", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutItemActionPerformed

    private void printCoordinatesItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printCoordinatesItemActionPerformed
        printCoordinates();
    }//GEN-LAST:event_printCoordinatesItemActionPerformed

    private void printCoordinatesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printCoordinatesButtonActionPerformed
        printCoordinates();
    }//GEN-LAST:event_printCoordinatesButtonActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        Object source = evt.getSource();
        if (source instanceof JTabbedPane) {
            JTabbedPane tabbedPaneSource = (JTabbedPane) source;
            Component component = tabbedPaneSource.getSelectedComponent();
            if (component instanceof FocussingView) {
                ((FocussingView) component).focus();
            }
        }
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void checkMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkMenuItemActionPerformed
    }//GEN-LAST:event_checkMenuItemActionPerformed

    private void newsItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newsItemActionPerformed
        presenter.showNews();
    }//GEN-LAST:event_newsItemActionPerformed

    private void reportProblemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportProblemButtonActionPerformed
        presenter.showProblems();
    }//GEN-LAST:event_reportProblemButtonActionPerformed

    private void editHistoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editHistoryButtonActionPerformed
        presenter.showEditHistory();
    }//GEN-LAST:event_editHistoryButtonActionPerformed

    private void helpItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpItemActionPerformed
        presenter.showMainHelp();
    }//GEN-LAST:event_helpItemActionPerformed

    private void lineCodeUseItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineCodeUseItemActionPerformed
        presenter.showCodes();
    }//GEN-LAST:event_lineCodeUseItemActionPerformed

    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkButtonActionPerformed

    private void printSetupItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printSetupItemActionPerformed
        setupPrinter();
    }//GEN-LAST:event_printSetupItemActionPerformed

    @Override
    public void setThreeD(boolean threeD) {
    }

    private void printCoordinates() {
        // presenter.printCoordinates();
    }

    private void load() {
        presenter.open();
    }

    private void shutdown() {
        System.exit(0);
    }

    private void exit() {
        if (presenter.fileIsDirty()) {
            int result = JOptionPane.showConfirmDialog(this, "File is changed - continue to exit?", "Exit",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
        }
        hideView();
    }

    /**
     *
     * @param searchText
     * @return the class name of the LAG which contains the search text, or null
     * if not found.
     */
    private static String lafClassName(String searchText) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().contains(searchText)) {
                return info.getClassName();
            }
        }
        return null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Windows look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            String className = lafClassName("Nimbus");
            if (className == null) {
                return;
            }
            UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DatalogFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(() -> new DatalogFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DXFButton;
    private javax.swing.JMenuItem aboutItem;
    private javax.swing.JMenuItem addDetailItem;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton checkButton;
    private javax.swing.JMenuItem checkMenuItem;
    private javax.swing.JMenuItem codesMenuItem;
    private javax.swing.JMenu detailsMenu;
    private javax.swing.JMenuItem editACodeItem;
    private javax.swing.JMenuItem editBCodeItem;
    private javax.swing.JMenuItem editBearingItem;
    private javax.swing.JMenuItem editCCodeItem;
    private javax.swing.JMenuItem editDeletedItem;
    private javax.swing.JMenuItem editDistanceItem;
    private javax.swing.JButton editHistoryButton;
    private javax.swing.JMenuItem editLevelKilledItem;
    private javax.swing.JMenuItem editLineCodeItem;
    private javax.swing.JMenuItem editPrismHeightItem;
    private javax.swing.JMenuItem editStationItem;
    private javax.swing.JMenuItem editVerticalAngleItem;
    private javax.swing.JButton exitButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem helpItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem lineCodeUseItem;
    private javax.swing.JButton loadButton;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newsItem;
    private javax.swing.JMenuItem plotItem;
    private javax.swing.JButton printCoordinatesButton;
    private javax.swing.JMenuItem printCoordinatesItem;
    private javax.swing.JMenuItem printSetupItem;
    private javax.swing.JButton reportProblemButton;
    private javax.swing.JMenuItem saveAsDXFItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JButton saveButton;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPopupMenu.Separator separator1;
    private javax.swing.JPopupMenu.Separator separator2;
    private javax.swing.JMenu stationsMenu;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @param presenter the presenter to set
     */
    @Override
    public void setPresenter(DatalogPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public void hideView() {
        setVisible(false);
        drawingView.endAllDrawing();
        dispose();
    }

    /**
     * @param drawingView the drawingView to set
     */
    @Override
    public void setDrawingView(DrawingView drawingView) {
        this.drawingView = (DrawingPanel) drawingView;
    }

    /**
     * @param detailDataView the detailDataView to set
     */
    @Override
    public void setDetailDataView(DetailDataView detailDataView) {
        this.detailDataView = (DetailDataPanel) detailDataView;
    }

    /**
     * @param detailCoordinateView the detailCoordinateView to set
     */
    @Override
    public void setDetailCoordinateView(DetailCoordinateView detailCoordinateView) {
        this.detailCoordinateView = (DetailCoordinatePanel) detailCoordinateView;
    }

    /**
     * @param stationView the stationView to set
     */
    @Override
    public void setStationView(StationView stationView) {
        this.stationView = (StationPanel) stationView;
    }

    /**
     * @param settingsView the settingsView to set
     */
    @Override
    public void setSettingsView(SettingsView settingsView) {
        this.settingsView = (SettingsPanel) settingsView;
    }

    @Override
    public void showInformation() {
        tabbedPane.setSelectedIndex(TAB.INFORMATION.getIndex());
        informationPanel.enableControls(true);
    }

    /**
     * @return the presenter
     */
    @Override
    public DatalogPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void editDetails() {
        tabbedPane.setSelectedIndex(1);
    }

    @Override
    public void editStations() {
        tabbedPane.setSelectedIndex(3);
    }

    @Override
    public void setFileName(String fileName) {
        setTitle("Datalog X - " + fileName);

        // No action needed in this implementation
    }

    /**
     * @param logoView the logoView to set
     */
    @Override
    public void setLogoView(LogoView logoView) {
        logoPanel = (LogoPanel) logoView;
    }

    /**
     * @param informationView the informationView to set
     */
    @Override
    public void setInformationView(InformationView informationView) {
        this.informationPanel = (InformationPanel) informationView;
    }

    @Override
    public void switchToTab(TAB tab) {
        for (int tabIndex = 0; tabIndex < tabbedPane.getTabCount(); tabIndex++) {
            String title = tabbedPane.getTitleAt(tabIndex);
            if (title.equals(tab.getName())) {
                tabbedPane.setSelectedIndex(tabIndex);
                return;
            }
        }
    }

    @Override
    public Optional<String> getTestFileName() {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public Optional<String> getTestFileInput() {
        throw new IllegalArgumentException("Not implemented");
    }

    @Override
    public void setupPrinter() {
        presenter.setupPrinter();
    }
}
