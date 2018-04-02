package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2018/4/2.
 */

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;
    @SerializedName("cw")
    public Carwash carwash;

    public Sport sport;


    public class Comfort {
        @SerializedName("txt")
        public String info;
    }

    public class Carwash {
        @SerializedName("txt")
        public String info;
    }

    public class Sport {
        @SerializedName("txt")
        public String info;
    }
}
