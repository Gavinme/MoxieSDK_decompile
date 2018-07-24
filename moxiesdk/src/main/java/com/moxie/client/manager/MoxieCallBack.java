package com.moxie.client.manager;

import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class MoxieCallBack {
    public boolean callback(MoxieContext moxieContext, MoxieCallBackData moxieCallBackData) {
        return false;
    }

    public boolean onError(MoxieContext moxieContext, int i, Throwable th) {
        return false;
    }
}
