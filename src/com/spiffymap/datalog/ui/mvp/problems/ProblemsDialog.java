package com.spiffymap.datalog.ui.mvp.problems;

import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import javax.swing.JDialog;
import net.spiffymap.datalog.shared.mvp.problems.ProblemsPresenter;
import net.spiffymap.datalog.shared.mvp.problems.ProblemsView;

/**
 *
 * @author steve
 */
public class ProblemsDialog extends JDialog implements ProblemsView {

    private ProblemsPresenter presenter;

    /**
     * Creates new form ProblemsDialog
     *
     * @param parent
     */
    public ProblemsDialog(Frame parent) {
        super(parent, true);
        initComponents();
        setSize(600, 400);
        UIUtils.centreOnScreen(ProblemsDialog.this);
    }

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public void hideView() {
        setVisible(false);
    }

    @Override
    public void setDescription(String description) {
        problemText.setText(description);
    }

    @Override
    public void setText(String text) {
        editorPane.setText(text);
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
        sendButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        splitPane = new javax.swing.JSplitPane();
        bottomPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        editorPane = new javax.swing.JEditorPane();
        bottomLabelPanel = new javax.swing.JPanel();
        logLabel = new javax.swing.JLabel();
        topPanel = new javax.swing.JPanel();
        topLabelPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();
        problemScrollPane = new javax.swing.JScrollPane();
        problemText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Problems");

        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(sendButton);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        bottomPanel.setLayout(new java.awt.BorderLayout());

        editorPane.setEditable(false);
        scrollPane.setViewportView(editorPane);

        bottomPanel.add(scrollPane, java.awt.BorderLayout.CENTER);

        bottomLabelPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        logLabel.setText("Event log");
        bottomLabelPanel.add(logLabel);

        bottomPanel.add(bottomLabelPanel, java.awt.BorderLayout.NORTH);

        splitPane.setBottomComponent(bottomPanel);

        topPanel.setLayout(new java.awt.BorderLayout());

        topLabelPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        descriptionLabel.setText("Description of problem");
        topLabelPanel.add(descriptionLabel);

        topPanel.add(topLabelPanel, java.awt.BorderLayout.NORTH);

        problemText.setColumns(20);
        problemText.setRows(5);
        problemScrollPane.setViewportView(problemText);

        topPanel.add(problemScrollPane, java.awt.BorderLayout.CENTER);

        splitPane.setLeftComponent(topPanel);

        getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        hideView();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        presenter.sendProblem(editorPane.getText(), problemText.getText());
    }//GEN-LAST:event_sendButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomLabelPanel;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JEditorPane editorPane;
    private javax.swing.JLabel logLabel;
    private javax.swing.JScrollPane problemScrollPane;
    private javax.swing.JTextArea problemText;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton sendButton;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JPanel topLabelPanel;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @param presenter the presenter to set
     */
    @Override
    public void setPresenter(ProblemsPresenter presenter) {
        this.presenter = presenter;
    }
}
