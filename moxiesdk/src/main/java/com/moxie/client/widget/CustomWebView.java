package com.moxie.client.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import com.proguard.annotation.NotProguard;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;

@NotProguard
@SuppressLint({"SetJavaScriptEnabled"})
/* compiled from: TbsSdkJava */
public class CustomWebView extends WebView {
    public static final String TAG = "CustomWebView";
    private Context context;

    public CustomWebView(Context context) {
        super(context);
        this.context = context.getApplicationContext();
        setup();
    }

    public CustomWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context.getApplicationContext();
        setup();
    }

    public CustomWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context.getApplicationContext();
        setup();
    }

    public void setup() {
        setInitialScale(0);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        requestFocusFromTouch();
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setDatabaseEnabled(true);
        String path = this.context.getDir("database", 0).getPath();
        settings.setDatabasePath(path);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationDatabasePath(path);
        settings.setGeolocationEnabled(true);
        settings.setAppCacheMaxSize(8388608);
        settings.setAppCachePath(this.context.getDir("cache", 0).getPath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(-1);
        setScrollBarStyle(0);
    }
}
