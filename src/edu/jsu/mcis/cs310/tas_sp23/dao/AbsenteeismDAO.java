package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp23.Employee;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class AbsenteeismDAO {
    
    private static final String QUERY_FIND = "SELECT percentage FROM absenteeism WHERE employeeid=? AND payperiod=?";
    private static final String QUERY_CREATE = "INSERT INTO absenteeism (employeeid, payperiod, percentage) " + "VALUES (?, ?, ?)";
    private static final String QUERY_DELETE = "DELETE FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    
    private final DAOFactory daoFactory;

    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
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
