package com.moxie.client.fragment;

import com.moxie.client.utils.ErrorHandle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: TbsSdkJava */
public class WebViewAgreementH5Fragment extends BaseWebViewFragment {
    private static String j = "";
    private static String script = "";
    Handler i;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            super.onCreateView(layoutInflater, viewGroup, bundle);
            Bundle arguments = getArguments();
            j = arguments.getString("url");
            script = arguments.getString("script");
            this.i = new Handler(Looper.getMainLooper());
            this.customWebView.getSettings().setCacheMode(2);
            this.customWebView
                    .loadUrl(TextUtils.isEmpty(j) ? "https://api.51datakey.com/h5/agreement/agreement.html" : j);
            a(new OnWebViewClientListener() {
                @Override
                public final void onPageFinished(String str) {
                    WebViewAgreementH5Fragment.this.i.post(new Runnable() {
                        public void run() {
                            WebViewAgreementH5Fragment.a(WebViewAgreementH5Fragment.this);
                        }
                    });
                }
            });
            return this.b;
        } catch (Throwable e) {
            ErrorHandle.b("WebViewOfficialH5Fragment#onCreateView", e);
            ErrorHandle.a(getActivity(), e);
            return null;
        }
    }
    @Override
    protected final boolean a() {
        return true;
    }

    public final void d() {
        try {
            this.customWebView.loadUrl(this.customWebView.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.customWebView != null) {
            this.customWebView.resumeTimers();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        try {
            if (this.customWebView != null) {
                this.customWebView.removeAllViews();
                this.customWebView.destroy();
                this.customWebView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    static /* synthetic */ void a(WebViewAgreementH5Fragment webViewAgreementH5Fragment) {
        if (!TextUtils.isEmpty(script)) {
            webViewAgreementH5Fragment.customWebView.loadUrl("javascript:" + script);
        }
    }
}
