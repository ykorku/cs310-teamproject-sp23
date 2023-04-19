package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp23.Employee;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;


/**
 * <p> The Absenteeism class provides an interface for accessing, modifying, and
 * creating Employee objects in the database. </p>
 * @author Dalton Estes
 */
public class AbsenteeismDAO {
    
    /**
     * <p> A string query statement in SQL format used by {@link #find find()} 
     * method to interact with the database to find an employee with a specific
     * id. </p>
     */
    private static final String QUERY_FIND = "SELECT percentage FROM absenteeism WHERE employeeid=? AND payperiod=?";
    
    /**
     * <p> A string query statement in SQL format used by {@link #create create()} 
     * method to insert into the database an employee's absenteeism with a specific
     * id. </p>
     */
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) " + "VALUES (?, ?, ?)";
    
    /**
     * <p> A string query statement in SQL format used by {@link #create create()} 
     * method to delete from the database an employee's absenteeism with a specific
     * id. </p>
     */
    private static final String QUERY_DELETE = "DELETE FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    
    /**
     * <p> A DAOFactory object used by {@link #AbsenteeismDAO AbsenteeismDAO()} to construct
     * an AbsenteeismDAO object. </p>
     */
    private final DAOFactory daoFactory;

    /**
    * <p> Constructs an AbsenteeismDAO object with the provided DAOFactory. </p>
    * @param daoFactory the DAOFactory used to create the DAO objects
    */
    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    /**
     * <p> Method for finding absenteeism entries within the database and 
     * returning them. </p>
     * @param employee represents an employee of the company within the database
     * @param payPeriodStart time variable representing the start of a pay period
     * @return Absenteeism object representing an employee's absenteeism as a 
     * percentage
     */
    public Absenteeism find(Employee employee, LocalDate payPeriodStart){
        Absenteeism abs = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, employee.getId());
                ps.setDate(2, java.sql.Date.valueOf(payPeriodStart));

                boolean hasresults = ps.execute();

                if (hasresults) {
                    rs = ps.getResultSet();
                    if (rs.next()) {
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                        abs = new Absenteeism(employee, payPeriodStart, percentage);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
        return abs;
    }
    
    /**
     * <p> This is a method for creating absentee entries within the database. </p>
     * @param absent Absenteeism object representing an employees absence 
     */
    public void create(Absenteeism absent) {
    Connection conn = null;
    PreparedStatement ps_create = null;
    PreparedStatement ps_delete = null;
    PreparedStatement ps_find = null;

    try {
        conn = daoFactory.getConnection();
        
        if (conn.isValid(0)) {
            ps_find = conn.prepareStatement(QUERY_FIND);
            ps_find.setInt(1, absent.getEmployee().getId());
            ps_find.setDate(2, java.sql.Date.valueOf(absent.getPayPeriodStart()));
            
            Absenteeism results = find(absent.getEmployee(),
                    absent.getPayPeriodStart());
            
            if (results != null) {
                ps_delete = conn.prepareStatement(QUERY_DELETE);
                ps_delete.setInt(1, absent.getEmployee().getId());
                ps_delete.setDate(2, Date.valueOf(absent.getPayPeriodStart()));
                
                ps_delete.execute();
            }
            
            ps_create = conn.prepareStatement(QUERY_CREATE);
            ps_create.setInt(1, absent.getEmployee().getId());
            ps_create.setDate(2, Date.valueOf(absent.getPayPeriodStart()));
            ps_create.setBigDecimal(3, absent.getBigDec());
            ps_create.execute();
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (ps_find != null) { try { ps_find.close(); } catch (Exception e) { e.printStackTrace(); } }
        if (ps_create != null) { try { ps_create.close(); } catch (Exception e) { e.printStackTrace(); } }
        if (ps_delete != null) { try { ps_delete.close(); } catch (Exception e) { e.printStackTrace(); } }
        }
    }
}
