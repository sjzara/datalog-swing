package com.spiffymap.datalog.ui.model;

/**
 * Value formatting for display.
 * @author steve
 */
public class Formatting {
    
    public static String formatDistance(double value) {
        return String.format("%1$f0.3", value);
    }    
}
