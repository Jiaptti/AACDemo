package com.aacdemo.persistence.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018/3/29.
 */
@Entity(tableName = "favorite")
public class FavoriteEntity {
    @NonNull
    @PrimaryKey
    public String id;//城市ID
    public String name;//城市名称
    public String path;//隶属层级，从小到大

    public FavoriteEntity(@NonNull String id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return "FavoriteEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
