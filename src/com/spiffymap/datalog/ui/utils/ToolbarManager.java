package com.spiffymap.datalog.ui.utils;

import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

/**
 * Methods for building and maintaining toolbars
 *
 * @author steve
 */
public class ToolbarManager {

    /**
     * Set up toolbar
     *
     * @param toolbar
     * @param iconDirectory
     * @param parameters Toolbar settings - name (or null if space wanted), icon
     * file name tool tip text, method to be called, and so on repeating.
     */
    public static void setup(JComponent toolbar, String iconDirectory, Settings... parameters) {
        toolbar.removeAll();
        toolbar.validate();
        int buttonStart = 0;
        for (Settings settings : parameters) {
            if (settings == null) {
                continue;
            }
            JButton button = new JButton();
            button.setName(settings.getName());
            URL iconURL = ToolbarManager.class.getResource(iconDirectory + "/" + settings.getIconResource());
            ImageIcon icon = new ImageIcon(iconURL);
            int width = icon.getIconWidth() + 3;
            int height = icon.getIconWidth();
            button.setIcon(icon);
            button.setPreferredSize(new Dimension(width, height));
            button.setToolTipText(settings.getTooltipText());
            button.addActionListener(action -> settings.getActionCode().run());
            toolbar.add(button);
            button.setBounds(buttonStart, 0, width, height);
            buttonStart += width;
        }
    }

}
