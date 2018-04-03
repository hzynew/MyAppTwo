package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huang on 2018/4/2.
 */

public class Weather {
    public Basic basic;
    public String status;
    public Now now;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
    public AQI aqi;
    public Suggestion suggestion;


}
