package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.time.*;
import java.util.*;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.DailySchedule;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import java.math.BigDecimal;
import java.math.MathContext;

import java.math.RoundingMode;

/**
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 */
public final class DAOUtility {
    
        /**
         * <p> Integer constant for substring start. </p>
         */
        static final int START_OF_SUBSTRING = 20;
        
        /**
         * <p> Integer constant for substring end. </p>
         */
        static final int END_OF_SUBSTRING = 44;
    
    
    /**
     * <p> Utility method that take a punch list for a single day as a paremeter
     * and serializes it into json format. </p>
     * @param dailyPunchList represents the daily list of punches for an employee
     * @return a serialized json string
     */ 
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
    
    /**
     * <p> Helper method to calculate total minutes worked on a shift. </p>
     * @param dailypunchlist represents the daily list of punches for an employee
     * @param shift represents the shift of an employee
     * @return total minutes work on a given shift
     */
    public static int calculateTotalMinutes(ArrayList<Punch> punchlist, Shift sh) {
        int minsWorked = 0;
        int total = 0;
        boolean stopLunch = false;
        boolean startLunch = false;
        DayOfWeek day = null;
        
        for(int i = 0; i < punchlist.size(); i++) {
            int lastPunch = (punchlist.size() - 1);
            
            if(lastPunch > i) {
                Punch punch = punchlist.get(i);
                Punch pairPunch = punchlist.get(i + 1);
                
                day = DayOfWeek.from(punch.getOriginaltimestamp().toLocalDate());
                DailySchedule sl = sh.getDailySchedule(day);
                
                if((punch.getPunchtype() == EventType.CLOCK_IN) && (pairPunch.getPunchtype() == EventType.CLOCK_OUT)) {
                    if((punch.getAdjustmentType()) == (PunchAdjustmentType.LUNCH_START)) {
                        startLunch = true;
                    }
                    if((punch.getAdjustmentType()) == (PunchAdjustmentType.LUNCH_STOP)) {
                        stopLunch = true;
                    }
                    Duration duration = Duration.between(punch.getAdjustedtimestamp(), pairPunch.getAdjustedtimestamp());
                    total += duration.toMinutes();
                } else {
                    if(pairPunch.getPunchtype()!= EventType.TIME_OUT) {
                        minsWorked += total;
                    }
                    total = 0;
                    stopLunch = false;
                    startLunch = false;
                }
                if((total > sl.getLunchthreshold()) && (!(stopLunch && startLunch))) {
                        total -= sh.getLunchDuration();
                }
            } else {
                minsWorked += total;
                System.err.println("Day: " + day);
                System.err.println("Hours worked: " + total);
            }
        }
        return minsWorked;
    }
    
    /**
     * <p> Helper method that returns an absenteeism percentage, total minutes worked,
     * and punch list in json format. </p>
     * @param punchlist represents the shift of an employee
     * @param shift represents the shift of an employee
     * @return absenteeism, total minutes, and punch list serialized into json
     */
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

    /**
     * <p> Helper method that calculates total absenteeism as a percentage value. </p>
     * @param punchList represents the punch list of shifts of an employee
     * @param shift represents the shift of an employee
     * @return absenteeism expressed as a percentage value
     */
    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchList, Shift shift) {
        double totalHours = shift.getScheduleHours();
        double minsWorked = calculateTotalMinutes(punchList, shift);
        System.err.println(totalHours);
        System.err.println(minsWorked);
        BigDecimal absenteeism = new BigDecimal(100 - ((minsWorked/totalHours) * 100));
        absenteeism = absenteeism.round(new MathContext(4, RoundingMode.HALF_UP));
        
        return absenteeism;
    }
}
