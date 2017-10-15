package csteam4project1;
import java.util.*;
/**
 *
 * @author Aaron Branham and Cole Landers
 */
public class Punch {
	private int punchID; 
	private int terminalID;
	private String badgeID; 
	private GregorianCalendar originalTimeStamp; //change to GregorianCal 
	private int eventTypeID;
	private String eventData;
	private GregorianCalendar adjustedTimeStamp; //Change to GregorianCal
	
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
	
	public String printAdjustedTimestamp() {
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
	
	public String printOriginalTimestamp() {
		return "#" + badgeID + " " + getEventType(eventTypeID) + correctDOW(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
		+ " " + padInt(cal.get(Calendar.MONTH) + 1) + "/" + padInt(cal.get(Calendar.DAY_OF_MONTH)) + "/" + cal.get(Calendar.YEAR) + 
		" " + padInt(cal.get(Calendar.HOUR_OF_DAY)) + ":" + padInt(cal.get(Calendar.MINUTE)) + ":" + padInt(cal.get(Calendar.SECOND));
	}
}