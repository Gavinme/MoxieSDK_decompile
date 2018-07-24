package com.moxie.crawler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.moxie.client.utils.LogUtils;
import com.moxie.crawler.common.TitleLayout;

/**
 * Created by taoweisong on 16/4/1.
 */
public class WebViewActivity extends Activity  implements
        View.OnClickListener {

    private WebView webView;
    private TitleLayout mTitleLayout;
    private String mTitle = "导入结果";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //获取用户传过来的参数
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            init(bundle.getString("openUrl"));
            if(!TextUtils.isEmpty(bundle.getString("title")))
                mTitle = bundle.getString("title");
        } else {
            finish();
        }
        mTitleLayout = (TitleLayout) findViewById(R.id.TitleLayout);
        mTitleLayout.setTitle(mTitle);
        mTitleLayout.getLeftImage().setOnClickListener(this);
        mTitleLayout.getRightImage().setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        try {
            if (id == R.id.TextView_Back) {
                if(webView.canGoBack()){
                    webView.goBack();
                } else {
                    finish();
                }
            } else if(id == R.id.TextView_Refresh) {
                webView.reload();
            }
        } catch (Exception e) {

        }
    }

    private void init(String openUrl){

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);

        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new WebMailJavaScriptInterface(), "android");

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                    builder.setMessage(message);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm();
                        }
                    });
                    builder.setCancelable(false).create().show();
                } catch (Exception e){
                    e.printStackTrace();
                    try{
                        if(result != null)
                            result.confirm();
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                return true;
            }
        });

        //WebView加载web资源
        webView.loadUrl(openUrl);
    }

    /**
     * js交互
     */
    class WebMailJavaScriptInterface {
        @JavascriptInterface
        public void mxBack(){
            finish();
        }
    }

    @Override
    protected void onResume() {
        Log.e("WebViewActivity", "onResume");
        // 设置为竖屏
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
        if(this.webView != null) {
            this.webView.resumeTimers();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if(parent!=null){
                parent.removeView(webView);
            }
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
