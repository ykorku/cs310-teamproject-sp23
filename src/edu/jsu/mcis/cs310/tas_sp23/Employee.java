package edu.jsu.mcis.cs310.tas_sp23;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p> The Employee class represents an employee in the company using its id, 
 * first name, last name, middle name, active date, badge, department, shift, and 
 * employee type. This class provides getter methods to
 * access these fields. </p>
 * @author Dalton Estes
 */
public class Employee {
    
    
    /**
    * <p> An integer variable representing an employee's id number. </p>
    */
    private final int id;
    
    /**
     * <p> String variables representing an employee's first, middle, and last name. </p>
     */
    private final String firstname, lastname, middlename;
    
    /**
     * <p> A LocalDateTime variable representing the date of activity for an
     * employee. </p>
     */
    private final LocalDateTime active;
    
    /**
     * <p> A Badge object representing an employee's badge. </p>
     */
    private final Badge badge;
    
    /**
     * <p> A department object representing an employee's department. </p>
     */
    private final Department depart;
    
    /**
     * <p> A shift object representing an employee's shift. </p>
     */
    private final Shift shift;
    
    /**
     * <p> An EmployeeType object representing an employee's type, such as
     * temporary, part-time, or full-time. </p>
     */
    private final EmployeeType etype;

    /**
     * <p> Constructs an Employee object. </p>
     * @param id the employee's id
     * @param firstname the employee's first name
     * @param lastname the employee's last name
     * @param middlename the employee's middle name
     * @param active the date and time the employee was activated
     * @param badge the employee's badge
     * @param depart the department the employee is associated with
     * @param shift the shift the employee is assigned to
     * @param etype the employee's type
     */
    public Employee(int id, String firstname, String lastname,
            String middlename, LocalDateTime active, Badge badge,
            Department depart, Shift shift, EmployeeType etype) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.active = active;
        this.badge = badge;
        this.depart = depart;
        this.shift = shift;
        this.etype = etype;
    }
    
    /**
     * <p> Returns the employee's id. </p>
     * @return the employee's id
     */
    public int getId() {
        return id;
    }
    
    /**
     * <p> Returns the employee's first name. </p>
     * @return the employee's first name
     */
    public String getFirstname() {
        return firstname;
    }
    
    /**
     * <p> Returns the employee's middle name. </p>
     * @return the employee's middle name
     */
    public String getMiddlename() {
        return firstname;
    }
    
    /**
     * <p> Returns the employee's last name. </p>
     * @return the employee's last name
     */
    public String getLastname() {
        return lastname;
    }
    
    /**
     * <p> Returns the date and time the employee was activated. </p>
     * @return the date and time the employee was activated
     */
    public LocalDateTime getActive() {
        return active;
    }
    
    
    /**
     * <p> Returns the employee's badge. </p>
     * @return the employee's badge
     */
    public Badge getBadge() {
        return badge;
    }
    
    /**
     *<p> Returns the department the employee is associated with. </p>
     * @return the department the employee is associated with
     */
    public Department getDepartment() {
        return depart;
    }
    
    /**
     * <p> Return the shift an employee is assigned to. </p>
     * @return shift
     */
    public Shift getShift() {
        return shift;
    }

    /**
     * <p> Returns the employee's type. </p>
     * @return the employee's type
     */
    public EmployeeType getEtype() {
        return etype;
    }
    
    /**
     * <p> Returns a string representation of the employee object. </p>
     * @return a string representation of the employee object
     */
    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateText = active.format(formatter);
        
        StringBuilder s = new StringBuilder();

        s.append("ID #").append(id).append(": ");
        s.append(lastname).append(", ").append(firstname).append(" ").append(middlename).append(" ");
        s.append("(#").append(badge.getId()).append("), Type: ").append(etype).append(", ");
        s.append("Department: ").append(depart.getDescription()).append(", Active: ").append(dateText);

        return s.toString();
    }
}
