package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.BadgeDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;

/**
 * <p> A simple test file that ensures the project has a connection with the database. </p>
 * @author JSU
 */
public class Main {
    
    /**
     * <p> This method uses a database class to test database connection. </p>
     * @param args 
     */
    public static void main(String[] args) {
        
        // test database connectivity; get DAOs
        
        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        
        // find badge
        
        Badge b = badgeDAO.find("31A25435");
        
        // output should be "Test Badge: #31A25435 (Munday, Paul J)"

        System.err.println("Test Badge: " + b.toString());
        
    }

}