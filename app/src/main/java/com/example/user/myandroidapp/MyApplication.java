package com.example.user.myandroidapp;

import android.app.Application;
import android.view.Display;

import com.example.user.myandroidapp.model.OwnedPokemonInfo;
import com.facebook.appevents.AppEventsLogger;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseObject;
import com.facebook.FacebookSdk;

/**
 * Created by user on 2016/8/25.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        ParseObject.registerSubclass(OwnedPokemonInfo.class); //register parse object
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .enableLocalDataStore()
                .applicationId("wtv7ED5J2PeCGDWedvOTJmJpspDlbQt2QfxD94Xj")
                .clientKey("ZmKvxQyaSZuyACQIadcpubiOXuWKQBSKxlwjpklo")
                .server("https://parseapi.back4app.com/")
                .build()
        );

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheSize(50 * 1024 * 1024) //50MB
                .diskCacheFileCount(100)
                .build();

        ImageLoader.getInstance().init(config);

    }
}
