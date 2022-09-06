package com.spiffymap.datalog.ui.utils;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author steve
 * @version 16-Mar-2017
 */
public class RightCellRenderer extends DefaultTableCellRenderer {

    public RightCellRenderer() {
        setHorizontalAlignment(JLabel.RIGHT);
    }
    
    /**
     * 
     * @param table
     * @param column 
     */
    public static void setRightAlignment(JTable table, int column) {
        table.getColumnModel().getColumn(column).setCellRenderer(new RightCellRenderer());
    }
}
