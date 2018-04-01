package com.hzy.myapptwo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huang on 2018/4/1.
 */

public class Now {
    @SerializedName("tmp")
    String temperature;
    @SerializedName("cond")
    More more;

    private class More {
        @SerializedName("txt")
        public String into;
    }
}
