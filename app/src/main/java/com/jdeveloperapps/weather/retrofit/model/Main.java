package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp") public float temp;
    @SerializedName("feels_like") public float feels_like;
    @SerializedName("humidity") public String humidity;
}
