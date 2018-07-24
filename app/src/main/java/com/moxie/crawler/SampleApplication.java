package com.moxie.crawler;

import android.app.Application;

import com.moxie.client.manager.MoxieSDK;

/**
 * Created by MiaoHW on 2017/6/30.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MoxieSDK.init(this);
    }
}
