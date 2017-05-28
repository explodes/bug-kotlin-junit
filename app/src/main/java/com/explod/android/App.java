package com.explod.android;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.explod.logging.Logger;


public abstract class App extends Application {

    @Nullable
    private static App sInstance;

    @NonNull
    public static App getApp() {
        if (sInstance == null) throw new NullPointerException("App not created");
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Logger.init(this);
    }
}
