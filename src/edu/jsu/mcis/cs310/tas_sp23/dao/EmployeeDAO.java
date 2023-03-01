package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Department;
import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import edu.jsu.mcis.cs310.tas_sp23.Punch;
import edu.jsu.mcis.cs310.tas_sp23.Employee;
import edu.jsu.mcis.cs310.tas_sp23.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp23.EventType;

import java.sql.*;
import java.time.LocalDateTime;

public class EmployeeDAO {
    
    private static final String QUERY_FIND_FROM_ID = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND_FROM_BADGE = "SELECT * FROM employee WHERE badgeid = ?";
    
    private final DAOFactory daoFactory;
    
    EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
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
                        
                        int departid = rs.getInt("departmentid");
                        int shiftid = rs.getInt("shiftid");
                        
                        EmployeeDAO employeeDAO = new EmployeeDAO(daoFactory);
                        BadgeDAO badgeDAO = new BadgeDAO(daoFactory);
                        DepartmentDAO departmentDAO = new DepartmentDAO(daoFactory);
                        ShiftDAO shiftDAO = new ShiftDAO(daoFactory);
                        
                        String badgeId = rs.getString("badgeid");
                        Badge badge = badgeDAO.find(badgeId);
                        Department department = departmentDAO.find(departid);
                        Shift shift = shiftDAO.find(id);
                        
                        String firstname = rs.getString("firstname");
                        String middlename = rs.getString("middlename");
                        String lastname = rs.getString("lastname");
                        
                        //Get eventtype. 
                        
                        int employeetype = rs.getInt("employeetypeid");
                        EmployeeType employeetypeid = EmployeeType.values()[employeetype];
  
                        //Get timestamp from database. It must be casted into LocalDateTime. 
                        
                        java.sql.Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
                        timestamp = rs.getTimestamp("active");
                        LocalDateTime active = timestamp.toLocalDateTime();  

                        //create employee variable.
                        
                        employee = new Employee(id, firstname, lastname, middlename, active, badge, department, shift, employeetypeid);
                        
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
