package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListMassive implements Serializable {
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
    @SerializedName("wind") public Wind wind;
    @SerializedName("dt_txt") public String dt_txt;
 }
