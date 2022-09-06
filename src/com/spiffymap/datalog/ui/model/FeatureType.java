package com.spiffymap.datalog.ui.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author steve
 */
public class FeatureType {

    public final static Map<String, FeatureType> FEATURE_TYPES = new HashMap<>();

    private static void addFeatureType(String code, String description, String aUse, String bUse) {
        FEATURE_TYPES.put(code, new FeatureType(code, description, aUse, bUse));
    }

    private static void setRoadSign(String code) {
        FEATURE_TYPES.get(code).setRoadSign(true);
    }

    private static void setHedgeType(String code) {
        FEATURE_TYPES.get(code).setHedgeType(true);
    }

    private static void setOffset(String code) {
        FEATURE_TYPES.get(code).setOffset(true);
    }

    private static void setPointCode(String code) {
        FEATURE_TYPES.get(code).setPointCode(true);
    }

    private static void setTextLabel(String code) {
        FEATURE_TYPES.get(code).setTextLabel(true);
    }

    static {
        addFeatureType("AG", "Aligned gulley", null, "Width");
        addFeatureType("RP", "Rain water pipe", null, null);
        addFeatureType("DK", "Drop kerb", "Link", "Width");
        addFeatureType("KB", "Kerb", "Link", "Width");
        addFeatureType("KC", "Curved kerb", "Link", "Width");
        addFeatureType("WL", "Wall", "Link", "Width");
        addFeatureType("WC", "Curved wall", "Link", "Width");
        addFeatureType("SE", "Surface edge", "Link", "Width");
        addFeatureType("SC", "Curved surface edge", "Link", "Width");
        addFeatureType("TR", "Tree", "Trunk radius", "Canopy radius");
        addFeatureType("BE", "Building edge", "Link", "Width");
        addFeatureType("MH", "Manhole", "Link", "Width");
        addFeatureType("CM", "Circular manhole", null, "Radius");
        addFeatureType("IC", "Inspection cover", "Link", "Width");
        addFeatureType("CP", "Centre post (point);", null, null);
        addFeatureType("RS", "Road sign", "Link", "Width");
        addFeatureType("NB", "Notice board", "Link", "Width");
        addFeatureType("NP", "Name plate", "Link", "Width");
        addFeatureType("SV", "Stop valve", null, null);
        addFeatureType("LB", "Letter box", null, null);
        addFeatureType("FH", "Fire hydrant", null, null);
        addFeatureType("PO", "Post", null, null);
        addFeatureType("GR", "Grass", null, null);
        addFeatureType("CO", "Concrete", null, null);
        addFeatureType("TA", "Tarmac", null, null);
        addFeatureType("HG", "Hedge", "Link", "Width");
        addFeatureType("TC", "Telephone cable", "Link", null);
        addFeatureType("EC", "Electricity cable", "Link", null);
        addFeatureType("BO", "Bollard", null, null);
        addFeatureType("OHR", "Tree/shrub overhang to the right", "Link", "Offset");
        addFeatureType("OHL", "Tree/shrub overhang to the left", "Link", "Offset");
        addFeatureType("SL", "Spot level", null, null);
        addFeatureType("MF", "Move forward (bearing, distance and VA are ignored)", "Link", "Distance");
        addFeatureType("ML", "Move left (bearing, distance and VA are ignored)", "Link", "Distance");
        addFeatureType("MR", "Move right (bearing, distance and VA are ignored)", "Link", "Distance");
        setHedgeType("HG");
        setRoadSign("RS");
        Arrays.asList("AG", "FH", "BO", "TP", "EP", "IP", "CM", "TR", "SL", "WV", "LP", "GV",
                "AV", "BB", "CH", "GU", "LB", "MP", "PM", "RE", "VP", "TS", "DP", "PO", "RP", "TO", "SV", "GR",
                "CO", "TA", "MF", "ML", "MR", "GA", "PA", "CG").forEach(FeatureType::setPointCode);
        Arrays.asList("OR", "OL", "OF", "OB").forEach(FeatureType::setOffset);
        setTextLabel("TX");
        setTextLabel("RN");
    }
    private String code;
    private boolean pointCode;
    private boolean roadSign;
    private boolean aLinked;
    private boolean offset;
    private boolean hedgeType;
    private boolean textLabel;
    private String description;
    private String aUse;
    private String bUse;

    public FeatureType(String code, String description, String aUse, String bUse) {
        this.code = code;
        this.description = description;
        this.aUse = aUse;
        this.bUse = bUse;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the pointCode
     */
    public boolean isPointCode() {
        return pointCode;
    }

    /**
     * @return the roadSign
     */
    public boolean isRoadSign() {
        return roadSign;
    }

    /**
     * @return the aLinked
     */
    public boolean isaLinked() {
        return aLinked;
    }

    /**
     * @return the offset
     */
    public boolean isOffset() {
        return offset;
    }

    /**
     * @return the hedgeType
     */
    public boolean isHedgeType() {
        return hedgeType;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the aUse
     */
    public String getaUse() {
        return aUse;
    }

    /**
     * @return the bUse
     */
    public String getbUse() {
        return bUse;
    }

    /**
     * @param pointCode the pointCode to set
     */
    public void setPointCode(boolean pointCode) {
        this.pointCode = pointCode;
    }

    /**
     * @param roadSign the roadSign to set
     */
    public void setRoadSign(boolean roadSign) {
        this.roadSign = roadSign;
    }

    /**
     * @param aLinked the aLinked to set
     */
    public void setaLinked(boolean aLinked) {
        this.aLinked = aLinked;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(boolean offset) {
        this.offset = offset;
    }

    /**
     * @param hedgeType the hedgeType to set
     */
    public void setHedgeType(boolean hedgeType) {
        this.hedgeType = hedgeType;
    }

    /**
     * @return the textLabel
     */
    public boolean isTextLabel() {
        return textLabel;
    }

    /**
     * @param textLabel the textLabel to set
     */
    public void setTextLabel(boolean textLabel) {
        this.textLabel = textLabel;
    }
}
