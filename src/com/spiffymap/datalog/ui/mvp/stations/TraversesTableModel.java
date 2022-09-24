package com.spiffymap.datalog.ui.mvp.stations;

import com.spiffymap.geo.shared.geom.CoordinateDouble;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import net.spiffymap.datalog.client.model.Traverse;
import static net.spiffymap.datalog.client.model.Traverse.COORD_FORMAT;

/**
 *
 * @author steve
 */
public class TraversesTableModel extends DefaultTableModel {

    private List<Traverse> traverses;
    private Map<Integer, CoordinateDouble> lastCoordinates;
    private Map<Integer, CoordinateDouble> coordinateErrors;

    /**
     * @param traverses the traverses to set
     */
    public void setTraverses(List<Traverse> traverses) {
        this.traverses = traverses;
        findCoordinates();
    }

    /**
     * Build maps of final traverse coordinates and errors for each traverse.
     */
    private void findCoordinates() {
        lastCoordinates = new HashMap<>();
        coordinateErrors = new HashMap<>();
        for (int row = 0; row < getRowCount(); row++) {
            Traverse traverse = traverses.get(row);
            LinkedHashMap<String, CoordinateDouble> calculatedCoordinates = traverse.getCalculatedCoordinates();
            if (calculatedCoordinates != null && !calculatedCoordinates.isEmpty()) {
                List<String> allKeys = new ArrayList<>(calculatedCoordinates.keySet());
                CoordinateDouble lastCoords = calculatedCoordinates.get(allKeys.get(allKeys.size() - 1));
                lastCoordinates.put(row, lastCoords);
            }
            Double eastingError = traverse.getEastingError();
            Double northingError = traverse.getNorthingError();
            if (eastingError != null && northingError != null) {
                coordinateErrors.put(row, new CoordinateDouble(eastingError, northingError));
            }
        }
    }

    @Override
    public int getRowCount() {
        if (traverses == null) {
            return 0;
        } else {
            return traverses.size();
        }
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (row >= traverses.size()) {
            return null;
        }
        Traverse traverse = traverses.get(row);
        CoordinateDouble finalCoords = lastCoordinates.get(row);
        CoordinateDouble coordErrors = coordinateErrors.get(row);
        switch (column) {
            case 0:
                return row;
            case 1:
                return traverse.getStart();
            case 2:
                return traverse.getEnd();
            case 3:
                if (finalCoords != null) {
                    return String.format(COORD_FORMAT, finalCoords.getX());
                } else {
                    return "";
                }
            case 4:
                if (finalCoords != null) {
                    return String.format(COORD_FORMAT, finalCoords.getY());
                } else {
                    return "";
                }
            case 5:
                if (coordErrors != null) {
                    return String.format(COORD_FORMAT, coordErrors.getX());
                } else {
                    return "";
                }
            case 6:
                if (coordErrors != null) {
                    return String.format(COORD_FORMAT, coordErrors.getY());
                } else {
                    return "";
                }
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Number";
            case 1:
                return "Start stn";
            case 2:
                return "End stn";
            case 3:
                return "Close E";
            case 4:
                return "Close N";
            case 5:
                return "E error";
            case 6:
                return "N error";
            default:
                return "";
        }
    }

}
