package edu.jsu.mcis.cs310.tas_sp23;

/**
 * <p> Enumerated data type representing the type of employee of variations temporary, 
 * part-time and full-time. </p>
 * @author Dalton Estes
 */
public enum EmployeeType {

    /**
     * <p> 0 in enumeration, equating to temporary or part-time. </p>
     */
    PART_TIME("Temporary / Part-Time"),
    
    /**
     * <p> 1 in enumeration, equating to full-time. </p>
     */
    FULL_TIME("Full-Time");
    
    /**
     * <p> String variable for description. </p>
     */
    private final String description;

    /**
     * <p> Setter for employee type. </p>
     * @param d description
     */
    private EmployeeType(String d) {
        description = d;
    }
    
    /**
     * <p> toString method returning description. </p>
     * @return 
     */
    @Override
    public String toString() {
        return description;
    }
    
}
