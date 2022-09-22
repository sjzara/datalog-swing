package com.spiffymap.datalog.ui.mvp.details;

import net.spiffymap.datalog.client.model.Detail;

/**
 *
 * @author steve
 */
public class DetailDataListModel extends DetailListModel {

    @Override
    public String detailToString(Detail detail) {
        return detail.printString(true);
    }

}
