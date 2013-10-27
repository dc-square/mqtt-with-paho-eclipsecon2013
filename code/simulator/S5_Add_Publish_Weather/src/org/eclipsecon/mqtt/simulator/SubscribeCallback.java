package org.eclipsecon.mqtt.simulator;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

 class SubscribeCallback implements MqttCallback {


    private final PahoClient pahoClient;
    private final String deviceIntervalTopic;

    public SubscribeCallback(PahoClient pahoClient, String deviceIntervalTopic) {
        this.pahoClient = pahoClient;
        this.deviceIntervalTopic = deviceIntervalTopic;
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.equals(deviceIntervalTopic)) {
            final int interval;
            try {
                interval = Integer.parseInt(new String(message.getPayload(), "UTF-8"));
            } catch (NumberFormatException e) {
                System.out.println("Could not parse interval out of payload ");
                return;
            }

            if (!(interval < 1)) {
                pahoClient.changeInterval(interval);
            }
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
