package csteam4project1;

import java.sql.SQLException;

public class MysqlClass{

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
       Database db = new Database();
        
       Badge badge = db.getBadge("D2CC71D4");
       System.out.println(badge.toString());
       
       Punch punch = db.getPunch(42);
       System.out.println(punch.printOriginalTimestamp());
       
       Shift shift = db.getShift(1);
       System.out.println(shift.toString());
    }
}