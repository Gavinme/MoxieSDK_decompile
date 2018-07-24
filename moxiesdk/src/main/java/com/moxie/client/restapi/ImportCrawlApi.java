package com.moxie.client.restapi;

import android.text.TextUtils;
import com.moxie.client.commom.GlobalConstants;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.encrypt.Encrypt;
import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.model.TaskCreateResponse;
import com.moxie.client.model.TaskStatusResult;
import com.moxie.client.utils.AESCBC;
import com.moxie.client.utils.AESECB;
import com.moxie.client.utils.Base64;
import com.moxie.client.utils.RsaUtil;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class ImportCrawlApi {
    public static TaskCreateResponse a(SiteAccountInfo siteAccountInfo) {
        TaskCreateResponse taskCreateResponse = new TaskCreateResponse();
        try {
            String str;
            String b = b(siteAccountInfo);
            if (siteAccountInfo.i().toLowerCase().equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
                str = "https://api.51datakey.com" + "/{task_type}/v1/tasks".replace("{task_type}", "gateway");
            } else if (siteAccountInfo.i().toLowerCase().equalsIgnoreCase("social")) {
                str = "https://api.51datakey.com" + "/{task_type}/v1/tasks".replace("{task_type}", "gateway");
            } else {
                str = "https://api.51datakey.com" + "/{task_type}/v1/tasks".replace("{task_type}", siteAccountInfo.i().toLowerCase());
            }
            HashMap extendParams = GlobalParams.i().a().getExtendParams();
            if (!(extendParams == null || !extendParams.containsKey(MxParam.PARAM_CREATE_TASK_URL) || TextUtils.isEmpty((CharSequence) extendParams.get(MxParam.PARAM_CREATE_TASK_URL)))) {
                str = (String) extendParams.get(MxParam.PARAM_CREATE_TASK_URL);
            }
            String encyptBody = Encrypt.getInstance().encyptBody(b);
            HttpUrlConnection.getinstance();
            if (TextUtils.isEmpty(encyptBody)) {
                encyptBody = b;
            }
            return b(HttpUrlConnection.postString(str, encyptBody, a(true)));
        } catch (Exception e) {
            return null;
        }
    }

    public static TaskStatusResult a(String str, String str2) {
        try {
            String str3;
            String str4;
            if (str2.toLowerCase().equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC) || str2.toLowerCase().equalsIgnoreCase("social")) {
                str3 = "https://api.51datakey.com" + "/{task_type}/v1/tasks/{task_id}/status".replace("{task_id}", str).replace("{task_type}", "gateway");
            } else {
                str3 = "https://api.51datakey.com" + "/{task_type}/v1/tasks/{task_id}/status".replace("{task_id}", str).replace("{task_type}", str2.toLowerCase());
            }
            HashMap extendParams = GlobalParams.i().a().getExtendParams();
            if (extendParams == null || !extendParams.containsKey(MxParam.PARAM_TASK_STATUS_URL) || TextUtils.isEmpty((CharSequence) extendParams.get(MxParam.PARAM_TASK_STATUS_URL))) {
                str4 = str3;
            } else {
                str4 = ((String) extendParams.get(MxParam.PARAM_TASK_STATUS_URL)).replace("{task_id}", str);
            }
            HttpUrlConnection.getinstance();
            return a(HttpUrlConnection.getString(str4, a(false)));
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean a(SiteAccountInfo siteAccountInfo, String str, String str2) {
        Boolean valueOf = Boolean.valueOf(true);
        try {
            String str3;
            JSONObject jSONObject = new JSONObject();
            if (!siteAccountInfo.o().equals("")) {
                jSONObject.put("input", siteAccountInfo.o());
            } else if (str.equals("")) {
                jSONObject.put("input", str2);
            } else {
                jSONObject.put("input", str);
            }
            String jSONObject2 = jSONObject.toString();
            String str4 = "https://api.51datakey.com" + "/{task_type}/v1/tasks/{task_id}/input".replace("{task_id}", siteAccountInfo.j()).replace("{task_type}", siteAccountInfo.i().toLowerCase());
            HashMap extendParams = GlobalParams.i().a().getExtendParams();
            if (extendParams == null || !extendParams.containsKey(MxParam.PARAM_TASK_STATUS_URL) || TextUtils.isEmpty((CharSequence) extendParams.get(MxParam.PARAM_TASK_STATUS_URL))) {
                str3 = str4;
            } else {
                str3 = ((String) extendParams.get(MxParam.PARAM_TASK_STATUS_URL)).replace("{task_id}", siteAccountInfo.j());
            }
            HttpUrlConnection.getinstance();
            if (HttpUrlConnection.postString(str3, jSONObject2, a(false)) == null) {
                return Boolean.valueOf(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueOf;
    }

    private static String b(SiteAccountInfo siteAccountInfo) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("user_id", siteAccountInfo.k());
        jSONObject.put("type", siteAccountInfo.i());
        jSONObject.put("subtype", siteAccountInfo.f());
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("username", siteAccountInfo.m());
        jSONObject2.put("cookie", "");
        jSONObject2.put("idp_pass", "");
        if (!TextUtils.isEmpty(siteAccountInfo.d())) {
            jSONObject2.put("useragent", siteAccountInfo.d());
        }
        jSONObject2.put("password", siteAccountInfo.n());
        if (siteAccountInfo.l().intValue() == 1) {
            jSONObject2.put("cookie", siteAccountInfo.q());
        }
        if (siteAccountInfo.i().toUpperCase().equalsIgnoreCase("BANK")) {
            jSONObject2.put("login_target", siteAccountInfo.b());
            jSONObject2.put(MxParam.PARAM_CUSTOM_LOGIN_TYPE, siteAccountInfo.c());
            jSONObject2.put("html_source", siteAccountInfo.a());
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("arguments", jSONObject2);
        jSONObject3.put("origin", "0");
        jSONObject3.put("phase", "CRAWL");
        jSONObject.put("param", jSONObject3);
        jSONObject2 = new JSONObject();
        jSONObject2.put("ip", "");
        jSONObject2.put("city", "");
        jSONObject2.put("province", "");
        jSONObject2.put("lat", GlobalParams.i().c());
        jSONObject2.put("lon", GlobalParams.i().b());
        HashMap userBaseInfo = GlobalParams.i().a().getUserBaseInfo();
        if (userBaseInfo == null || !userBaseInfo.containsKey(MxParam.PARAM_USER_BASEINFO_IDCARD)) {
            try {
                jSONObject2.put(MxParam.PARAM_IDCARD, siteAccountInfo.g());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            jSONObject2.put(MxParam.PARAM_IDCARD, userBaseInfo.get(MxParam.PARAM_USER_BASEINFO_IDCARD));
        }
        if (userBaseInfo == null || !userBaseInfo.containsKey(MxParam.PARAM_USER_BASEINFO_REALNAME)) {
            jSONObject2.put(MxParam.PARAM_NAME, siteAccountInfo.h());
        } else {
            jSONObject2.put(MxParam.PARAM_NAME, userBaseInfo.get(MxParam.PARAM_USER_BASEINFO_REALNAME));
        }
        if (userBaseInfo == null || !userBaseInfo.containsKey(MxParam.PARAM_USER_BASEINFO_MOBILE)) {
            jSONObject2.put(MxParam.PARAM_PHONE, GlobalParams.i().k());
        } else {
            jSONObject2.put(MxParam.PARAM_PHONE, userBaseInfo.get(MxParam.PARAM_USER_BASEINFO_MOBILE));
        }
        jSONObject.put("context", jSONObject2);
        return jSONObject.toString();
    }

    private static TaskStatusResult a(String str) {
        if (str == null) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            TaskStatusResult taskStatusResult = new TaskStatusResult();
            if (jSONObject.has("code")) {
                taskStatusResult.a(jSONObject.getInt("code"));
            } else {
                taskStatusResult.a(200);
            }
            if (jSONObject.has("description")) {
                taskStatusResult.f(jSONObject.getString("description"));
            }
            if (jSONObject.has("phase")) {
                taskStatusResult.b(jSONObject.getString("phase"));
            }
            if (jSONObject.has("phase_status")) {
                taskStatusResult.c(jSONObject.getString("phase_status"));
            }
            if (jSONObject.has("progress")) {
                taskStatusResult.a(Integer.valueOf(jSONObject.getInt("progress")));
            }
            if (jSONObject.has("finished")) {
                taskStatusResult.b(Integer.valueOf(jSONObject.getBoolean("finished") ? 1 : 0));
            }
            if (jSONObject.has("task_id")) {
                taskStatusResult.d(jSONObject.getString("task_id"));
            }
            if (jSONObject.has("wait_seconds")) {
                taskStatusResult.a(Long.valueOf(jSONObject.getLong("wait_seconds")));
            }
            if (jSONObject.has("input")) {
                try {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("input");
                    taskStatusResult.i(jSONObject2.getString("param_name"));
                    taskStatusResult.g(jSONObject2.getString("type"));
                    taskStatusResult.h(jSONObject2.getString("value"));
                    taskStatusResult.b(Long.valueOf(jSONObject2.getLong("wait_seconds")));
                    if (jSONObject2.getString("type") == "sms" && jSONObject2.has("b64val")) {
                        taskStatusResult.h(jSONObject2.getString("b64val"));
                    }
                } catch (Exception e) {
                }
            }
            return taskStatusResult;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static HashMap<String, String> a(boolean z) {
        HashMap<String, String> hashMap = new HashMap();
        if (z) {
            hashMap.put("Authorization", a());
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("user_agent", "");
                jSONObject.put("phone_no", GlobalParams.i().k());
                jSONObject.put("os_type", GlobalParams.i().m());
                jSONObject.put("os_version", GlobalParams.i().n());
                jSONObject.put("model", GlobalParams.i().l());
                jSONObject.put("sdk_version", GlobalParams.i().h());
                jSONObject.put("device_id", GlobalParams.i().j());
                jSONObject.put("network_type", GlobalParams.i().o());
                jSONObject.put("imei", GlobalParams.i().p());
                jSONObject.put("imsi", GlobalParams.i().q());
                jSONObject.put("ap_mac", GlobalParams.i().g());
                jSONObject.put("mac", GlobalParams.i().d());
                jSONObject.put("lac", GlobalParams.i().e());
                jSONObject.put("cid", GlobalParams.i().f());
                String a = AESECB.a();
                AESCBC aescbc = new AESCBC(Base64.encode(a.getBytes()));
                String a2 = aescbc.a(jSONObject.toString());
                hashMap.put("X-Moxie-Sep", a2 + "." + RsaUtil.a(Base64.encode(a.getBytes()), GlobalConstants.b, "UTF-8"));
                aescbc.b(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hashMap.put("Authorization", a());
        }
        return hashMap;
    }

    private static String a() {
        return "apikey " + GlobalParams.i().a().getApiKey();
    }

    private static TaskCreateResponse b(String str) {
        TaskCreateResponse taskCreateResponse = new TaskCreateResponse();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("email_id")) {
                taskCreateResponse.b(jSONObject.getString("email_id"));
            }
            if (jSONObject.has("task_id")) {
                taskCreateResponse.c(jSONObject.getString("task_id"));
            }
            if (jSONObject.has("mapping_id")) {
                taskCreateResponse.a(jSONObject.getString("mapping_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return taskCreateResponse;
    }
}
