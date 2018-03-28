package com.aacdemo.api;

import android.arch.lifecycle.LiveData;

import com.aacdemo.base.BaseResult;
import com.aacdemo.entity.Location;
import com.aacdemo.entity.WeatherEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public interface Api {
    /**
     * 天气实况
     */
    @GET("weather/now.json")
    LiveData<ApiResponse<BaseResult<WeatherEntity>>> now(@Query("key") String key, @Query("location") String location, @Query("language") String language, @Query("unit") String unit);

    /**
     * 城市搜索
     */
    @GET("location/search.json")
    LiveData<ApiResponse<BaseResult<Location>>> searchCity(@Query("key") String key, @Query("q") String q, @Query("language") String language, @Query("limit") int limit, @Query("offset") int offset);


}
