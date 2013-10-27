package org.eclipsecon.mqtt.simulator;

public class RandomGenerator {


    private final int Min = -20;
    private final int Max = 60;
    private boolean trend = true;
    private Integer lastValue;

    /**
     * Random temperature generator
     *
     * @return a increasing/decreasing series of numbers from -20 and 60 and backwards
     */
    public int getRandomTemp() {

        int variation = 0;
        if (lastValue == null) {
            lastValue = 0;
        } else {
            if (trend) {
                variation = getNumberBetween(0, 3);
            } else {
                variation = getNumberBetween(-3, 0);
            }

        }
            lastValue = lastValue + variation;
        if(lastValue>Max) {
            lastValue = Max;
            trend = false;
        }
        else if(lastValue<Min) {
            lastValue = Min;
            trend = true;
        }

        return lastValue;

    }

    /**
     * Random Weather Forecast
     *
     * @return string which describes the weather situation
     */
    public String getWeather(int temp) {
        String weather = null;
        if (temp < 0) {
            weather = "SNOW";
        }
        else if ((temp >= 0) && (temp <= 35)) {
            final int number = getNumberBetween(1, 4);
            switch (number) {
                case 1:
                    weather = "CLOUD";
                    break;
                case 2:
                    weather = "RAIN";
                    break;
                case 3:
                    weather = "SUN";
                    break;
                case 4:
                    weather = "STORM";
                    break;
            }
        } else if ((temp > 35) && (temp <= 50)){
            final int number = getNumberBetween(1, 2);
            switch (number) {
                case 1:
                    weather = "SUN";
                    break;
                case 2:
                    weather = "STORM";
                    break;
            }
        }
        else {
            weather = "SUN";
        }

        return weather;

    }

    /**
     * Warning from glaze
     *
     * @return false, if their is no need to worry and true if the streets are icy.
     */
    public String isStreetIcy(int temp)
    {
        String glazeWarning;
        if (temp > 4) {
            glazeWarning = "false";

        } else {
            glazeWarning = "true";
        }
        return glazeWarning;
    }

    private int getNumberBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }


}
