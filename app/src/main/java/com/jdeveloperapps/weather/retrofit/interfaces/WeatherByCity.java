package com.jdeveloperapps.weather.retrofit.interfaces;

import com.jdeveloperapps.weather.retrofit.model.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherByCity {
    @GET("data/2.5/forecast")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry,
                                     @Query("units") String metric,
                                     @Query("lang") String lang,
                                     @Query("appid") String keyApi);
}
