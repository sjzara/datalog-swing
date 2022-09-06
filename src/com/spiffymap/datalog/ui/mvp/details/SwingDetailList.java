package com.spiffymap.datalog.ui.mvp.details;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import net.spiffymap.datalog.client.model.DatalogFile;
import net.spiffymap.datalog.client.model.Detail;
import net.spiffymap.datalog.shared.mvp.details.DetailListView;

/**
 *
 * @author steve
 */
public interface SwingDetailList extends DetailListView {

    DetailListModel getListModel();

    JList getList();

    default void updateList(DatalogFile file) {
        List<Detail> details = file.getDetails();
        getListModel().setDetails(details);
    }

    /**
     *
     * @return indexes
     */
    @Override
    default List<Integer> getSelectedIndexes() {
        ListSelectionModel selectionModel = getList().getSelectionModel();
        List<Integer> selectedIndexes = new ArrayList<>();
        for (int index = 0; index < getListModel().getSize(); index++) {
            if (selectionModel.isSelectedIndex(index)) {
                selectedIndexes.add(index);
            }
        }
        return selectedIndexes;

    }

    /**
     *
     * @param startIndex
     * @param endIndex
     */
    @Override
    default void setSelectedIndexes(int startIndex, int endIndex) {
        ListSelectionModel selectionModel = getList().getSelectionModel();
        selectionModel.setSelectionInterval(startIndex, endIndex);
    }

    @Override
    default void updateDetails(List<Integer> indexes, List<Detail> details) {
        DetailListView.super.updateDetails(indexes, details);
        // Restore selection
        ListSelectionModel selectionModel = getList().getSelectionModel();
        indexes.forEach(index -> selectionModel.setSelectionInterval(index, index));
    }

    @Override
    default void gotoListIndex(int index) {
        getList().scrollRectToVisible(getList().getCellBounds(index, index));
        getList().getSelectionModel().setSelectionInterval(index, index);
        getList().requestFocus();
    }

}
