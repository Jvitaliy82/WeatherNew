package com.jdeveloperapps.weather.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wind implements Serializable {
    @SerializedName("speed") public float speed;
    @SerializedName("deg") public int deg;
}
