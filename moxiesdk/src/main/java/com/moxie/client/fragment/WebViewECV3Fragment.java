package com.moxie.client.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.moxie.client.fragment.mvp.contract.WebViewECV3Contract.Presenter;
import com.moxie.client.fragment.mvp.contract.WebViewECV3Contract.View;
import com.moxie.client.fragment.mvp.presenter.WebViewECV3Presenter;
import com.moxie.client.fragment.mvp.presenter.WebViewECV3Presenter.MoxieJavaScriptInterface;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.utils.Assert;
import com.moxie.client.utils.ErrorHandle;
import com.tencent.smtt.sdk.CookieManager;
import org.greenrobot.eventbus.EventBus;

/* compiled from: TbsSdkJava */
public class WebViewECV3Fragment extends BaseWebViewFragment implements View {
    private Presenter i;

    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            super.onCreateView(layoutInflater, viewGroup, bundle);
            new WebViewECV3Presenter(this, getArguments()).b();
            return this.b;
        } catch (Throwable e) {
            ErrorHandle.b("WebViewECV3Fragment#onCreateView", e);
            ErrorHandle.a(getActivity(), e);
            return null;
        }
    }

    public final void a(Presenter presenter) {
        this.i = (Presenter) Assert.a(presenter);
    }

    public static void a(SiteAccountInfo siteAccountInfo) {
        EventBus.getDefault().post(siteAccountInfo);
    }

    public final void a(MoxieJavaScriptInterface moxieJavaScriptInterface) {
        this.customWebView.addJavascriptInterface(moxieJavaScriptInterface, "android");
        this.customWebView.getSettings().setJavaScriptEnabled(true);
    }

    public final void d() {
        try {
            CookieManager.getInstance().removeAllCookie();
            this.customWebView.clearCache(true);
            this.customWebView.loadUrl(this.customWebView.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.customWebView.getSettings().setUserAgentString(str);
        }
    }

    public void onDestroy() {
        if (this.i != null) {
            this.i.a();
        }
        super.onDestroy();
    }

    public final Presenter e() {
        return this.i;
    }
}
