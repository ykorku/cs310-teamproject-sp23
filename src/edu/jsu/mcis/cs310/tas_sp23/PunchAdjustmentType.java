package edu.jsu.mcis.cs310.tas_sp23;

/**
 * <p> Enumerated data type for the type of punch adjustment. </p>
 * @author Dalton Estes
 */
public enum PunchAdjustmentType {

    /**
     * <p> 0 in enumeration equating to none. </p>
     */
    NONE("None"),
    
    /**
     * <p> 1 in enumeration equating to shift start. </p>
     */
    SHIFT_START("Shift Start"),
    
    /**
     * <p> 2 in enumeration equating to shift stop. </p>
     */
    SHIFT_STOP("Shift Stop"),
    
    /**
     * <p> 3 in enumeration equating to shift dock. </p>
     */
    SHIFT_DOCK("Shift Dock"),
    
    /**
     * <p> 4 in enumeration equating to lunch start. </p>
     */
    LUNCH_START("Lunch Start"),
    
    /**
     * <p> 5 in enumeration equating to lunch stop. </p>
     */
    LUNCH_STOP("Lunch Stop"),
    
    /**
     * <p> 6 in enumeration equating to interval round. </p>
     */
    INTERVAL_ROUND("Interval Round");

    /**
     * <p> String variable for description. </p>
     */
    private final String description;

    /**
     * <p> Setter for punch adjustment type. </p>
     * @param d description
     */
    private PunchAdjustmentType(String d) {
        description = d;
    }

    /**
     * toString method returning description. </p>
     * @return description
     */
    @Override
    public String toString() {
        return description;
    }
}
