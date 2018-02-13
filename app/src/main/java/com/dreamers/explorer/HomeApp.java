package com.dreamers.explorer;

import android.app.Application;

import com.dreamers.explorer.dagger.AppComponent;
import com.dreamers.explorer.dagger.AppModule;
import com.dreamers.explorer.dagger.DaggerAppComponent;
import com.dreamers.explorer.dagger.NetworkModule;

/**
 * Created by c029312 on 2/7/18.
 */

public class HomeApp extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDagger();
    }

    private void initDagger() {
        appComponent = DaggerAppComponent.builder()
                .networkModule(new NetworkModule())
                .appModule(new AppModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}