package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.EventType;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;

public class PunchDAO {

    private static final String QUERY_FIND_FROM_ID = "SELECT * FROM event WHERE id = ?";
    private static final String QUERY_LIST_FROM_BADGE = "SELECT * FROM event WHERE badgeid = ? AND timestamp between '?' and '?' ORDER BY timestamp;";

    private final DAOFactory daoFactory;

    PunchDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Punch find(int id) {

        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

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
                        
                        int eventtypeid = rs.getInt("eventtypeid");
                        EventType punchtype = EventType.values()[eventtypeid];
                                
                        int terminalid = rs.getInt("terminalid");
                        
                        //Get timestamp from database. It must be casted into LocalDateTime. 
                        
                        java.sql.Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
                        timestamp = rs.getTimestamp("timestamp");
                        LocalDateTime originalTimeStamp = timestamp.toLocalDateTime();  

                        //create punch variable.
                        
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
    
    public ArrayList list(Badge badge, LocalDate day){
        
        ArrayList<Punch> punchArray = new ArrayList<Punch>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();    

            if (conn.isValid(0)) {
                
                LocalDate dayPlusOne = day.plusDays(1); // Day 2 //
                
                
                LocalDateTime ts2 = LocalDateTime.atTime(0, 0, 0);
                
                ps = conn.prepareStatement(QUERY_LIST_FROM_BADGE);
                ps.setString(1, badge.getId()); 
                ps.setTimestamp(2, ); // Day 1 //
                ps.setTimestamp(3, ); // Day 2 //

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                       int id = rs.getInt("id");
                       int terminalid = rs.getInt("terminalid");
                       // timestamp retrieve here but not sure //
                       int eventtypeid = rs.getInt("eventtypeid");
                       EventType punchtype = EventType.values()[eventtypeid];
                       
                       Punch punch = new Punch(id, terminalid, badge, originalTimeStamp, punchtype); // ts from passed parameter okay to use? //

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