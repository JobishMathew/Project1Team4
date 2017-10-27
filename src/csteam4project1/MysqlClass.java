package csteam4project1;

import java.sql.*;

public class MysqlClass{

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
       TASDatabase db = new TASDatabase();
       
       Punch punch = db.getPunch(5463);
       
       Shift shift = db.getShift(2);

  		punch.adjust(shift);
  		System.out.println(punch.printOriginalTimestamp());
                System.out.println(punch.printAdjustedTimestamp());
                
    }
}