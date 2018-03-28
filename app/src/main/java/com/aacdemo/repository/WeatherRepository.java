package com.aacdemo.repository;

import android.arch.lifecycle.LiveData;

import com.aacdemo.api.ApiResponse;
import com.aacdemo.api.ApiService;
import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.WeatherEntity;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class WeatherRepository {
    public static class SingletonHolder{
        private static WeatherRepository INSTANCE = new WeatherRepository();
    }

    private WeatherRepository(){}

    public static WeatherRepository getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public LiveData<ApiResponse<BaseResult<WeatherEntity>>> getWeatherNow(String key, String location, String language, String unit){
        return ApiService.getInstance().getApi().now(key, location, language, unit);
    }
}
