package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huang on 2018/4/2.
 */

public class Weather {
    public String status;
    Basic basic;
    AQI aqi;
    Now now;
    Suggestion suggestion;
    @SerializedName("daily_forecast")
    List<Forecast> forecastList;
}
