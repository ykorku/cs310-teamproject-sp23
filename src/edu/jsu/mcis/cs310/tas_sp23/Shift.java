/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp23;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

/**
 *
 * @author yunus
 */
public class Shift {
    private final String id, description;
    private final LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private final int roundinterval, graceperiod, dockpenalty, lunchthreshold;
    
    private final LocalTime lunchduration = null, shiftduration = null;
    

    public Shift(HashMap<String, String> shiftValues) {
        this.id =  shiftValues.get("id");
        this.description =  shiftValues.get("description");
        
        this.shiftstart = LocalTime.parse(shiftValues.get("shiftstart"));
        this.shiftstop = LocalTime.parse(shiftValues.get("shiftstop"));
        
        this.roundinterval =  Integer.parseInt(shiftValues.get("roundinterval"));
        this.graceperiod =  Integer.parseInt(shiftValues.get("graceperiod"));
        this.dockpenalty =  Integer.parseInt(shiftValues.get("dockpenalty"));
        
        this.lunchstart = LocalTime.parse(shiftValues.get("lunchstart"));
        this.lunchstop = LocalTime.parse(shiftValues.get("lunchstop"));
        
        this.lunchthreshold =  Integer.parseInt(shiftValues.get("lunchthreshold"));
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
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
    
    
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append(description).append(": ");
        Duration duration = Duration.between(shiftstart, shiftstop);
        long workduration=duration.toMinutes();
        
        s.append(shiftstart).append(" - ").append(shiftstop).append(" (").append(workduration).append(" minutes").append(")").append("; ");
        
        Duration lunchduration = Duration.between(lunchstart, lunchstop);
        long lduration=lunchduration.toMinutes();
        
        s.append("Lunch: ").append(lunchstart).append(" - ").append(lunchstop).append(" ").append("(").append(lduration).append(" minutes").append(")");
        
        return s.toString();

    }
    
}
