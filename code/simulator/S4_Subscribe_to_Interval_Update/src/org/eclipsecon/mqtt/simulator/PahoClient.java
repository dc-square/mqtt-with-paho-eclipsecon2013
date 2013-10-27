package org.eclipsecon.mqtt.simulator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoClient {

    //Publish interval in seconds
    private final int DEFAULT_INTERVAL = 5;
    private final String DEVICE_STATUS_TOPIC = "eclipsecon/status";
    
    private int interval = DEFAULT_INTERVAL;

    public void start() {

        try {
            MqttClient client = new MqttClient("tcp://localhost", "simulator", new MemoryPersistence());

            final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setWill(DEVICE_STATUS_TOPIC, "offline".getBytes(), 2, true);

            client.connect(mqttConnectOptions);

            if (client.isConnected()) {

                client.publish(DEVICE_STATUS_TOPIC, "online".getBytes(), 2, true);       

                publishDataPeriodically(client);
            } else {
                System.out.println("Could not connect!");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void publishDataPeriodically(MqttClient client) {

        Publisher publisher = new Publisher(client);

        while (true) {
            try {
                publisher.publishData();
                Thread.sleep(interval * 1000);

            } catch (MqttException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}