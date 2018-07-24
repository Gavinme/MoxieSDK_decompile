package com.moxie.client.crash;

import android.text.TextUtils;
import com.moxie.client.utils.ErrorHandle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class JSONReportBuilder {
    public static JSONObject a(CrashReportData crashReportData) {
        if (crashReportData == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (crashReportData.a != null) {
                jSONObject2.put("mxLongitude", crashReportData.a.a);
                jSONObject2.put("mxLatitude", crashReportData.a.b);
                jSONObject2.put("mxAppVersion", crashReportData.a.c);
                jSONObject2.put("mxBundleIdentifier", crashReportData.a.d);
                jSONObject2.put("mxModel", crashReportData.a.e);
                jSONObject2.put("mxUUIDStr", crashReportData.a.f);
                jSONObject2.put("mxSystem_User_agent", crashReportData.a.g);
                jSONObject2.put("mxAppBuild", crashReportData.a.h);
                jSONObject2.put("NetworkStatus", crashReportData.a.i);
                jSONObject2.put("mxCurProvince", crashReportData.a.j);
                jSONObject2.put("mxSDKVersion", crashReportData.a.k);
                jSONObject2.put("mxIPStr", crashReportData.a.l);
                jSONObject2.put("mxOSType", crashReportData.a.m);
                jSONObject2.put("mxOSVersion", crashReportData.a.p);
                jSONObject2.put("mxAppName", crashReportData.a.o);
                jSONObject2.put("mxCurCity", crashReportData.a.q);
                jSONObject2.put("mxDeviceString", crashReportData.a.r);
                jSONObject2.put("mxPhoneName", crashReportData.a.n);
                jSONObject2.put("mxPhoneNo", crashReportData.a.t);
            }
            JSONObject jSONObject3 = new JSONObject();
            if (crashReportData.d != null) {
                for (String str : crashReportData.d.keySet()) {
                    jSONObject3.put(str, (String) crashReportData.d.get(str));
                }
            }
            jSONObject.put("device_info", jSONObject2);
            jSONObject.put("exception_info", jSONObject3);
            jSONObject.put("time", crashReportData.b);
            jSONObject.put("type", crashReportData.c);
        } catch (Throwable e) {
            ErrorHandle.a("JSONReportBuilder", e);
        }
        return jSONObject;
    }

    private static CrashReportData a(JSONObject jSONObject) {
        CrashReportData crashReportData = new CrashReportData();
        String str;
        try {
            if (jSONObject.has("device_info")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("device_info");
                DeviceInfo deviceInfo = new DeviceInfo();
                deviceInfo.b = jSONObject2.optString("mxLatitude");
                deviceInfo.a = jSONObject2.optString("mxLongitude");
                deviceInfo.r = jSONObject2.optString("mxDeviceString");
                deviceInfo.q = jSONObject2.optString("mxCurCity");
                deviceInfo.p = jSONObject2.optString("mxOSVersion");
                deviceInfo.o = jSONObject2.optString("mxAppName");
                deviceInfo.n = jSONObject2.optString("mxPhoneName");
                deviceInfo.m = jSONObject2.optString("mxOSType");
                deviceInfo.l = jSONObject2.optString("mxIPStr");
                deviceInfo.k = jSONObject2.optString("mxSDKVersion");
                deviceInfo.s = jSONObject2.optString("mxCurProvince");
                deviceInfo.i = jSONObject2.optString("NetworkStatus");
                deviceInfo.h = jSONObject2.optString("mxAppBuild");
                deviceInfo.g = jSONObject2.optString("mxSystem_User_agent");
                deviceInfo.h = jSONObject2.optString("mxAppBuild");
                deviceInfo.f = jSONObject2.optString("mxUUIDStr");
                deviceInfo.e = jSONObject2.optString("mxModel");
                deviceInfo.d = jSONObject2.optString("mxBundleIdentifier");
                deviceInfo.c = jSONObject2.optString("mxAppVersion");
                deviceInfo.t = jSONObject2.optString("mxPhoneNo");
                crashReportData.a = deviceInfo;
            }
            if (jSONObject.has("exception_info")) {
                JSONObject jSONObject3 = jSONObject.getJSONObject("exception_info");
                Iterator keys = jSONObject3.keys();
                Map hashMap = new HashMap();
                while (keys.hasNext()) {
                    str = (String) keys.next();
                    hashMap.put(str, jSONObject3.optString(str));
                }
                crashReportData.d = hashMap;
            }
            crashReportData.b = jSONObject.optString("time");
            crashReportData.c = jSONObject.optString("type");
        } catch (Exception e) {
            str = ErrorReporter.a;
        }
        return crashReportData;
    }

    public static CrashReportData a(String str) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return a(new JSONObject(str));
    }
}
