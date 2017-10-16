package csteam4project1;
import java.sql.*;

public class Shift {
  
        public int shiftId;
        public String description;
        public Time start;
        public Time stop;
        public int interval;
        public int graceperiod;
        public int dock;
        public Time lunchstart;
        public Time lunchstop;
        public int lunchdeduct;
        public int maxtime;
        public int overtimethreshold;
        
        public Shift(int shiftId, String description, Time Start, Time Stop, int Interval, int Graceperiod, int Dock, Time Lunchstart, Time Lunchstop, int Lunchdeduct, int Maxtime, int Overtimethreshold){

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
        
    private String getHourMin(Time time){

        return time.toString().substring(0, 5);
	}
	
	@Override
	public String toString(){

		return description + ": " + getHourMin(start) + " - " + getHourMin(stop) + " (510 minutes); Lunch: " + getHourMin(lunchstart) + " - " + getHourMin(lunchstop) + " (30 minutes)";
	}  
}
