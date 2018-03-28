package com.aacdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.aacdemo.base.BaseEntity;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class Location extends BaseEntity implements Parcelable{
    private String id;
    private String name;
    private String country;
    private String path;
    private String timezone;
    private String timezone_offset;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(String timezone_offset) {
        this.timezone_offset = timezone_offset;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.country);
        dest.writeString(this.path);
        dest.writeString(this.timezone);
        dest.writeString(this.timezone_offset);
    }

    public Location() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Location(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.country = in.readString();
        this.path = in.readString();
        this.timezone = in.readString();
        this.timezone_offset = in.readString();
    }
    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
