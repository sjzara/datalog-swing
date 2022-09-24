package com.spiffymap.datalog.ui.mvp.main;

import javax.swing.JOptionPane;

/**
 *
 * @author steve
 */
public class SwingMessageDisplay {

    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, "Message", message, JOptionPane.PLAIN_MESSAGE);
    }

    public static void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, "Error", errorMessage, JOptionPane.ERROR_MESSAGE);
    }
}
