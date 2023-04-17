package edu.jsu.mcis.cs310.tas_sp23.dao;

import java.sql.*;

/**
 * <p> Class with methods that create DAO objects </p>
 * @author Dalton Estes
 */
public final class DAOFactory {

    /**
     * <p> String constant representing the url for use in {@link #DAOFactory DAOFactory()}. </p>
     */
    private static final String PROPERTY_URL = "url";
    
    /**
     * <p> String constant representing the username for use in {@link #DAOFactory DAOFactory()}. </p>
     */
    private static final String PROPERTY_USERNAME = "username";
    
    /**
     * <p> String constant representing the password for use in {@link #DAOFactory DAOFactory()}. </p>
     */
    private static final String PROPERTY_PASSWORD = "password";

    /**
     * <p> String variables representing the url, username, and password for 
     * use in {@link #DAOFactory DAOFactory()}. </p>
     */
    private final String url, username, password;
    
    /**
     * <p> Connection type variable used to establish connection, originally 
     * set to null value. </p>
     */
    private Connection conn = null;

    /**
     * <p> Method that verifies url, username, and password and then establishes
     * connection to database using the driver. </p>
     * @param prefix prefix for property to access
     */
    public DAOFactory(String prefix) {

        DAOProperties properties = new DAOProperties(prefix);

        this.url = properties.getProperty(PROPERTY_URL);
        this.username = properties.getProperty(PROPERTY_USERNAME);
        this.password = properties.getProperty(PROPERTY_PASSWORD);

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }

    /**
     * <p> Method for getting connection. </p>
     * @return connection
     */
    Connection getConnection() {
        return conn;
    }

    /**
     * <p> Method for creating a new BadgeDAO object. </p>
     * @return a new BadgeDAO object
     */
    public BadgeDAO getBadgeDAO() {
        return new BadgeDAO(this);
    }
    
    /**
     * <p> Method for creating a new PunchDAO object. </p>
     * @return a new PunchDAO object
     */
    public PunchDAO getPunchDAO() {
        return new PunchDAO(this);
    }
    
    /**
     * <p> Method for creating a new ShiftDAO object. </p>
     * @return a new ShiftDAO object
     */
    public ShiftDAO getShiftDAO() {
         return new ShiftDAO(this);
    }
  
    /**
     * <p> Method for creating a new DepartmentDAO object. </p>
     * @return a new DepartmentDAO object
     */
    public DepartmentDAO getDepartmentDAO() {
        return new DepartmentDAO(this);
    }
    
    /**
     * <p> Method for creating a new EmployeeDAO object. </p>
     * @return a new EmployeeDAO object
     */
    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAO(this);
    }
    
    /**
     * <p> Method for creating a new AbsenteeismDAO object. </p>
     * @return a new AbsenteeismDAO object
     */
    public AbsenteeismDAO getAbsenteeismDAO() {
        return new AbsenteeismDAO(this);
    }
}
