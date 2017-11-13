package test;

import csteam4project1.TASDatabase;
import csteam4project1.Punch;
import csteam4project1.Shift;

import org.junit.*;
import static org.junit.Assert.*;

public class Feature4 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        try {
            db = new TASDatabase();
        }
        catch(Exception e){};
    }
    
    @Test
    public void testMinutesAccruedShift1Weekday() {
		
		/* Get Punch */
        try{
            Punch p = db.getPunch(3634);

                    /* Compute Pay Period Total */

            int m = db.getMinutesAccrued(p);

                    /* Compare to Expected Value */
        
            assertEquals(480, m);
        }
        catch (Exception e){}
    }
    
    @Test
    public void testMinutesAccruedShift1Weekend() {
		
		/* Get Punch */
        try{
        Punch p = db.getPunch(1087);
		
		/* Compute Pay Period Total */
        
        int m = db.getMinutesAccrued(p);
		
		/* Compare to Expected Value */
        
        assertEquals(330 , m);
        }
        catch(Exception e) {}
    }

    @Test
    public void testMinutesAccruedShift2Weekday() {
		
		/* Get Punch */
        try {
        Punch p = db.getPunch(4943);
		
		/* Compute Pay Period Total */
        
        int m = db.getMinutesAccrued(p);
		
		/* Compare to Expected Value */
        
        assertEquals(540 , m);
        }
        catch (Exception e){}
    }
    
}