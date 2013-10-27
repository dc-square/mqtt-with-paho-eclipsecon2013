package org.eclipsecon.mqtt.simulator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoClient {

    public void start() {

        try {
            MqttClient client = new MqttClient("tcp://localhost", "simulator", new MemoryPersistence());
            client.connect();

            if (client.isConnected()) {
            	System.out.println("Connected!");
            } else {
                System.out.println("Could not connect!");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}