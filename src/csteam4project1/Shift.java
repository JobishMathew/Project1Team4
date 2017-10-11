package csteam4project1;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package shiftclass;

/**
 *
 * @author Fink
 */
public class Shift {
  
        public String shiftId;
        public String description;
        public String start;
        public String stop;
        public String interval;
        public String graceperiod;
        public String dock;
        public String lunchstart;
        public String lunchstop;
        public String lunchdeduct;
        public String maxtime;
        public String overtimethreshold;
        
        public Shift() {
            System.out.println("No shift information recieved.");
        }
        
        public Shift(String shiftId, String description, String Start, String Stop, String Interval, String Graceperiod, String Dock, String Lunchstart, String Lunchstop, String Lunchdeduct, String Maxtime, String Overtimethreshold) {
            this.shiftId = shiftId;
            this.description = description;
            this.start = Start;
            this.stop = Stop;
            this.interval = Interval;
            this.graceperiod = Graceperiod;
            this.dock = Dock;
            this.lunchstart = Lunchstart;
            this.lunchstop = Lunchstop;
            this.lunchdeduct = Lunchdeduct;
            this.maxtime = Maxtime;
            this.overtimethreshold = Overtimethreshold;
        }
        
        private String getHourMin(String time){
		return time.substring(0,5);
	}
	
	@Override
	public String toString(){
		return description + ": " + getHourMin(start) + " - " + getHourMin(stop) + " (510 minutes); Lunch: " + getHourMin(lunchstart) + " - " + getHourMin(lunchstop) + " (30 minutes)";
	}
        /*public String toString() {
            String returnstring;
            returnstring = (start + "," + stop + "," + interval + "," + graceperiod + "," + dock + "," + lunchstart + "," + lunchstop + "," + lunchdeduct + "," + maxtime + "," + overtimethreshold);
            return returnstring;
        }*/
    }
