package lock;
import com.phidget22.RCServo;

import java.util.ArrayList;
import java.util.Arrays;

import com.phidget22.PhidgetException;
import com.phidget22.RCServoPositionChangeEvent;
import com.phidget22.RCServoPositionChangeListener;
import com.phidget22.RCServoTargetPositionReachedEvent;
import com.phidget22.RCServoTargetPositionReachedListener;
import com.phidget22.RCServoVelocityChangeEvent;
import com.phidget22.RCServoVelocityChangeListener;
import com.phidget22.RFID;
import com.phidget22.RFIDTagEvent;
import com.phidget22.RFIDTagListener;
import com.phidget22.RFIDTagLostEvent;
import com.phidget22.RFIDTagLostListener;

import reader.RFIDdata;


public class LockMover {
	
   /* ArrayList<RFIDdata> allSensors = new ArrayList<RFIDdata>();

	final ArrayList<RFIDdata> tagItemsArray = allSensors;
	ArrayList<String> tagItemsList = new ArrayList<String>();*/
	
	static RCServo servo = null;
	private static LockMover instance = null;
	
	public static RCServo getInstance() {
		  System.out.println("In singleton constructor");
	      if(servo == null) {
	         servo = LockMover();
	      }
	      return servo;
	   }
	
	 /*public static void main(String[] args) throws PhidgetException {
	    	new LockMover();
	    }*/
	 
	private static RCServo LockMover() {
		try {
			System.out.println("In constructing lock mover");
			
			servo = new RCServo();
			
			servo.addVelocityChangeListener(new RCServoVelocityChangeListener() {
				public void onVelocityChange(RCServoVelocityChangeEvent e) {
					System.out.println("Velocity Changed: " + e.getVelocity());
				}
	        });
			
			servo.addPositionChangeListener(new RCServoPositionChangeListener() {
					public void onPositionChange(RCServoPositionChangeEvent e) {
						//System.out.println("Position Changed: " + e.getPosition());
					}
		     });
		        
		    servo.addTargetPositionReachedListener(new RCServoTargetPositionReachedListener() {
					public void onTargetPositionReached(RCServoTargetPositionReachedEvent e) {
						//System.out.println("Target Position Reached: " + e.getPosition());
					}
		     });


			// Start listening for motor interaction
			servo.open(2000);
		}catch (PhidgetException e) {
			e.printStackTrace();
		}
		return servo;
	 }

	//create method to move the lock/motor:
	
	public static void moveServoTo(double motorPosition) {
        try {
        		// Get the servo that is available
        		LockMover.getInstance();//gets access to motor via singleton
        		System.out.println("Now moving to "+motorPosition);
        		servo.setMaxPosition(210.0);
        		servo.setTargetPosition(motorPosition);
        		servo.setEngaged(true);
		} catch (PhidgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 public void openDoor() throws Exception{
        System.out.println("\n\nSetting lock open position to 180 for 3 seconds\n\n");
        try {
        	servo.setTargetPosition(180);
        	Thread.sleep(3000);
        	System.out.println("\n\nSetting lock close position to 0\n\n");
        	servo.setTargetPosition(0);
        	Thread.sleep(3000);        	
    	} catch (PhidgetException ex) {
    		System.out.println(ex.getDescription());
    	}
		
	}  
	
}



