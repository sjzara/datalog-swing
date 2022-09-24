package com.spiffymap.datalog.ui.mvp.stations;

import com.spiffymap.server.ui.UIUtils;
import java.awt.Frame;
import java.util.List;
import javax.swing.JDialog;
import net.spiffymap.datalog.client.model.Traverse;
import net.spiffymap.datalog.shared.mvp.stations.TraversesPresenter;
import net.spiffymap.datalog.shared.mvp.stations.TraversesView;

/**
 *
 * @author steve
 */
public class TraversesDialog extends JDialog implements TraversesView {

    private TraversesPresenter presenter;
    private final TraversesTableModel tableModel = new TraversesTableModel();
    private boolean accepted;
    private List<Traverse> traverses;

    /**
     * Creates new form TraversesDialog
     *
     * @param parent
     */
    public TraversesDialog(Frame parent) {
        super(parent, true);
        initComponents();
        UIUtils.centreOnScreen(TraversesDialog.this);
        traversesTable.setModel(tableModel);
    }

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public void hideView() {
        setVisible(false);
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
        addButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        calculateButton = new javax.swing.JButton();
        bottomPanel = new javax.swing.JPanel();
        acceptButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        splitPane = new javax.swing.JSplitPane();
        scrollPane = new javax.swing.JScrollPane();
        traversesTable = new javax.swing.JTable();
        reportPanel = new javax.swing.JPanel();
        labelPanel = new javax.swing.JPanel();
        traverseReportLabel = new javax.swing.JLabel();
        reportScrollPane = new javax.swing.JScrollPane();
        reportText = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Traverses");

        topPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        topPanel.add(addButton);

        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        topPanel.add(deleteButton);

        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });
        topPanel.add(calculateButton);

        getContentPane().add(topPanel, java.awt.BorderLayout.PAGE_START);

        bottomPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });
        bottomPanel.add(acceptButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        bottomPanel.add(cancelButton);

        getContentPane().add(bottomPanel, java.awt.BorderLayout.SOUTH);

        splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        scrollPane.setPreferredSize(new java.awt.Dimension(400, 250));

        traversesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPane.setViewportView(traversesTable);

        splitPane.setLeftComponent(scrollPane);

        reportPanel.setLayout(new java.awt.BorderLayout());

        labelPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        traverseReportLabel.setText("Traverse report");
        labelPanel.add(traverseReportLabel);

        reportPanel.add(labelPanel, java.awt.BorderLayout.NORTH);

        reportScrollPane.setViewportView(reportText);

        reportPanel.add(reportScrollPane, java.awt.BorderLayout.CENTER);

        splitPane.setRightComponent(reportPanel);

        getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        presenter.add();
    }//GEN-LAST:event_addButtonActionPerformed

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_calculateButtonActionPerformed

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        accepted = true;
        setVisible(false);
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        accepted = false;
        setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton addButton;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JButton calculateButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel labelPanel;
    private javax.swing.JPanel reportPanel;
    private javax.swing.JScrollPane reportScrollPane;
    private javax.swing.JEditorPane reportText;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JPanel topPanel;
    private javax.swing.JLabel traverseReportLabel;
    private javax.swing.JTable traversesTable;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the accepted
     */
    @Override
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @return the traverses
     */
    @Override
    public List<Traverse> getTraverses() {
        return traverses;
    }

    /**
     * @param traverses the traverses to set
     */
    @Override
    public void setTraverses(List<Traverse> traverses) {
        this.traverses = traverses;
        tableModel.setTraverses(traverses);
        tableModel.fireTableDataChanged();
    }

    /**
     * @param presenter the presenter to set
     */
    @Override
    public void setPresenter(TraversesPresenter presenter) {
        this.presenter = presenter;
    }
}
