package edu.jsu.mcis.cs310.tas_sp23.dao;

import edu.jsu.mcis.cs310.tas_sp23.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp23.Employee;
import java.sql.*;
import java.time.LocalDate;

public class AbsenteeismDAO {
    
    private final DAOFactory daoFactory;

    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public void find(Employee employee, LocalDate localDate) {
        
    }
    
    public void create(Absenteeism absent) {
        
    }
}
