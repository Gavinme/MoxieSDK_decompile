package com.moxie.client.manager;

import android.text.TextUtils;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.utils.ErrorHandle;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class LogTracker {
    private final String a = "LogTracker";

    public static LogTracker a() {
        return new LogTracker();
    }

    private void a(JSONArray jSONArray) {
        try {
            final JSONObject jSONObject = new JSONObject();
            jSONObject.put("entries", jSONArray);
            final HashMap hashMap = new HashMap();
            hashMap.put("Authorization", "service 26854f1fab6142a5b93f3436a5ce6e2a");
            hashMap.put("Content-Encoding", "gzip");
            new StringBuilder("UploadLogTracking=").append(jSONObject.toString());
            new Thread(new Runnable() {

                public void run() {
                    HttpUrlConnection.getinstance();
                    HttpUrlConnection.postString("http://log.51datakey.com:18825/metrics-gateway/api/v1/tracking", jSONObject.toString(), hashMap);
                }
            }).start();
        } catch (Throwable e) {
            ErrorHandle.a("LogTracker", e);
        }
    }

    public final void a(JSONObject jSONObject, String str) {
        if (jSONObject != null && !TextUtils.isEmpty(str)) {
            try {
                JSONArray jSONArray = new JSONArray();
                b(jSONObject, str);
                jSONArray.put(jSONObject);
                a(jSONArray);
            } catch (Throwable e) {
                ErrorHandle.a("LogTracker", e);
            }
        }
    }

    public final void a(List<JSONObject> list, String str) {
        if (list != null) {
            try {
                if (list.size() != 0 && !TextUtils.isEmpty(str)) {
                    JSONArray jSONArray = new JSONArray();
                    for (JSONObject jSONObject : list) {
                        b(jSONObject, str);
                        jSONArray.put(jSONObject);
                    }
                    a(jSONArray);
                }
            } catch (Throwable e) {
                ErrorHandle.a("LogTracker", e);
            }
        }
    }

    private static void b(JSONObject jSONObject, String str) throws JSONException {
        if (jSONObject != null && !TextUtils.isEmpty(str)) {
            jSONObject.put("sysName", "sdk/Android-" + GlobalParams.i().h());
            jSONObject.put("phase", "CRAWL");
            jSONObject.put("taskId", str);
            jSONObject.put("timestamp", new Date().getTime());
        }
    }
}
