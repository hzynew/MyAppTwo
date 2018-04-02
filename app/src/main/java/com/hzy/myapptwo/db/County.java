package com.hzy.myapptwo.db;

import org.litepal.crud.DataSupport;

/**
 * Created by huang on 2018-03-30.
 */

public class County extends DataSupport{
    private int id;
    private String countyName;//县级
    private String weatherId;
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getweatherId() {
        return weatherId;
    }

    public void setCountyCode(String countyCode) {
        this.weatherId = countyCode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

}
