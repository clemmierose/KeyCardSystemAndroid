package lock;
import org.eclipse.paho.client.mqttv3.*;
import utils.Utils;

public class LockSubscriberCallback  implements MqttCallback {


	    public static final String userid = "20106355"; // my student-id

	    @Override
	    public void connectionLost(Throwable cause) {
	        //This is called when the connection is lost. We could reconnect here.
	    }

	    @Override
	    public void messageArrived(String topic, MqttMessage message) throws Exception {
	        System.out.println("Message arrived. Listening to topic: " + topic + "  Message is: " + message.toString());
	       
	        // Move motor to open, then shut after pausing
	        LockMover.moveServoTo(180.0);
	        System.out.println("Waiting until motor at position 180");
	        Utils.waitFor(5);
	        LockMover.moveServoTo(0.0);
	        Utils.waitFor(2);
	       
	        if ((userid+"/LWT").equals(topic)) {
	            System.err.println("Sensor gone!");
	        }
	    }

	    @Override
	    public void deliveryComplete(IMqttDeliveryToken token) {
	        //no-op
	    }
	}

