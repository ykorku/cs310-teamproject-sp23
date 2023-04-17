package edu.jsu.mcis.cs310.tas_sp23;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * <p> The Absenteeism class represents an employee's absenteeism in the company using 
 * an employee object, payPeriodStart localDate, and a BigDecimal bigDec representing 
 * percent absenteeism. This class provides getter methods to
 * access these fields. </p>
 * @author Dalton Estes
 */
public class Absenteeism {
    
    /**
     * <p> employee is an Employee object representing a specific employee with their 
     * first name, last name, middle name, active status, badge, department, shift 
     * and employee type. </p>
     */
    private final Employee employee;        // Employee object
    
    /**
     * <p> payPeriodStart is a LocalDate variable representing the start of an employee's pay period. </p>
     */
    private final LocalDate payPeriodStart; // Pay period start
    
    /**
     * <p> bigDec is a BigDecimal value used to represent the absenteeism of an 
     * employee as a percentage. </p>
     */
    private final BigDecimal bigDec;        // Percent absenteeism

    
    /**
     * <p> Constructs an Absenteeism object. </p>
     * @param employee the employee object representing the employee
     * @param payPeriodStart the employee's payPeriodStart
     * @param bigDec the employee's absenteeism as a percentage
     */
    public Absenteeism(Employee employee, LocalDate payPeriodStart, BigDecimal bigDec) {
        this.employee = employee;
        this.payPeriodStart = payPeriodStart;
        this.bigDec = bigDec;
    }

    /**
     * <p> Returns the employee object. </p>
     * @return the employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * <p> Returns the employee's pay period start. </p>
     * @return the employee's pay period start
     */
    public LocalDate getPayPeriodStart() {
        return payPeriodStart;
    }

    /**
     * <p> Returns the employee's absenteeism represented as a percentage. </p>
     * @return the employee's absenteeism represented as a percentage
     */
    public BigDecimal getBigDec() {
        return bigDec;
    }

    /**
     * <p> Returns a string representation of the absenteeism object. </p>
     * @return a string representation of the absenteeism object
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String dateText = payPeriodStart
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
                .format(formatter);
        String percent = String.format("%.2f", bigDec.floatValue());
        
        s.append('#').append(employee.getBadge().getId()).append(' ');
        s.append("(Pay Period Starting ").append(dateText).append("): ");
        s.append(percent).append("%");
        
        return s.toString();
    }
}