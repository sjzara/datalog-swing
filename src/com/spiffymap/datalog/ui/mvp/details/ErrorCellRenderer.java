package com.spiffymap.datalog.ui.mvp.details;

import java.awt.Component;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author steve
 */
public class ErrorCellRenderer extends JLabel implements TableCellRenderer {

    public final static ImageIcon ERROR_ICON;
    public final static ImageIcon VALID_ICON;

    static {
        URL errorIconUrl = ErrorCellRenderer.class.getResource("/resources/error.png");
        ERROR_ICON = new ImageIcon(errorIconUrl, "Error");
        URL validIconUrl = ErrorCellRenderer.class.getResource("/resources/valid.png");
        VALID_ICON = new ImageIcon(validIconUrl, "Valid");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int rowIndex, int vColIndex) {
        if ((Boolean) value) {
            setIcon(ERROR_ICON);
        } else {
            setIcon(VALID_ICON);
        }
        return this;
    }
}
