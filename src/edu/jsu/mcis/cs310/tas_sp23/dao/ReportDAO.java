/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp23.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author yunus
 */
public class ReportDAO {
    
    private static final String QUERY_FIND = "SELECT firstname, middlename, lastname, employee.departmentid, employeetype.description, badge.description, department.description  FROM employee JOIN department ON employee.departmentid=department.id JOIN employeetype ON employee.employeetypeid=employeetype.id JOIN badge ON employee.badgeid=badge.id ORDER BY badge.description";
    private static final String QUERY_FIND_byID ="SELECT firstname, middlename, lastname, employee.departmentid, employeetype.description, badge.description, department.description  FROM employee JOIN department ON employee.departmentid=department.id JOIN employeetype ON employee.employeetypeid=employeetype.id JOIN badge ON employee.badgeid=badge.id WHERE employee.departmentid=? ORDER BY badge.description ";
            
    private final DAOFactory daoFactory;
    
    ReportDAO(DAOFactory aThis) {
        this.daoFactory = aThis;
    }

    public JsonArray getBadgeSummary(Integer departmentId) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        JsonArray jsonArray = new JsonArray();

        try {
            java.sql.Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                if (departmentId != null) {
                    ps = conn.prepareStatement(QUERY_FIND_byID);
                    ps.setInt(1, departmentId);
                } else {
                    ps = conn.prepareStatement(QUERY_FIND);
                }

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
                        String name = rs.getString("badge.description");
                        String badgeid = rs.getString("badgeid");
                        String department = rs.getString("department.description");
                        String employeetype = rs.getString("employeetype.description");
                        

                        // Create a JSON object for each record
                        JsonObject jsonObject = new JsonObject();
                        
                        jsonObject.put("badgeid", badgeid);
                        jsonObject.put("name", name);  
                        jsonObject.put("department", department);
                        jsonObject.put("employeetype", employeetype);

                        jsonArray.add(jsonObject);
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

        return jsonArray;
    }

}
