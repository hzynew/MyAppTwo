package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2018/4/1.
 */

public class AQI {
    @SerializedName("city")
    public AQICity aqIcity;

    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
