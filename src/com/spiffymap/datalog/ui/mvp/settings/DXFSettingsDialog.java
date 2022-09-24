package com.spiffymap.datalog.ui.mvp.settings;

import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import java.util.Optional;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import net.spiffymap.datalog.shared.mvp.dialogs.DXFSettingsView;
import net.spiffymap.datalog.shared.mvp.dialogs.DecimalPlaces;

/**
 *
 * @author steve
 */
public class DXFSettingsDialog extends JDialog implements DXFSettingsView {

    private volatile boolean accepted = false;
    private Runnable codeToRunIfSuccess;

    /**
     * Creates new form DXFScaleDialog
     *
     * @param parent
     */
    public DXFSettingsDialog(Frame parent) {
        super(parent, true);
        initComponents();
        setSize(300, 220);
        UIUtils.centreOnScreen(DXFSettingsDialog.this);
        ComboBoxModel scaleModel = new DefaultComboBoxModel(SCALES);
        scaleComboBox.setModel(scaleModel);
        ComboBoxModel placesModel = new DefaultComboBoxModel(DecimalPlaces.placeStrings());
        decimalPlacesCombo.setModel(placesModel);
    }

    @Override
    public void showView(Runnable codeToRunIfSuccess) {
        this.codeToRunIfSuccess = codeToRunIfSuccess;
        setVisible(true);
    }

    @Override
    public void setScale(Integer scale) {
        scaleComboBox.setSelectedItem(String.valueOf(scale));
    }

    @Override
    public Optional<Integer> getScale() {
        String selectedObject = (String) scaleComboBox.getSelectedItem();
        if (selectedObject == null) {
            return Optional.empty();
        } else {
            return Optional.of(Integer.parseInt(selectedObject));
        }
    }

    @Override
    public void setThreeD(boolean threeD) {
        threeDCheckBox.setSelected(threeD);
    }

    @Override
    public boolean isThreeD() {
        return threeDCheckBox.isSelected();
    }

    @Override
    public void setPlaces(DecimalPlaces place) {
        int count = decimalPlacesCombo.getItemCount();
        for (int index = 0; index < count; index++) {
            if (place.getDisplay().equals(decimalPlacesCombo.getItemAt(index))) {
                decimalPlacesCombo.setSelectedIndex(index);
                break;
            }
        }
    }

    @Override
    public Optional<DecimalPlaces> getPlaces() {
        String placesString = (String) decimalPlacesCombo.getSelectedItem();
        if (placesString == null) {
            return Optional.empty();
        } else {
            return Optional.of(DecimalPlaces.fromString(placesString));
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

        topPanel = new javax.swing.JPanel();
        scaleLabel = new javax.swing.JLabel();
        scaleComboBox = new javax.swing.JComboBox<>();
        bottomPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        centrePanel = new javax.swing.JPanel();
        centreMiddlePanel = new javax.swing.JPanel();
        threeDPanel = new javax.swing.JPanel();
        threeDCheckBox = new javax.swing.JCheckBox();
        decimalPlacesPanel = new javax.swing.JPanel();
        placesLabel = new javax.swing.JLabel();
        decimalPlacesCombo = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DXF Settings");
        setType(java.awt.Window.Type.UTILITY);

        topPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        scaleLabel.setText("Scale: 1 in ");
        topPanel.add(scaleLabel);

        scaleComboBox.setEditable(true);
        scaleComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        scaleComboBox.setToolTipText("Select the scale at which things should look their best");
        topPanel.add(scaleComboBox);

        getContentPane().add(topPanel, java.awt.BorderLayout.NORTH);

        bottomPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        bottomPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        bottomPanel.add(cancelButton);

        getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);

        centrePanel.setLayout(new java.awt.BorderLayout());

        centreMiddlePanel.setLayout(new java.awt.BorderLayout());

        threeDPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        threeDCheckBox.setText("Three D");
        threeDCheckBox.setToolTipText("Add level coordinate to drawing features");
        threeDPanel.add(threeDCheckBox);

        centreMiddlePanel.add(threeDPanel, java.awt.BorderLayout.NORTH);

        decimalPlacesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        placesLabel.setText("Decimal places");
        decimalPlacesPanel.add(placesLabel);

        decimalPlacesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        decimalPlacesCombo.setToolTipText("Number of decimal places to be shown in the text of levels ");
        decimalPlacesPanel.add(decimalPlacesCombo);

        centreMiddlePanel.add(decimalPlacesPanel, java.awt.BorderLayout.CENTER);

        centrePanel.add(centreMiddlePanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(centrePanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        accepted = true;
        SwingUtilities.invokeLater(codeToRunIfSuccess);
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel centreMiddlePanel;
    private javax.swing.JPanel centrePanel;
    private javax.swing.JComboBox<String> decimalPlacesCombo;
    private javax.swing.JPanel decimalPlacesPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel placesLabel;
    private javax.swing.JComboBox<String> scaleComboBox;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JCheckBox threeDCheckBox;
    private javax.swing.JPanel threeDPanel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the accepted
     */
    @Override
    public boolean isAccepted() {
        return accepted;
    }
}
