package reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
//import java.util.Arrays;
//import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phidget22.*;

public class CardReader {
	
	//ADD THIS FOR DATA TO BE RECIEVED BY SERVER
	//create phidget's rfid:
		RFID rfid = new RFID();
	//create publisher :
		CardReaderPublisher publisher = new CardReaderPublisher ();
		
		RFIDdata oneSensor = new RFIDdata("","");
		
		Gson gson = new Gson();
		
		String oneSensorJson = new String();//Json type of oneSensor
		
		String allSensorsJson = new String();	
    // address of server which will receive sensor data:
   // public static String sensorServerURL = "http://localhost:8080/PhidgetServer2019/SensorServer";
		
	 //  public static String ServerURL = "http://localhost:8080/DoorLockServers/SensorClient";
	  public static String ServerURL = "http://localhost:8080/DoorLockServers/CardValidator";

//main 
	   public static void main(String[] args) throws PhidgetException {
		
		new CardReader();
	}
	
//USE FOR SERVER TO SEND DATA:
		
public CardReader() throws PhidgetException {
	
		rfid.addTagListener(new RFIDTagListener() {
			public void onTag(RFIDTagEvent e) {
				String tagRead = e.getTag();
	
				System.out.println("DEBUG: TAG READ IS:" + tagRead);
				System.out.println("D: SENDING NEW RFID VALUE: " + tagRead);
				
				
				//publish rfid data:
				try {
					publisher.publishRfid(tagRead);
				} catch (MqttException mqtte) {
					mqtte.printStackTrace();
				}	
				System.out.println("Debug: publishing new rfid value");
				//set reader id via deviceserialno:
				String readerDevice = null;
				try {
					readerDevice = Integer.toString(rfid.getDeviceSerialNumber());
				} catch (PhidgetException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				//change onesensor to json
				oneSensor.setReaderid(readerDevice);//needs to take in readerid
				oneSensor.setTagid(tagRead);
				oneSensorJson = gson.toJson(oneSensor);
				sendToServer(oneSensorJson);//
				
			try {
				oneSensorJson = URLEncoder.encode(oneSensorJson, "UTF-8");
			}catch(UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			sendToServer(tagRead);
		}
			
	});
		
		rfid.addTagLostListener(new RFIDTagLostListener() {
  		  // What to do when a tag is lost
		public void onTagLost(RFIDTagLostEvent e) {
			// debug message:
			System.out.println("DEBUG: Tag lost: " + e.getTag());
		  }
		});
		
		
		rfid.open(5000); //open and detect cards, wait 5 seconds
		//find sensor and start reading 
		
		// rfid.setAntennaEnabled(true);
		 
		  try {     
	            System.out.println("Device Name " + rfid.getDeviceName());
	            System.out.println("Serial Number " + rfid.getDeviceSerialNumber());
	            System.out.println("Device Version " + rfid.getDeviceVersion());

	            rfid.setAntennaEnabled(true);

	            System.out.println("\n\nGathering data for 15 seconds\n\n");
	            pause(15);
	            
	            rfid.close();
	            System.out.println("\nClosed RFID Reader");
	            
	        } catch (PhidgetException ex) {
	            System.out.println(ex.getDescription());
	        }
		  
  
	}
	
		
//method to send info to server:
		  public String sendToServer(String oneSensorJson){
		        URL url;
		        HttpURLConnection conn;
		        BufferedReader rd;
		        
		        try {
		        	oneSensorJson = URLEncoder.encode(oneSensorJson, "UTF-8");
		        }catch(UnsupportedEncodingException e1) {
		        	e1.printStackTrace();
		        }
		        String fullURL = ServerURL + "?rfiddata="+oneSensorJson;
		        System.out.println("Sending data to: "+fullURL);  // DEBUG confirmation message
		        String line;
		        String result = "";
		        try {
		           url = new URL(fullURL);
		           conn = (HttpURLConnection) url.openConnection();
		           conn.setRequestMethod("GET");
		           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));//ERROR HERE
		           // Request response from server to enable URL to be opened
		           while ((line = rd.readLine()) != null) {
		              result += line;
		           }
		           rd.close();
		        } catch (Exception e) {
		           e.printStackTrace();
		        }
		        return result;    	
		    }
		  
//add the pause method
	private void pause(int secs){
        try {
			Thread.sleep(secs*1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
//add the checkTag method
	/*private void checkTag(String tagStr) {
		if (tagItemsList.contains(tagStr)) {
			System.out.println("Tag recognised!");
			
		}
	}else {
		System.out.println("Unknown tag, rejected!");
	}*/
	

}
