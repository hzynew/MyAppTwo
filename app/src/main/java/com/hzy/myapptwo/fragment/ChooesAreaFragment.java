package com.hzy.myapptwo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzy.myapptwo.MainActivity;
import com.hzy.myapptwo.R;
import com.hzy.myapptwo.activity.WeatherActivity;
import com.hzy.myapptwo.db.City;
import com.hzy.myapptwo.db.County;
import com.hzy.myapptwo.db.Province;
import com.hzy.myapptwo.utils.HttpUtil;
import com.hzy.myapptwo.utils.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by huang on 2018-03-30.
 */

public class ChooesAreaFragment extends Fragment {

    private static final int LEVEL_PROVINCE = 1;
    private static final int LEVEL_CITY = 2;
    private static final int LEVEL_COUNTY = 3;

    private ProgressDialog progressDialog;

    private TextView titleTv;
    private Button backBtn;
    private ListView listView;

    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    //省级列表数据
    private List<Province> provincesList;
    //市级列表数据
    private List<City> cityList;
    //县级列表数据
    private List<County> countyList;

    //选择的省
    private Province selProvince;
    //选择的市
    private City selCity;
    //当前选择级别
    private int currentLevel;

    public Context mContext;

    private static final String TAG = "ChooesAreaFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chooes_area, container, false);
        titleTv = view.findViewById(R.id.title_tv);
        backBtn = view.findViewById(R.id.back_btn);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selProvince = provincesList.get(i);
                    queryCitys();
                } else if (currentLevel == LEVEL_CITY) {
                    selCity = cityList.get(i);
                    queryCountys();
                } else if(currentLevel == LEVEL_COUNTY){
                    String weatherId = countyList.get(i).getWeatherId();
                    if(getActivity() instanceof MainActivity){
                        Intent intent = new Intent(mContext, WeatherActivity.class);
                        intent.putExtra("weather_id",weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
                        weatherActivity.drawerLayout.closeDrawers();
                        weatherActivity.swipeRefreshLayout.setRefreshing(true);
                        weatherActivity.requestWeather(weatherId);
                    }

                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                } else if (currentLevel == LEVEL_COUNTY) {
                    queryCitys();
                }
            }
        });
        queryProvinces();//初始化搜索省级数据
    }

    private void queryProvinces() {
        titleTv.setText("中国");
        backBtn.setVisibility(View.GONE);
        provincesList = DataSupport.findAll(Province.class);
        if (provincesList.size() > 0) {
            if(dataList == null){
                dataList = new ArrayList<>();
            }else {
                dataList.clear();
            }
            for (Province province : provincesList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china/";
            queryFromServer(address, "province");
        }
    }


    private void queryCitys() {
        titleTv.setText(selProvince.getProvinceName());
        backBtn.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            if(dataList == null){
                dataList = new ArrayList<>();
            }else {
                dataList.clear();
            }
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    private void queryCountys() {
        titleTv.setText(selCity.getCityName());
        backBtn.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?", String.valueOf(selCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            if(dataList == null){
                dataList = new ArrayList<>();
            }else {
                dataList.clear();
            }
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selProvince.getProvinceCode();
            int cityCode = selCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode +"/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean result = false;
                String responseText = response.body().string();
                switch (type) {
                    case "province":
                        result = Utility.handleResponseProvice(responseText);
                        break;
                    case "city":
                        result = Utility.handleResponseCity(responseText, selProvince.getId());
                        break;
                    case "county":
                        result = Utility.handleResponseCounty(responseText, selCity.getId());
                        break;
                    default:
                        break;
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            switch (type) {
                                case "province":
                                    queryProvinces();
                                    break;
                                case "city":
                                    queryCitys();
                                    break;
                                case "county":
                                    queryCountys();
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
            }


            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败……", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext(), 0);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("正在加载中……");
        }
        progressDialog.show();
    }

}
