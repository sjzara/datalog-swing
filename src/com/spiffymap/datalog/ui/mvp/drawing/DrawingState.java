package com.spiffymap.datalog.ui.mvp.drawing;

/**
 * Scale and offsets of a drawing.
 *
 * @author steve
 */
public class DrawingState {

    private final double scale;
    private final int xOffset;
    private final int yOffset;

    public DrawingState(double scale, int xOffset, int yOffset) {
        this.scale = scale;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * @return the scale
     */
    public double getScale() {
        return scale;
    }

    /**
     * @return the xOffset
     */
    public int getxOffset() {
        return xOffset;
    }

    /**
     * @return the yOffset
     */
    public int getyOffset() {
        return yOffset;
    }

}
