package edu.jsu.mcis.cs310.tas_sp23;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Absenteeism {
    private final Employee employee;        // Employee object
    private final LocalDate payPeriodStart; // Pay period start
    private final BigDecimal bigDec;        // Percent absenteeism

    public Absenteeism(Employee employee, LocalDate payPeriodStart, BigDecimal bigDec) {
        this.employee = employee;
        this.payPeriodStart = payPeriodStart;
        this.bigDec = bigDec;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getPayPeriodStart() {
        return payPeriodStart;
    }

    public BigDecimal getBigDec() {
        return bigDec;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        
        
        return s.toString();
    }
}
