package com.inbeacon.inbeacon_sdk_android_example;

import android.app.Application;
import android.util.Log;

import com.inbeacon.sdk.InbeaconManager;

import java.util.HashMap;

/**
 * Created by rvw on 7-10-2014.
 */
public class myApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InbeaconManager.initialize(this, "demo", "QmE3WWlMNUluUnp2Y2h1MUF4NFpJQ01aZ2ZCRnVGbng");
        Log.i("hello","welkom inbeaconapplication");

        HashMap<String, String> user=new HashMap<String, String>();
        user.put("name","Dwight Schulz");
        user.put("email","dwight@ateam.com");
        InbeaconManager.getSharedInstance().attachUser(user);
        InbeaconManager.getSharedInstance().refresh();
    }

}
