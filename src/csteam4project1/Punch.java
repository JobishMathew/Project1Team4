package csteam4project1;
import java.sql.Timestamp;
import java.util.*;

public class Punch {
	private int punchID; 
	private int terminalID;
	private String badgeID; 

	private GregorianCalendar originalTimeStamp;
	private int eventTypeID;
	private String eventData;
	private GregorianCalendar adjustedTimeStamp;

	
    public Punch(String badgeID, int terminalID, int eventTypeID){
        this.punchID = 0;
        this.terminalID = terminalID;
        this.badgeID = badgeID;
        this.originalTimeStamp = new GregorianCalendar();
        this.eventTypeID = eventTypeID;
        this.eventData = null;
        this.adjustedTimeStamp = new GregorianCalendar();
    }
        
	public Punch (int punchID, int terminalID, String badgeID, long 
				 originalTimeStamp, int eventTypeID, String 
				 eventData,long adjustedTimeStamp) {
		this.punchID = punchID;
		this.terminalID = terminalID;
		this.badgeID = badgeID;
		this.originalTimeStamp = new GregorianCalendar();
		this.originalTimeStamp.setTimeInMillis(originalTimeStamp);
		this.eventTypeID = eventTypeID;
		this.eventData = eventData;

		this.adjustedTimeStamp = new GregorianCalendar();
		this.adjustedTimeStamp.setTimeInMillis(adjustedTimeStamp);

	}
	
	private String getEventType(int eventTypeID) {
		if (eventTypeID == 0) return "CLOCKED OUT: ";
		else if (eventTypeID == 1) return "CLOCKED IN: ";
		else return "TIMED OUT: ";
	}
	
	public void setAdjustedTimestamp(long ats) {
		adjustedTimeStamp.setTimeInMillis(ats);
	}
	
	public GregorianCalendar printAdjustedTimestamp() {
		return adjustedTimeStamp;
	}
	
	private String padInt(int padding) {
		if (padding < 10) return "0" + padding;
		else return padding + "";
	}
        
    private String correctDOW(String DOW){
        String correctDOW = DOW.toUpperCase();
        return correctDOW;
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

    public GregorianCalendar getOriginalTimeStamp() {
        return originalTimeStamp;
    }

    public void setOriginalTimeStamp(GregorianCalendar originalTimeStamp) {
        this.originalTimeStamp = originalTimeStamp;
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

    public GregorianCalendar getAdjustedTimeStamp() {
        return adjustedTimeStamp;
    }

    public void setAdjustedTimeStamp(GregorianCalendar adjustedTimeStamp) {
        this.adjustedTimeStamp = adjustedTimeStamp;
    }
    
    public void adjust(Shift s){
        long startInMillis = s.getStart().getTime();
        long stopInMillis = s.getStop().getTime();
        long lunchStartInMillis = s.getLunchstart().getTime();
        long lunchStopInMillis = s.getLunchstop().getTime();
        long originalPunchInMillis = originalTimeStamp.getTimeInMillis();

        if(eventTypeID == 1){
            //Implement based on Clocked in
        }
        else if(eventTypeID == 0){
            //Implement based on Clocked out
        }
        else{
            //Implement based on Timed out??
        }
    }
	
	public String printOriginalTimestamp() {
		return "#" + badgeID + " " + getEventType(eventTypeID) + correctDOW(originalTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
		+ " " + padInt(originalTimeStamp.get(Calendar.MONTH) + 1) + "/" + padInt(originalTimeStamp.get(Calendar.DAY_OF_MONTH)) + "/" + originalTimeStamp.get(Calendar.YEAR) + 
		" " + padInt(originalTimeStamp.get(Calendar.HOUR_OF_DAY)) + ":" + padInt(originalTimeStamp.get(Calendar.MINUTE)) + ":" + padInt(originalTimeStamp.get(Calendar.SECOND));
	}
}