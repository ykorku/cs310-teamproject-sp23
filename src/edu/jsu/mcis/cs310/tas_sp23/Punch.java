package edu.jsu.mcis.cs310.tas_sp23;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class Punch {

    private int id;
    private final int terminalid;
    private final Badge badge;
    private final EventType punchtype;
    private LocalDateTime originalTimeStamp;
    private LocalDateTime adjustedTimeStamp;
    private PunchAdjustmentType adjustmentType = PunchAdjustmentType.NONE;

    public Punch(int terminalid, Badge badge, EventType punchtype) {
        
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchtype = punchtype;
    }

    public Punch(int id, int terminalid, Badge badge,
            LocalDateTime originalTimeStamp, EventType punchtype) {
        
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

    private long adjust_helper(LocalTime start, LocalTime time, int round) {
        //adjustmentType = adjustmentType.INTERVAL_ROUND;
        return ((Math.floorDiv((Duration.between(start, time)
                .toMinutes()), round))+1)*round;
    }
    
    private LocalTime over_schedule(LocalTime original, int round) {
        double mins = (original.getHour() * 60) + original.getMinute();
        double rnd = round;
        int before = (int)((Math.floor(mins/rnd))*rnd);
        int after = (int)((Math.ceil(mins/rnd)) * rnd);
        LocalTime time = original;
        
        if (Math.abs(before - mins) == Math.abs(after - mins)) {
            time = LocalTime.of(((int)(mins/60)), ((int)(mins%60)));
            adjustmentType = adjustmentType.NONE;
        } else if (Math.abs(before - mins) > Math.abs(after - mins)) {
            time = LocalTime.of((after/60),(after%60));
            adjustmentType = adjustmentType.INTERVAL_ROUND;
        } else if (Math.abs(before - mins) < Math.abs(after - mins)) {
            time = LocalTime.of((before/60),(before%60));
            adjustmentType = adjustmentType.INTERVAL_ROUND;
        }

        return time;
    }

    private LocalDateTime round_time(LocalDateTime original) {
        LocalDateTime temp = original;
        int sec = original.toLocalTime().getSecond();
        TimeZone tz = TimeZone.getDefault();
        
        if(sec >= 30) {
            temp = original.plusMinutes(1);
        }
        
        return temp;
    }

    public void adjust(Shift s) {
        LocalDateTime rndDateTime = round_time(getOriginaltimestamp());
        
        LocalTime original_time = rndDateTime.toLocalTime()
                .truncatedTo(ChronoUnit.MINUTES);
        LocalDate original_date = rndDateTime.toLocalDate();

        String punch_type = getPunchtype().toString();
        // Adjusted shift start times
        LocalTime startRound = s.getShiftstart()
                .minus(Duration.ofMinutes((long)s.getRoundinterval()));
        LocalTime startGrace = s.getShiftstart()
                .plus(Duration.ofMinutes((long)s.getGraceperiod()));
        LocalTime startDock = s.getShiftstart()
                .plus(Duration.ofMinutes((long)s.getDockpenalty()));
        String day = getOriginaltimestamp().getDayOfWeek().toString().toLowerCase();

        // Adjusted shift stop times
        LocalTime stopRound = s.getShiftstop()
                .plus(Duration.ofMinutes((long)s.getRoundinterval()));
        LocalTime stopGrace = s.getShiftstop()
                .minus(Duration.ofMinutes((long)s.getGraceperiod()));
        LocalTime stopDock = s.getShiftstop()
                .minus(Duration.ofMinutes((long)s.getDockpenalty()));

        // Start and stop times for lunch
        LocalTime lunchStart = s.getLunchstart();
        LocalTime lunchStop = s.getLunchstop();

        // Weekday time adjustment
        if (!(day.equals("saturday") || day.equals("sunday"))) {
            switch(punch_type) {
                case "CLOCK IN":
                    // Adjust lunch punch in to end of lunch
                    if (original_time.isAfter(lunchStart) && original_time.isBefore(lunchStop)) {
                        adjustedTimeStamp = LocalDateTime.
                                of(original_date, lunchStop);
                        adjustmentType = adjustmentType.LUNCH_STOP;
                    // Punch between 15 min before and 5 min after shift start
                    } else if (original_time.isAfter(startRound) && original_time.isBefore(startGrace)) {
                        adjustedTimeStamp = LocalDateTime
                                .of(original_date, s.getShiftstart());
                        adjustmentType = adjustmentType.SHIFT_START;
                    // Punch more than 15 mins before shift start
                    } else if (original_time.isBefore(startRound)) {
                        adjustedTimeStamp = LocalDateTime.of(original_date,
                                over_schedule(original_time,
                                        s.getRoundinterval()));
                    } else if (original_time.isAfter(startGrace) || original_time.equals(startDock)) {
                        adjustedTimeStamp = LocalDateTime.of(original_date, startDock);
                        adjustmentType = adjustmentType.SHIFT_DOCK;
                    }
                    break;
                case "CLOCK OUT":
                    // Adjust lunch punch out to beginning of lunch
                    if (original_time.isAfter(lunchStart) && original_time.isBefore(lunchStop)) {
                        adjustedTimeStamp = LocalDateTime.of(original_date, lunchStart);
                        adjustmentType = adjustmentType.LUNCH_START;
                    // Punch between 15 min after and 5 min before shift ends
                    } else if (original_time.isAfter(stopGrace) && original_time.isBefore(stopRound)) {
                        adjustedTimeStamp = LocalDateTime
                                .of(original_date, s.getShiftstop());
                        adjustmentType = adjustmentType.SHIFT_STOP;
                    // Punch more than 15 mins after shift end
                    } else if (original_time.isAfter(stopRound)) {
                        adjustedTimeStamp = LocalDateTime.of(original_date,
                                over_schedule(original_time,
                                        s.getRoundinterval()));
                    } else if (original_time.isBefore(stopGrace)) {
                        adjustedTimeStamp = LocalDateTime.of(original_date, stopDock);
                        if (adjustedTimeStamp.isAfter(rndDateTime)) {
                            adjustmentType = adjustmentType.INTERVAL_ROUND;
                        } else if (adjustedTimeStamp.equals(rndDateTime) || adjustedTimeStamp.isBefore(rndDateTime)) {
                            adjustmentType = adjustmentType.SHIFT_DOCK;
                        }
                    }
                    break;
                case "TIME OUT":
                    System.out.println("TIME OUT");
                    break;
                default:
                    System.out.println("Default");
                    break;
            }
        // Weekend time adjustment
        } else {
            switch(punch_type) {
                case "CLOCK IN":
                    adjustedTimeStamp = LocalDateTime.of(original_date,
                            over_schedule(original_time,
                                    s.getRoundinterval()));
                    break;
                case "CLOCK OUT":
                    adjustedTimeStamp = LocalDateTime.of(original_date,
                            over_schedule(original_time,
                                        s.getRoundinterval()));
                    break;
                case "TIME OUT":
                    System.out.println("TIME OUT");
                    break;
                default:
                    System.out.println("Default");
                    break;
            }
        }
    }
    
    public String printOriginal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateText = originalTimeStamp.format(formatter);

        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeText = originalTimeStamp.format(formatter);

        String dayOfWeek = originalTimeStamp.getDayOfWeek().toString()
                .substring(0, 3).toUpperCase();
        
        StringBuilder s = new StringBuilder();
        s.append("#").append(badge.getId()).append(" ");
        s.append(punchtype).append(": ").append(dayOfWeek).append(" ")
                .append(dateText).append(" ").append(timeText);
        
        return s.toString();

    }

    public String printAdjusted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String dateText = originalTimeStamp.format(formatter);
        
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeText = adjustedTimeStamp.format(formatter);
        
        String dayOfWeek = originalTimeStamp.getDayOfWeek().toString()
                .substring(0, 3).toUpperCase();
        
        StringBuilder s = new StringBuilder();
        s.append("#").append(badge.getId()).append(" ")
                .append(punchtype).append(": ").append(dayOfWeek)
                .append(" ").append(dateText).append(" ")
                .append(timeText).append(" (").append(adjustmentType)
                .append(")");
        
        return s.toString();
    }
}
