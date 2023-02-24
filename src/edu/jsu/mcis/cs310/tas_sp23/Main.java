package edu.jsu.mcis.cs310.tas_sp23;

import edu.jsu.mcis.cs310.tas_sp23.dao.PunchDAO;
import edu.jsu.mcis.cs310.tas_sp23.dao.DAOFactory;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        
        // test database connectivity; get DAOs

        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        PunchDAO punchDAO = daoFactory.getPunchDAO();
        
        // find badge

        Punch b = punchDAO.find(343);
        
        // output should be "Test Badge: #31A25435 (Munday, Paul J)"
        
        System.err.println("Test Badge: " + b.printOriginal());

    }

}
