package com.spiffymap.datalog.ui.mvp.printing;

import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import javax.swing.JDialog;
import net.spiffymap.datalog.shared.drawing.PrinterSettings;
import net.spiffymap.datalog.shared.mvp.dialogs.printing.PrinterSetupView;

/**
 *
 * @author steve
 */
public class PrinterSetupDialog extends JDialog implements PrinterSetupView {

    private boolean accepted;
    private PrinterSettings settings;

    /**
     * Creates new form PrinterSettingsDialog
     *
     * @param parent
     */
    public PrinterSetupDialog(Frame parent) {
        super(parent, true);
        initComponents();
        UIUtils.centreOnScreen(PrinterSetupDialog.this);
    }

    @Override
    public PrinterSettings getSettings() {
        return settings;
    }

    @Override
    public boolean isAccepted() {
        return accepted;
    }
    
    @Override
    public void showView(String title, PrinterSettings settings) {
        setTitle(title);
        this.settings = settings;
        update();
        setVisible(true);
    }
    
    /**
     * Set controls to values present in the settings
     */
    private void update() {
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scaleButtonGroup = new javax.swing.ButtonGroup();
        textSizeButtonGroup = new javax.swing.ButtonGroup();
        jCheckBox1 = new javax.swing.JCheckBox();
        gridLabel = new javax.swing.JLabel();
        minimumEastingField = new javax.swing.JTextField();
        minimumNorthingField = new javax.swing.JTextField();
        minimumEastingLabel = new javax.swing.JLabel();
        minimumNorthingLabel = new javax.swing.JLabel();
        whatToPlotPanel = new javax.swing.JPanel();
        plotDetailsCheckBox = new javax.swing.JCheckBox();
        plotStationsCheckBox = new javax.swing.JCheckBox();
        plotNumbersCheckBox = new javax.swing.JCheckBox();
        plotLevelsCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        smallRadioButton = new javax.swing.JRadioButton();
        mediumRadioButton = new javax.swing.JRadioButton();
        largeRadioButton = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        scaleComboBox = new javax.swing.JComboBox<>();
        oneInRadioButton = new javax.swing.JRadioButton();
        fullSizeRadioButton = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        decPlacesInLevelLabel = new javax.swing.JLabel();
        decimalPlacesInLevelsCombo = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        sequenceLabel = new javax.swing.JLabel();
        sequenceField = new javax.swing.JTextField();
        sequenceOfLabel = new javax.swing.JLabel();
        sequenceOfField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jCheckBox1.setText("Plot grid");

        gridLabel.setText("Grid");

        minimumEastingLabel.setText("Minimum easting");

        minimumNorthingLabel.setText("Minimum northing");

        whatToPlotPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        plotDetailsCheckBox.setText("Plot details");

        plotStationsCheckBox.setText("Plot stations");

        plotNumbersCheckBox.setText("Plot numbers");

        plotLevelsCheckBox.setText("Plot levels");

        javax.swing.GroupLayout whatToPlotPanelLayout = new javax.swing.GroupLayout(whatToPlotPanel);
        whatToPlotPanel.setLayout(whatToPlotPanelLayout);
        whatToPlotPanelLayout.setHorizontalGroup(
            whatToPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(whatToPlotPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(whatToPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plotDetailsCheckBox)
                    .addComponent(plotStationsCheckBox)
                    .addComponent(plotNumbersCheckBox)
                    .addComponent(plotLevelsCheckBox))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        whatToPlotPanelLayout.setVerticalGroup(
            whatToPlotPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(whatToPlotPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plotDetailsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plotStationsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plotNumbersCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plotLevelsCheckBox)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Text size"));

        textSizeButtonGroup.add(smallRadioButton);
        smallRadioButton.setText("Small");

        textSizeButtonGroup.add(mediumRadioButton);
        mediumRadioButton.setText("Medium");
        mediumRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediumRadioButtonActionPerformed(evt);
            }
        });

        textSizeButtonGroup.add(largeRadioButton);
        largeRadioButton.setText("Large");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(smallRadioButton)
                    .addComponent(mediumRadioButton)
                    .addComponent(largeRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(smallRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mediumRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(largeRadioButton)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Plotting scale"));

        scaleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        scaleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleComboBoxActionPerformed(evt);
            }
        });

        scaleButtonGroup.add(oneInRadioButton);
        oneInRadioButton.setText("1 in");

        scaleButtonGroup.add(fullSizeRadioButton);
        fullSizeRadioButton.setText("Full size");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(oneInRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scaleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fullSizeRadioButton)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scaleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oneInRadioButton)
                    .addComponent(fullSizeRadioButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        decPlacesInLevelLabel.setText("Decimal places in levels");

        decimalPlacesInLevelsCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(decPlacesInLevelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(decimalPlacesInLevelsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(decimalPlacesInLevelsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decPlacesInLevelLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setToolTipText("");

        sequenceLabel.setText("Sequence");

        sequenceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sequenceFieldActionPerformed(evt);
            }
        });

        sequenceOfLabel.setText("of");

        sequenceOfField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sequenceOfFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(sequenceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sequenceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sequenceOfLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sequenceOfField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sequenceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sequenceLabel)
                    .addComponent(sequenceOfLabel)
                    .addComponent(sequenceOfField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(gridLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(minimumEastingLabel)
                            .addComponent(minimumNorthingLabel))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(minimumEastingField)
                            .addComponent(minimumNorthingField, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(whatToPlotPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(442, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gridLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minimumEastingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimumEastingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(minimumNorthingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(minimumNorthingLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(whatToPlotPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void scaleComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scaleComboBoxActionPerformed

    private void mediumRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediumRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mediumRadioButtonActionPerformed

    private void sequenceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sequenceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sequenceFieldActionPerformed

    private void sequenceOfFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sequenceOfFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sequenceOfFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel decPlacesInLevelLabel;
    private javax.swing.JComboBox<String> decimalPlacesInLevelsCombo;
    private javax.swing.JRadioButton fullSizeRadioButton;
    private javax.swing.JLabel gridLabel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton largeRadioButton;
    private javax.swing.JRadioButton mediumRadioButton;
    private javax.swing.JTextField minimumEastingField;
    private javax.swing.JLabel minimumEastingLabel;
    private javax.swing.JTextField minimumNorthingField;
    private javax.swing.JLabel minimumNorthingLabel;
    private javax.swing.JRadioButton oneInRadioButton;
    private javax.swing.JCheckBox plotDetailsCheckBox;
    private javax.swing.JCheckBox plotLevelsCheckBox;
    private javax.swing.JCheckBox plotNumbersCheckBox;
    private javax.swing.JCheckBox plotStationsCheckBox;
    private javax.swing.ButtonGroup scaleButtonGroup;
    private javax.swing.JComboBox<String> scaleComboBox;
    private javax.swing.JTextField sequenceField;
    private javax.swing.JLabel sequenceLabel;
    private javax.swing.JTextField sequenceOfField;
    private javax.swing.JLabel sequenceOfLabel;
    private javax.swing.JRadioButton smallRadioButton;
    private javax.swing.ButtonGroup textSizeButtonGroup;
    private javax.swing.JPanel whatToPlotPanel;
    // End of variables declaration//GEN-END:variables
}