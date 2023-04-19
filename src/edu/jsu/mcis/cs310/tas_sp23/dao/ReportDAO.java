/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.jsu.mcis.cs310.tas_sp23.dao;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Data Access Object (DAO) for retrieving report data from the database.
 * Provides methods for generating badge summary report for employees.
 * 
 * @author yunus
 */
public class ReportDAO {
    
    private static final String QUERY_FIND = "SELECT firstname, middlename, lastname, employee.departmentid, employeetype.description, badge.description, department.description, badgeid  FROM employee JOIN department ON employee.departmentid=department.id JOIN employeetype ON employee.employeetypeid=employeetype.id JOIN badge ON employee.badgeid=badge.id ORDER BY badge.description";
    private static final String QUERY_FIND_byID ="SELECT firstname, middlename, lastname, employee.departmentid, employeetype.description, badge.description, department.description, badgeid  FROM employee JOIN department ON employee.departmentid=department.id JOIN employeetype ON employee.employeetypeid=employeetype.id JOIN badge ON employee.badgeid=badge.id WHERE employee.departmentid=? ORDER BY badge.description ";
            
    private final DAOFactory daoFactory;
    
    /**
     * <p>Constructs a ReportDAO object with the given DAOFactory.</p>
     * 
     * @param daoFactory the DAOFactory to use for obtaining database connections
     */
    ReportDAO(DAOFactory aThis) {
        this.daoFactory = aThis;
    }
    
    /**
     * <p>Generates a badge summary report for employees.</p>
     * 
     * @param departmentId the ID of the department to generate the report for, or null to generate report for all departments
     * @return a JSON string representation of the badge summary report
     * @throws DAOException if a database error occurs
     */
    public String getBadgeSummary(Integer departmentId) {
        
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
                        jsonObject.put("type", employeetype);

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
        String json = Jsoner.serialize(jsonArray);
        return json;
    }

}
