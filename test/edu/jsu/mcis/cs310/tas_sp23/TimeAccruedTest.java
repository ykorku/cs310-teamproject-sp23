package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.ShiftDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_sp23.dao.PunchDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOUtility;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class TimeAccruedTest {
    private DAOFactory daoFactory;

    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }

    @Test
    public void testMinutesAccruedShift1Weekday() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */
        Punch p = punchDAO.find(3634);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */
        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        assertEquals(480, m);
    }

    @Test
    public void testMinutesAccruedShift1WeekdayWithTimeout() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */
        Punch p = punchDAO.find(436);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */
        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        assertEquals(0, m);
    }

    @Test
    public void testMinutesAccruedShift1Weekend() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */
        Punch p = punchDAO.find(1087);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */
        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        assertEquals(360, m);
    }

    @Test
    public void testMinutesAccruedShift2Weekday() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */
        Punch p = punchDAO.find(4943);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */
        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        assertEquals(540, m);
    }
    
    @Test
    public void testMinutesAccruedShift1Weekday2() {
        
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();

        /* Get Punch/Badge/Shift Objects */
        Punch p = punchDAO.find(3635);
        Badge b = p.getBadge();
        Shift s = shiftDAO.find(b);
        
        /* Get/Adjust Punch List */
        ArrayList<Punch> dailypunchlist = punchDAO.list(b, p.getOriginaltimestamp().toLocalDate());

        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }

        /* Compute Pay Period Total */
        int m = DAOUtility.calculateTotalMinutes(dailypunchlist, s);

        /* Compare to Expected Value */
        assertEquals(480, m);
    }
}
