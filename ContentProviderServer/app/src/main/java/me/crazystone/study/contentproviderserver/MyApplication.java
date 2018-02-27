package me.crazystone.study.contentproviderserver;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by crazy_stone on 18-2-27.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
