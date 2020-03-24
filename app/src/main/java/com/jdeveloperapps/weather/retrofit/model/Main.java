package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {
    @SerializedName("temp") public float temp;
    @SerializedName("feels_like") public float feels_like;
    @SerializedName("humidity") public String humidity;
    @SerializedName("pressure") public String pressure;
}
