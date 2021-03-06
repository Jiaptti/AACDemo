package com.aacdemo.entity;

import com.aacdemo.base.BaseEntity;

import java.util.List;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class WeatherEntity extends BaseEntity {
    private Location location;
    private Now now;
    private String last_update;

    public WeatherEntity(Location location, Now now, String last_update){
        this.location = location;
        this.now = now;
        this.last_update = last_update;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
