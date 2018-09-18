package com.moxie.client.fragment;

import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import com.moxie.client.fragment.FragmentEvent.SaveAccountInfo;
import com.moxie.client.js.BaseJavaScriptInterfaceImpl;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitStart;
import com.moxie.client.tasks.model.CookieLoginInfo;
import com.moxie.client.tasks.task.LoadJsTask;
import com.moxie.client.tasks.utils.SiteConfigHelper;
import com.moxie.client.utils.ErrorHandle;
import com.moxie.client.utils.SharedPreferMgr;
import com.proguard.annotation.NotProguard;
import com.tencent.smtt.sdk.CookieManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

/* compiled from: TbsSdkJava */
public class WebViewOfficialH5Fragment extends BaseWebViewFragment {
    private Handler i;
    private SiteAccountInfo j;
    private CookieLoginInfo k;
    private LoadJsTask l;
    private ArrayList<String> m = null;
    private boolean n = false;
    private String o = "";
    private String p = "";
    private String q;
    private String r;
    private String s;

    @NotProguard
            /* compiled from: TbsSdkJava */
    class MoxieJavaScriptInterface extends BaseJavaScriptInterfaceImpl {
        MoxieJavaScriptInterface() {
        }

        @NotProguard
        @JavascriptInterface
        public void mxGetAccountInfo(String str) {
            WebViewOfficialH5Fragment.this.i.post(new Runnable() {
                public void run() {
                    WebViewOfficialH5Fragment.this.e();
                }
            });
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveAccountInfo(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(TaskStatus.ACCOUNT) && jSONObject.has("pwd")) {
                    WebViewOfficialH5Fragment.this.q = "";
                    WebViewOfficialH5Fragment.this.r = "";
                    WebViewOfficialH5Fragment.this.b(str);
                }
                if (jSONObject.has("sepwd")) {
                    WebViewOfficialH5Fragment.this.s = "";
                }
                WebViewOfficialH5Fragment.this.b(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveCookies() {
            WebViewOfficialH5Fragment.this.i.post(new Runnable() {
                public void run() {
                    if (!WebViewOfficialH5Fragment.this.n) {
                        WebViewOfficialH5Fragment.this.n = true;
                        String trim = WebViewOfficialH5Fragment.this.j.m().trim();
                        if (!(TextUtils.isEmpty(trim) || trim.contains("@"))) {
                            trim = trim + "@" + WebViewOfficialH5Fragment.this.p;
                        }
                        WebViewOfficialH5Fragment.this.j.l(trim);
                        WebViewOfficialH5Fragment.this.j
                                .p(CookieManager.getInstance()
                                        .getCookie(WebViewOfficialH5Fragment.this.customWebView.getUrl()));
                        EventBus.getDefault().post(WebViewOfficialH5Fragment.this.j);
                        EventBus.getDefault().post(new LoginSubmitStart("提交登录...", WebViewOfficialH5Fragment.this.j));
                        WebViewOfficialH5Fragment.this.n = true;
                    }
                }
            });
        }

        @NotProguard
        @JavascriptInterface
        public String mxGetAccountInfo() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(TaskStatus.ACCOUNT, WebViewOfficialH5Fragment.this.q);
                jSONObject.put("password", WebViewOfficialH5Fragment.this.r);
                jSONObject.put("sepwd", WebViewOfficialH5Fragment.this.s);
                return jSONObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            super.onCreateView(layoutInflater, viewGroup, bundle);
            Bundle arguments = getArguments();
            this.p = arguments.getString("host");
            this.q = arguments.getString(TaskStatus.ACCOUNT);
            this.r = arguments.getString("password");
            String string = arguments.getString("loginParams");
            if (!TextUtils.isEmpty(string)) {
                try {
                    JSONObject jSONObject = new JSONObject(string);
                    if (jSONObject.has("username")) {
                        this.q = jSONObject.getString("username");
                    }
                    if (jSONObject.has("password")) {
                        this.r = jSONObject.getString("password");
                    }
                    if (jSONObject.has("sepwd")) {
                        this.s = jSONObject.getString("sepwd");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.k = SiteConfigHelper.a(this.p);
            a(this.k.a(), this.k.j());
            this.i = new Handler(Looper.getMainLooper());
            if (this.j == null) {
                this.j = new SiteAccountInfo();
            }
            this.j.a(Integer.valueOf(1));
            if (this.p.equalsIgnoreCase(MxParam.PARAM_FUNCTION_LINKEDIN)) {
                this.j.d("SOCIAL");
                this.j.c("LINKEDIN");
            } else {
                this.j.d("EMAIL");
            }
            this.l = new LoadJsTask(this);
            this.l.execute(new String[] {this.p, this.k.i(), this.k.j()});
            this.customWebView.addJavascriptInterface(new MoxieJavaScriptInterface(), "android");
            a(new OnWebViewClientListener() {
                @Override
                public final void shouldOverrideUrlLoading(String str) {
                }

                @Override
                public final void onPageFinished(String str) {
                    WebViewOfficialH5Fragment.this.i.post(new Runnable() {

                        public void run() {
                            WebViewOfficialH5Fragment.a(WebViewOfficialH5Fragment.this);
                        }
                    });
                }

                @Override
                public final boolean shouldOverrideUrlLoading() {
                    WebViewOfficialH5Fragment.this.c(WebViewOfficialH5Fragment.this.k.k());
                    return false;
                }
            });
            c(this.k.k());
            return this.b;
        } catch (Throwable e2) {
            ErrorHandle.b("WebViewOfficialH5Fragment#onCreateView", e2);
            ErrorHandle.a(getActivity(), e2);
            return null;
        }
    }

    private void c(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.customWebView.getSettings().setUserAgentString(str);
        }
    }

    public final void d() {
        try {
            this.customWebView.loadUrl(this.customWebView.getUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(TaskStatus.ACCOUNT)) {
                this.j.l(jSONObject.getString(TaskStatus.ACCOUNT));
                EventBus.getDefault().post(new SaveAccountInfo(jSONObject.getString(TaskStatus.ACCOUNT)));
            }
            if (jSONObject.has("pwd")) {
                this.j.m(jSONObject.getString("pwd"));
            }
            if (jSONObject.has("sepwd")) {
                this.j.n(jSONObject.getString("sepwd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void e() {
        JSONObject jSONObject = new JSONObject();
        try {
            String m = this.j.m();
            String str = TaskStatus.ACCOUNT;
            if (TextUtils.isEmpty(m)) {
                m = "";
            }
            jSONObject.put(str, m);
            m = this.j.n();
            if (TextUtils.isEmpty(m)) {
                m = "";
            }
            jSONObject.put("pwd", m);
            m = this.j.o();
            if (TextUtils.isEmpty(m)) {
                m = "";
            }
            jSONObject.put("sepwd", m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadUrl("window.mxGetAccountInfoCallback('" + jSONObject.toString() + "')");
    }

    @Override
    public void onDestroy() {
        try {
            if (!(this.l == null || this.l.b())) {
                this.l.c();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    static /* synthetic */ void a(WebViewOfficialH5Fragment webViewOfficialH5Fragment) {
        if (TextUtils.isEmpty(webViewOfficialH5Fragment.o)) {
            webViewOfficialH5Fragment.loadUrl(SharedPreferMgr.a(webViewOfficialH5Fragment.p));
        } else {
            webViewOfficialH5Fragment.loadUrl(webViewOfficialH5Fragment.o);
        }
    }
}
