package csteam4project1;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database{
    
    private static Connection conn = null;
    private String myUrl = null;
    private String queryBadge = null;
    private String queryPunches = null;
    private String queryShifts= null;
    private Statement st;
    private PreparedStatement preparedSt;
    
    public Database() throws ClassNotFoundException{
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
        queryBadge = "SELECT * FROM badge WHERE id = '"+ badge +"'";
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
        queryPunches = "SELECT * FROM event WHERE id = '"+id+"'";
        ResultSet rs = st.executeQuery(queryPunches);

        int punchID = 0;
        int terminalId = 0;
        String badgeId = null;
        long originalTimestamp = 0;
        Timestamp ogTimestamp = null;
        int eventTypeId = 0;
        String eventData = null;
        long adjustedTimestamp = 0;
        Timestamp adjTimestamp = null;
        
        
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
        queryShifts = "SELECT * FROM shift WHERE id = '"+id+"'";
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
    	String queryInsertPunch = "INSERT INTO event values(?,?,?)";
    	preparedSt = conn.prepareStatement(queryInsertPunch);

    	int punchID = p.getPunchID();

    	return pucnhID;
    }

	public void close(Statement st){
        try {
            st.close();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}