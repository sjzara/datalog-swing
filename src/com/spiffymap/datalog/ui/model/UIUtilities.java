package com.spiffymap.datalog.ui.model;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author steve
 */
public class UIUtilities {

    /**
     *
     * @param titles
     * @param actions
     * @return menu
     */
    public static JPopupMenu buildPopupMenu(String[] titles, Runnable[] actions) {
        JPopupMenu menu = new JPopupMenu();
        for (int index = 0; index < titles.length; index++) {
            JMenuItem menuItem = new JMenuItem();
            menuItem.setText(titles[index]);
            Runnable action = actions[index];
            menuItem.addActionListener(event -> action.run());
            menu.add(menuItem);
        }
        return menu;
    }
}
