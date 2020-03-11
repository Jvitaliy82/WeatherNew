package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class WeatherRequest {
    @SerializedName("cod") public String code;
    @SerializedName("list") public ListMassive[] listMassives;
    @SerializedName("city") public City city;
}
