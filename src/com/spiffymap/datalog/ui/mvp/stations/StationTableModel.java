package com.spiffymap.datalog.ui.mvp.stations;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import com.spiffymap.geo.shared.Angle;
import net.spiffymap.datalog.client.model.Station;

/**
 *
 * @author steve
 * @version 07-Apr-2016
 */
public class StationTableModel extends DefaultTableModel {

    private List<Station> stations;

    public static String formatPosition(double value) {
        return String.format("%.3f", value);
    }

    public static String formatNumber(String numberString) {
        return String.format("%5s", numberString);
    }

    @Override
    public int getRowCount() {
        if (stations == null) {
            return 0;
        } else {
            return stations.size();
        }
    }

    @Override
    public int getColumnCount() {
        return 14;
    }

    private String getAngleOrBlank(Station station, Angle angle) {
        if (angle == null || !station.getHasData()) {
            return "";
        } else {
            return angle.toString();
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (stations == null) {
            return null;
        }
        if (row >= stations.size()) {
            return null;
        }
        Station station = stations.get(row);
        switch (column) {
            case 0:
                return formatNumber(station.getNumber());
            case 1:
                return formatNumber(station.getRo());
            case 2:
                return formatNumber(station.getVs());
            case 3:
                return formatPosition(station.getEasting());
            case 4:
                return formatPosition(station.getNorthing());
            case 5:
                return formatPosition(station.getLevel());
            case 6:
                return formatPosition(station.getInstrumentHeight());
            case 7:
                return getAngleOrBlank(station, station.getFaceLeft1());
            case 8:
                return getAngleOrBlank(station, station.getFaceRight1());
            case 9:
                return getAngleOrBlank(station, station.getFaceRight2());
            case 10:
                return getAngleOrBlank(station, station.getFaceLeft2());
            case 11:
                return getAngleOrBlank(station, station.getBearing());
            case 12:
                return formatPosition(station.getDiffHeight());
            case 13:
                return formatPosition(station.getDistance());
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Number";
            case 1:
                return "R.O.";
            case 2:
                return "V.S.";
            case 3:
                return "    Easting";
            case 4:
                return "    Northing";
            case 5:
                return "      Level";
            case 6:
                return "Inst. height";
            case 7:
                return "RO brng 1";
            case 8:
                return "VS brng 1";
            case 9:
                return "VS brng 2";
            case 10:
                return "RO brng 2";
            case 11:
                return "Bearing";
            case 12:
                return "Diff Ht";
            case 13:
                return "Distance";
            default:
                return null;
        }
    }

    /**
     *
     * @param row
     * @param column
     * @return false
     */
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class getColumnClass(int column) {
        return String.class;
    }

    /**
     * @param stations the stations to set
     */
    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    /**
     * @return the stations
     */
    public List<Station> getStations() {
        return stations;
    }

}
