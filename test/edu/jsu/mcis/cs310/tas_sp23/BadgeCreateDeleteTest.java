package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;

/*
 * @author Josh
*/
public class BadgeCreateDeleteTest {
    private DAOFactory daoFactory;
    
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }
    
    @Test
    public void testCreateBadge1() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        String[][] badges = {{"1234ABCD", "ABCD1234", "12AB34CD"},
            {"Taylor, Toby M", "Mewller, Ferris R", "Purr, Edgar Allen"}};
        
        /* Clear badges to prepare for test */
        badgeDAO.delete(badges[0][0]);
        badgeDAO.delete(badges[1][0]);
        badgeDAO.delete(badges[2][0]);
        
        /* Create new badge */
        for (int i = 0; i < badges[0].length; i++) {
            boolean result = badgeDAO.create(badges[0][i], badges[1][i]);
            
            assertTrue(result);
        }
        
        /* Delete badeges */
        for (int x = 0; x < badges[0].length; x++) {
            boolean result = badgeDAO.delete(badges[0][x]);
            
            assertTrue(result);
        }
        
        /* Get previous list of badges to compare */
        // The .list() may or may not need
        //      adjustmentdepending on BadgeDAO implementaion
        JsonArray b1 = (JsonArray)Jsoner.deserialize(badgeDAO.list());
        
        assertEquals(130, b1.size());
    }
}
