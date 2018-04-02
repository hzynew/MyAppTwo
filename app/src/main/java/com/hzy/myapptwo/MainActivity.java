package com.hzy.myapptwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hzy.myapptwo.activity.WeatherActivity;
import com.hzy.myapptwo.fragment.ChooesAreaFragment;
import com.hzy.myapptwo.gson.Weather;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getString("weather",null)!=null){
            startActivity(new Intent(this, WeatherActivity.class));
            finish();
        }else {
            createFragment();
        }
    }

    public void createFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.chooes_area_frag,new ChooesAreaFragment());
        transaction.commit();
    }
}
