package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util;

import android.app.Application;

import com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.Ads.AppOpenManager;
import com.google.android.gms.ads.MobileAds;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public AppOpenManager appOpenManager;


    public MyApplication() {
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        MyApplication myApplication;
        synchronized (MyApplication.class) {
            myApplication = mInstance;
        }
        return myApplication;
    }


    public static Application getApp() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;

        MobileAds.initialize(this);
        MobileAds.setAppMuted(true);
        appOpenManager = new AppOpenManager(this);

    }
}
