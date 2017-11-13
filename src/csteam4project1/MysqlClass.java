package csteam4project1;

import java.sql.*;

public class MysqlClass{

    public static void main(String[] args) throws ClassNotFoundException, SQLException{
       TASDatabase db = new TASDatabase();
       
       Punch punch = db.getPunch(4943);
       
       Shift shift = db.getShift(2);
       int i  = db.getMinutesAccrued(punch);
       System.out.println(i);
    }
}