package edu.jsu.mcis.cs310.tas_sp23;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOUtility;
import edu.jsu.mcis.cs310.tas_sp23.dao.EmployeeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.PunchDAO;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        
        // test database connectivity; get DAOs
        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        // find badge
        //Badge b = badgeDAO.find("31A25435");
        
        // output should be "Test Badge: #31A25435 (Munday, Paul J)"

        //System.err.println("Test Badge: " + b.toString());
        
        
        try {
        
            EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
            PunchDAO punchDAO = daoFactory.getPunchDAO();

            /* Expected JSON Data */

            String expectedJSON = "{\"absenteeism\":\"2.50%\",\"totalminutes\":2340,\"punchlist\":[{\"originaltimestamp\":\"TUE 09/04/2018 06:48:31\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"TUE 09/04/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3279\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"TUE 09/04/2018 12:02:42\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"TUE 09/04/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3333\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"WED 09/05/2018 06:46:48\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"WED 09/05/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3395\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"WED 09/05/2018 12:02:26\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"WED 09/05/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3461\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"WED 09/05/2018 12:26:35\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"WED 09/05/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\",\"terminalid\":\"104\",\"id\":\"3462\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"WED 09/05/2018 17:33:44\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"WED 09/05/2018 17:30:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"104\",\"id\":\"3498\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"THU 09/06/2018 06:46:06\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"THU 09/06/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3523\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"THU 09/06/2018 12:03:34\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"THU 09/06/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3569\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"THU 09/06/2018 12:27:34\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"THU 09/06/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\",\"terminalid\":\"104\",\"id\":\"3570\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"THU 09/06/2018 17:33:21\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"THU 09/06/2018 17:30:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"104\",\"id\":\"3597\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"FRI 09/07/2018 06:50:35\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09/07/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3634\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"FRI 09/07/2018 12:03:54\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09/07/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3687\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"FRI 09/07/2018 12:23:41\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09/07/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\",\"terminalid\":\"104\",\"id\":\"3688\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"FRI 09/07/2018 15:34:13\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"FRI 09/07/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\",\"terminalid\":\"104\",\"id\":\"3716\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"SAT 09/08/2018 05:55:36\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"SAT 09/08/2018 06:00:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"104\",\"id\":\"3756\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"SAT 09/08/2018 12:03:37\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"SAT 09/08/2018 12:00:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"104\",\"id\":\"3839\",\"punchtype\":\"CLOCK OUT\"}]}";

            JsonObject expected = (JsonObject)(Jsoner.deserialize(expectedJSON));

            /* Get Punch */

            Punch p = punchDAO.find(4943);
            Employee e = employeeDAO.find(p.getBadge());
            Shift s = e.getShift();
            Badge b = e.getBadge();

            /* Get Pay Period Punch List */

            LocalDate ts = p.getOriginaltimestamp().toLocalDate();
            LocalDate begin = ts.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

            ArrayList<Punch> punchlist = punchDAO.list(b, begin, end);

            /* Adjust Punch List */

            //for (Punch punch : punchlist) {
            punchlist.get(0).adjust(s);
           //}

            /* JSON Conversion */

           // String actualJSON = DAOUtility.getPunchListPlusTotalsAsJSON(punchlist, s);
            //System.out.println(actualJSON);
            //JsonObject actual = (JsonObject) (Jsoner.deserialize(actualJSON));
            //System.out.println("Actual  : " + Jsoner.serialize(actual));
            //System.out.println("Expected: " + Jsoner.serialize(expected));

            /* Compare to Expected JSON */

            //assertEquals(expected, actual);
            
            //System.out.println()
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}