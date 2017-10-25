package csteam4project1;
import java.util.*;

public class Punch {
        private final int MILLIS_IN_MINUTE = 60000;
        private final int MINUTES_IN_HOUR = 60;
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
	
	public GregorianCalendar getAdjustedTimestamp() {
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
    
    public String getTypePunch(int eventType){
        if(eventType == 1 && (!correctDOW(originalTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())).equals("SAT") || (!correctDOW(originalTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())).equals("SUN")))){
            
            return " (Shift Start)";
        }
        else if(eventType == 0 && (!correctDOW(originalTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())).equals("SAT") || (!correctDOW(originalTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())).equals("SUN")))){
            
            return " (Shift Stop)";
        }
        else{
            return " (Interval Round)";
        }
    }
    
    public void adjust(Shift s){
        int startInMillis = (((s.getStart().getHours() * MINUTES_IN_HOUR) + s.getStart().getMinutes()) * MILLIS_IN_MINUTE);
        int stopInMillis = (((s.getStop().getHours() * MINUTES_IN_HOUR) + s.getStop().getMinutes()) * MILLIS_IN_MINUTE);
        int lunchStartInMillis = (((s.getLunchstart().getHours() * MINUTES_IN_HOUR) + s.getLunchstart().getMinutes()) * MILLIS_IN_MINUTE);
        int lunchStopInMillis = (((s.getLunchstop().getHours() * MINUTES_IN_HOUR) + s.getLunchstop().getMinutes()) * MILLIS_IN_MINUTE);
        int originalPunchInMillis = (((originalTimeStamp.get(Calendar.HOUR) * MINUTES_IN_HOUR) + originalTimeStamp.get(Calendar.MINUTE)) * MILLIS_IN_MINUTE);

        if(eventTypeID == 1){
            if(startInMillis > originalPunchInMillis){
                int minuteDiff = ((startInMillis - originalPunchInMillis)/MILLIS_IN_MINUTE);
                System.out.println(minuteDiff);
                if(minuteDiff <= s.getGraceperiod()){
                    adjustedTimeStamp.set(originalTimeStamp.get(Calendar.YEAR), originalTimeStamp.get(Calendar.MONTH), originalTimeStamp.get(Calendar.DATE));
                    adjustedTimeStamp.set(Calendar.HOUR_OF_DAY, s.getStart().getHours());
                    adjustedTimeStamp.set(Calendar.MINUTE, s.getStart().getMinutes());
                }
                else{
                    adjustedTimeStamp.set(originalTimeStamp.get(Calendar.YEAR), originalTimeStamp.get(Calendar.MONTH), originalTimeStamp.get(Calendar.DATE));
                    
                    int actualMinute = Math.abs(MINUTES_IN_HOUR - minuteDiff);
                    int roll = actualMinute % s.getInterval();
                    
                    if((roll != 0) && (roll <= (s.getInterval()/2))){
                        actualMinute = actualMinute - roll;
                    }
                    else if(roll > (s.getInterval()/2)){
                        actualMinute = actualMinute + (s.getInterval() - roll);
                    }
                    
                    if(actualMinute <= (s.getInterval()/2)){
                        adjustedTimeStamp.set(Calendar.HOUR_OF_DAY, originalTimeStamp.get(Calendar.HOUR_OF_DAY) + 1);
                        adjustedTimeStamp.set(Calendar.MINUTE, actualMinute);  
                    }
                    else{
                        adjustedTimeStamp.set(Calendar.HOUR_OF_DAY, originalTimeStamp.get(Calendar.HOUR_OF_DAY));
                        adjustedTimeStamp.set(Calendar.MINUTE, actualMinute);
                    }
                }

            }
            /*else if(startInMillis < originalPunchInMillis){

            }*/
            else{
                adjustedTimeStamp.set(originalTimeStamp.get(Calendar.YEAR), originalTimeStamp.get(Calendar.MONTH), originalTimeStamp.get(Calendar.DATE));
                adjustedTimeStamp.set(Calendar.HOUR_OF_DAY, originalTimeStamp.get(Calendar.HOUR_OF_DAY));
                adjustedTimeStamp.set(Calendar.MINUTE, originalTimeStamp.get(Calendar.MINUTE));
            }/*
        }
        else if(eventTypeID == 0){
            //Implement based on Clocked out
        }
        else{
            //Implement based on Timed out??
        }*/
    }
    }
	
	public String printOriginalTimestamp() {
		return "#" + badgeID + " " + getEventType(eventTypeID) + correctDOW(originalTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
		+ " " + padInt(originalTimeStamp.get(Calendar.MONTH) + 1) + "/" + padInt(originalTimeStamp.get(Calendar.DAY_OF_MONTH)) + "/" + originalTimeStamp.get(Calendar.YEAR) + 
		" " + padInt(originalTimeStamp.get(Calendar.HOUR_OF_DAY)) + ":" + padInt(originalTimeStamp.get(Calendar.MINUTE)) + ":" + padInt(originalTimeStamp.get(Calendar.SECOND));
	}
        
        public String printAdjustedTimestamp() {
		return "#" + badgeID + " " + getEventType(eventTypeID) + correctDOW(adjustedTimeStamp.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()))
		+ " " + padInt(adjustedTimeStamp.get(Calendar.MONTH) + 1) + "/" + padInt(adjustedTimeStamp.get(Calendar.DAY_OF_MONTH)) + "/" + adjustedTimeStamp.get(Calendar.YEAR) + 
		" " + padInt(adjustedTimeStamp.get(Calendar.HOUR_OF_DAY)) + ":" + padInt(adjustedTimeStamp.get(Calendar.MINUTE)) + ":" + padInt(adjustedTimeStamp.get(Calendar.SECOND)) + getTypePunch(eventTypeID);
	}
}