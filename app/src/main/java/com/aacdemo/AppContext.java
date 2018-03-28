package com.aacdemo;

import android.app.Application;

/**
 * Created by hanjiaqi on 2018/3/28.
 */

public class AppContext extends Application{
    private static AppContext mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
//        AppContext.init();
    }

    public static AppContext getAppContext() {
        return mApp;
    }
}
