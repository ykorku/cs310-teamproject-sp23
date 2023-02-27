package edu.jsu.mcis.cs310.tas_sp23;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {

    private int id;
    private final int terminalid;
    private final Badge badge;
    private final EventType punchtype;
    private LocalDateTime originalTimeStamp;
    private PunchAdjustmentType adjustmenttime = null;

    public Punch(int terminalid, Badge badge, EventType punchtype) {
        
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        
    }
    
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originalTimeStamp, EventType punchtype) {
        
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
        this.originalTimeStamp = originalTimeStamp;
        this.punchtype = punchtype;
        
    }

    public int getId() {
        return id;
    }
    
    public int getTerminalid() {
        return terminalid;
    }
    
    public Badge getBadge() {
        return badge;
    }
    
    public EventType getPunchtype() {
        return punchtype;
    }
    
    public LocalDateTime getOriginaltimestamp() {
        return originalTimeStamp;
    }
    
    public String printOriginal() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateText = originalTimeStamp.format(formatter);
        
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeText = originalTimeStamp.format(formatter);
        
        String dayOfWeek = originalTimeStamp.getDayOfWeek().toString().substring(0, 3).toUpperCase();
        
        StringBuilder s = new StringBuilder();        
        s.append("#").append(badge.getId()).append(" ");
        s.append(punchtype).append(": ").append(dayOfWeek).append(" ").append(dateText).append(" ").append(timeText);
        
        return s.toString();

    }

}
