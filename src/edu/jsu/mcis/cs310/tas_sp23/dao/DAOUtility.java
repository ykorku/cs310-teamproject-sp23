package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.Shift;

/**
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
        public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
            
            int totalMinutes = 0;
            LocalTime in = null ;
            LocalTime out = null ;
            
            LocalTime lunchin=shift.getLunchstart();
            LocalTime lunchout=shift.getLunchstop();
            
            boolean lunchused=false;
           
            

            for (int i = 0; i < dailypunchlist.size(); i++) {
                
                
                
                Punch punch = dailypunchlist.get(i);
                EventType punchtype = punch.getPunchtype();
                
                

                if (punchtype == EventType.CLOCK_IN) {
                    LocalTime dateFromDateTime=punch.getAdjustedtimestamp().toLocalTime();
                    
                    in = dateFromDateTime;
                } 
                else if (punchtype == EventType.CLOCK_OUT ) {
                    LocalTime dateFromDateTime=punch.getAdjustedtimestamp().toLocalTime();
                    
                    out = dateFromDateTime;
                } 
                else if (punchtype == EventType.TIME_OUT) {
                    Punch prevpunch = dailypunchlist.get(i-1);
                    EventType prevpunchtype = prevpunch.getPunchtype();
                    if(punchtype == EventType.CLOCK_IN && prevpunchtype == EventType.TIME_OUT){
                        in = null;
                        out = null;
                    }
                } 
                
                if (out != null){
                    if(in.equals(lunchin)&&out.equals(lunchout)){
                        lunchused=true;
                    }
                    if(in.isAfter(lunchin)&&in.isBefore(lunchout)){
                        lunchused=true;
                    }
                    if(out.isAfter(lunchin)&&out.isBefore(lunchout)){
                        lunchused=true;
                    }
                }
                

                if (out != null) {
                    Duration duration = Duration.between(in, out);
                    long minutesBetween = duration.toMinutes();
                    
                    totalMinutes += minutesBetween;
                    
                    in = null;
                    out = null;
                    
                } 
                
                if(lunchused==false&&totalMinutes == 360 ||totalMinutes>360){
                        
                    totalMinutes-=30;
                    lunchused=true;
                        
                }
                    
            }
            
            
            
            return totalMinutes;
        }


}