package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import java.sql.*;
import java.util.zip.CRC32;

public class BadgeDAO {

    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";
    private static final String QUERY_DELETE = "DELETE FROM badge WHERE id = ?";
    private static final String QUERY_COUNT = "SELECT COUNT(*) AS count FROM badge";
    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Badge find(String id) {
        Badge badge = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setString(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        String description = rs.getString("description");
                        badge = new Badge(id, description);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());

        } finally {
            if (rs != null) {
                
                try { rs.close(); }
                
                catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (ps != null) {
                
                try { ps.close(); }
                
                catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return badge;
    }
    
    public boolean create(Badge badge) {
        try {
            Connection conn = daoFactory.getConnection();

            // Check if the badge already exists
            String checkSql = "SELECT id FROM badge WHERE description = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkSql);
            checkStatement.setString(1, badge.getDescription());
            ResultSet checkResult = checkStatement.executeQuery();
            if (checkResult.next()) {
                // Returns true if the badge exists
                return true;
            }

            // Generate badge ID using CRC-32 checksum 
            CRC32 crc = new CRC32();
            crc.update(badge.getDescription().getBytes());
            String badgeId = String.format("%08X", crc.getValue());

            // Prepare SQL statement to create badge
            String insertSql = "INSERT INTO badge (id, description) VALUES (?, ?)";
            PreparedStatement insertStatement = conn.prepareStatement(insertSql);
            insertStatement.setString(1, badgeId);
            insertStatement.setString(2, badge.getDescription());

            // Check if a row was affected
            int rowsAffected = insertStatement.executeUpdate();
            return (rowsAffected == 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(String id) {
        boolean success = false;
        boolean results = false;
        int initial_count;
        int adjusted_count;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                ps = conn.prepareCall(QUERY_COUNT);
                results = ps.execute();
                if (results && (ps.getFetchSize() == 0)){
                    success = true;
                } else if (results && (ps.getFetchSize() != 0)){
                    results = false;

                    ps = conn.prepareCall(QUERY_DELETE);
                    ps.setString(1, id);
                    results = ps.execute();
                    if (results) {
                        System.out.println("3rd if");
                        ps = conn.prepareCall(QUERY_COUNT);
                        results = ps.execute();
                        if (results) {
                            adjusted_count = ps.getFetchSize();
                            success = true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { 
                try { ps.close(); }
                catch (Exception e) { e.printStackTrace(); } }
        }
        
        
        return success;
    }
    
}
