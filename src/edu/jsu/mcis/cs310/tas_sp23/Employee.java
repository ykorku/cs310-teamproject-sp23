package edu.jsu.mcis.cs310.tas_sp23;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Employee {
    
     private final int id ;
     private final String firstname , lastname , middlename ;
     private final  LocalDateTime active ;
     private final  Badge  badge;
     private final  Department  depart ;
     private final  Shift  shift ;
     private final  EmployeeType  etype ;

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
     
    public int getId() {
        return id;
    }
    
    public String getFirstname() {
        return firstname;
    }
    
    public String getMiddlename() {
        return firstname;
    }
    
    public String getLastname() {
        return lastname;
    }
    
    public LocalDateTime getActive() {
        return active;
    }
    
    public Badge getBadge() {
        return badge;
    }
    
    public Department getDepartment() {
        return depart;
    }
    
    public Shift getShift() {
        return shift;
    }

    public EmployeeType getEtype() {
        return etype;
    }
    
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
