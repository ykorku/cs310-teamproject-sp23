package edu.jsu.mcis.cs310.tas_sp23;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * <p>This class represents a shift with its associated properties, such as shift start and stop times, lunch start and stop times,
 * round interval, grace period, dock penalty, and lunch threshold.</p>
 * 
 * @author yunus
 */
public class Shift {
    private final String id, description;
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final int roundinterval, graceperiod, dockpenalty, lunchthreshold;
    
    private final LocalTime lunchduration = null, shiftduration = null;
    
    /**
     * <p>Constructs a Shift object with the given shift values.</p>
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
     * <p>Returns the ID of the shift.</p>
     * 
     * @return the ID of the shift
     */
    public String getId() {
        return id;
    }
    /**
     * <p>Returns the description of the shift.</p>
     * 
     * @return the description of the shift
     */
    public String getDescription() {
        return description;
    }
    /**
     * <p>Returns the start time of the shift.</p>
     * 
     * @return the start time of the shift
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }
    /**
     * <p>Returns the stop time of the shift.</p>
     * 
     * @return the stop time of the shift
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }
    /**
     * <p>Returns the round interval of the shift.</p>
     * 
     * @return the round interval of the shift
     */
    public int getRoundinterval() {
        return roundinterval;
    }
    /**
     * <p>Returns the grace period of the shift.</p>
     * 
     * @return the grace period of the shift
     */
    public int getGraceperiod() {
        return graceperiod;
    }
    /**
     * <p>Returns the dock penalty of the shift.</p>
     * 
     * @return the dock penalty of the shift
     */
    public int getDockpenalty() {
        return dockpenalty;
    }
    /**
     * <p>Returns the lunch start time of the shift.</p>
     * 
     * @return the lunch start time of the shift
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }
    /**
     * <p>Returns the lunch stop time of the shift.</p>
     * 
     * @return the lunch stop time of the shift
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }
    /**
     * <p>Returns the lunch threshold of the shift.</p>
     * 
     * @return the lunch threshold of the shift
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    /**
     * <p>Returns a string representation of the shift object.</p>
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
