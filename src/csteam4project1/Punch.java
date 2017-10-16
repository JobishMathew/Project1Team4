package csteam4project1;
import java.sql.Timestamp;
import java.util.*;
/**
 *
 * @author Aaron Branham and Cole Landers
 */
public class Punch { 
	private String terminalID;
	private String badgeID; 
        private String originalTimeStamp; //change to GregorianCal 
        private String eventTypeID;
        private String eventData;
	private String adjustedTimeStamp; //Change to GregorianCal
	private Calendar cal;
	
	public Punch (String terminalID, String badgeID, String originalTimeStamp, String eventTypeID, 
									String eventData,String adjustedTimeStamp) {
		this.terminalID = terminalID;
		this.badgeID = badgeID;
		this.originalTimeStamp = originalTimeStamp;
		this.eventTypeID = eventTypeID;
		this.eventData = eventData;
		this.adjustedTimeStamp = adjustedTimeStamp;
                GregorianCalendar calendar = new GregorianCalendar();
                Timestamp ts2 = Timestamp.valueOf("2017-08-01 05:54:04");
                long ts2Time = ts2.getTime();
                calendar.setTimeInMillis(ts2Time);
                System.out.println(ts2Time);
		cal = toGregorian(originalTimeStamp);
	}
	
	private String getEventType(String eventTypeID) {
		if (eventTypeID.equals("0")) return "CLOCKED OUT: ";
		else if (eventTypeID.equals("1")) return "CLOCKED IN: ";
		else return "TIMED OUT: ";
	}
	
	private Calendar toGregorian(String originalTimeStamp) {
		int year = Integer.parseInt(originalTimeStamp.substring(0,4));
		int month = Integer.parseInt(originalTimeStamp.substring(5,7));
		int dayOfMonth = Integer.parseInt(originalTimeStamp.substring(8,10));
		int hourOfDay = Integer.parseInt(originalTimeStamp.substring(11,13));
		int minute = Integer.parseInt(originalTimeStamp.substring(14,16));
		int second = Integer.parseInt(originalTimeStamp.substring(17,19));
		cal = GregorianCalendar.getInstance();
		cal.set(year, month - 1, dayOfMonth, hourOfDay, minute, second);
		return cal;
	}
	
	public void setAdjustedTimestamp(String ats) {
		adjustedTimeStamp = ats;
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