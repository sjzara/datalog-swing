package com.spiffymap.datalog.ui.model;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 * JTable which sets its size based on examples of content
 *
 * @author steve
 * @version 10-Apr-2016
 */
public class AutoSizingTable extends JTable {

    private Map<Integer, String> columnContentExamples;
    private boolean sized = false;

    /**
     * @param columnContentExamples the columnContentExamples to set
     */
    public void setColumnContentExamples(Map<Integer, String> columnContentExamples) {
        this.columnContentExamples = columnContentExamples;
    }

    /**
     *
     * @param g
     */
    private void setColumnWidths(Graphics g) {
        for (int column : columnContentExamples.keySet()) {
            String contentExample = columnContentExamples.get(column);
            setColumnWidth(g, column, contentExample);
        }
    }

    /**
     * Set a column width based on an example of strings displayed
     *
     * @param g graphics
     * @param column
     * @param typicalString
     */
    private void setColumnWidth(Graphics g, int column, String typicalString) {
        FontMetrics metrics = g.getFontMetrics();
        int dataWidth = metrics.stringWidth(typicalString);
        int nameWidth = metrics.stringWidth(getColumnName(column));
        int maxWidth = Math.max(dataWidth, nameWidth);
        TableColumn tableColumn = getColumnModel().getColumn(column);
        tableColumn.setResizable(true);
        tableColumn.setMinWidth(maxWidth + 10);
        tableColumn.setWidth(maxWidth + 10);
        tableColumn.setPreferredWidth(maxWidth + 10);
    }

    @Override
    public void paint(Graphics g) {
        if (!sized) {
            if (columnContentExamples != null) {
                setColumnWidths(g);
                sized = true;
            }
        }
        super.paint(g);
    }

}
