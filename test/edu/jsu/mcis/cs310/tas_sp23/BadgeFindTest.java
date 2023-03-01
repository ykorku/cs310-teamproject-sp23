package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import org.junit.*;
import static org.junit.Assert.*;

public class BadgeFindTest {
    private DAOFactory daoFactory;

    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }

    @Test
    public void testFindBadge1() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b1 = badgeDAO.find("12565C60");

        /* Compare to Expected Values */
        assertEquals("#12565C60 (Chapman, Joshua E)", b1.toString());
    }

    @Test
    public void testFindBadge2() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b2 = badgeDAO.find("08D01475");

        /* Compare to Expected Values */
        assertEquals("#08D01475 (Littell, Amie D)", b2.toString());
    }
    
    @Test
    public void testFindBadge3() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b3 = badgeDAO.find("D2CC71D4");

        /* Compare to Expected Values */
        assertEquals("#D2CC71D4 (Lawson, Matthew J)", b3.toString());
    }

    /*
     * @author Josh
    */
    @Test
    public void testFindBadge4() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b4 = badgeDAO.find("FF591F68");

        /* Compare to Expected Values */
        assertEquals("#FF591F68 (Miller, Robert K)", b4.toString());
    }
    
    @Test
    public void testFindBadge5() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b5 = badgeDAO.find("ADD650A8");

        /* Compare to Expected Values */
        assertEquals("#ADD650A8 (Taylor, Jennifer T)", b5.toString());
    }
    
    /*
     * @author Esat
    */
    @Test
    public void testFindBadge6() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b6 = badgeDAO.find("C8E646D8");

        /* Compare to Expected Values */
        assertEquals("#C8E646D8 (Merrick, Thomas L)", b6.toString());
    }
    
    
    @Test
    public void testFindBadge7() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b7 = badgeDAO.find("EC531DE6");

        /* Compare to Expected Values */
        assertEquals("#EC531DE6 (Elliott, Nancy L)", b7.toString());
    }
    
    
    @Test
    public void testFindBadge8() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b8 = badgeDAO.find("B4BBABEC");

        /* Compare to Expected Values */
        assertEquals("#B4BBABEC (Lawless, Brian L)", b8.toString());
    }
    
    
    @Test
    public void testFindBadge9() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b9 = badgeDAO.find("021890C0");

        /* Compare to Expected Values */
        assertEquals("#021890C0 (Chapell, George R)", b9.toString());
    }
    
    
    @Test
    public void testFindBadge10() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Retrieve Badges from Database */
        Badge b10 = badgeDAO.find("DFD9BB5C");

        /* Compare to Expected Values */
        assertEquals("#DFD9BB5C (Gallegos, Phillip M)", b10.toString());
    }
    
}
