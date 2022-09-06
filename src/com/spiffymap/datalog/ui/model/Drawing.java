package com.spiffymap.datalog.ui.model;

/**
 * Drawing information
 *
 * @author steve
 */
public class Drawing {

    private double scale;
    private double offsetX;
    private double offsetY;
    private double minX = Double.MAX_VALUE;
    private double maxX = -Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxY = -Double.MAX_VALUE;

    public Drawing() {
    }

    public void accumulateXY(double x, double y) {
        if (x < minX) {
            minX = x;
        }
        if (x > maxX) {
            maxX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (y > maxY) {
            maxY = y;
        }
    }

    /**
     * Set scaling to fit these ranges. Scaling is done so that the drawing
     * shape is not changed and will fit the smallest dimension of the ranges.
     *
     * @param scaleX1
     * @param scaleY1
     * @param scaleX2
     * @param scaleY2
     */
    public void scaleTo(double scaleX1, double scaleY1, double scaleX2, double scaleY2) {
        double xScale = 0;
        if (maxX != minX) {
            xScale = (scaleX2 - scaleX1) / (maxX - minX);
        }
        double yScale = 0;
        if (maxY != minY) {
            yScale = (scaleY2 - scaleY1) / (maxY - minY);
        }
        scale = Math.min(xScale, yScale);
        offsetX = scaleX1 - scale * minX;
        offsetY = scaleY1 - scale * minY;
    }

    /**
     * Scale a point given as x & y.
     *
     * @param x
     * @param y
     * @return scaled point
     */
    public Coordinate scalePoint(double x, double y) {
        return new Coordinate(x * scale + offsetX, y * scale + offsetY);
    }

    /**
     * Scale a point given as a Coordinate
     *
     * @param coord
     * @return scaled point
     */
    public Coordinate scalePoint(Coordinate coord) {
        return new Coordinate(coord.getX(), coord.getY());
    }
}
