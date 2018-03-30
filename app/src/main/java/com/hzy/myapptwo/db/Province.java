package com.hzy.myapptwo.db;

import org.litepal.crud.DataSupport;

/**
 * Created by huang on 2018-03-30.
 */

public class Province extends DataSupport{

    private int id;
    private String provinceName;//省级
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

}