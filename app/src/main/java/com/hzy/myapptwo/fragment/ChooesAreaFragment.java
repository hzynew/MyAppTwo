package com.hzy.myapptwo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hzy.myapptwo.R;
import com.hzy.myapptwo.db.City;
import com.hzy.myapptwo.db.County;
import com.hzy.myapptwo.db.Province;

import java.util.ArrayList;
import java.util.List;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chooes_area,container,false);
        titleTv = view.findViewById(R.id.title_tv);
        backBtn = view.findViewById(R.id.back_btn);
        listView = view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryProvinces();//初始化搜索省级数据
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(currentLevel == LEVEL_PROVINCE){
                    selProvince = provincesList.get(i);
                    queryCitys();
                }else if (currentLevel == LEVEL_CITY){
                    selCity = cityList.get(i);
                    queryCountys();
                }
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel == LEVEL_CITY){
                    queryProvinces();
                }else if (currentLevel == LEVEL_COUNTY){
                    queryCitys();
                }
            }
        });

    }

    private void queryCountys() {

    }

    private void queryProvinces() {

    }

    private void queryCitys() {

    }
}
