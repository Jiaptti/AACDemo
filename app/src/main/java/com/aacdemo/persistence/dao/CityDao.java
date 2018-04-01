package com.aacdemo.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.aacdemo.persistence.entity.CityEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30.
 */
@Dao
public interface CityDao {
    /**
     * 省
     */
    @Query("select DISTINCT province_zh from city where country_name = :country_name")
    public LiveData<List<String>> getProvinces(String country_name);

    /**
     * 市
     */
    @Query("select distinct city_zh from city where province_zh = :province_zh")
    public LiveData<List<String>> getCities(String province_zh);

    /**
     * 县(地级市下)
     */
    @Query("select * from city where city_zh = :city_zh")
    public LiveData<List<CityEntity>> getCounties(String city_zh);

    /**
     * 县(直辖市下)
     */
    @Query("SELECT * FROM city WHERE province_zh = :province_zh")
    public LiveData<List<CityEntity>> getMunicipalCounties(String province_zh);
}
