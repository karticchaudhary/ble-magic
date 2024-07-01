package com.kartic.blemagic.sample.ui;

import android.content.Context;

import com.kartic.blemagic.BleManager;

public class MyBleManager {

    private static MyBleManager instance = null;

    private final BleManager bleManager;

    private MyBleManager(Context context) {
        bleManager = new BleManager(context);
    }

    public static synchronized MyBleManager getInstance(Context context) {
        if (instance == null) {
            instance = new MyBleManager(context.getApplicationContext());
        }
        return instance;
    }

    public BleManager getBleManager() {
        return bleManager;
    }

    public void nukeSession() {
        if (bleManager != null)
            bleManager.nukeSession();
    }

}
