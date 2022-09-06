package com.spiffymap.datalog.client.server;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * Functions for handling columns.
 *
 * @author steve
 * @version 06-Feb-2017
 */
public class ColumnUtils {

    /**
     * 
     * @param table
     * @param widths 
     */
    public static void setColumnWidths(JTable table, int... widths) {
        TableColumnModel columnModel = table.getColumnModel();
        for (int index = 0; index < widths.length; index++) {
            TableColumn column = columnModel.getColumn(index);
            column.setPreferredWidth(widths[index]);
            column.setMaxWidth(widths[index]);
            column.setMinWidth(widths[index]);
            column.setResizable(false);
        }
    }

}
