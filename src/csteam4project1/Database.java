package csteam4project1;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database{
    
     private static Connection conn = null;
     //private static String myDriver = null;
     private String myUrl = null;
     private String queryBadge = null;
     private String queryPunches = null;
     private String queryShifts= null;
     private Statement st;
    
public Database() throws ClassNotFoundException{
    try {
           myUrl = "jdbc:mysql://localhost/tas";
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection(myUrl,"Tasuser", "tasuser");
           
        } 
        catch (SQLException ex) {
            // handle any errors
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
         st.close();
         Badge b = new Badge(badgeId, badgeDescription);
        //return new String[] {badgeId, badgeDescription} ;
        return b;
    }
public Punch getPunch( int id ) throws SQLException{
        st = conn.createStatement();
        queryPunches = "SELECT * FROM event WHERE id = '"+id+"'";
        ResultSet rs = st.executeQuery(queryPunches);
        //int punchId;
        String terminalId = null;
        String badgeId = null;
        String originalTimestamp = null; //need to get this as long
        String eventTypeId = null;
        String eventData = null;
        String adjustedTimeStamp = null; //need to get this as long
        
        
        while(rs.next()){
            //punchId = String.valueOf(rs.getInt("id"));
            //punchId = rs.getInt("id");
           // System.out.println(punchId);
            terminalId = String.valueOf(rs.getInt("terminalid"));
            //System.out.println(terminalId);
            badgeId = rs.getString("badgeid");
           // System.out.println(badgeId);
            originalTimestamp = String.valueOf(rs.getTimestamp("originaltimestamp"));
            eventTypeId = String.valueOf(rs.getInt("eventtypeid"));
            eventData = rs.getString("eventdata");
            adjustedTimeStamp = rs.getString("adjustedtimestamp");
            
          }
         st.close();
         Punch p = new Punch(terminalId,badgeId,originalTimestamp, eventTypeId, eventData, adjustedTimeStamp  );
    //return new String[] {terminalId, badgeId,originalTimestamp};
    return p;
}

public Shift getShift(int id) throws SQLException{
    st = conn.createStatement();
    queryShifts = "SELECT * FROM shift WHERE id = '"+id+"'";
    ResultSet rs = st.executeQuery(queryShifts);
    String shiftId = null;
    String descrp = null;
    String start = null;
    String stop = null;
    String interval = null;
    String gracePeriod = null;
    String dock = null;
    String lunchStart = null;
    String  lunchStop = null;
    String lunchDeduct = null;
    String maxTime = null;
    String overTimeThreshold = null;
    
    
    while(rs.next()){
        shiftId = String.valueOf(rs.getInt("id"));
        descrp = rs.getString("description");
        start = String.valueOf(rs.getTime("start"));
        stop = String.valueOf(rs.getTime("stop"));
        interval = String.valueOf(rs.getInt("interval"));
        gracePeriod = String.valueOf(rs.getInt("graceperiod"));
        dock = String.valueOf(rs.getInt("dock"));
        lunchStart = String.valueOf(rs.getTime("lunchstart"));
        lunchStop = String.valueOf(rs.getTime("lunchstop"));
        lunchDeduct = String.valueOf(rs.getInt("lunchdeduct"));
        maxTime = String.valueOf(rs.getInt("maxtime"));
        overTimeThreshold = String.valueOf(rs.getInt("overtimethreshold"));
    }
    st.close();
    Shift sc = new Shift(shiftId,descrp,start,stop, interval, gracePeriod,dock, lunchStart,lunchStop,lunchDeduct, maxTime, overTimeThreshold);
    //return new String[] {shiftId, start,stop, lunchStart,lunchStop };
    return sc;
}
public void close(Statement st){
         try {
             st.close();
         } catch (SQLException ex) {
             Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}