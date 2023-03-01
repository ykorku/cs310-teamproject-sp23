/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Badge;
import edu.jsu.mcis.cs310.tas_sp23.Shift;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author yunus
 */
public class ShiftDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM shift WHERE id = ?";
    
    private static final String QUERY_FIND1 = "SELECT * FROM employee WHERE badgeid = ?";

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

                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, i1);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();
                    
                  

                    while (rs.next()) {

                        HashMap<String, String> shiftValues = new HashMap<>();
                        
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

                ps = conn.prepareStatement(QUERY_FIND1);
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
    
}
