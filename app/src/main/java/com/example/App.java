package com.example;

import android.app.Application;

import com.example.ympush.AndroidUtil;
import com.example.ympush.MainActivity;
import com.ydk.show.ACManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ACManager.init(this, "设备imei","分配的appId");
    }
}
