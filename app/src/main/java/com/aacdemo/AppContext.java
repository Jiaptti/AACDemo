package com.aacdemo;

import android.app.Application;

import com.aacdemo.persistence.database.AppDataBase;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class AppContext extends Application{
    private static AppContext mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        AppDataBase.init();
    }

    public static AppContext getAppContext() {
        return mApp;
    }
}
