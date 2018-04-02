package com.hzy.myapptwo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.hzy.myapptwo.R;
import com.hzy.myapptwo.gson.Weather;
import com.hzy.myapptwo.utils.HttpUtil;
import com.hzy.myapptwo.utils.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by huang on 2018-04-02.
 */

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;
    private LinearLayout forecastLayou;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = pref.getString("weather", null);
        if (weatherString != null) {
            Weather weather = Utility.handleResponseWeather(weatherString);
            showWeather(weather);
        } else {
            String weatherId = getIntent().getStringExtra("weather_Id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
    }

    /**
     * 根据天气ID请求城市天气信息
     *
     * @param weatherId
     */

    private void requestWeather(String weatherId) {
        String weatherURL = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=6e7b599d602d4a08ac392f04d97b7ddc";
        HttpUtil.sendOKHttpRequest(weatherURL, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String request = response.body().string();
                final Weather weather = Utility.handleResponseWeather(request);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", request);
                            editor.apply();
                            showWeather(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, " 获取天气信息失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, " 获取天气信息失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }


    private void initView() {
        weatherLayout = findViewById(R.id.weather_layout);
        forecastLayou = findViewById(R.id.forecast_layout);
        titleCity = findViewById(R.id.title_city);
        titleUpdateTime = findViewById(R.id.title_update_time);
        degreeText = findViewById(R.id.degree_text);
        weatherInfoText = findViewById(R.id.weather_info_text);
        aqiText = findViewById(R.id.aqi_text);
        pm25Text = findViewById(R.id.pm25_text);
        comfortText = findViewById(R.id.comfort_text);
        carWashText = findViewById(R.id.car_wash_text);
        sportText = findViewById(R.id.sport_text);
    }

    private void showWeather(Weather weather) {

    }
}
