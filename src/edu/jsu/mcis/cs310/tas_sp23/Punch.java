package edu.jsu.mcis.cs310.tas_sp23;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {

    private int id;
    private int terminalid;
    private Badge badge;
    private EventType punchtype;
    private LocalDateTime originaltimestamp;
    private PunchAdjustmentType adjustmenttime = null;

    public Punch(int terminalid, Badge badge, EventType punchtype) {
        
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
        
    }
    
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype) {
        
        this.id = id;
        this.terminalid = terminalid;
        this.badge = badge;
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
        return originaltimestamp;
    }
    
    public String printOriginal() {
        
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("");
        String text = date.format(formatter);
        LocalDate parsedData = LocalDate.parse(text, formatter);
        
        StringBuilder s = new StringBuilder();

        s.append('#').append(badge).append(' ');
        s.append(punchtype).append(':').append(' ');
        s.append(parsedData);

        return s.toString();

    }

}
