package com.aacdemo.persistence.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018/3/30.
 */
@Entity(tableName = "city")
public class CityEntity {
    @NonNull
    @PrimaryKey
    public String id;
    public String name_zh;//城市名称
    public String name_en;
    public String country_name;//所属国家
    public String country_code;
    public String province_zh;//省级
    public String province_en;
    public String city_zh;//地级
    public String city_en;

    public CityEntity(@NonNull String id, String name_zh, String name_en, String country_name, String country_code, String province_zh, String province_en, String city_zh, String city_en) {
        this.id = id;
        this.name_zh = name_zh;
        this.name_en = name_en;
        this.country_name = country_name;
        this.country_code = country_code;
        this.province_zh = province_zh;
        this.province_en = province_en;
        this.city_zh = city_zh;
        this.city_en = city_en;
    }

    @Override
    public String toString() {
        return "CityEntity{" +
                "id='" + id + '\'' +
                ", name_zh='" + name_zh + '\'' +
                ", name_en='" + name_en + '\'' +
                ", country_name='" + country_name + '\'' +
                ", country_code='" + country_code + '\'' +
                ", province_zh='" + province_zh + '\'' +
                ", province_en='" + province_en + '\'' +
                ", city_zh='" + city_zh + '\'' +
                ", city_en='" + city_en + '\'' +
                '}';
    }
}
