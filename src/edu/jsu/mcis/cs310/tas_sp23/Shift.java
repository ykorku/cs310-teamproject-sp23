package edu.jsu.mcis.cs310.tas_sp23;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * This class represents a shift with its associated properties, such as shift start and stop times, lunch start and stop times,
 * round interval, grace period, dock penalty, and lunch threshold.
 * 
 * @author yunus
 */
public class Shift {
    private final String id, description;
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final int roundinterval, graceperiod, dockpenalty, lunchthreshold;
    
    private final LocalTime lunchduration = null, shiftduration = null;
    
    /**
     * Constructs a Shift object with the given shift values.
     * 
     * @param shiftValues a HashMap containing the shift values
     */
    public Shift(HashMap<String, String> shiftValues) {
        this.id = shiftValues.get("id");
        this.description = shiftValues.get("description");
        
        this.shiftstart = LocalTime.parse(shiftValues.get("shiftstart"));
        this.shiftstop = LocalTime.parse(shiftValues.get("shiftstop"));
        
        this.roundinterval = Integer.parseInt(shiftValues.get("roundinterval"));
        this.graceperiod = Integer.parseInt(shiftValues.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(shiftValues.get("dockpenalty"));
        
        this.lunchstart = LocalTime.parse(shiftValues.get("lunchstart"));
        this.lunchstop = LocalTime.parse(shiftValues.get("lunchstop"));
        
        this.lunchthreshold = Integer.parseInt(shiftValues.get("lunchthreshold"));
    }
    /**
     * Returns the ID of the shift.
     * 
     * @return the ID of the shift
     */
    public String getId() {
        return id;
    }
    /**
     * Returns the description of the shift.
     * 
     * @return the description of the shift
     */
    public String getDescription() {
        return description;
    }
    /**
     * Returns the start time of the shift.
     * 
     * @return the start time of the shift
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }
    /**
     * Returns the stop time of the shift.
     * 
     * @return the stop time of the shift
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }
    /**
     * Returns the round interval of the shift.
     * 
     * @return the round interval of the shift
     */
    public int getRoundinterval() {
        return roundinterval;
    }
    /**
     * Returns the grace period of the shift.
     * 
     * @return the grace period of the shift
     */
    public int getGraceperiod() {
        return graceperiod;
    }
    /**
     * Returns the dock penalty of the shift.
     * 
     * @return the dock penalty of the shift
     */
    public int getDockpenalty() {
        return dockpenalty;
    }
    /**
     * Returns the lunch start time of the shift.
     * 
     * @return the lunch start time of the shift
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }
    /**
     * Returns the lunch stop time of the shift.
     * 
     * @return the lunch stop time of the shift
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }
    /**
     * Returns the lunch threshold of the shift.
     * 
     * @return the lunch threshold of the shift
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    /**
     * Returns a string representation of the shift object.
     * 
     * @return a string representation of the shift object
     */
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        Duration duration;

        s.append(description).append(": ");
        // Determine if shiftstart and shiftstop span midnight
        if (shiftstart.isBefore(shiftstop)) {
            duration = Duration.between(shiftstart, shiftstop);
        } else {
            duration = Duration.ofHours(24).minus(Duration
                    .between(shiftstop, shiftstart));
        }
        long workDuration = duration.toMinutes();
        
        s.append(shiftstart).append(" - ").append(shiftstop)
                .append(" (").append(workDuration)
                .append(" minutes").append(")").append("; ");
        
        Duration lunchDuration = Duration.between(lunchstart, lunchstop);
        long lduration = lunchDuration.toMinutes();
        
        s.append("Lunch: ").append(lunchstart).append(" - ")
                .append(lunchstop).append(" ").append("(")
                .append(lduration).append(" minutes").append(")");
        return s.toString();
    }
}
