package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.DailySchedule;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author yunus
 */
public class ShiftDAO {
    //SELECT * FROM dailyschedule JOIN shift ON dailyschedule.id=shift.dailyscheduleid
    private static final String QUERY_FIND_ID = "SELECT * FROM dailyschedule JOIN shift ON dailyschedule.id=shift.dailyscheduleid WHERE shift.dailyscheduleid = ?";
    
    private static final String QUERY_FIND_BADGE = "SELECT * FROM employee WHERE badgeid = ?";
    
    private static final String QUERY_CHECK_BADGE = "SELECT * FROM scheduleoverride s WHERE badgeid = ?";
    
    private static final String QUERY_FIND_BADGE2 = "SELECT * FROM scheduleoverride s JOIN dailyschedule d ON d.id=s.dailyscheduleid WHERE badgeid = ? AND (? BETWEEN CAST(`start` AS DATE) AND CAST(`end` AS DATE))";
    private static final String QUERY_FIND_BADGE3 = "SELECT * FROM scheduleoverride s JOIN dailyschedule d ON d.id=s.dailyscheduleid WHERE ? BETWEEN CAST(`start` AS DATE) AND CAST(`end` AS DATE)";
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory aThis) {
        this.daoFactory = aThis;
    }
    
    public Shift find(int i1) {
        
        Shift shift = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, i1);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                    while (rs.next()) {

                        HashMap<String, String> shiftValues = new HashMap<>();
                        //HashMap<String, String> dsValues = new HashMap<>();
                        
                        shiftValues.put("id",  rs.getString("id"));
                        shiftValues.put("description",  rs.getString("description"));
                        
                        shiftValues.put("shiftstart",  rs.getString("shiftstart"));
                        shiftValues.put("shiftstop",  rs.getString("shiftstop"));
                        shiftValues.put("roundinterval",  rs.getString("roundinterval"));
                        shiftValues.put("graceperiod",  rs.getString("graceperiod"));
                        shiftValues.put("dockpenalty",  rs.getString("dockpenalty"));
                        shiftValues.put("lunchstart",  rs.getString("lunchstart"));
                        shiftValues.put("lunchstop",  rs.getString("lunchstop"));
                        shiftValues.put("lunchthreshold",  rs.getString("lunchthreshold"));
                        
                        shift = new Shift(shiftValues);
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
        return shift;
    }
    
    public Shift find(Badge b1) {
       
        Shift shift = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setObject(1, b1.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {
                    
                    rs = ps.getResultSet();
                    
                     if (rs.next()) {
                        Integer shiftid = rs.getInt("shiftid");
                        shift = find(shiftid);
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
        return shift;
    }
    
    public Shift find(Badge b1, LocalDate localdate) {
       
        Shift shift = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean hasBadge = false;
        
        HashMap<String, String> override=new HashMap<String, String>();

        try {

            Connection conn = daoFactory.getConnection();
            String id = b1.getId();
            shift = find(b1);
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_CHECK_BADGE);
                ps.setString(1, id);
                ps.execute();
                rs = ps.getResultSet();
                if(rs.next()) {
                    
                }
              
                ps = conn.prepareStatement(QUERY_FIND_BADGE3);
                ps.setDate(1, java.sql.Date.valueOf(localdate));
                ps.execute();
                
                rs = ps.getResultSet();
                
                if(hasBadge) {
                    ps = conn.prepareStatement(QUERY_FIND_BADGE2);
                    ps.setString(1, id);
                    ps.setDate(2, java.sql.Date.valueOf(localdate));
                    ps.execute();
                    
                }
                
                if (rs.next()) {
                    
                    override.put("id",  rs.getString("id"));
                    //override.put("description",  rs.getString("description"));

                    override.put("shiftstart",  rs.getString("shiftstart"));
                    override.put("shiftstop",  rs.getString("shiftstop"));
                    override.put("roundinterval",  rs.getString("roundinterval"));
                    override.put("graceperiod",  rs.getString("graceperiod"));
                    override.put("dockpenalty",  rs.getString("dockpenalty"));
                    override.put("lunchstart",  rs.getString("lunchstart"));
                    override.put("lunchstop",  rs.getString("lunchstop"));
                    override.put("lunchthreshold",  rs.getString("lunchthreshold"));

                    DayOfWeek day = DayOfWeek.values()[(rs.getInt("day")) - 1];
                    shift.setDailySchedule(day, override);

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
        return shift;
    }
    
    
}
