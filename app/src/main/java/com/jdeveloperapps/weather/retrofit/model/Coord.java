package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lat") public double lat;
    @SerializedName("lon") public double lon;
}
