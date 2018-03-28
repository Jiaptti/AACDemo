package com.aacdemo.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class BaseEntity implements Parcelable{

    public BaseEntity() {
    }

    protected BaseEntity(Parcel in) {
    }
    public static final Creator<BaseEntity> CREATOR = new Creator<BaseEntity>() {
        @Override
        public BaseEntity createFromParcel(Parcel in) {
            return new BaseEntity(in);
        }

        @Override
        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
