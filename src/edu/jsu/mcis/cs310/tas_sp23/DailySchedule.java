
package edu.jsu.mcis.cs310.tas_sp23;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;


public class DailySchedule {
    
    private  final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final  int roundinterval, graceperiod, dockpenalty, lunchthreshold;
    private final long lunchDuration, shiftDuration;
  

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
    
    
    
    public LocalTime getShiftstart() {
        return shiftstart;
    }
    
    public LocalTime getShiftstop() {
        return shiftstop;
    }
    
    public int getRoundinterval() {
        return roundinterval;
    }
    
    public int getGraceperiod() {
        return graceperiod;
    }
    
    public int getDockpenalty() {
        return dockpenalty;
    }
    
    public LocalTime getLunchstart() {
        return lunchstart;
    }
    
    public LocalTime getLunchstop() {
        return lunchstop;
    }
    
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    
    public long getShiftDuration() {
        return shiftDuration;
    }
    
    public long getlunchDuration() {
        return lunchDuration;
    }
    
}
