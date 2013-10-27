package org.eclipsecon.mqtt.simulator;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Publisher {

    private final String WEATHER_TOPIC = "eclipsecon/weather";
    private final String TEMPERATURE_TOPIC = "eclipsecon/temperature";

    private final MqttClient client;
    private final RandomGenerator randomGenerator;

    public Publisher(MqttClient client) {
        this.client = client;
        this.randomGenerator = new RandomGenerator();
    }

    public void publishData() throws MqttException {
        final Integer temperature = publishTemperature();
        final String weather = publishWeather(temperature);

        System.out.println("Weather:\t"+weather+" \t Temperature: \t"+temperature);
    }

    private String publishWeather(int temp) throws MqttException {
        final String weather = randomGenerator.getWeather(temp);
        client.publish(WEATHER_TOPIC, weather.getBytes(), 2, false);
        return weather;
    }

    private Integer publishTemperature() throws MqttException {
        Integer temperature = randomGenerator.getRandomTemp();
        client.publish(TEMPERATURE_TOPIC, temperature.toString().getBytes(), 2, false);
        return temperature;
    }

}
