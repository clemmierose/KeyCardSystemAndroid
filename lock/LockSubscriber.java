package lock;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import utils.Utils;

public class LockSubscriber {



    // public static final String BROKER_URL = "tcp://iot.eclipse.org:1883";
    public static final String BROKER_URL = "tcp://broker.mqttdashboard.com:1883";

    public static final String userid = "20106355"; // change this to be your student-id
    String clientId = userid + "-sub";


    private MqttClient mqttClient;

    public LockSubscriber() {

        try {
        	// Create a client to connect to the broker, with a random client id
        	// this ensures publisher and client have different client ids
            mqttClient = new MqttClient(BROKER_URL, userid + "-" +Utils.createRandomNumber(1, 1000));
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {
        try {
            mqttClient.setCallback(new LockSubscriberCallback());
            mqttClient.connect();

            //Subscribe to correct topic
            final String topic = userid+"/motor"; //swap to rfid if you want//swap from motor
            mqttClient.subscribe(topic);

            System.out.println("Subscriber is now listening to "+topic);

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String... args) {
        final LockSubscriber subscriber = new LockSubscriber();
        subscriber.start();
    }

}
