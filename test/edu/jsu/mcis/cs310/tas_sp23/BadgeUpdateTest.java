package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;

/*
 * @author Josh
*/
public class BadgeUpdateTest {
    private DAOFactory daoFactory;
    
    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }
    
    @Test
    public void testUpdateBadge1() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        /* Delete badge for test prep */
        badgeDAO.delete("AB12CD34");
        
        /* Create badge with incorrect name */
        badgeDAO.create("AB12CD34", "Name, Wrong D");
        
        /* Update badge with the correct name */
        Badge b1 = badgeDAO.update("AB12CD34", "Name, Right A");
        
        /* Create new badge */
        assertEquals("#AB12CD34 (Name, Right A)", b1.toString());
        assertNotEquals("#AB12CD34 (Name, Wrong D)", b1.toString());
    }
    
    @Test
    public void testUpdateBadge2() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        /* Delete badge for test prep */
        badgeDAO.delete("1A2B3C4D");
        
        /* Create badge with incorrect name */
        badgeDAO.create("1A2B3C4D", "Name, Incorrect Q");
        
        /* Update badge with the correct name */
        Badge b2 = badgeDAO.update("1A2B3C4D", "Name, Correct H");
        
        /* Create new badge */
        assertEquals("#1A2B3C4D (Name, Incorrect Q)", b2.toString());
        assertNotEquals("#AB12CD34 (Name, Correct H)", b2.toString());
    }
}
