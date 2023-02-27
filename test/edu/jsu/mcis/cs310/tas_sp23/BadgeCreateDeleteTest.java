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
    
    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }
    
    @Test
    public void testCreateBadge() {
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
    
    @Test
    public void testDeleteBadge() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        String[][] badges = {{"4321DCBA", "DCBA4321", "DC43BA21"},
            {"Taylor, Missy M", "Riddle, Savvy R", "Taylor, Miss K"}};
        
        /* Clear badges to prepare for test */
        badgeDAO.delete(badges[0][0]);
        badgeDAO.delete(badges[1][0]);
        badgeDAO.delete(badges[2][0]);
        
        /* Create new badge */
        for (int i = 0; i < badges[0].length; i++) {
            boolean create_result = badgeDAO.create(badges[0][i], badges[1][i]);
            assertTrue(create_result);
            
            
            boolean delete_result = badgeDAO.delete(badges[0][i]);
            assertTrue(delete_result);
        }
        
        /* Get previous list of badges to compare */
        // The .list() may or may not need
        //      adjustmentdepending on BadgeDAO implementaion
        JsonArray b1 = (JsonArray)Jsoner.deserialize(badgeDAO.list());
        
        assertEquals(130, b1.size());
    }
}
