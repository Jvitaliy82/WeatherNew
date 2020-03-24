package com.jdeveloperapps.weather.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrepareUtil {

    private static final String TAG = "mylog";

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

    public static String prepareDate(String dateTXT) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatOld = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateNew = new SimpleDateFormat("dd EEEE, HH:mm");
        Date date = new Date();
        try {
            date = dateFormatOld.parse(dateTXT);

        } catch (ParseException e) {
            Log.d(TAG, "prepareDate: неудачное преобразование даты");
        }
        return dateNew.format(date);
    }
}
