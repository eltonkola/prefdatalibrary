package com.aldroid.prefdatalibrary;

import android.app.Application;

/**
 * Created by elton on 5/7/15.
 */
public class PrefDataApp extends Application {

    private static PrefDataApp context;

    public PrefDataApp() {
    }

    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static PrefDataApp getContext() {
        return context;
    }


}
