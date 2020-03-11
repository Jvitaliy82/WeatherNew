package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class ListMassive {
    @SerializedName("main") public Main main;
    @SerializedName("weather") public Weather[] weather;
 }
