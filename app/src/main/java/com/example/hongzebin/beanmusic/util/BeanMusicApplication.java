package com.example.hongzebin.beanmusic.util;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class BeanMusicApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
