package com.hzy.myapptwo.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.hzy.myapptwo.db.City;
import com.hzy.myapptwo.db.County;
import com.hzy.myapptwo.db.Province;
import com.hzy.myapptwo.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huang on 2018-03-30.
 */

public class Utility {

    /**
     * 解析省级数据
     * @param resopnse
     * @return
     */
    public static boolean handleResponseProvice(String resopnse){
        if(!TextUtils.isEmpty(resopnse)){
            try{
                JSONArray allProvice = new JSONArray(resopnse);
                for(int i=0;i<allProvice.length();i++){
                    JSONObject provinceObject = allProvice.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析市级数据
     * @param resopnse
     * @return
     */
    public static boolean handleResponseCity(String resopnse,int provinceId){
        if(!TextUtils.isEmpty(resopnse)){
            try{
                JSONArray allCity = new JSONArray(resopnse);
                for(int i=0;i<allCity.length();i++){
                    JSONObject cityObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 解析县级数据
     * @param resopnse
     * @return
     */
    public static boolean handleResponseCounty(String resopnse,int cityId){
        if(!TextUtils.isEmpty(resopnse)){
            try{
                JSONArray allCounty = new JSONArray(resopnse);
                for(int i=0;i<allCounty.length();i++){
                    JSONObject countyObject = allCounty.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 通过GSON将返回的JSON数据解析成weather实体类
     */
    public static Weather handleResponseWeather(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
