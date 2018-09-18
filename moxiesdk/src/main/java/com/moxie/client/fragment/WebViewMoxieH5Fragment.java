package com.moxie.client.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.encrypt.Encrypt;
import com.moxie.client.fragment.FragmentEvent.BackToClose;
import com.moxie.client.fragment.FragmentEvent.BackToFinish;
import com.moxie.client.fragment.FragmentEvent.OpenOfficialWebView;
import com.moxie.client.fragment.FragmentEvent.RefreshStatus;
import com.moxie.client.fragment.FragmentEvent.RefreshTitle;
import com.moxie.client.fragment.FragmentEvent.SaveAccountInfo;
import com.moxie.client.fragment.FragmentEvent.SaveTaskId;
import com.moxie.client.js.BaseJavaScriptInterfaceImpl;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.utils.ErrorHandle;
import com.proguard.annotation.NotProguard;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class WebViewMoxieH5Fragment extends BaseWebViewFragment {
    private static String i = "";

    @NotProguard
    /* compiled from: TbsSdkJava */
    class MoxieJavaScriptInterface extends BaseJavaScriptInterfaceImpl {
        MoxieJavaScriptInterface() {
        }

        @NotProguard
        @JavascriptInterface
        public String mxGetTenantInfo() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("userId", GlobalParams.i().a().getUserId());
                jSONObject.put("apiKey", GlobalParams.i().a().getApiKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jSONObject.toString();
        }

        @NotProguard
        @JavascriptInterface
        public void mxBack() {
            EventBus.getDefault().post(new BackToClose());
        }

        @NotProguard
        @JavascriptInterface
        public void mxFinishImport(String str) {
            EventBus.getDefault().post(new BackToFinish(str));
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveTaskId(String str) {
            EventBus.getDefault().post(new SaveTaskId(str));
        }

        @NotProguard
        @JavascriptInterface
        public void mxRefreshStatus(String str) {
            EventBus.getDefault().post(new RefreshStatus(str));
        }

        @NotProguard
        @JavascriptInterface
        public void mxRefreshTitle(String str) {
            EventBus.getDefault().post(new RefreshTitle(str));
        }

        @NotProguard
        @JavascriptInterface
        public void mxRelogin(String str) {
            try {
                EventBus.getDefault().post(new OpenOfficialWebView(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @NotProguard
        @JavascriptInterface
        public String mxEncryptPostBodyString(String str) {
            CharSequence encyptBody;
            Exception e;
            try {
                encyptBody = Encrypt.getInstance().encyptBody(str);
                try {
                    if (TextUtils.isEmpty(encyptBody)) {
                        return str;
                    }
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return encyptBody.toString();
                }
            } catch (Exception e3) {
                e = e3;
                encyptBody = str;
                e.printStackTrace();
                return encyptBody.toString();
            }
            return encyptBody.toString();
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveAccountInfo(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(TaskStatus.ACCOUNT)) {
                    EventBus.getDefault().post(new SaveAccountInfo(jSONObject.getString(TaskStatus.ACCOUNT)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = null;
        try {
            super.onCreateView(layoutInflater, viewGroup, bundle);
            i = getArguments().getString("url");
            a(new ArrayList<String>(), i);
            this.customWebView.addJavascriptInterface(new MoxieJavaScriptInterface(), "android");
            this.customWebView.loadUrl(i);
            return this.b;
        } catch (Throwable e) {
            ErrorHandle.b("WebViewMoxieH5Fragment#onCreateView", e);
            ErrorHandle.a(getActivity(), e);
            return view;
        }
    }

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
}
