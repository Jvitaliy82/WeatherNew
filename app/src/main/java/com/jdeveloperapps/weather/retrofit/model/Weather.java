package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Weather implements Serializable {
    @SerializedName("description") public String description;
}
