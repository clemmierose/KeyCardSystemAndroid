package reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

import com.phidget22.*;



public class ReadertoServer {

	 
	   RFID rfid = new RFID();
	   
	   RFIDdata oneSensor = new RFIDdata("unknown","unknown");
	   
	   Gson gson = new Gson();
	   	
	   String oneSensorJson = new String();
	   
	   //ADD THIS FOR DATA TO BE RECIEVED BY SERVER
	    // address of server which will receive sensor data
	    public static String sensorServerURL = "http://localhost:8080/webServer/CardValidator";//was phidget2019/SensorServer
	     
	    //main 
	    public static void main(String[] args) throws PhidgetException {

	        new ReadertoServer();
	    }

	    public ReadertoServer() throws PhidgetException {
	    	// Make the RFID Phidget able to detect loss or gain of an rfid card
	        rfid.addTagListener(new RFIDTagListener() {
	        		// What to do when a tag is found
	        	//extracts tag data from server and transforms into variable
				public void onTag(RFIDTagEvent e) {
					String tagRead = e.getTag();
					// optional print, used as debug here
					System.out.println("DEBUG: Tag read: " + tagRead);
					
					System.out.println("DEBUG: Sending new rfid value : " + tagRead);
					
					oneSensorJson = gson.toJson(oneSensor);
					
					sendToServer(oneSensorJson);
					
					try {
						oneSensorJson = URLEncoder.encode(oneSensorJson, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					//sendToServer(tagRead);

				}
	        });

	        rfid.addTagLostListener(new RFIDTagLostListener() {
	        		  // What to do when a tag is lost
				public void onTagLost(RFIDTagLostEvent e) {
					// optional print, used as debug here
					System.out.println("DEBUG: Tag lost: " + e.getTag());
				}
	        });
	        
	        // Open and start detecting rfid cards
	        rfid.open(5000);  // wait 5 seconds for device to respond
	 
	         // attach to the sensor and start reading
	        try {      
	                            
	            System.out.println("\n\nGathering data for 15 seconds\n\n");
	            pause(15);
	            rfid.close();
	            System.out.println("\nClosed RFID Reader");
	            
	        } catch (PhidgetException ex) {
	            System.out.println(ex.getDescription());
	        }

	    }
	    
	    //method that sends info to server

	    public String sendToServer(String oneSensorJson){
	        URL url;
	        HttpURLConnection conn;
	        BufferedReader rd;
	        String fullURL = sensorServerURL + "?rfiddata="+oneSensorJson;//was ?sensordata
	        System.out.println("Sending data to: "+fullURL);  // DEBUG confirmation message
	        String line;
	        String result = "";
	        try {
	           url = new URL(fullURL);
	           conn = (HttpURLConnection) url.openConnection();
	           conn.setRequestMethod("GET");
	           rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
	    
		private void pause(int secs){
	        try {
				Thread.sleep(secs*1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

}
