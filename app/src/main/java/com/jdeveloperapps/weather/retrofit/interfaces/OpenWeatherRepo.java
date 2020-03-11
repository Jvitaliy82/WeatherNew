package com.jdeveloperapps.weather.retrofit.interfaces;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepo {

    private static OpenWeatherRepo singleton;
    private WeatherByCity API_BY_CITY;
    private WeatherByGPS API_BY_GPS;

    private OpenWeatherRepo() {
        API_BY_CITY = createAdapterByCity();
        API_BY_GPS = createAdapterGPS();
    }

    public WeatherByCity getAPI_BY_CITY() {
        return API_BY_CITY;
    }

    public WeatherByGPS getAPI_BY_GPS() {
        return API_BY_GPS;
    }

    public static OpenWeatherRepo getSingleton() {
        OpenWeatherRepo localInstance = singleton;
        if (localInstance == null) {
            synchronized (OpenWeatherRepo.class) {
                localInstance = singleton;
                if (localInstance == null) {
                    singleton = localInstance = new OpenWeatherRepo();
                }
            }
        }
        return localInstance;
    }

    private WeatherByCity createAdapterByCity() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(WeatherByCity.class);
    }

    private WeatherByGPS createAdapterGPS() {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return adapter.create(WeatherByGPS.class);
    }

}

