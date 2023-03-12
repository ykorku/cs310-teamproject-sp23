package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND_FROM_ID = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST_FROM_BADGE = "SELECT *, DATE(`timestamp`) AS tsdate FROM event WHERE badgeid = ? HAVING tsdate = ? ORDER BY 'timestamp';";
    private static final String QUERY_LIST_NEXT_DAY = "SELECT *, DATE(`timestamp`) AS tsdate FROM event WHERE badgeid = ? HAVING tsdate > ? ORDER BY 'timestamp' LIMIT 1;";

    private final DAOFactory daoFactory;

    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

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
}
