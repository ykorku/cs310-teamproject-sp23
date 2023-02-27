package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import edu.jsu.mcis.cs310.tas_sp23.dao.EmployeeDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import com.github.cliftonlabs.json_simple.*;
import org.junit.*;
import static org.junit.Assert.*;

/*
 * @author Josh
 */
public class EmployeeUpdateTest {
    public DAOFactory daoFactory;
    private String badge_num;
    private String f_name;
    private String m_name;
    private String l_name;
    private int emp_type;
    private int dept_num;
    private int shift;
    private LocalTime time;
    private LocalDate date;
    
    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
        
        // Setup data once so each item can be tested more easily
        badge_num = "DCAB4312";
        f_name = "Firstname";
        m_name = "Midname";
        l_name = "Lastname";
        emp_type = 1;   /* Full-time */
        dept_num = 4;   /* Grinding */
        shift = 2;      /* Shift 2 */
        
        time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        date = LocalDate.now();
    }
    
    // WILL THE UPDATE METHOD BE SPECIFIC OR OVERLOADED???
    @Test
    public void testChangeDepartment() {
        // Setup
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        
        // Clear data if exists
        employeeDAO.delete(badge_num);
        badgeDAO.delete(badge_num);
        
        // Create new employee
        employeeDAO.create(badge_num, f_name, m_name, l_name, emp_type,
                dept_num, shift, date, time);
        
        boolean e1 = employeeDAO.update(badge_num, 6);  /* Office */
        Employee emp1 = employeeDAO.find(badge_num);
        
        assertTrue(e1);
        assertEquals("ID #131: Lastname, Firstname Midname (#DCAB4312), "
                + "Type: Full-Time, Department: Office, Active: " + date.toString(),
                emp1.toString());
    }
    
    @Test
    public void testChangeShift() {
        // Setup
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        
        // Clear data if exists
        employeeDAO.delete(badge_num);
        badgeDAO.delete(badge_num);
        
        // Create new employee
        employeeDAO.create(badge_num, f_name, m_name, l_name, emp_type,
                dept_num, shift, date, time);
        
        boolean e3 = employeeDAO.update(badge_num, 4);  /* Shift 3 */
        Employee emp3 = employeeDAO.find(badge_num);
        
        assertTrue(e3);
        assertEquals("ID #131: Lastname, Firstname Midname (#DCAB4312), "
                + "Shift: Shift 3", emp3.toString());
    }
    
    @Test
    public void testChangeName() {
        // Setup
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        // New names for employee
        String firstName = "Missy";
        String midName = "";
        String lastName = "Taylor";
        
        // Clear data if exists
        employeeDAO.delete(badge_num);
        badgeDAO.delete(badge_num);
        
        // Create new employee
        employeeDAO.create(badge_num, f_name, m_name, l_name, emp_type,
                dept_num, shift, date, time);
        
        // Overloaded update method? More tests will be needed.
        boolean e5 = employeeDAO.update(badge_num, firstName, midName, lastName);
        Employee emp5 = employeeDAO.find(badge_num);
        
        Badge b1 = badgeDAO.find(badge_num);
        
        assertEquals("#DCAB4312 (Taylor, Missy)", b1.toString());
        
        assertTrue(e5);
        assertEquals("ID #131: Lastname, Firstname Midname (#DCAB4312), "
                + "Type: Temporary / Part-Time, Department: Grinding, Active: "
                + date.toString(), emp5.toString());
    }
    
    @Test
    public void testChangeEmployeeType() {
        // Setup
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        
        // Clear data if exists
        employeeDAO.delete(badge_num);
        badgeDAO.delete(badge_num);
        
        // Create new employee
        employeeDAO.create(badge_num, f_name, m_name, l_name, emp_type,
                dept_num, shift, date, time);
        
        boolean e7 = employeeDAO.update(badge_num, 0);
        Employee emp7 = employeeDAO.find(badge_num);
        
        assertTrue(e7);
        assertEquals("ID #131: Lastname, Firstname Midname (#DCAB4312), "
                + "Type: Temporary / Part-Time, Department: Grinding, Active: "
                + date.toString(), emp7.toString());
    }
}

/** Departments
 *  1	Assembly	103
 *  2	Cleaning	107
 *  3	Warehouse	106
 *  4	Grinding	104
 *  5	Hafting		105
 *  6	Office		102
 *  7	Press		104
 *  8	Shipping	107
 *  9	Tool and Die	104
 * 10	Maintenance	104
 */