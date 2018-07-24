package com.moxie.crawler;

import android.app.Fragment;

import com.moxie.client.manager.StatusViewHandler;

/**
 * Created by MiaoHW on 2017/9/5.
 */

public class StatusViewHandlerImpl implements StatusViewHandler {
    @Override
    public Fragment statusViewForMoxieSDK() {
        return new ImportResultFragment();
    }
}
