package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.PunchAdjustmentType;
import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;

/**
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
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

}
