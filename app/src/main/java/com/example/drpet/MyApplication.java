package com.example.drpet;

import android.app.Application;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().name("RealmData.realm").build();
        Realm.setDefaultConfiguration(configuration);*/
    }
}
