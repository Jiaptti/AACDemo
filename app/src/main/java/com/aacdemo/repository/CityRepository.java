package com.aacdemo.repository;

import android.arch.lifecycle.LiveData;

import com.aacdemo.api.ApiResponse;
import com.aacdemo.api.ApiService;
import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.Location;
import com.aacdemo.persistence.database.AppDataBase;
import com.aacdemo.persistence.entity.CityEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */

public class CityRepository {
    private volatile static CityRepository mInstance;

    public static CityRepository getInstance(){
        if(mInstance == null) {
            synchronized (CityRepository.class){
                if(mInstance == null){
                    mInstance = new CityRepository();
                }
            }
        }
        return mInstance;
    }
    private CityRepository(){}

    public LiveData<ApiResponse<BaseResult<Location>>> searchCity(String key, String q, String language, int limit, int offset){
        return ApiService.getInstance().getApi().searchCity(key, q, language, limit, offset);
    }

    public LiveData<List<String>> getCities(String province_zh){
        return AppDataBase.getInstance().cityDao().getCities(province_zh);
    }

    public LiveData<List<CityEntity>> getCounties(String city_zh){
        return AppDataBase.getInstance().cityDao().getCounties(city_zh);
    }

    public LiveData<List<String>> getProvinces(String country_name){
        return AppDataBase.getInstance().cityDao().getProvinces(country_name);
    }

    public LiveData<List<CityEntity>> getMunicipalCounties(String province_zh){
        return AppDataBase.getInstance().cityDao().getMunicipalCounties(province_zh);
    }
}
