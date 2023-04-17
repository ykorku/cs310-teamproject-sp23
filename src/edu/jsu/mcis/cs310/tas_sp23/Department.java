package edu.jsu.mcis.cs310.tas_sp23;

/**
 * <p> The Department class represents a department in the company, including its
 * ID, description, and terminal ID. This class provides getter methods to
 * access these fields. </p>
 * @author Dalton Estes
 */
public class Department {
    
    /**
     * <p> String variable representing an employee description. </p>
     */
    private final String description;
    
    /**
     * <p> Integer variables representing an employee id and terminal. </p>
     */
    private final int id, terminalid;
    
    /**
     * <p> Constructs a new Department object with the specified ID, description,
     * and terminal ID. </p>
     * @param id the department's ID
     * @param description the department's description
     * @param terminalid the ID of the department's terminal
     */
    public Department(int id, String description, int terminalid){
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    /**
     * <p> Returns the department's ID. </p>
     * @return the department's ID
     */
    public int getId(){
        return id;
    }
    
    /**
     * <p> Returns the department's description. </p>
     * @return the department's description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * <p> Returns the ID of the department's terminal. </p>
     * @return the ID of the department's terminal
     */
    public int getTerminalid(){
        return terminalid;
    }
    
    /**
     * <p> Returns a string representation of the Department object, including 
     * its ID, description, and terminal ID. </p>
     * @return a string representation of the Department object
     */
    @Override
    public String toString(){
        
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');
        s.append(", Terminal ID: ").append(terminalid);
        
        return s.toString();
    }
}
