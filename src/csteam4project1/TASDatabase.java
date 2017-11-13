package csteam4project1;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TASDatabase{

    private final long NUM_OF_MILLIS_IN_MINUTE = 60000;   
    private final int NUM_OF_MILLIS_IN_SECOND = 1000; 
    private final int TIME_FRAME = 3;
    private static Connection conn = null;
    private String myUrl = null;
    private Statement st;
    private Statement preparedSt;
    
    public TASDatabase() throws ClassNotFoundException{
        try{
            myUrl = "jdbc:mysql://localhost/tas";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(myUrl,"Tasuser", "tasuser");
               
        } 
        catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public Badge getBadge(String badge) throws SQLException{
        st = conn.createStatement();
        String queryBadge = "SELECT * FROM badge WHERE id = '"+ badge +"'";
        ResultSet rs = st.executeQuery(queryBadge);

        String badgeId = null;
        String badgeDescription = null;

        while(rs.next()){
            badgeId = rs.getString("id");
            badgeDescription = rs.getString("description");
        }
        close(st);

        Badge b = new Badge(badgeId, badgeDescription);
        
        return b;
    }

    public Punch getPunch(int id) throws SQLException{    
        st = conn.createStatement();
        String queryPunches = "SELECT * FROM event WHERE id = '"+id+"'";
        ResultSet rs = st.executeQuery(queryPunches);

        int punchID = 0;
        int terminalId = 0;
        String badgeId = null;
        long originalTimestamp = 0;
        Timestamp ogTimestamp;
        int eventTypeId = 0;
        String eventData = null;
        long adjustedTimestamp = 0;
        Timestamp adjTimestamp;

        while(rs.next()){
            punchID = rs.getInt("id");
            terminalId = rs.getInt("terminalid");
            badgeId = rs.getString("badgeid");
            ogTimestamp = rs.getTimestamp("originaltimestamp");
            originalTimestamp = ogTimestamp.getTime();
            eventTypeId = rs.getInt("eventtypeid");
            eventData = rs.getString("eventdata");
            adjTimestamp = rs.getTimestamp("adjustedtimestamp");
            if(adjTimestamp != null){adjustedTimestamp = adjTimestamp.getTime();}
            }
        close(st);

        Punch p = new Punch(punchID, terminalId, badgeId, originalTimestamp, eventTypeId, eventData, adjustedTimestamp);

        return p;
    }

    public Shift getShift(int id) throws SQLException{
        st = conn.createStatement();
        String queryShifts = "SELECT * FROM shift WHERE id = '"+id+"'";
        ResultSet rs = st.executeQuery(queryShifts);

        int shiftId = 0;
        String description = null;
        Time start = null;
        Time stop = null;
        int interval = 0;
        int gracePeriod = 0;
        int dock = 0;
        Time lunchStart = null;
        Time  lunchStop = null;
        int lunchDeduct = 0;
        int maxTime = 0;
        int overTimeThreshold = 0;
        
        while(rs.next()){
            shiftId = rs.getInt("id");
            description = rs.getString("description");
            start = rs.getTime("start");
            stop = rs.getTime("stop");
            interval = rs.getInt("interval");
            gracePeriod = rs.getInt("graceperiod");
            dock = rs.getInt("dock");
            lunchStart = rs.getTime("lunchstart");
            lunchStop = rs.getTime("lunchstop");
            lunchDeduct = rs.getInt("lunchdeduct");
            maxTime = rs.getInt("maxtime");
            overTimeThreshold = rs.getInt("overtimethreshold");
        }
        close(st);

        Shift sc = new Shift(shiftId, description, start, stop, interval, gracePeriod, dock, lunchStart, lunchStop, lunchDeduct, maxTime, overTimeThreshold);

        return sc;
    }

    public int insertPunch(Punch p) throws SQLException{
        
        int terminalID = p.getTerminalID();
        String badgeID = p.getBadgeID();
        int eventTypeID = p.getEventTypeID();
        
    	preparedSt = conn.createStatement();
    	int queryInsertPunch = preparedSt.executeUpdate("INSERT INTO event (terminalid, badgeid, eventtypeid) VALUES(" + terminalID + ","+ badgeID + "," + eventTypeID + ")");
        
        close(st);
        
    	return queryInsertPunch;
    }
    

    public int getMinutesAccrued(Punch p) throws SQLException {      
        Statement s = conn.createStatement();        
        st = conn.createStatement();  
        
        String badgeID = p.getBadgeID();
        String queryShiftId = "SELECT shiftid FROM employee WHERE badgeid = '" + p.getBadgeID() + "'";
        ResultSet rs = st.executeQuery(queryShiftId);
                  
        int shiftId = 0;
        while (rs.next()) {
            shiftId = rs.getInt("shiftid");
        }    
        Shift shift = getShift(shiftId);
        p.adjust(shift);  
        
        GregorianCalendar startOfTime = new GregorianCalendar(p.getOriginalTimestamp().get(Calendar.YEAR), p.getOriginalTimestamp().get(Calendar.MONTH), p.getOriginalTimestamp().get(Calendar.DAY_OF_MONTH), shift.getStart().getHours() - TIME_FRAME, shift.getStart().getMinutes());
        GregorianCalendar stopOfTime = new GregorianCalendar(p.getOriginalTimestamp().get(Calendar.YEAR), p.getOriginalTimestamp().get(Calendar.MONTH), p.getOriginalTimestamp().get(Calendar.DAY_OF_MONTH), shift.getStop().getHours() + TIME_FRAME, shift.getStop().getMinutes());
        
        String queryPunches = "SELECT id FROM event WHERE ((UNIX_TIMESTAMP(originaltimestamp) * " + NUM_OF_MILLIS_IN_SECOND + ")" +  " >= '" + startOfTime.getTimeInMillis() + "'  AND (UNIX_TIMESTAMP(originaltimestamp) * " + NUM_OF_MILLIS_IN_SECOND + ") <'" + stopOfTime.getTimeInMillis() + "') AND badgeid = '" + p.getBadgeID() + "'";
        ResultSet r = s.executeQuery(queryPunches);
        
        List<Punch> punchList = new ArrayList<>();
        while(r.next()) {
            Punch punch = getPunch(r.getInt("id"));
            punchList.add(punch);
        }

        long start = 0;
        long stop = 0;
        for (int i = 0; i < punchList.size(); i++) {
            punchList.get(i).adjust(shift);
            if (punchList.get(i).getEventTypeID() == 1) {
                if (!punchList.get(i).getAdjustmentType().equals(" (Lunch Stop)")) {
                    start = punchList.get(i).getAdjustedTimestamp().getTimeInMillis();
                }
            }
            else if (punchList.get(i).getEventTypeID() == 0) {
                if (!punchList.get(i).getAdjustmentType().equals(" (Lunch Start)")) {
                    stop = punchList.get(i).getAdjustedTimestamp().getTimeInMillis();
                }
            }
        }
        
        long minsAccruedLong = stop - start;
        long lunch = 30;
        int minsAccrued;
        if (minsAccruedLong >= shift.getLunchdeduct()) {
            
            minsAccruedLong = (minsAccruedLong / NUM_OF_MILLIS_IN_MINUTE)- lunch;
            minsAccrued = (int) minsAccruedLong;
            
        }
        else {
            minsAccruedLong = (minsAccruedLong / NUM_OF_MILLIS_IN_MINUTE);
            minsAccrued = (int) minsAccruedLong;
        }
              
        
        close(s);
        close(st);
        return minsAccrued;
    }


    public void close(Statement st){
        try {
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(TASDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}