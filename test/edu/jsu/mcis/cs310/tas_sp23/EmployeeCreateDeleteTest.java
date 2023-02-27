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
public class EmployeeCreateDeleteTest {
    public DAOFactory daoFactory;
    
    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }
    
    @Test
    public void testCreateEmployee() {
        // Setup
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        
        // Data to test
        String badge_num = "DCAB4312";
        String f_name = "Firstname";
        String m_name = "Midname";
        String l_name = "Lastname";
        int emp_type = 1;
        int dept_num = 4;
        int shift = 2;
        
        employeeDAO.delete(badge_num);
        badgeDAO.delete(badge_num);
        
        // Local Date/Time for time hired
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        // Need to use the date to insert a specific date
        LocalDate date = LocalDate.now();
        
        employeeDAO.create(badge_num, f_name, m_name, l_name, emp_type,
                dept_num, shift, date, time);
        
        Employee emp1 = employeeDAO.find(badge_num);
        Badge b1 = badgeDAO.find(emp1.getBadge());
        JsonArray e1 = (JsonArray)Jsoner.deserialize(employeeDAO.list());
        
        // Employee was created Test
        assertEquals("ID #131: Lastname, Firstname Midname (#DCAB4312), "
                + "Type: Full-Time, Department: Grinding, Active: " + date.toString(),
                emp1.toString());
        
        // Was a badge also created when the Employee was created Test
        assertEquals("#DCAB4312 (Lastname, Firstname Midname)", b1.toString());
        assertEquals(131, e1.size());
    }
    
    @Test
    public void testDeleteEmployee() {
        // Setup
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        
        // Data to test
        String badge_num = "D1C2B3A4";
        String f_name = "Firstname";
        String m_name = "Midname";
        String l_name = "Lastname";
        int emp_type = 0;
        int dept_num = 6;
        int shift = 4;
        
        employeeDAO.delete(badge_num);
        
        // Local Date/Time for time hired
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDate date = LocalDate.now();
        
        employeeDAO.create(badge_num, f_name, m_name, l_name, emp_type,
                dept_num, shift, date, time);
        
        boolean emp1 = employeeDAO.delete(badge_num);
        boolean b1 = badgeDAO.delete(badge_num);
        JsonArray e1 = (JsonArray)Jsoner.deserialize(employeeDAO.list());
        
        assertTrue(emp1);
        assertTrue(b1);
        assertEquals(130, e1.size());
    }
}
