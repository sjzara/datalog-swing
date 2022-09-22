package com.spiffymap.datalog.ui.utils;

/**
 *
 * @author steve
 */
public class Settings {

    private final String name;
    private final String iconResource;
    private final String tooltipText;
    private final Runnable actionCode;

    public Settings(String name, String iconResource, String tooltipText, Runnable actionCode) {
        this.name = name;
        this.iconResource = iconResource;
        this.tooltipText = tooltipText;
        this.actionCode = actionCode;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the iconResource
     */
    public String getIconResource() {
        return iconResource;
    }

    /**
     * @return the tooltipText
     */
    public String getTooltipText() {
        return tooltipText;
    }

    /**
     * @return the actionCode
     */
    public Runnable getActionCode() {
        return actionCode;
    }

}
