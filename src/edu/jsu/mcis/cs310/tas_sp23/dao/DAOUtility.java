package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import java.math.BigDecimal;

/**
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 */
public final class DAOUtility {
    
    public BigDecimal calculateAbsenteeism(ArrayList<Punch> punchList, Shift shift) {
        
        BigDecimal totalMins = new BigDecimal(calculateTotalMinutes(punchList, shift));
        Duration schedule;
        
        
        if (shift.getShiftstart().isBefore(shift.getShiftstop())) {
            schedule = Duration.between(shift.getShiftstart(), 
                    shift.getShiftstop());
        } else {
            schedule = Duration.ofHours(24).minus(Duration
                    .between(shift.getShiftstart(),
                            shift.getShiftstop()));
        }
        
        return totalMins.divide(new BigDecimal(schedule.toMinutes()));
    }
    
    public int calculateTotalMinutes(ArrayList<Punch> punchList, Shift shift) {
        
        return 20;
    }
}
