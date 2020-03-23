package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ListMassive {
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("wind") public Wind wind;
    @SerializedName("dt_txt") public String dt_txt;
 }
