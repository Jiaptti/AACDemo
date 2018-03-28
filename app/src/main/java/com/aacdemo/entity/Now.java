package com.aacdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.aacdemo.base.BaseEntity;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class Now extends BaseEntity implements Parcelable{
    private String text;
    private int code;
    private int temperature;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.code);
        dest.writeInt(this.temperature);
    }

    public Now() {
    }

    protected Now(Parcel in) {
        this.text = in.readString();
        this.code = in.readInt();
        this.temperature = in.readInt();
    }

    public static final Creator<Now> CREATOR = new Creator<Now>() {
        @Override
        public Now createFromParcel(Parcel source) {
            return new Now(source);
        }

        @Override
        public Now[] newArray(int size) {
            return new Now[size];
        }
    };
}
