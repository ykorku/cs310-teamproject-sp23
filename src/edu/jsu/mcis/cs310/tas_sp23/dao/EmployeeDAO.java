package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Department;
import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import edu.jsu.mcis.cs310.tas_sp23.Employee;
import edu.jsu.mcis.cs310.tas_sp23.EmployeeType;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * <p> The EmployeeDAO class provides an interface for accessing and modifying 
 * Employee objects in the database. </p>
 * @author Dalton Estes
 */
public class EmployeeDAO {
    
    /**
     * <p> A string query statement in SQL format used by {@link #find find()} 
     * method to interact with the database to find an employee with a specific
     * id. </p>
     */
    private static final String QUERY_FIND_FROM_ID = "SELECT * FROM employee WHERE id = ?";
    
    /**
     * <p> A string query statement in SQL format used by {@link #find find()} 
     * method to interact with the database to find an employee with a specific
     * badge id. </p>
     */
    private static final String QUERY_FIND_FROM_BADGE = "SELECT * FROM employee WHERE badgeid = ?";
    
    
    /**
     * <p> A DAOFactory object used by {@link #EmployeeDAO EmployeeDAO()} to construct
     * an EmployeeDAO object. </p>
     */
    private final DAOFactory daoFactory;
    
    
    /**
    * <p> Constructs an EmployeeDAO object with the provided DAOFactory. </p>
    * @param daoFactory the DAOFactory used to create the DAO objects
    */
    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
    * <p> Retrieves an employee from the database with the given ID. </p>
    *
    * @param id the ID of the employee to retrieve
    * @return the Employee object associated with the ID, or null if not found
    * @throws DAOException if there is a problem accessing the database
    */
    public Employee find(int id) {
        
        Employee employee = null;

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
                        
                        //Get name.
                        String firstname = rs.getString("firstname");
                        String middlename = rs.getString("middlename");
                        String lastname = rs.getString("lastname");
                        
                        //Get timestamp from database. It must be casted into LocalDateTime.
                        LocalDateTime active = rs.getTimestamp("active").toLocalDateTime();
                        
                        //Get Badge object.
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        String badgeId = rs.getString("badgeid");
                        Badge badge = badgeDAO.find(badgeId);
                        
                        //Get department object
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory);
                        int departid = rs.getInt("departmentid");
                        Department department = departmentDAO.find(departid);
                        
                        //Get shift object.
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory);
                        int shiftId = rs.getInt("shiftid");
                        Shift shift = shiftDAO.find(shiftId);
                                
                        //Get eventtype.
                        int employeeTypeId = rs.getInt("employeetypeid");
                        EmployeeType employeetype = EmployeeType.values()[employeeTypeId];
  

                        //create employee object.
                        employee = new Employee(id, firstname, lastname, middlename, active, badge, department, shift, employeetype);
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
        return employee;
    }
    
    /**
    * <p> Retrieves an employee from the database with the given Badge. </p>
    *
    * @param b the Badge of the employee to retrieve
    * @return the Employee object associated with the Badge, or null if not found
    * @throws DAOException if there is a problem accessing the database
    */
    public Employee find(Badge b) {
        
        Employee employee = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_FROM_BADGE);
                ps.setString(1, b.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {
                    
                    rs = ps.getResultSet();
                    
                     if (rs.next()) {
                         
                        Integer id = rs.getInt("id");
                        employee = find(id);
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
        return employee;
    }
}
