package edu.jsu.mcis.cs310.tas_sp23;

/**
 * <p> Enumerated data type for event type representing the type of punches. </p>
 * @author Dalton Estes
 */
public enum EventType {

    /**
     * <p> 0 in enumeration equating to a clock out. </p>
     */
    CLOCK_OUT("CLOCK OUT"),
    
    /**
     * <p> 1 in enumeration equating to a clock in. </p>
     */
    CLOCK_IN("CLOCK IN"),
    
    /**
     * <p> 2 in enumeration equating to a time out. </p>
     */
    TIME_OUT("TIME OUT");

    /**
     * <p> String variable for description. </p>
     */
    private final String description;

    /**
     * <p> Setter for event type. </p>
     * @param d description
     */
    private EventType(String d) {
        description = d;
    }

    /**
     * <p> toString method returning description. </p>
     * @return description
     */
    @Override
    public String toString() {
        return description;
    }

}
