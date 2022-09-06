package com.spiffymap.datalog.ui.mvp.stations;

import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import javax.swing.JDialog;
import net.spiffymap.datalog.shared.mvp.stations.TraverseDisplayView;

/**
 *
 * @author steve
 */
public class TraverseDisplayDialog extends JDialog implements TraverseDisplayView {

    /**
     * Creates new form TraverseDisplayDialog
     * @param parent
     */
    public TraverseDisplayDialog(Frame parent) {
        super(parent, true);
        initComponents();
        setSize(1000, 500);
        UIUtils.centreOnScreen(TraverseDisplayDialog.this);
    }

    @Override
    public void showText(String text) {
        textPane.setText(text);
        setVisible(true);
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
        closeButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Traverse");

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        textPane.setEditable(false);
        scrollPane.setViewportView(textPane);

        getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextPane textPane;
    // End of variables declaration//GEN-END:variables
}
