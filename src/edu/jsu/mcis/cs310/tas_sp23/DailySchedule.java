
package edu.jsu.mcis.cs310.tas_sp23;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * <p> The DailySchedule class represents an employee's daily schedule.
 * This class provides getter methods to access these fields. </p>
 * @author User
 */
public class DailySchedule {
    
    private  final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final  int roundinterval, graceperiod, dockpenalty, lunchthreshold;
    private final long lunchDuration, shiftDuration;
  

    /**
     * <p> Uses a hash map to create dailyschedule objects. </p>
     * @param shiftValues a hash map of all the punches of a day
     */
    public DailySchedule(HashMap<String, String> shiftValues) {
        
        this.shiftstart = LocalTime.parse(shiftValues.get("shiftstart"));
        this.shiftstop = LocalTime.parse(shiftValues.get("shiftstop"));
        this.roundinterval = Integer.parseInt(shiftValues.get("roundinterval"));
        this.graceperiod = Integer.parseInt(shiftValues.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(shiftValues.get("dockpenalty"));
        this.lunchstart = LocalTime.parse(shiftValues.get("lunchstart"));
        this.lunchstop = LocalTime.parse(shiftValues.get("lunchstop"));
        this.lunchthreshold = Integer.parseInt(shiftValues.get("lunchthreshold"));
        this.lunchDuration = (Duration.between(lunchstart, lunchstop).toMinutes());
        this.shiftDuration = (Duration.between(shiftstart, shiftstop).toMinutes());
        
    }
    
    /**
     * <p> Getter method for shift start. <p>
     * @return shift start
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }
    
    /**
     * <p> Getter method for shift stop. <p>
     * @return shift stop
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }
    
    /**
     * <p> Getter method for round interval. <p>
     * @return round interval
     */
    public int getRoundinterval() {
        return roundinterval;
    }
    
    /**
     * <p> Getter method for grace period. <p>
     * @return grace period
     */
    public int getGraceperiod() {
        return graceperiod;
    }
    
    /**
     * <p> Getter method for dock penalty. <p>
     * @return dock penalty
     */
    public int getDockpenalty() {
        return dockpenalty;
    }
    
    /**
     * <p> Getter method for lunch start. <p>
     * @return lunch start
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }
    
    /**
     * <p> Getter method for lunch stop. <p>
     * @return lunch stop
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }
    
    /**
     * <p> Getter method for lunch threshold. <p>
     * @return lunch threshold
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    
    /**
     * <p> Getter method for shift duration. <p>
     * @return shift duration
     */
    public long getShiftDuration() {
        return shiftDuration;
    }
    
    /**
     * <p> Getter method for lunch duration. <p>
     * @return lunch duration
     */
    public long getlunchDuration() {
        return lunchDuration;
    }
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        
        s.append(shiftstart).append(" - ").append(shiftstop)
                .append(" (").append(shiftDuration)
                .append(" minutes").append(")").append("; ");
        
        s.append("Lunch: ").append(lunchstart).append(" - ")
                .append(lunchstop).append(" ").append("(")
                .append(lunchDuration).append(" minutes").append(")");
        return s.toString();
    }
}
