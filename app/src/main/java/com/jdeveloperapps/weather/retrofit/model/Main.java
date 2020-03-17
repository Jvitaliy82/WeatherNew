package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp") public float temp;
    @SerializedName("humidity") public String humidity;
}
