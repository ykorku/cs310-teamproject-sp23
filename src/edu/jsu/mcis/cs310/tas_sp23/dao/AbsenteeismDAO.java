package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Absenteeism;
import java.sql.*;

public class AbsenteeismDAO {
    
    private static final String QUERY = "SELECT * FROM ABSENTEEISM";
    private final DAOFactory daoFactory;

    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    
}
