package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2018/4/2.
 */

public class Forecast {
    String data;

    @SerializedName("tmp")
    Temperature temperature;

    @SerializedName("cond")
    More more;

    public class More {
        @SerializedName("txt")
        public String into;
    }

    public class Temperature {
        String max;
        String min;
    }
}
