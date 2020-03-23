package com.jdeveloperapps.weather.utils;

public class PrepareUtil {

    public static String prepareTemp(float f) {
        StringBuilder sb = new StringBuilder();
        if (f > 0) sb.append("+");
        sb.append(Math.round(f));
        sb.append(" °C");
        return sb.toString();
    }

    public static String prepareSpeedWind(float f) {
        StringBuilder sb = new StringBuilder();
        sb.append(Math.round(f));
        sb.append(" m/s");
        return sb.toString();
    }

    public static String preparePressure(String pressure) {
        StringBuilder sb = new StringBuilder();
        sb.append(pressure);
        sb.append(" mBar");
        return sb.toString();
    }
}
