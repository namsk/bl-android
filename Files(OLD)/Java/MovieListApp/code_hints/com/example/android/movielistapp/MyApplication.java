package com.example.android.movielistapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by namsk on 2018. 2. 12..
 */

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    public static Context getContext() {
        return context;
    }
}
