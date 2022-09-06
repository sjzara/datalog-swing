package com.spiffymap.datalog.ui.mvp.history;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import net.spiffymap.datalog.shared.model.history.EditHistoryItem;

/**
 *
 * @author steve
 */
public class EditHistoryTableModel extends DefaultTableModel {

    private List<EditHistoryItem> editHistoryItems;

    /**
     * @param editHistoryItems the editHistoryItems to set
     */
    public void setEditHistoryItems(Deque<EditHistoryItem> editHistoryItems) {
        if (editHistoryItems != null) {
            this.editHistoryItems = new ArrayList<>(editHistoryItems);
        } else {
            this.editHistoryItems = new ArrayList<>();
        }
    }

    @Override
    public int getRowCount() {
        if (editHistoryItems == null) {
            return 0;
        } else {
            return editHistoryItems.size();
        }
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Time";
            case 1:
                return "Type";
            case 2:
                return "Number";
            case 3:
                return "Property";
            case 4:
                return "From";
            case 5:
                return "To";
            default:
                return "";
        }

    }

    @Override
    public Object getValueAt(int row, int column) {
        if (editHistoryItems == null) {
            return null;
        }
        EditHistoryItem item = editHistoryItems.get(row);
        switch (column) {
            case 0:
                return item.getTime().toString();
            case 1:
                return item.getClassName();
            case 2:
                return item.getNumber();
            case 3:
                return item.getField();
            case 4:
                return item.getOldValue();
            case 5:
                return item.getNewValue();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
