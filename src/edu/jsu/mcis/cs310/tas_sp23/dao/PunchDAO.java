package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.Employee;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

/**
 * <p> The PunchDAO class provides an interface for accessing, modifying, and
 * creating punch objects in the database. </p>
 * @author Dalton Estes
 */
public class PunchDAO {

    /**
     * <p> A string query statement in SQL format used by {@link #find find()} 
     * method to interact with the database to find a badge with a specific
     * id. </p>
     */
    private static final String QUERY_FIND_FROM_ID = "SELECT * FROM event WHERE id = ?";
    
    /**
     * <p> A string query statement in SQL format used by {@link #list list()} 
     * method to interact with the database to find a badge with a specific
     * id between a range of dates. </p>
     */
    private static final String QUERY_LIST_FROM_BADGE = "SELECT *, DATE(`timestamp`) AS tsdate FROM event WHERE badgeid = ? HAVING tsdate = ? ORDER BY 'timestamp';";
    
    /**
     * <p> A string query statement in SQL format used by {@link #list list()} 
     * method to interact with the database to find a badge with a specific
     * id between a range of dates and returning the next day after the last day
     * in the range. </p>
     */
    private static final String QUERY_LIST_NEXT_DAY = "SELECT *, DATE(`timestamp`) AS tsdate FROM event WHERE badgeid = ? HAVING tsdate > ? ORDER BY 'timestamp' LIMIT 1;";
    
    /**
     * <p> A string query statement in SQL format used by {@link #list list()} 
     * method to interact with the database to find a badge with a specific
     * id over a range of dates and list it. </p>
     */
    private static final String QUERY_LIST_DATE_RANGE = "SELECT * FROM event WHERE ((CAST(`timestamp` AS DATE) BETWEEN ? AND ?)) AND badgeid = ?;";
    
    /**
     * <p> A DAOFactory object used by {@link #PunchDAO PunchDAO()} to construct
     * a PunchDAO object. </p>
     */
    private final DAOFactory daoFactory;

    /**
     * <p> Constructs a PunchDAO object with the provided DAOFactory. </p>
     * @param daoFactory daoFactory object
     */
    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * <p> Find method that interacts with the database to find punches of a 
     * specified id. </p>
     * @param id represents an id
     * @return punch
     */
    public Punch find(int id) {

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDateTime originalTimeStamp;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_FIND_FROM_ID);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        //Create badge variable. Use BadgeDAO to find it.
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        String badgeId = rs.getString("badgeid");
                        Badge badge = badgeDAO.find(badgeId);
                        
                        //Get eventtype. punchtype
                        EventType punchtype = EventType.values()[rs.getInt("eventtypeid")];
                                
                        int terminalid = rs.getInt("terminalid");
                        
                        originalTimeStamp = rs.getTimestamp("timestamp").toLocalDateTime();
                        punch = new Punch(id, terminalid, badge, originalTimeStamp, punchtype);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return punch;
    }

    /**
     * <p> list method that interacts with the database to find punches of a 
     * specified id for a day. </p>
     * @param badge represents a badge
     * @param day represents a day
     * @return punch
     */
    public ArrayList list (Badge badge, LocalDate day){

        ArrayList<Punch> punchArray = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();    

            if (conn.isValid(0)) {                
                
                ps = conn.prepareStatement(QUERY_LIST_FROM_BADGE);
                ps.setString(1, badge.getId()); 
                ps.setDate(2, java.sql.Date.valueOf(day));// Day 1 //

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                    PunchDAO punchDAO = new PunchDAO(daoFactory);
                    
                    while (rs.next()) {
                        
                       int id = rs.getInt("id");                       
                       Punch punch = punchDAO.find(id);                      
                       punchArray.add(punch);
                    }
                    
                    //If last punch is CLOCK_IN, next day must be checked for closing pair.
                    
                    int lastIndex = punchArray.size();                    
                    Punch lastPunchIndex = punchArray.get(lastIndex - 1);
                    
                    EventType lastPunch = lastPunchIndex.getPunchtype();

                    if (lastPunch == EventType.CLOCK_IN) {
                        
                        ps = conn.prepareStatement(QUERY_LIST_NEXT_DAY);
                        
                        //Check one day past previous range. 

                        ps.setString(1, badge.getId()); 
                        ps.setDate(2, java.sql.Date.valueOf(day)); // Day 2 //
                        
                        boolean hasresults2 = ps.execute();
                        
                        if (hasresults2) {
                            
                            rs = ps.getResultSet();
                            
                            while (rs.next()) {
                                
                                //Find punch type of next day.
                                
                                int id = rs.getInt("id");
                                Punch firstPunchDay3 = punchDAO.find(id);                               
                                EventType firstPunchOfDay = firstPunchDay3.getPunchtype();
                                
                                if ((firstPunchOfDay == EventType.CLOCK_OUT) || (firstPunchOfDay == EventType.TIME_OUT)) {
                                    punchArray.add(firstPunchDay3);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return punchArray;
    }

    /**
     * <p> List method that interacts with the database to find punches of a 
     * specified id for a range of dates. </p>
     * @param badge representing badge object
     * @param start representing start of range of dates
     * @param end representing end of range of dates
     * @return punch array of list of punches
     */
    public ArrayList list (Badge badge, LocalDate start, LocalDate end){

        ArrayList<Punch> punchArray = new ArrayList<>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();    

            if (conn.isValid(0)) {                
                
                ps = conn.prepareStatement(QUERY_LIST_DATE_RANGE);
                // Date range
                ps.setDate(1, java.sql.Date.valueOf(start)); /* 1st */
                ps.setDate(2, java.sql.Date.valueOf(end));/* 2nd */
                // Badge to query for
                ps.setString(3, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                    PunchDAO punchDAO = new PunchDAO(daoFactory);
                    
                    while (rs.next()) {
                        
                       int id = rs.getInt("id");                       
                       Punch punch = punchDAO.find(id);                      
                       punchArray.add(punch);
                    }
                    
                    //If last punch is CLOCK_IN, next day must be checked for closing pair.
                    
                    int lastIndex = punchArray.size();                    
                    Punch lastPunchIndex = punchArray.get(lastIndex - 1);
                    
                    EventType lastPunch = lastPunchIndex.getPunchtype();

                    if (lastPunch == EventType.CLOCK_IN) {
                        ps = conn.prepareStatement(QUERY_LIST_NEXT_DAY);
                        
                        LocalDate day = lastPunchIndex.getOriginaltimestamp().toLocalDate();
                        //Check one day past previous range. 

                        ps.setString(1, badge.getId()); 
                        ps.setDate(2, java.sql.Date.valueOf(day)); // Day 2 //
                        
                        boolean hasresults2 = ps.execute();
                        
                        if (hasresults2) {
                            
                            rs = ps.getResultSet();
                            
                            while (rs.next()) {
                                
                                //Find punch type of next day.
                                
                                int id = rs.getInt("id");
                                Punch firstPunchDay3 = punchDAO.find(id);                               
                                EventType firstPunchOfDay = firstPunchDay3.getPunchtype();
                                
                                if ((firstPunchOfDay == EventType.CLOCK_OUT) || (firstPunchOfDay == EventType.TIME_OUT)) {
                                    punchArray.add(firstPunchDay3);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return punchArray;
    }
    
    /**
     * <p> Create method that interacts with the database to create punches of a 
     * specified id </p>
     * @param punch representing a punch
     * @return punchId
     */
    public int create(Punch punch) {
        
        int punchId = 0;

        try {
            
            Connection conn = daoFactory.getConnection();

           // Get employee's department and department's clock terminal
           
            EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
            Employee employee = employeeDAO.find(punch.getBadge());
            int clockTerminalId = employee.getDepartment().getTerminalid();

            // Check if the new punch originated from the correct clock terminal
            
            int newPunchTerminalId = punch.getTerminalid();
            if ((newPunchTerminalId == 0) || (newPunchTerminalId == clockTerminalId)) {
                
                // Punch is authorized, proceed with insertion
                
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    
                    LocalDateTime time = punch.getOriginaltimestamp().withNano(0); // zero seconds/nanoseconds
                    java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf(time);
                    ps = conn.prepareStatement("INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, punch.getTerminalid());
                    ps.setString(2, punch.getBadge().getId());
                    ps.setTimestamp(3, ts2);
                    ps.setInt(4, punch.getPunchtype().ordinal());

                    int affectedRows = ps.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Creating punch failed, no rows affected.");
                    }

                    rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        punchId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating punch failed, no ID obtained.");
                    }

                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) {
                            throw new DAOException(e.getMessage());
                        }
                    }
                    if (ps != null) {
                        try {
                            ps.close();
                        } catch (SQLException e) {
                            throw new DAOException(e.getMessage());
                        }
                    }
                }
            }
        } catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }

        return punchId;
    }
}
