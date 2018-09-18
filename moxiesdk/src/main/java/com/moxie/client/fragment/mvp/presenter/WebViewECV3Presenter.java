package com.moxie.client.fragment.mvp.presenter;

import java.util.HashMap;
import java.util.regex.Pattern;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.file.FileConstant;
import com.moxie.client.fragment.BaseWebViewFragment.OnWebViewClientListener;
import com.moxie.client.fragment.FragmentEvent.SaveAccountInfo;
import com.moxie.client.fragment.LogEntry;
import com.moxie.client.fragment.WebViewECV3Fragment;
import com.moxie.client.fragment.mvp.contract.WebViewECV3Contract.Presenter;
import com.moxie.client.fragment.mvp.contract.WebViewECV3Contract.View;
import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.js.BaseJavaScriptInterfaceImpl;
import com.moxie.client.model.JsBaseRequest;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.model.TaskStatusDescription;
import com.moxie.client.tasks.event.ScreenCaptureEvent;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitStart;
import com.moxie.client.tasks.event.TaskStatusEvent;
import com.moxie.client.tasks.event.UploadFileEvent;
import com.moxie.client.tasks.model.CookieLoginInfo;
import com.moxie.client.tasks.task.LoadJsTask;
import com.moxie.client.tasks.utils.SiteConfigHelper;
import com.moxie.client.utils.Base64;
import com.moxie.client.utils.ErrorHandle;
import com.moxie.client.utils.SharedPreferMgr;
import com.moxie.client.utils.ZipUtil;
import com.orhanobut.logger.Logger;
import com.proguard.annotation.NotProguard;
import com.tencent.smtt.sdk.CookieManager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;

/* compiled from: TbsSdkJava */
public class WebViewECV3Presenter implements Presenter {
    private static String g = "";
    private static String l = "1";
    private static String m = "0";
    private Handler handler;
    private SiteAccountInfo b;
    private CookieLoginInfo cookieLoginInfo;
    private LoadJsTask d;
    private String e = "";
    private WebViewECV3Fragment webViewECV3Fragment;
    private HashMap<String, byte[]> hashMap = new HashMap();
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private String n;
    private String o;

    @NotProguard
    /* compiled from: TbsSdkJava */
    public class MoxieJavaScriptInterface extends BaseJavaScriptInterfaceImpl {
        String TAG = "JavascriptInterface";

        @NotProguard
        @JavascriptInterface
        public void mxSaveScreenShot() {

            Logger.e(TAG, new LogEntry("mxSaveScreenShot").toString());
            EventBus.getDefault().post(new ScreenCaptureEvent());
        }

        @NotProguard
        @JavascriptInterface
        public void mxCreateTask() {

            Logger.e(TAG, new LogEntry("mxCreateTask").toString());
            WebViewECV3Presenter.f(WebViewECV3Presenter.this);
        }

        @NotProguard
        @JavascriptInterface
        public void mxRequest(final String str, final String str2) {
            Logger.e(TAG, new LogEntry("mxRequest", str, str2).toString());
            WebViewECV3Presenter.this.handler.post(new Runnable() {

                public void run() {
                    WebViewECV3Presenter.handleReq(WebViewECV3Presenter.this, str, str2);
                }
            });
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveRequest(final String str) {
            Logger.json(str);
            WebViewECV3Presenter.this.handler.post(new Runnable() {

                public void run() {
                    WebViewECV3Presenter.d(WebViewECV3Presenter.this, str);
                }
            });
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveCrawInfo(String str) {
            Logger.e(TAG, new LogEntry("mxSaveCrawInfo", str).toString());
            try {
                WebViewECV3Presenter.this.n = new JSONObject(str).optString("successUrlRegex");
            } catch (Throwable e) {
                ErrorHandle.b("mxSaveCrawInfo fail", e);
            }
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveProgress(String str) {
            Logger.e(TAG, new LogEntry("mxSaveProgress", str).toString());
            if (GlobalParams.i().a() == null || TextUtils.isEmpty(GlobalParams.i().a().getLoadingViewText())) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    String optString = jSONObject.optString(TaskStatus.PERCENT);
                    if (!TextUtils.isEmpty(optString) && optString.length() > 4) {
                        optString = optString.substring(0, 4);
                    }
                    EventBus.getDefault().post(new TaskStatusEvent.TaskStatusProgressEvent(
                            new TaskStatusDescription(jSONObject.optString("text"), optString)));
                } catch (Throwable e) {
                    ErrorHandle.b("mxSaveProgress fail", e);
                }
            }
        }

        @NotProguard
        @JavascriptInterface
        public void mxGetAccountInfo(String str) {
            Logger.e(TAG, new LogEntry("mxGetAccountInfo", str).toString());
            WebViewECV3Presenter.this.handler.post(new Runnable() {
                final /* synthetic */ WebViewECV3Presenter a;

                {
                    this.a = WebViewECV3Presenter.this;
                }

                public void run() {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        String m = this.a.b.m();
                        String str = TaskStatus.ACCOUNT;
                        if (TextUtils.isEmpty(m)) {
                            m = "";
                        }
                        jSONObject.put(str, m);
                        String n = this.a.b.n();
                        m = this.a.b.o();
                        if (TextUtils.isEmpty(n)) {
                            n = "";
                        }
                        jSONObject.put("pwd", n);
                        if (TextUtils.isEmpty(m)) {
                            m = "";
                        }
                        jSONObject.put("sepwd", m);
                    } catch (Throwable e) {
                        ErrorHandle.b("callbackJsAccount fail", e);
                    }
                    this.a.webViewECV3Fragment
                            .loadUrl("window.mxGetAccountInfoCallback('" + jSONObject.toString() + "')");
                }
            });
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveAccountInfo(final String str) {
            Logger.e(TAG, new LogEntry("mxSaveAccountInfo", str).toString());
            WebViewECV3Presenter.this.handler.post(new Runnable() {
                final /* synthetic */ WebViewECV3Presenter b = WebViewECV3Presenter.this;

                public void run() {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has(TaskStatus.ACCOUNT)) {
                            this.b.b.l(jSONObject.getString(TaskStatus.ACCOUNT).trim());
                            EventBus.getDefault().post(new SaveAccountInfo(jSONObject.getString(TaskStatus.ACCOUNT)));
                        }
                        if (jSONObject.has("pwd")) {
                            this.b.b.m(jSONObject.getString("pwd").trim());
                        }
                        if (jSONObject.has("sepwd")) {
                            this.b.b.n(jSONObject.getString("sepwd").trim());
                        }
                    } catch (Throwable e) {
                        ErrorHandle.b("WebViewECV3Presenter", e);
                    }
                }
            });
        }

        @NotProguard
        @JavascriptInterface
        public void mxSaveItem(String str) {
            Logger.e(TAG, new LogEntry("mxSaveItem", str).toString());
            String optString;
            String optString2;
            Throwable th;
            String str2 = "utf-8";
            try {
                JSONObject jSONObject = new JSONObject(str);
                optString = jSONObject.optString("itemName");
                try {
                    optString2 = jSONObject.optString("data");
                } catch (Throwable e) {
                    Throwable th2 = e;
                    String str3 = null;
                    th = th2;
                    ErrorHandle.b("mxSaveItem fail", th);
                    if (WebViewECV3Presenter.this.hashMap != null) {
                        return;
                    }
                    return;
                }
                try {
                    if (jSONObject.has("encoding")) {
                        str2 = jSONObject.getString("encoding");
                    }
                } catch (JSONException e2) {
                    th = e2;
                    ErrorHandle.b("mxSaveItem fail", th);
                    if (WebViewECV3Presenter.this.hashMap != null) {
                        return;
                    }
                    return;
                }
            } catch (Throwable e3) {
                optString = null;
                th = e3;
                optString2 = null;
                ErrorHandle.b("mxSaveItem fail", th);
                if (WebViewECV3Presenter.this.hashMap != null) {
                    return;
                }
                return;
            }
            if (WebViewECV3Presenter.this.hashMap
                    != null && !TextUtils.isEmpty(optString) && !WebViewECV3Presenter.this.hashMap
                    .containsKey(optString) && !TextUtils.isEmpty(optString2)) {
                try {
                    WebViewECV3Presenter.this.hashMap.put(optString, optString2.getBytes(str2));
                } catch (Throwable e4) {
                    ErrorHandle.b("mxSaveItem encoding fail", e4);
                }
            }
        }

        @NotProguard
        @JavascriptInterface
        public void mxUpload() {
            Logger.e(TAG, new LogEntry("mxUpload").toString());
            if (!WebViewECV3Presenter.this.k) {
                WebViewECV3Presenter.this.k = true;
                WebViewECV3Presenter.k(WebViewECV3Presenter.this);
            }
        }
    }

    public WebViewECV3Presenter(@NonNull View view, Bundle bundle) {
        this.webViewECV3Fragment = (WebViewECV3Fragment) view;
        this.webViewECV3Fragment.a((Presenter) this);
        this.webViewECV3Fragment.a(new MoxieJavaScriptInterface());
        this.e = bundle.getString("host");
        this.cookieLoginInfo = SiteConfigHelper.a(this.e);
        g = this.cookieLoginInfo.g();
        this.hashMap = new HashMap();
        FileConstant.b(this.webViewECV3Fragment.getActivity(), g);
        this.handler = new Handler(Looper.getMainLooper());
        if (this.b == null) {
            this.b = new SiteAccountInfo();
        }
        this.b.a(Integer.valueOf(1));
        this.b.d(this.cookieLoginInfo.h());
        this.b.c(this.cookieLoginInfo.g());
        this.webViewECV3Fragment.a(this.cookieLoginInfo.a(), this.cookieLoginInfo.j());
        this.d = new LoadJsTask(this.webViewECV3Fragment);
        this.d.execute(new String[] {this.e, this.cookieLoginInfo.b(), this.cookieLoginInfo.c()});
    }

    public final void a(int i, SiteAccountInfo siteAccountInfo) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("code", i);
            if (siteAccountInfo != null) {
                if (!TextUtils.isEmpty(siteAccountInfo.j())) {
                    jSONObject.put("taskId", siteAccountInfo.j());
                }
                if (!TextUtils.isEmpty(siteAccountInfo.e())) {
                    jSONObject.put("mappingId", siteAccountInfo.e());
                }
            }
            this.webViewECV3Fragment.loadUrl("createTaskFinishCallback('" + jSONObject.toString() + "')");
        } catch (Throwable e) {
            ErrorHandle.b("WebViewECV3Presenter", e);
        }
    }

    private void a(String str) {
        this.webViewECV3Fragment.b(str);
    }

    public final void b() {
        this.webViewECV3Fragment.a(new OnWebViewClientListener() {

            @Override
            public final void shouldOverrideUrlLoading(final String str) {
                if (!(WebViewECV3Presenter.this.cookieLoginInfo
                              == null || TextUtils.isEmpty(WebViewECV3Presenter.this.cookieLoginInfo.d()))) {
                    for (int i = 0; i < WebViewECV3Presenter.this.cookieLoginInfo.f(); i++) {
                        WebViewECV3Presenter.this.handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                WebViewECV3Presenter.this.webViewECV3Fragment
                                        .loadUrl(WebViewECV3Presenter.this.cookieLoginInfo
                                                .d());
                            }
                        }, (long) (WebViewECV3Presenter.this.cookieLoginInfo.e() * i));
                    }
                }
                WebViewECV3Presenter.this.handler.post(new Runnable() {

                    public void run() {
                        WebViewECV3Presenter.a(WebViewECV3Presenter.this, str);
                    }
                });
            }

            @Override
            public final void onPageFinished(final String str) {
                Log.e("gq", "onPageFinished");
                WebViewECV3Presenter.this.o = str;
                WebViewECV3Presenter.this.handler.post(new Runnable() {

                    public void run() {
                        Logger.e("JavascriptInterface", "onPageFinished url= " + str);
                        new StringBuilder("onPageFinished url=").append(str);
                        WebViewECV3Presenter.e(WebViewECV3Presenter.this);
                    }
                });
            }

            @Override
            public final boolean shouldOverrideUrlLoading() {
                WebViewECV3Presenter.this.a(WebViewECV3Presenter.this.cookieLoginInfo.k());
                return false;
            }
        });
        a(this.cookieLoginInfo.k());
    }

    public final void a() {
        try {
            this.i = true;
            if (this.d != null && !this.d.b()) {
                this.d.c();
            }
        } catch (Throwable e) {
            ErrorHandle.b("unsubscribe fail", e);
        }
    }

    private String getResponse(JsBaseRequest jsBaseRequest) {
        String rep;
        try {
            new StringBuilder("excuteGet fileName=").append(jsBaseRequest.itemName).append(" start.");
            HashMap<String, String> hashMap = newHashmap(jsBaseRequest.headers);
            hashMap.put("user-agent", cookieLoginInfo.k());
            hashMap.put("cookie", CookieManager.getInstance().getCookie(this.o));
            hashMap.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            HttpUrlConnection.getinstance();
            rep = HttpUrlConnection.getStringByCode(jsBaseRequest.url, hashMap, jsBaseRequest.encoding);
            new StringBuilder("excuteGet fileName=").append(jsBaseRequest.itemName).append(" end.");
        } catch (Throwable e3) {
            return null;
        }
        return rep;
    }

    private byte[] PostResponse(JsBaseRequest jsBaseRequest) {
        byte[] bArr = null;
        try {
            HashMap<String, String> map = newHashmap(jsBaseRequest.headers);
            map.put("user-agent", this.cookieLoginInfo.k());
            map.put("cookie", CookieManager.getInstance().getCookie(this.o));
            HttpUrlConnection.getinstance();
            bArr = HttpUrlConnection.getPostStringByCode(jsBaseRequest.url, jsBaseRequest.data, map, jsBaseRequest
                    .failCode);
        } catch (Throwable e) {
            ErrorHandle.b("excutePost fail", e);
        }
        return bArr;
    }

    private static HashMap<String, String> newHashmap(String str) {
        HashMap<String, String> hashMap = new HashMap<>();
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                for (int i = 0; i < jSONObject.names().length(); i++) {
                    hashMap.put(jSONObject.names().getString(i),
                            jSONObject.get(jSONObject.names().getString(i)).toString());
                }
            } catch (Throwable e) {
                ErrorHandle.b("parseHeader fail", e);
            }
        }
        return hashMap;
    }

    static /* synthetic */ void a(WebViewECV3Presenter webViewECV3Presenter, String str) {
        if (!TextUtils.isEmpty(webViewECV3Presenter.n) && !TextUtils.isEmpty(str) && Pattern
                .compile(webViewECV3Presenter.n).matcher(str).find()) {
            webViewECV3Presenter.b.p(CookieManager.getInstance().getCookie(str));
            webViewECV3Presenter.b.a(webViewECV3Presenter.cookieLoginInfo.k());
            new StringBuilder("init task cookiesUrl=").append(str).append(" cookie=")
                    .append(webViewECV3Presenter.b.q());
            WebViewECV3Fragment.a(webViewECV3Presenter.b);
        }
    }

    static /* synthetic */ void e(WebViewECV3Presenter webViewECV3Presenter) {
        if (TextUtils.isEmpty(webViewECV3Presenter.webViewECV3Fragment.c())) {
            webViewECV3Presenter.webViewECV3Fragment.loadUrl(SharedPreferMgr.a(webViewECV3Presenter.e));
            return;
        }
        webViewECV3Presenter.webViewECV3Fragment.loadUrl(webViewECV3Presenter.webViewECV3Fragment.c());
    }

    public static /* synthetic */ byte[] getbytes(WebViewECV3Presenter webViewECV3Presenter,
                                                  JsBaseRequest jsBaseRequest) {
        if ("get".equalsIgnoreCase(jsBaseRequest.type/*type*/)) {
            String response = webViewECV3Presenter.getResponse(jsBaseRequest);
            if (TextUtils.isEmpty(response)) {
                return null;
            }
            return response.getBytes();
        } else if ("post".equalsIgnoreCase(jsBaseRequest.type)) {
            return webViewECV3Presenter.PostResponse(jsBaseRequest);
        } else {
            return null;
        }
    }

    static /* synthetic */ void a(WebViewECV3Presenter webViewECV3Presenter, byte[] bArr, JsBaseRequest jsBaseRequest) {
        if (bArr != null) {
            try {
                webViewECV3Presenter.hashMap.put(jsBaseRequest.itemName, bArr);
            } catch (Throwable e) {
                ErrorHandle.b("handleRequestResult fail", e);
            }
        }
    }

    static /* synthetic */ void b(WebViewECV3Presenter webViewECV3Presenter, byte[] rep, JsBaseRequest jsBaseRequest) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("itemName", jsBaseRequest.itemName);
            if (rep != null) {
                Logger.e("JavascriptInterface", "itemName:" + jsBaseRequest.itemName + " " + new String(rep));
                if ("post".equalsIgnoreCase(jsBaseRequest.type)) {
                    jSONObject.put("code", l);
                    new StringBuilder("sendRequest response(post):  item=").append(jsBaseRequest.itemName)
                            .append("; data=").append(new String(rep, jsBaseRequest.encoding));
                    jSONObject.put("data", Base64.encode(new String(rep, jsBaseRequest.encoding).getBytes()));
                } else if ("get".equalsIgnoreCase(jsBaseRequest.type)) {
                    jSONObject.put("code", l);
                    new StringBuilder("sendRequest response(get):  item=").append(jsBaseRequest.itemName)
                            .append("; data=").append(new String(rep));
                    jSONObject.put("data", Base64.encode(rep));
                }
                if (!TextUtils.isEmpty(jsBaseRequest.responseId)) {
                    jSONObject.put("responseId", jsBaseRequest.responseId);
                }
            } else {
                new StringBuilder("sendRequest response fail:  item=").append(jsBaseRequest.itemName).append("; url=")
                        .append(jsBaseRequest.url);
                jSONObject.put("code", m);
            }

            webViewECV3Presenter.webViewECV3Fragment.loadUrl("requestFinishCallback('" + jSONObject.toString() + "')");
        } catch (Throwable e) {
            ErrorHandle.b("requstFinishCallback fail", e);
        }
    }

    static /* synthetic */ void f(final WebViewECV3Presenter webViewECV3Presenter) {
        if (!webViewECV3Presenter.j) {
            webViewECV3Presenter.j = true;
            webViewECV3Presenter.handler.post(new Runnable() {
                /* synthetic */ WebViewECV3Presenter a;

                {
                    this.a = webViewECV3Presenter;
                }

                public void run() {
                    EventBus.getDefault().post(new LoginSubmitStart("提交登录...", this.a.b));
                }
            });
        }
    }

    private static /* synthetic */ void handleReq(final WebViewECV3Presenter webViewECV3Presenter, final String str,
                                                  final String str2) {
        new Thread(new Runnable() {

            public void run() {
                JsBaseRequest jsBaseRequest = JsBaseRequest.getJsRequest(str);
                jsBaseRequest.responseId = str2;
                webViewECV3Presenter.webViewECV3Fragment
                        .loadUrl("mxNativeCallback('" + new String(
                                WebViewECV3Presenter.getbytes(webViewECV3Presenter, jsBaseRequest)) + "','"
                                + jsBaseRequest.responseId
                                + "')");
            }
        }).start();
    }

    static /* synthetic */ void d(final WebViewECV3Presenter webViewECV3Presenter, final String str) {
        try {
            new Thread(new Runnable() {

                public void run() {
                    try {
                        JsBaseRequest jsBaseRequest = JsBaseRequest.getJsRequest(str);
                        byte[] response =
                                WebViewECV3Presenter.getbytes(webViewECV3Presenter, jsBaseRequest);//http request
                        WebViewECV3Presenter.a(webViewECV3Presenter,/*call back result*/ response, jsBaseRequest);//put
                        // maps jsreq.itemName
                        WebViewECV3Presenter.b(webViewECV3Presenter, response, jsBaseRequest);
                    } catch (Throwable e) {
                        ErrorHandle.b("sendRequest fail", e);
                    }
                }
            }).start();
        } catch (Throwable e) {
            ErrorHandle.b("sendRequest fail1", e);
        }
    }

    static /* synthetic */ void k(WebViewECV3Presenter webViewECV3Presenter) {
        String str =
                FileConstant.a(webViewECV3Presenter.webViewECV3Fragment.getActivity(), g).getAbsolutePath() + "/" + g
                        + ".zip";//storage/emulated/0/Android/data/com.moxie.crawler/cache/alipay/alipay.zip
        boolean isZipped = false;
        try {
            isZipped = ZipUtil.a(webViewECV3Presenter.hashMap, str);
        } catch (Throwable e) {
            ErrorHandle.b("zip uploadFile fail", e);
        }
        UploadFileEvent uploadFileEvent = new UploadFileEvent();
        if (isZipped) {
            uploadFileEvent.zippedFile = g;
            EventBus.getDefault().post(uploadFileEvent);
            return;
        }
        uploadFileEvent.b = webViewECV3Presenter.hashMap;
        EventBus.getDefault().post(uploadFileEvent);
    }
}
