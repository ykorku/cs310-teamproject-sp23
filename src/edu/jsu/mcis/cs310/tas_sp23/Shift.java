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
        
        dailySchedules = new HashMap<>();
        dailySchedules.put(DayOfWeek.MONDAY, defaultschedule);
        dailySchedules.put(DayOfWeek.TUESDAY, defaultschedule);
        dailySchedules.put(DayOfWeek.WEDNESDAY, defaultschedule);
        dailySchedules.put(DayOfWeek.THURSDAY, defaultschedule);
        dailySchedules.put(DayOfWeek.FRIDAY, defaultschedule);
        
    }
    
    public DailySchedule getDefaultschedule(){
        return defaultschedule;
    }
    
    public DailySchedule getDailySchedule(DayOfWeek day) {
        
        DailySchedule dailySchedule = dailySchedules.get(day);


        return dailySchedule;
        
    }
    
    public void setDailySchedule (DayOfWeek day, HashMap<String, String> override) {
        
        DailySchedule overrideSchedule = new DailySchedule(override);
        dailySchedules.replace(day, defaultschedule, overrideSchedule);
        
    }
    
    public int getScheduleHours() {
        
        Duration timeWorked;
        Duration lunch;
        int totalHours = 0;
        
        for(DayOfWeek day : dailySchedules.keySet()) {
            
            DailySchedule dailySchedule = dailySchedules.get(day);
            
            timeWorked = Duration.between(dailySchedule.getShiftstart(), dailySchedule.getShiftstop());
            lunch = Duration.between(dailySchedule.getLunchstart(), dailySchedule.getLunchstop());
            timeWorked = timeWorked.minus(lunch);
            totalHours += timeWorked.toMinutes();
            System.err.println(day);
            System.err.println(totalHours);
            
            
        }

        return totalHours;
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
