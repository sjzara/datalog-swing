package com.spiffymap.datalog.ui.mvp.details;

import net.spiffymap.datalog.client.model.Detail;

/**
 *
 * @author steve
 */
public class DetailCoordinateListModel extends DetailListModel {

    @Override
    public String detailToString(Detail detail) {
        return detail.printString(false);
    }

}
