package com.spiffymap.datalog.ui.mvp.details;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import net.spiffymap.datalog.client.model.Detail;

/**
 *
 * @author steve
 */
public abstract class DetailListModel implements ListModel {

    private List<Detail> details;
    private final Set<ListDataListener> listeners = new HashSet<>();

    @Override
    public void removeListDataListener(ListDataListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addListDataListener(ListDataListener listener) {
        listeners.add(listener);
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public abstract String detailToString(Detail detail);

    @Override
    public String getElementAt(int index) {
        return detailToString(getDetails().get(index));
    }

    @Override
    public int getSize() {
        if (details == null) {
            return 0;
        } else {
            return details.size();
        }
    }

    public ListDataListener[] getListDataListeners() {
        return listeners.toArray(new ListDataListener[]{});
    }

}
