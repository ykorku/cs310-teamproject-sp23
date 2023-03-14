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
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailyPunchList) {
        
        JsonArray jsonData = new JsonArray();
        int dailyPunchList_size = dailyPunchList.size();
        int counter = 0;
        int number = 20;
        int number1 = 43;
        for(int i = 0; i < dailyPunchList_size; i++) {
            
            Punch punch = dailyPunchList.get(i);
            JsonObject data = new JsonObject();
            //DAOFactory daoFactory = new DAOFactory("tas.jdbc");

            data.put("terminalid", String.valueOf(punch.getTerminalid()));
            data.put("id", String.valueOf(punch.getId()));
            Badge b = punch.getBadge();
            data.put("badgeid", String.valueOf(b.getId()));
            //data.put("terminalid", String.valueOf(punch.getTerminalid()));
            EventType et = punch.getPunchtype();
            data.put("punchtype", String.valueOf(et.toString()));
            PunchAdjustmentType pat = punch.getAdjustmentType();
            data.put("adjustmenttype", pat.toString());
            //LocalDateTime time = punch.getOriginaltimestamp();
            String original = punch.printOriginal();
            String time = original.substring(20);
            time = time.trim();
            data.put("originaltimestamp", time);
            
            String adjustedOriginal = punch.printAdjusted();
            String adjustedTime = adjustedOriginal.substring(20, 44);
            adjustedTime = adjustedTime.trim();
            data.put("adjustedtimestamp", adjustedTime);

            jsonData.add(data);
            counter++;
        }
        
        String json = Jsoner.serialize(jsonData);

        return json;
        
    }

}
