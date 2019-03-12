package com.moxie.client.fragment;

import java.util.List;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.utils.CommonMethod;
import com.moxie.client.utils.ErrorHandle;
import com.moxie.client.utils.SharedPreferMgr;
import com.moxie.client.widget.CustomWebView;
import com.moxie.client.widget.TitleLayout;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage;
import com.tencent.smtt.export.external.interfaces.ConsoleMessage.MessageLevel;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

/* compiled from: TbsSdkJava */
public abstract class BaseWebViewFragment extends Fragment {
    protected LayoutInflater a;
    protected View b;
    protected CustomWebView customWebView;
    protected ViewGroup d;
    protected ImageView e;
    protected TitleLayout f;
    ProgressBar g;
    protected String h = "";
    private OnWebViewClientListener i;

    /* compiled from: TbsSdkJava */
    public static class OnWebViewClientListener {

        public void shouldOverrideUrlLoading(String str) {
        }

        public void onPageFinished(String str) {
        }

        public boolean shouldOverrideUrlLoading() {
            return false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.a = layoutInflater;
        this.b = layoutInflater.inflate(
                getResources().getIdentifier("moxie_client_fragment_webview", "layout", getActivity().getPackageName()),
                viewGroup, false);
        this.customWebView = (CustomWebView) this.b
                .findViewById(getResources().getIdentifier("CustomWebView_Main", "id", getActivity().getPackageName()));
        this.d = (ViewGroup) this.b
                .findViewById(getResources().getIdentifier("ProgressBar_Main", "id", getActivity().getPackageName()));
        this.e = (ImageView) this.b
                .findViewById(getResources().getIdentifier("ImageView_Bg", "id", getActivity().getPackageName()));
        this.g = (ProgressBar) this.b.findViewById(
                getResources().getIdentifier("webview_top_progressbar", "id", getActivity().getPackageName()));
        this.g.setProgress(0);
        this.d.setOnClickListener(new OnClickListener() {
            final /* synthetic */ BaseWebViewFragment a;

            {
                this.a = BaseWebViewFragment.this;
            }

            public void onClick(View view) {
            }
        });
        if (VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this.customWebView, true);
            this.customWebView.getSettings().setMixedContentMode(0);
        }
        this.customWebView.getSettings().setCacheMode(2);
        this.customWebView.clearCache(true);
        this.customWebView.getSettings().setJavaScriptEnabled(true);
        this.customWebView.getSettings().setAppCacheEnabled(true);
        this.customWebView.getSettings().setDomStorageEnabled(true);
        this.customWebView.getSettings().setSavePassword(false);
        if (a()) {
            this.customWebView.getSettings().setLoadWithOverviewMode(true);
            this.customWebView.getSettings().setUseWideViewPort(true);
            this.customWebView.getSettings().setSupportZoom(true);
            this.customWebView.getSettings().setBuiltInZoomControls(true);
        }
        this.g.setProgressDrawable(
                new ClipDrawable(new ColorDrawable(Color.parseColor(GlobalParams.i().a().getThemeColor())), 3, 1));
        getActivity();
        CommonMethod.a(this.customWebView);
        if (VERSION.SDK_INT >= 19) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        this.customWebView.setDownloadListener(new DownloadListener() {
            final /* synthetic */ BaseWebViewFragment a;

            {
                this.a = BaseWebViewFragment.this;
            }

            public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
                CommonMethod.a(this.a.getActivity(), str);
            }
        });
        this.customWebView.setWebChromeClient(new WebChromeClient() {
            final /* synthetic */ BaseWebViewFragment a;

            {
                this.a = BaseWebViewFragment.this;
            }

            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (this.a.g != null) {
                    this.a.g.setVisibility(View.VISIBLE);
                    this.a.g.setProgress(i);
                }
                if (i == 100 && this.a.g != null) {
                    this.a.g.setVisibility(View.GONE);
                }
            }

            public void onGeolocationPermissionsShowPrompt(String str,
                                                           GeolocationPermissionsCallback
                                                                   geolocationPermissionsCallback) {
                geolocationPermissionsCallback.invoke(str, true, false);
                super.onGeolocationPermissionsShowPrompt(str, geolocationPermissionsCallback);
            }

            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                if (consoleMessage.messageLevel() == MessageLevel.DEBUG) {
                    String.format("%s -- From line %s of %s",
                            new Object[] {consoleMessage.message(), Integer.valueOf(consoleMessage.lineNumber()),
                                    consoleMessage.sourceId()});
                } else if (consoleMessage.messageLevel() == MessageLevel.LOG
                        || consoleMessage.messageLevel() == MessageLevel.TIP) {
                    String.format("%s -- From line %s of %s",
                            new Object[] {consoleMessage.message(), Integer.valueOf(consoleMessage.lineNumber()),
                                    consoleMessage.sourceId()});
                } else if (consoleMessage.messageLevel() == MessageLevel.WARNING) {
                    String.format("%s -- From line %s of %s",
                            new Object[] {consoleMessage.message(), Integer.valueOf(consoleMessage.lineNumber()),
                                    consoleMessage.sourceId()});
                } else if (consoleMessage.messageLevel() == MessageLevel.ERROR) {
                    String.format("%s -- From line %s of %s",
                            new Object[] {consoleMessage.message(), Integer.valueOf(consoleMessage.lineNumber()),
                                    consoleMessage.sourceId()});
                    "Uncaught ReferenceError: PGCallback is not defined".equals(consoleMessage.message());
                }
                return true;
            }

            @Override
            public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
                Log.e("gq", "onJsAlert " + str + str + jsResult);
                try {
                    Builder builder = new Builder(this.a.getActivity());
                    builder.setMessage(str2);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        //                        final /* synthetic */ AnonymousClass3 b;

                        public void onClick(DialogInterface dialogInterface, int i) {
                            jsResult.confirm();
                        }
                    });
                    builder.setCancelable(false).create().show();
                } catch (Exception e) {
                    if (jsResult != null) {
                        try {
                            jsResult.confirm();
                        } catch (Throwable e2) {
                            ErrorHandle.b("WebView onJsAlert confirm fail", e2);
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String s, String s1, JsResult jsResult) {
                Log.e("gq", "onJsAlert " + s + s1 + jsResult);
                return super.onJsConfirm(webView, s, s1, jsResult);
            }

            @Override
            public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
                Log.e("gq", "onJsAlert " + s1 + s2 + jsPromptResult);
                return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
            }

            @Override
            public void onReceivedTitle(WebView webView, String str) {
                super.onReceivedTitle(webView, str);
                if (!TextUtils.isEmpty(str)) {
                    BaseWebViewFragment.a(this.a, str);
                }
            }
        });
        this.customWebView.setWebViewClient(new WebViewClient() {
            final /* synthetic */ BaseWebViewFragment a;

            {
                this.a = BaseWebViewFragment.this;
            }

            @Override
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
                if (this.a.i != null) {
                    this.a.i.shouldOverrideUrlLoading(str);
                }
                this.a.d.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView webView, String str) {
                Log.e("JavascriptInterface", "onPageFinished url " + str);
                super.onPageFinished(webView, str);
                if (this.a.i != null) {
                    this.a.i.onPageFinished(str);
                }
                this.a.d.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedSslError(WebView webView, final SslErrorHandler sslErrorHandler, SslError sslError) {
                new Builder(this.a.getActivity()).setMessage("您访问的链接存在安全风险，仍要继续吗？")
                        .setPositiveButton("继续访问", new DialogInterface.OnClickListener() {
                            //                    final /* synthetic */ AnonymousClass4 b;

                            public void onClick(DialogInterface dialogInterface, int i) {
                                sslErrorHandler.proceed();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    //                    final /* synthetic */ AnonymousClass4 b;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        sslErrorHandler.cancel();
                    }
                }).show();
            }

            @Override
            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                new StringBuilder("onReceivedError errorCode=").append(i).append(",description=").append(str)
                        .append(",failingUrl=").append(str2);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (Uri.parse(str).getScheme().startsWith("file") || Uri.parse(str).getScheme().startsWith("http")
                        || "about:blank".equals(str)) {
                    return false;
                }
                CommonMethod.a(this.a.getActivity(), str);//调用浏览器
                if (this.a.i != null) {
                    this.a.i.shouldOverrideUrlLoading();
                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
                return null;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                return null;
            }
        });
        return this.b;
    }

    protected boolean a() {
        return false;
    }

    public final void a(List<String> list, String str) {
        if (list != null) {
            try {
                if (list.size() > 0) {
                    for (String b : list) {
                        syncCookies(b);
                    }
                    return;
                }
            } catch (Throwable e) {
                ErrorHandle.b("WebView clearCookie fail", e);
                return;
            }
        }
        if (!TextUtils.isEmpty(str)) {
            syncCookies(str);
        }
    }

    /**
     * cookies
     *
     * @param str
     */
    private void syncCookies(String str) {
        try {
            CookieSyncManager.createInstance(getActivity());
            CookieManager instance = CookieManager.getInstance();
            String cookie = instance.getCookie(str);
            if (cookie != null) {
                for (String split : cookie.split(";")) {
                    instance.setCookie(str, split.split("=")[0].trim() + "=; Expires=Wed, 31 Dec 1900 23:59:59 GMT");
                }
                instance.removeExpiredCookie();
                CookieSyncManager.getInstance().sync();
            }
        } catch (Throwable e) {
            ErrorHandle.b("WebView clearCookieInternal fail", e);
        }
    }

    public final void a(OnWebViewClientListener onWebViewClientListener) {
        this.i = onWebViewClientListener;
    }

    public final Boolean syncCookies() {
        Boolean valueOf = Boolean.valueOf(false);
        try {
            if (this.customWebView.canGoBack()) {
                this.customWebView.goBack();
                valueOf = Boolean.valueOf(true);
            }
        } catch (Throwable e) {
            ErrorHandle.b("WebView historyBack fail", e);
        }
        return valueOf;
    }

    public final void a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2)) {
            SharedPreferMgr.a(str, str2);
        }
        this.h = str2;
        this.customWebView.loadUrl(str3);
    }

    public final void loadUrl(final String str) {
        if (customWebView != null && !TextUtils.isEmpty(str)) {
            customWebView.post(new Runnable() {
                public void run() {
                    if (customWebView != null) {
                        customWebView.loadUrl("javascript:" + str);
                    }
                }
            });
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
    public void onDestroy() {
        try {
            if (this.customWebView != null) {
                ViewGroup viewGroup = (ViewGroup) this.customWebView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(this.customWebView);
                }
                this.customWebView.removeAllViews();
                this.customWebView.destroy();
                this.customWebView = null;
            }
            this.d.removeAllViews();
            this.d = null;
            this.g = null;
        } catch (Throwable e) {
            ErrorHandle.b("WebView onDestroy fail", e);
        }
        super.onDestroy();
    }

    public final String c() {
        return this.h;
    }

    static /* synthetic */ void a(BaseWebViewFragment baseWebViewFragment, String str) {
        if (baseWebViewFragment.f != null) {
            baseWebViewFragment.f.setTitle(str);
        }
    }
}
