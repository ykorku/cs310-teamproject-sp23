package edu.jsu.mcis.cs310.tas_sp23;

public class Department {
    
    private final String description;
    private final int id, terminalid;
    
    public Department(int id, String description, int terminalid){
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    public int getId(){
        return id;
    }
    
    public String getDescription(){
        return description;
    }
    
    public int getTerminalid(){
        return terminalid;
    }
    
    @Override
    public String toString(){
        
        StringBuilder s = new StringBuilder();
        
        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');
        s.append(", Terminal ID: ").append(terminalid);
        
        return s.toString();
    }
}
