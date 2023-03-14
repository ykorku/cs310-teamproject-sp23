package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.time.*;
import java.util.*;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType;
import static edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType.LUNCH_START;
import static edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType.LUNCH_STOP;
import edu.jsu.mcis.cs310.tas_sp23.Shift;

/**
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {

        //localdatetime use, 2 clock in second ignored, use threshold value, padjtype check value lunch

        int totalMinutes = 0;
        LocalDateTime in = null ;
        LocalDateTime out = null ;

        boolean lunchused=false;

        for (int i = 0; i < dailypunchlist.size(); i++) {

            Punch punch = dailypunchlist.get(i);
            EventType punchtype = punch.getPunchtype();
            PunchAdjustmentType adjtype = punch.getAdjustmentType();

            if (punchtype == EventType.CLOCK_IN) {
                
                in = punch.getAdjustedtimestamp();

                if(i>0){
                    
                    Punch prevpunch = dailypunchlist.get(i - 1);
                    EventType prevpunchtype = prevpunch.getPunchtype();
                    if((punchtype == EventType.CLOCK_IN) && (prevpunchtype == EventType.CLOCK_IN)){
                        in = prevpunch.getAdjustedtimestamp();

                    }
                    
                }
                
            } 
            else if (punchtype == EventType.CLOCK_OUT ) {
                out = punch.getAdjustedtimestamp();
            } 
            else if (punchtype == EventType.TIME_OUT) {
                
                Punch prevpunch = dailypunchlist.get(i - 1);
                EventType prevpunchtype = prevpunch.getPunchtype();
                
                if((punchtype == EventType.CLOCK_IN) && (prevpunchtype == EventType.TIME_OUT)){
                    
                    in = null;
                    out = null;
                    
                }
                
            }
            
            if(adjtype == null){
                
                if (punchtype == EventType.CLOCK_IN) {
                    in = punch.getAdjustedtimestamp();
                }
                
                if (punchtype == EventType.CLOCK_OUT) {
                    out = punch.getAdjustedtimestamp();
                }
                
                if (punchtype == EventType.TIME_OUT) {
                    
                    Punch prevpunch = dailypunchlist.get(i-1);
                    EventType prevpunchtype = prevpunch.getPunchtype();
                    
                    if((punchtype == EventType.CLOCK_IN) && (prevpunchtype == EventType.TIME_OUT)){
                        in = null;
                        out = null;
                    }
                    
                }
                
            }

                if((adjtype==LUNCH_START) || (adjtype==LUNCH_STOP)){
                    lunchused = true;
                }

            if ((out != null) && (in != null)) {
                
                Duration duration = Duration.between(in, out);
                long minutesBetween = duration.toMinutes();

                totalMinutes += minutesBetween;

                in = null;
                out = null;

            } 

            if((!lunchused) && (totalMinutes>shift.getLunchthreshold()) ){
                totalMinutes = totalMinutes - 30;
            }

        }

        return totalMinutes;
        
    }

}
