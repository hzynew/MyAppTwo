package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2018/4/2.
 */

public class Suggestion {

    @SerializedName("comf")
    Comfort comfort;
    @SerializedName("cw")
    Carwash carwash;

    Sport sport;


    private class Comfort {
        @SerializedName("txt")
        String info;
    }

    private class Carwash {
        @SerializedName("txt")
        String info;
    }

    private class Sport {
        @SerializedName("txt")
        String info;
    }
}
