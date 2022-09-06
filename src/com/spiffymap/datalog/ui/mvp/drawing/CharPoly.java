package com.spiffymap.datalog.ui.mvp.drawing;

/**
 * A rectangle which is the bounds of a character.
 *
 * @author steve
 */
public class CharPoly {

    private final double point1x;
    private final double point1y;
    private final double point2x;
    private final double point2y;
    private final double point3x;
    private final double point3y;
    private final double point4x;
    private final double point4y;
    // Parameters of minimum enclosing circle - used +
    private double centreX;
    private double centreY;
    private double radius;
    

    public CharPoly(double point1x, double point1y, double point2x, double point2y,
            double point3x, double point3y, double point4x, double point4y) {
        this.point1x = point1x;
        this.point1y = point1y;
        this.point2x = point2x;
        this.point2y = point2y;
        this.point3x = point3x;
        this.point3y = point3y;
        this.point4x = point4x;
        this.point4y = point4y;
        calculateBoundingCircle();
    }
    
    private void calculateBoundingCircle() {
        centreX = (point1x + point2x + point3x + point4x) / 4;
        centreY = (point1y + point2y + point3y + point4y) / 4;
        double dx1 = centreX - point1x;
        double dy1 = centreY - point1y;
        double dx2 = centreX - point2x;
        double dy2 = centreY - point2y;
        double dx3 = centreX - point3x;
        double dy3 = centreY - point3y;
        double dx4 = centreX - point4x;
        double dy4 = centreY - point4y;
        double maxDistSquared = Math.max(dx1*dx1 +dy1*dy1, Math.max(dx2*dx2 + dy2*dy2, 
                Math.max(dx3*dx3 + dy3*dy3, dx4*dx4 + dy4*dy4)));
        radius = Math.sqrt(maxDistSquared);
    }

    /**
     * @return the centreX
     */
    public double getCentreX() {
        return centreX;
    }

    /**
     * @return the centreY
     */
    public double getCentreY() {
        return centreY;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * Initial check for polygons being close enough for possible intersection.
     * @param other
     * @return result of check
     */
    public boolean intersectingByCircles(CharPoly other) {
        double otherCentreX = other.getCentreX();
        double otherCentreY = other.getCentreY();
        double otherRadius = other.getRadius();
        double dx = centreX - otherCentreX;
        double dy = centreY- otherCentreY;
        double radiusSum = radius + otherRadius;
        return radiusSum * radiusSum < dx * dx + dy * dy;
    } 
    
    /**
     * Full check for polygon intersection.
     * @param other
     * @return result of check
     */
    public boolean intersectsByShape(CharPoly other) {
        return false;      
    }
 }