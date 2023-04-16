package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.time.*;
import java.util.*;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType;
import static edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType.LUNCH_START;
import static edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType.LUNCH_STOP;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 */
public final class DAOUtility {
        static final int START_OF_SUBSTRING = 20;
        static final int END_OF_SUBSTRING = 44;
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailyPunchList) {
        JsonArray jsonData = new JsonArray();
        int dailyPunchList_size = dailyPunchList.size();

        for(int i = 0; i < dailyPunchList_size; i++) {
            Punch punch = dailyPunchList.get(i);
            JsonObject data = new JsonObject();
            
            /* Load punch variables into Json Object */
            data.put("terminalid", String.valueOf(punch.getTerminalid()));
            data.put("id", String.valueOf(punch.getId()));
            
            Badge b = punch.getBadge();
            data.put("badgeid", String.valueOf(b.getId()));

            EventType et = punch.getPunchtype();
            data.put("punchtype", String.valueOf(et.toString()));
            
            PunchAdjustmentType pat = punch.getAdjustmentType();
            data.put("adjustmenttype", pat.toString());

            String original = punch.printOriginal();
            String time = original.substring(START_OF_SUBSTRING);  //Substring needed for formatting.
            time = time.trim();  //Trims space before and after string.
            data.put("originaltimestamp", time);
            
            String adjustedOriginal = punch.printAdjusted();
            String adjustedTime = adjustedOriginal.substring(START_OF_SUBSTRING, END_OF_SUBSTRING);
            adjustedTime = adjustedTime.trim();
            data.put("adjustedtimestamp", adjustedTime);

            /* Add Json Object to Json Array. Ensures order of punches is the same. */
            jsonData.add(data);
        }
        String json = Jsoner.serialize(jsonData);
        return json;
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int totalMins = 0;
        LocalDateTime in = null ;
        LocalDateTime out = null ;

        boolean lunchUsed = false;
        long minsBetween;

        for (int i = 0; i < dailypunchlist.size(); i++) {
            minsBetween = 0;
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
                    lunchUsed = true;
                }

            if ((out != null) && (in != null)) {
                minsBetween = Duration.between(in, out).toMinutes();

                //totalMins += minsBetween;

                in = null;
                out = null;
            }

            if((!lunchUsed) && (minsBetween > shift.getLunchthreshold()) ) {
                totalMins = totalMins + (int)(minsBetween - 30);
            } else {
                totalMins = totalMins + (int)(minsBetween);
            }
        }
        return totalMins;
    }
    
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift){
        
        JsonArray jsonData = new JsonArray();
        JsonObject jsonData2 = new JsonObject();
        int punchList_size = punchlist.size();

        for(int i = 0; i < punchList_size; i++) {
            
            Punch punch = punchlist.get(i);
            JsonObject data = new JsonObject();
            
            /* Get absenteeism and total minutes */
            
            data.put("terminalid", String.valueOf(punch.getTerminalid()));
            data.put("id", String.valueOf(punch.getId()));
            
            Badge b = punch.getBadge();
            data.put("badgeid", String.valueOf(b.getId()));

            EventType et = punch.getPunchtype();
            data.put("punchtype", String.valueOf(et.toString()));
            
            PunchAdjustmentType pat = punch.getAdjustmentType();
            data.put("adjustmenttype", pat.toString());

            String original = punch.printOriginal();
            String time = original.substring(START_OF_SUBSTRING);  //Substring needed for formatting.
            time = time.trim();  //Trims space before and after string.
            data.put("originaltimestamp", time);
            
            String adjustedOriginal = punch.printAdjusted();
            String adjustedTime = adjustedOriginal.substring(START_OF_SUBSTRING, END_OF_SUBSTRING);
            adjustedTime = adjustedTime.trim();
            data.put("adjustedtimestamp", adjustedTime);

            /* Add Json Object to Json Array. Ensures order of punches is the same. */
            
            jsonData.add(data);
            
        }
        
        BigDecimal percentAbsent = DAOUtility.calculateAbsenteeism(punchlist, shift);
        int totalMin = DAOUtility.calculateTotalMinutes(punchlist, shift);

        /* Load variables into Json Object */
        
        StringBuilder s = new StringBuilder();
        String percent = String.format("%.2f", percentAbsent.floatValue());
        s.append(percent).append("%");
        jsonData2.put("absenteeism", s.toString());

        jsonData2.put("totalminutes",totalMin);
        jsonData2.put("punchlist", jsonData);
        
        String json = Jsoner.serialize(jsonData2);

        return json;
        
    }
    
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchList, Shift shift) {
        
        double totalHours = shift.getScheduleHours();
        double minsWorked = calculateTotalMinutes(punchList, shift);
        BigDecimal absenteeism = new BigDecimal(100 - (minsWorked/totalHours) * 100);
        
        return absenteeism;
    }
    
    /*
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchList, Shift shift) {
        BigDecimal minsWorked = new BigDecimal(calculateTotalMinutes(punchList, shift));
        Duration tempMins;
        int lunch_durations = 30;
        
        if (shift.getShiftstart().isBefore(shift.getShiftstop())) {
            tempMins = Duration.between(shift.getShiftstart(), 
                    shift.getShiftstop());
        } else {
            tempMins = Duration.ofHours(24).minus(Duration
                    .between(shift.getShiftstart(),
                            shift.getShiftstop()));
        }
        System.err.println(minsWorked);
        BigDecimal scheduledMins = new BigDecimal((tempMins.toMinutes() - lunch_durations)*5);
        BigDecimal percentage = minsWorked.divide(scheduledMins, 4, RoundingMode.HALF_UP)
                .subtract(new BigDecimal(1)).multiply(new BigDecimal(-100));
        
        return percentage;
    }
    */
}
