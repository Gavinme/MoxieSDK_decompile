package com.moxie.client.js;

import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public interface BaseJavaScriptInterface {
    String getGlobalMap();

    void mxCanLeave(String str);

    String mxGetClientVersion();

    String mxGetUserInfo();

    void mxHideWebView();

    void mxLog(String str);

    void mxOpenUrl(String str);

    void mxOpenWebView(String str);

    void mxShowWebView();

    void setCallbackMap(String str);

    void setGlobalMap(String str);
}
