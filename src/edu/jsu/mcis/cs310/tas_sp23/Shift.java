package edu.jsu.mcis.cs310.tas_sp23;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

/**
 *
 * @author yunus
 */
public class Shift {
    private final String id, description;
    
    private final DailySchedule defaultschedule;
    
    private HashMap<DayOfWeek, DailySchedule> dailySchedules;
    

    public Shift(HashMap<String, String> shiftValues) {
        this.id = shiftValues.get("id");
        this.description = shiftValues.get("description");
        this.defaultschedule = new DailySchedule(shiftValues);
        
        
        
    }
    
   
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public LocalTime getShiftstart() {
        return defaultschedule.getShiftstart();
    }
    
    public LocalTime getShiftstop() {
        return defaultschedule.getShiftstop();
    }
    
    public int getRoundinterval() {
        return defaultschedule.getRoundinterval();
    }
    
    public int getGraceperiod() {
        return defaultschedule.getGraceperiod();
    }
    
    public int getDockpenalty() {
        return defaultschedule.getDockpenalty();
    }
    
    public LocalTime getLunchstart() {
        return defaultschedule.getLunchstart();
    }
    
    public LocalTime getLunchstop() {
        return defaultschedule.getLunchstop();
    }
    
    public int getLunchthreshold() {
        return defaultschedule.getLunchthreshold();
    }
    
    public DailySchedule getDefaultschedule(){
        return defaultschedule;
    }
    
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        Duration duration;

        s.append(description).append(": ");
        // Determine if shiftstart and shiftstop span midnight
        if (defaultschedule.getShiftstart().isBefore(defaultschedule.getShiftstop())) {
            duration = Duration.between(defaultschedule.getShiftstart(), defaultschedule.getShiftstop());
        } else {
            duration = Duration.ofHours(24).minus(Duration
                    .between(defaultschedule.getShiftstop(), defaultschedule.getShiftstart()));
        }
        long workDuration = duration.toMinutes();
        
        s.append(defaultschedule.getShiftstart()).append(" - ").append(defaultschedule.getShiftstop())
                .append(" (").append(workDuration)
                .append(" minutes").append(")").append("; ");
        
        Duration lunchDuration = Duration.between(defaultschedule.getLunchstart(), defaultschedule.getLunchstop());
        long lduration = lunchDuration.toMinutes();
        
        s.append("Lunch: ").append(defaultschedule.getLunchstart()).append(" - ")
                .append(defaultschedule.getLunchstop()).append(" ").append("(")
                .append(lduration).append(" minutes").append(")");
        return s.toString();
    }
}
