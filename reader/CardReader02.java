package reader;

import java.util.ArrayList;
import java.util.Arrays;


import com.phidget22.*;

public class CardReader02 {

	RCServo servo = new RCServo();;
	RFID phid = new RFID();
	final String[] tagItemsArray = {"1600ee1bd9","tagid2","RoomC203", "RoomC151","5f00dc1000"};
	ArrayList<String> tagItemsList = new ArrayList<String>(Arrays.asList(tagItemsArray));

	
    public static void main(String[] args) throws PhidgetException {
    	new CardReader02();
    }
    
    public CardReader02() throws PhidgetException {

    	servo.addVelocityChangeListener(new RCServoVelocityChangeListener() {
			public void onVelocityChange(RCServoVelocityChangeEvent e) {
				// System.out.println("Velocity Changed: "+ e.getVelocity());
			}
        });
        
        servo.addPositionChangeListener(new RCServoPositionChangeListener() {
			public void onPositionChange(RCServoPositionChangeEvent e) {
				// System.out.println("Position Changed: "+ e.getPosition());
			}
        });
        
        servo.addTargetPositionReachedListener(new RCServoTargetPositionReachedListener() {
			public void onTargetPositionReached(RCServoTargetPositionReachedEvent e) {
				System.out.println("Target Position Reached: "+ e.getPosition());
			}
        });
    	// Make the RFID Phidget able to detect loss or gain of an rfid card
        phid.addTagListener(new RFIDTagListener() {
			public void onTag(RFIDTagEvent e) {
				System.out.println("Tag read: " + e.getTag());
				ValidateCard(e.getTag());
			}
        });

        phid.addTagLostListener(new RFIDTagLostListener() {
			public void onTagLost(RFIDTagLostEvent e) {
				System.out.println("Tag lost: " + e.getTag());
			}
        });
       
        try {
             

            System.out.println("Opening and waiting 5 seconds for servo attachment...");
            servo.open(5000);
            servo.setTargetPosition(0);;
            servo.setEngaged(true);
            
            // Open and start detecting rfid cards
            phid.open(5000);  // wait 5 seconds for device to respond

            // Display info on currently connected devices
            System.out.println("Device Name " + phid.getDeviceName());
            System.out.println("Serial Number " + phid.getDeviceSerialNumber());
            System.out.println("Device Version " + phid.getDeviceVersion());


            phid.setAntennaEnabled(true);

            
            System.out.println("\n\nGathering data for 15 seconds\n\n");
            pause(15);

            phid.close();
            servo.close();
            System.out.println("\nClosed RFID and Motor Servo");
            
        } catch (PhidgetException ex) {
            System.out.println(ex.getDescription());
        }
    }
    
    //check if card presented is valid, if yes then open door
    private void ValidateCard (String tagStr){
        if (tagItemsList.contains(tagStr)) { 
        	System.out.println("HELLO AND WELCOME MY FRIEND!" ); 
        	try {
				openDoor();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        else { 
        	System.out.println("UNKNOWN TAG. PLEASE GO AWAY."); 
        }      
    	
    }
    
    //if card is valid then move the motor to open door
	private void openDoor() throws Exception{
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
	//pause the motor
	private void pause(int secs){
        try {
			Thread.sleep(secs*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	
}
