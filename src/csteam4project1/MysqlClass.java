package csteam4project1;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.io.*;
import java.sql.Timestamp;
import java.lang.ClassLoader;

//package java.mysql.test;

/**
 *
 * @author User
 */
public class MysqlClass{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        // TODO code application logic her
       Database db = new Database();
        
       Badge badge = db.getBadge("D2CC71D4");
       //Badge test = new Badge(badge[0],badge[1]); 
       System.out.println(badge.toString());
       
       //System.out.println("ID is " + badge[0]);
       //System.out.println("Description is " + badge[1]);
       
       Punch punch = db.getPunch(42);
       //System.out.println(punch[2]);
       System.out.println(punch.printOriginalTimestamp());
       
       Shift shift = db.getShift(1);
       
       System.out.println(shift.toString());
        
        
    }

}

