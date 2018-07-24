package com.moxie.client.js;

import java.util.Iterator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.fragment.FragmentEvent.OpenAgreement;
import com.moxie.client.fragment.FragmentEvent.ShowOrHiddenWebView;
import com.moxie.client.fragment.LogEntry;
import com.moxie.client.tasks.event.CanLeaveEvent;
import com.moxie.client.tasks.event.OpenThirdPartEvent;
import com.moxie.client.tasks.event.SetResultEvent;
import com.proguard.annotation.NotProguard;

import android.util.Log;
import android.webkit.JavascriptInterface;

@NotProguard
/* compiled from: TbsSdkJava */
public class BaseJavaScriptInterfaceImpl implements BaseJavaScriptInterface {
    private static final String TAG = "BaseJavaScriptInterfaceImpl";
    private JSONObject mGlobalMap = new JSONObject();

    @NotProguard
    @JavascriptInterface
    public void mxOpenUrl(String str) {
        EventBus.getDefault().post(new OpenThirdPartEvent(str));
    }

    @NotProguard
    @JavascriptInterface
    public void mxLog(String str) {
    }

    @NotProguard
    @JavascriptInterface
    public void mxHideWebView() {
        EventBus.getDefault().post(new ShowOrHiddenWebView(false));
    }

    @NotProguard
    @JavascriptInterface
    public void mxShowWebView() {
        EventBus.getDefault().post(new ShowOrHiddenWebView(true));
    }

    @NotProguard
    @JavascriptInterface
    public String mxGetUserInfo() {

        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("userId", GlobalParams.i().a().getUserId());
            jSONObject.put("apiKey", GlobalParams.i().a().getApiKey());
            Log.e("JavascriptInterface",
                    new LogEntry("mxGetUserInfo", GlobalParams.i().a().getUserId(), GlobalParams.i().a().getApiKey())
                            .toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    @NotProguard
    @JavascriptInterface
    public String getGlobalMap() {
        Log.e("JavascriptInterface", new LogEntry("getGlobalMap", this.mGlobalMap.toString())
                .toString());
        new StringBuilder("getGlobalMap json : ").append(this.mGlobalMap.toString());
        return this.mGlobalMap.toString();
    }

    @NotProguard
    @JavascriptInterface
    public void setGlobalMap(String str) {
        try {
            Log.e("JavascriptInterface", new LogEntry("setGlobalMap", str)
                    .toString());
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str2 = (String) keys.next();
                this.mGlobalMap.put(str2, jSONObject.get(str2));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new StringBuilder("setGlobalMap json : ").append(this.mGlobalMap.toString());
    }

    @NotProguard
    @JavascriptInterface
    public String mxGetClientVersion() {
        return GlobalParams.i().h();
    }

    @NotProguard
    @JavascriptInterface
    public void mxCanLeave(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("canLeave")) {
                EventBus.getDefault().post(new CanLeaveEvent(jSONObject.getBoolean("canLeave")));
            }
            jSONObject.optString("");
        } catch (JSONException e) {
        }
    }

    @NotProguard
    @JavascriptInterface
    public void mxOpenWebView(String str) {
        Log.e("JavascriptInterface", new LogEntry("mxOpenWebView", str).toString());
        try {
            EventBus.getDefault().post(new OpenAgreement(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotProguard
    @JavascriptInterface
    public void setCallbackMap(String str) {
        try {
            Log.e("JavascriptInterface", new LogEntry("setCallbackMap", str)
                    .toString());
            EventBus.getDefault().post(new SetResultEvent(str));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
