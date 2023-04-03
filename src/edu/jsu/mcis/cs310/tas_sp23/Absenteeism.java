package edu.jsu.mcis.cs310.tas_sp23;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

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