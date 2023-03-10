package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class PunchDAO {

    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";

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
                
                ps = conn.prepareStatement(QUERY_FIND);
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
    
    
    public int create(Punch punch) {
        int punchId = 0;

        try {
            Connection conn = daoFactory.getConnection();

           // Get employee's department and department's clock terminal
            EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
            Employee employee = employeeDAO.find(punch.getBadge());
            int clockTerminalId = employee.getDepartment().getTerminalid();
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Check if the new punch originated from the correct clock terminal
            int newPunchTerminalId = punch.getTerminalid();
            if (newPunchTerminalId == 0 || newPunchTerminalId == clockTerminalId) {
                // Punch is authorized, proceed with insertion
                PreparedStatement ps = null;
                ResultSet rs = null;

                try {
                    ps = conn.prepareStatement("INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, punch.getTerminalid());
                    ps.setString(2, punch.getBadge().getId());
                    ps.setString(3, punch.getOriginaltimestamp().format(dtf));
                    ps.setInt(4, Integer.parseInt(punch.getPunchtype().toString()));

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