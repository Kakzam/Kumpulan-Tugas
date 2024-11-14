package com.sekud.id.base;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
