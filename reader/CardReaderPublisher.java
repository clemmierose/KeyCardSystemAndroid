package reader;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import utils.Utils;

public class CardReaderPublisher {
	
	 public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";
	 
	 public static final String userid = "20106355"; // student-id

	 
	 public static final String TOPIC_RFID       = userid + "/rfid";
	 public static final String TOPIC_MOTOR      = userid + "/motor";
	 public static final String TOPIC_C205       = userid + "/c205";
	    
	 private MqttClient client;
	    
	 public CardReaderPublisher() {

	        try {
	        	// Create a client to connect to the broker, with a random client id
	        	// this ensures publisher and client have different client ids
	            client = new MqttClient(BROKER_URL, userid + "-" + Utils.createRandomNumber(1, 1000));
	            // create mqtt session
	            MqttConnectOptions options = new MqttConnectOptions();
	            options.setCleanSession(false);
	            options.setWill(client.getTopic(userid + "/LWT"), "I'm gone :(".getBytes(), 0, false);
	            client.connect(options);
	        } catch (MqttException e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	    }
	 
	// Specific publishing methods for particular phidgets; here for card reader and motor/lock
	 
	 public void publishRfid(String rfidTag) throws MqttException {
	        final MqttTopic rfidTopic = client.getTopic(TOPIC_RFID);
	        final String rfid = rfidTag + "";
	        rfidTopic.publish(new MqttMessage(rfid.getBytes()));
	        System.out.println("Published data. Topic: " + rfidTopic.getName() + "   Message: " + rfid);
	    }
	    public void publishMotor(double newMotorPosition) throws MqttException {
	    	final MqttTopic newMotorTopic = client.getTopic(TOPIC_MOTOR);
	    	final String newMotorMessage = newMotorPosition + "";
	    	newMotorTopic.publish(new MqttMessage(newMotorMessage.getBytes()));
	    	System.out.println("Published data. Topic: " + newMotorTopic.getName() + "Message : " + newMotorMessage);
	    }
	    public void publishMotor() throws MqttException {
	    	final MqttTopic motorTopic = client.getTopic(TOPIC_MOTOR);
	    	final String newMotorMessage = "OPEN DOOR";
	    	motorTopic.publish(new MqttMessage(newMotorMessage.getBytes()));
	    	System.out.println("Published data. Topic: " + motorTopic.getName() + "Message : " + newMotorMessage); 
	    }
	 
}
