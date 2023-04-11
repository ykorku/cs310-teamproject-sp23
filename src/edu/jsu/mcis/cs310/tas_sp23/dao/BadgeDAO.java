package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import java.sql.*;

public class BadgeDAO {

    private static final String QUERY_FIND = "SELECT * FROM badge WHERE id = ?";
    private static final String QUERY_DELETE = "DELETE FROM badge WHERE bacgeid = ? AND payperiod = ?";
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
    
    public boolean delete(String id) {
        boolean success = false;
        boolean results = false;
        int initial_count = 0;
        int adjusted_count = 0;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareCall(QUERY_COUNT);
                results = ps.execute();
                if (results && rs.next()) {
                    initial_count = ps.getFetchSize();
                    results = false;
                }

                ps = conn.prepareCall(QUERY_DELETE);
                ps.setString(1, id);
                results = ps.execute();
                
                if (results) {
                    ps = conn.prepareCall(QUERY_COUNT);
                    results = ps.execute();
                    if (results && rs.next()) {
                        adjusted_count = ps.getFetchSize();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        return (initial_count > adjusted_count);
    }
}
