package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Department;
import java.sql.*;

/**
 * <p> The DepartmentDAO class provides an interface for accessing and modifying 
 * Department objects in the database. </p>
 * @author Dalton Estes
 */
public class DepartmentDAO {
    
    /**
     * <p> A string query statement in SQL format used by {@link #find find()} 
     * method to interact with the database to find a department  with a specific
     * id. </p>
     */
    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?";
    
    /**
     * <p> A DAOFactory object used by {@link #DepartmentDAO DepartmentDAO()} to construct
     * an Department    DAO object. </p>
     */
    private final DAOFactory daoFactory;

    /**
     * <p> Constructs a new DepartmentDAO instance. </p>
     * @param daoFactory the DAOFactory used to obtain a database connection
     */
    DepartmentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * <p> Finds a Department object with the given ID in the database. </p>
     * @param id the ID of the Department to find
     * @return a Department object with the given ID, or null if not found
     * @throws DAOException if there is an error accessing the database
     */
    public Department find(int id) {

        Department department = null;

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
                        
                        String description = rs.getString("description");
                        int terminalid = rs.getInt("terminalid");
                        department = new Department(id, description, terminalid);

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

        return department;

    }
    
}
