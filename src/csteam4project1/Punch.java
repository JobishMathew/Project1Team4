package csteam4project1;
import java.util.*;

public class Punch {
    private final int MINUTES_IN_HOUR = 60;
    private int punchID;
    private int terminalID;
    private String badgeID; 
    private GregorianCalendar originalTimestamp;
    private int eventTypeID;
    private String eventData;
    private GregorianCalendar adjustedTimestamp;
    private String adjustmentType;

    public Punch(String badgeID, int terminalID, int eventTypeID){
        this.punchID = 0;
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimestamp = new GregorianCalendar();
        this.eventTypeID = eventTypeID;
        this.eventData = null;
        this.adjustedTimestamp = new GregorianCalendar();
        adjustmentType = "";
    }
        
    public Punch (int punchID, int terminalID, String badgeID, long 
                            originalTimestamp, int eventTypeID, String 
                            eventData,long adjustedTimestamp) {
        this.punchID = punchID;
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimestamp = new GregorianCalendar();
        this.originalTimestamp.setTimeInMillis(originalTimestamp);
        this.eventTypeID = eventTypeID;
        this.eventData = eventData;
        this.adjustedTimestamp = new GregorianCalendar();
        this.adjustedTimestamp.setTimeInMillis(adjustedTimestamp);
        adjustmentType = "";
    }
    
    public Punch() {
        this(0 , 0 , "", 0, 0, "", 0);
    }

    public int getPunchID() {
        return punchID;
    }

    public void setPunchID(int punchID) {
        this.punchID = punchID;
    }

    public int getTerminalID() {
        return terminalID;
    }

    public void setTerminalID(int terminalID) {
        this.terminalID = terminalID;
    }

    public String getBadgeID() {
        return badgeID;
    }

    public void setBadgeID(String badgeID) {
        this.badgeID = badgeID;
    }

    public GregorianCalendar getOriginalTimestamp() {
        return originalTimestamp;
    }

    public void setOriginalTimestamp(GregorianCalendar originalTimestamp) {
        this.originalTimestamp = originalTimestamp;
    }

    public int getEventTypeID() {
        return eventTypeID;
    }

    public void setEventTypeID(int eventTypeID) {
        this.eventTypeID = eventTypeID;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public GregorianCalendar getAdjustedTimestamp() {
        return adjustedTimestamp;
    }

    public void setAdjustedTimestamp(GregorianCalendar adjustedTimestamp) {
        this.adjustedTimestamp = adjustedTimestamp;
    }

    private String getEventType(int eventTypeID) {
        switch (eventTypeID) {
            case 0:
                return "CLOCKED OUT: ";
            case 1:
                return "CLOCKED IN: ";
            default:
                return "TIMED OUT: ";
        }
    }
    
    public String getAdjustmentType(){
        return adjustmentType;
    }
    
    private String padInt(int padding) {
        if (padding < 10) return "0" + padding;
        else return padding + "";
    }
        
    private String correctDayOfWeek(String DOW){
        
        return DOW.toUpperCase();
    }
    
     public void adjust(Shift s){
        GregorianCalendar start = new GregorianCalendar(originalTimestamp.get(Calendar.YEAR), originalTimestamp.get(Calendar.MONTH), originalTimestamp.get(Calendar.DAY_OF_MONTH), s.getStart().getHours(), s.getStart().getMinutes());
        GregorianCalendar stop = new GregorianCalendar(originalTimestamp.get(Calendar.YEAR), originalTimestamp.get(Calendar.MONTH), originalTimestamp.get(Calendar.DAY_OF_MONTH), s.getStop().getHours(), s.getStop().getMinutes());
        GregorianCalendar lunchStart = new GregorianCalendar(originalTimestamp.get(Calendar.YEAR), originalTimestamp.get(Calendar.MONTH), originalTimestamp.get(Calendar.DAY_OF_MONTH), s.getLunchstart().getHours(), s.getLunchstart().getMinutes());
        GregorianCalendar lunchStop = new GregorianCalendar(originalTimestamp.get(Calendar.YEAR), originalTimestamp.get(Calendar.MONTH), originalTimestamp.get(Calendar.DAY_OF_MONTH), s.getLunchstop().getHours(), s.getLunchstop().getMinutes());

        GregorianCalendar grace = new GregorianCalendar();
        GregorianCalendar dock = new GregorianCalendar();
        
        if(originalTimestamp.get(Calendar.DAY_OF_WEEK) == 7 || originalTimestamp.get(Calendar.DAY_OF_WEEK) == 1){
            adjustedTimestamp.setTimeInMillis(originalTimestamp.getTimeInMillis());
            adjustedTimestamp.set(Calendar.SECOND, 0);
            if(originalTimestamp.get(Calendar.MINUTE) % s.getInterval() != 0){
                if(originalTimestamp.get(Calendar.MINUTE) % s.getInterval() <= s.getInterval()/2){
                    adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) - originalTimestamp.get(Calendar.MINUTE) % s.getInterval());
                }
                else{
                    adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) + (s.getInterval() - originalTimestamp.get(Calendar.MINUTE) % s.getInterval()));
                }
            }
            adjustmentType = "Interval Round";
        }
        else if(eventTypeID == 1){
            grace.setTimeInMillis(start.getTimeInMillis());
            grace.roll(Calendar.MINUTE, s.getGraceperiod());
            dock.setTimeInMillis(start.getTimeInMillis());
            dock.roll(Calendar.MINUTE, s.getDock());
            
            if(originalTimestamp.getTimeInMillis() > lunchStart.getTimeInMillis()){
                adjustedTimestamp.setTimeInMillis(lunchStop.getTimeInMillis());
                adjustmentType = "Lunch Stop";
            }
            else if((originalTimestamp.getTimeInMillis() <= grace.getTimeInMillis()) && (originalTimestamp.get(Calendar.HOUR_OF_DAY) == grace.get(Calendar.HOUR_OF_DAY))){
                adjustedTimestamp.setTimeInMillis(start.getTimeInMillis());
                adjustmentType = "Shift Start";
            }
            else if((originalTimestamp.get(Calendar.MINUTE) >= (start.get(Calendar.MINUTE) + (MINUTES_IN_HOUR - s.getDock())))  && (originalTimestamp.get(Calendar.HOUR_OF_DAY) == start.get(Calendar.HOUR_OF_DAY) -1)){
                adjustedTimestamp.setTimeInMillis(start.getTimeInMillis());
                adjustmentType = "Shift Start";
            }
            else if((originalTimestamp.get(Calendar.MINUTE) > (start.get(Calendar.MINUTE) + s.getGraceperiod())) && (originalTimestamp.get(Calendar.HOUR_OF_DAY) <= start.get(Calendar.MINUTE) + s.getDock())){
                adjustedTimestamp.setTimeInMillis(start.getTimeInMillis());
                adjustedTimestamp.roll(Calendar.MINUTE, s.getDock());
                adjustmentType = "Shift Dock";
            }
            else{
                adjustedTimestamp.setTimeInMillis(originalTimestamp.getTimeInMillis());
                if(originalTimestamp.get(Calendar.MINUTE) % s.getInterval() != 0){
                    adjustedTimestamp.set(Calendar.SECOND, 0);
                    if(originalTimestamp.get(Calendar.MINUTE) % s.getInterval() <= s.getInterval()/2){
                        adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) - originalTimestamp.get(Calendar.MINUTE) % s.getInterval()); 
                    }
                    else{
                        adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) + (s.getInterval() - originalTimestamp.get(Calendar.MINUTE) % s.getInterval()));
                    }
                    adjustmentType = "Interval Round";
                }
                else {adjustmentType = "None";}
            }
        }
        else if(eventTypeID == 0){
            grace.setTimeInMillis(stop.getTimeInMillis());
            grace.roll(Calendar.MINUTE, -1 * s.getGraceperiod());
            dock.setTimeInMillis(stop.getTimeInMillis());
            dock.roll(Calendar.MINUTE, -1 * s.getDock());
            
            if((originalTimestamp.getTimeInMillis() >= grace.getTimeInMillis()) && (originalTimestamp.get(Calendar.HOUR_OF_DAY) == grace.get(Calendar.HOUR_OF_DAY))){
                adjustedTimestamp.setTimeInMillis(stop.getTimeInMillis());
                adjustmentType = "Shift Stop";
            }
            else if((originalTimestamp.getTimeInMillis() > dock.getTimeInMillis()) && (originalTimestamp.get(Calendar.HOUR_OF_DAY) == dock.get(Calendar.HOUR_OF_DAY))){
                adjustedTimestamp.setTimeInMillis(stop.getTimeInMillis());
                adjustmentType = "Shift Stop";
            }
            else if((originalTimestamp.getTimeInMillis() < grace.getTimeInMillis()) && (originalTimestamp.getTimeInMillis() >= dock.getTimeInMillis())){
                adjustedTimestamp.setTimeInMillis(stop.getTimeInMillis());
                adjustedTimestamp.roll(Calendar.MINUTE, MINUTES_IN_HOUR - s.getDock());
                adjustmentType = "Shift Dock";
            }
            else if(originalTimestamp.getTimeInMillis() < lunchStop.getTimeInMillis()){
                adjustedTimestamp.setTimeInMillis(lunchStart.getTimeInMillis());
                adjustmentType = "Lunch Start";
            }
            else{
                adjustedTimestamp.setTimeInMillis(originalTimestamp.getTimeInMillis());
                if(originalTimestamp.get(Calendar.MINUTE) % s.getInterval() != 0){
                    adjustedTimestamp.set(Calendar.SECOND, 0);
                    if((originalTimestamp.get(Calendar.MINUTE) % s.getInterval() <= s.getInterval()/2)){
                        if((originalTimestamp.get(Calendar.MINUTE) % s.getInterval() == s.getInterval()/2) && originalTimestamp.get(Calendar.SECOND) > 0){
                            adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) + (s.getInterval() - originalTimestamp.get(Calendar.MINUTE) % s.getInterval()));
                        }
                        else{
                        adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) - originalTimestamp.get(Calendar.MINUTE) % s.getInterval());
                        }
                    }
                    else{
                        adjustedTimestamp.set(Calendar.MINUTE, originalTimestamp.get(Calendar.MINUTE) + (s.getInterval() - originalTimestamp.get(Calendar.MINUTE) % s.getInterval()));
                    }
                    adjustmentType = "Interval Round";
                }
                else {adjustmentType = "None";}
            }
        }
    }
         
    public String printOriginalTimestamp() {
        return "#" + badgeID + " " + getEventType(eventTypeID) + correctDayOfWeek(originalTimestamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
        + " " + padInt(originalTimestamp.get(Calendar.MONTH) + 1) + "/" + padInt(originalTimestamp.get(Calendar.DAY_OF_MONTH)) + "/" + originalTimestamp.get(Calendar.YEAR) + 
        " " + padInt(originalTimestamp.get(Calendar.HOUR_OF_DAY)) + ":" + padInt(originalTimestamp.get(Calendar.MINUTE)) + ":" + padInt(originalTimestamp.get(Calendar.SECOND));
    }
        
    public String printAdjustedTimestamp() {
        return "#" + badgeID + " " + getEventType(eventTypeID) + correctDayOfWeek(adjustedTimestamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
        + " " + padInt(adjustedTimestamp.get(Calendar.MONTH) + 1) + "/" + padInt(adjustedTimestamp.get(Calendar.DAY_OF_MONTH)) + "/" + adjustedTimestamp.get(Calendar.YEAR) + 
        " " + padInt(adjustedTimestamp.get(Calendar.HOUR_OF_DAY)) + ":" + padInt(adjustedTimestamp.get(Calendar.MINUTE)) + ":" + padInt(adjustedTimestamp.get(Calendar.SECOND)) + " (" +adjustmentType+ ")";
    }
}