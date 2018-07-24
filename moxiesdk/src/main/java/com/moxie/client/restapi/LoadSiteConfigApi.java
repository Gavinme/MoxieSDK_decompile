package com.moxie.client.restapi;

import android.text.TextUtils;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.model.MxParam;
import com.moxie.client.tasks.model.SiteConfigInfo;
import com.moxie.client.tasks.model.SiteConfigItem;
import com.moxie.client.tasks.model.SiteConfigsResponse;
import com.moxie.client.tasks.model.SitePerferenceMgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class LoadSiteConfigApi {
    public static void a(String str) {
        String str2;
        String str3 = "https://api.51datakey.com/conf/api/v2/sites?c=" + str;
        HashMap extendParams = GlobalParams.i().a().getExtendParams();
        if (extendParams == null || !extendParams.containsKey(MxParam.PARAM_CONFIG_URL) || TextUtils.isEmpty((CharSequence) extendParams.get(MxParam.PARAM_CONFIG_URL))) {
            str2 = str3;
        } else {
            str2 = (String) extendParams.get(MxParam.PARAM_CONFIG_URL);
        }
//        HttpUrlConnection.getinstance();
        HashMap hashMap = new HashMap();
        hashMap.put("Authorization", "apikey " + GlobalParams.i().a().getApiKey());
        a(HttpUrlConnection.getString(str2, hashMap), str);
    }

    public static SiteConfigsResponse a(String str, String str2) {
        if (str == null) {
            return null;
        }
        try {
            SiteConfigsResponse siteConfigsResponse = new SiteConfigsResponse();
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                SiteConfigItem siteConfigItem = new SiteConfigItem();
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.has("mailId")) {
                    siteConfigItem.a = String.valueOf(jSONObject.getInt("mailId"));
                }
                if (jSONObject.has("host")) {
                    siteConfigItem.b = jSONObject.getString("host");
                }
                if (jSONObject.has("accessMethod")) {
                    siteConfigItem.b(String.valueOf(jSONObject.getInt("accessMethod")));
                }
                if (jSONObject.has("category")) {
                    siteConfigItem.a(jSONObject.getString("category"));
                }
                if (jSONObject.has("status")) {
                    siteConfigItem.a(Integer.valueOf(jSONObject.getInt("status")));
                }
                SiteConfigInfo siteConfigInfo = new SiteConfigInfo();
                JSONObject jSONObject2 = jSONObject.getJSONObject("items");
                if (jSONObject2 != null) {
                    if (jSONObject2.has("isCanUseWebLogin")) {
                        siteConfigInfo.m(jSONObject2.getString("isCanUseWebLogin"));
                    }
                    if (jSONObject2.has("jsurl")) {
                        siteConfigInfo.n(jSONObject2.getString("jsurl"));
                    }
                    if (jSONObject2.has("jsurlv2")) {
                        siteConfigInfo.g(jSONObject2.getString("jsurlv2"));
                    }
                    if (jSONObject2.has("loginUrl")) {
                        siteConfigInfo.o(jSONObject2.getString("loginUrl"));
                    }
                    if (jSONObject2.has("userAgent")) {
                        siteConfigInfo.p(jSONObject2.getString("userAgent"));
                    }
                    if (jSONObject2.has("logo")) {
                        siteConfigInfo.l(jSONObject2.getString("logo"));
                    }
                    if (jSONObject2.has("successUrl")) {
                        siteConfigInfo.k(jSONObject2.getString("successUrl"));
                    }
                    if (jSONObject2.has("loginType")) {
                        siteConfigInfo.j(jSONObject2.getString("loginType"));
                    }
                    if (jSONObject2.has("subType")) {
                        siteConfigInfo.i(jSONObject2.getString("subType"));
                    }
                    if (jSONObject2.has("insuranceName")) {
                        siteConfigInfo.h(jSONObject2.getString("insuranceName"));
                    }
                    if (jSONObject2.has("fields")) {
                        siteConfigInfo.f(jSONObject2.getString("fields"));
                    }
                    if (jSONObject2.has("bankId")) {
                        siteConfigInfo.e(jSONObject2.getString("bankId"));
                    }
                    if (jSONObject2.has("bankName")) {
                        siteConfigInfo.d(jSONObject2.getString("bankName"));
                    }
                    if (jSONObject2.has("preLoadJS")) {
                        siteConfigInfo.c(jSONObject2.getString("preLoadJS"));
                    }
                    if (jSONObject2.has("preLoadDelay")) {
                        siteConfigInfo.a(jSONObject2.getInt("preLoadDelay"));
                    }
                    if (jSONObject2.has("preLoadTimes")) {
                        siteConfigInfo.b(jSONObject2.getInt("preLoadTimes"));
                    }
                    if (jSONObject2.has("loginUrlV3")) {
                        siteConfigInfo.b(jSONObject2.getString("loginUrlV3"));
                    }
                    if (jSONObject2.has("jsurlv3")) {
                        siteConfigInfo.a(jSONObject2.getString("jsurlv3"));
                    }
                    if (jSONObject2.has("domain")) {
                        JSONArray jSONArray2 = new JSONArray(jSONObject2.getString("domain"));
                        List arrayList2 = new ArrayList();
                        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                            arrayList2.add(jSONArray2.getString(i2));
                        }
                        siteConfigInfo.a(arrayList2);
                    }
                }
                siteConfigItem.c = siteConfigInfo;
                arrayList.add(siteConfigItem);
                SitePerferenceMgr.a(jSONObject.getString("host"), jSONObject.toString());
            }
            siteConfigsResponse.a = arrayList;
            if (!TextUtils.isEmpty(str)) {
                if (str2.equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
                    SitePerferenceMgr.a("moxie_sdk_all_ec_config", str);
                } else if (str2.equalsIgnoreCase("mailbox")) {
                    SitePerferenceMgr.a("moxie_sdk_all_mail_config", str);
                } else if (str2.equalsIgnoreCase(MxParam.PARAM_FUNCTION_INSURANCE)) {
                    SitePerferenceMgr.a("moxie_sdk_all_insurance_config", str);
                } else if (str2.equalsIgnoreCase("qzone")) {
                    SitePerferenceMgr.a("moxie_sdk_all_qzone_config", str);
                } else if (str2.equalsIgnoreCase("onlinebank")) {
                    SitePerferenceMgr.a("moxie_sdk_all_onlinebank_config", str);
                } else if (str2.equalsIgnoreCase(MxParam.PARAM_FUNCTION_LINKEDIN)) {
                    SitePerferenceMgr.a("moxie_sdk_all_linkedin_config", str);
                }
            }
            return siteConfigsResponse;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SiteConfigItem b(String str) {
        if (str == null) {
            return null;
        }
        try {
            SiteConfigItem siteConfigItem = new SiteConfigItem();
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("mailId")) {
                siteConfigItem.a = String.valueOf(jSONObject.getInt("mailId"));
            }
            if (jSONObject.has("host")) {
                siteConfigItem.b = jSONObject.getString("host");
            }
            if (jSONObject.has("accessMethod")) {
                siteConfigItem.b(String.valueOf(jSONObject.getInt("accessMethod")));
            }
            if (jSONObject.has("category")) {
                siteConfigItem.a(jSONObject.getString("category"));
            }
            if (jSONObject.has("status")) {
                siteConfigItem.a(Integer.valueOf(jSONObject.getInt("status")));
            }
            SiteConfigInfo siteConfigInfo = new SiteConfigInfo();
            jSONObject = jSONObject.getJSONObject("items");
            if (jSONObject != null) {
                if (jSONObject.has("isCanUseWebLogin")) {
                    siteConfigInfo.m(jSONObject.getString("isCanUseWebLogin"));
                }
                if (jSONObject.has("jsurl")) {
                    siteConfigInfo.n(jSONObject.getString("jsurl"));
                }
                if (jSONObject.has("jsurlv2")) {
                    siteConfigInfo.g(jSONObject.getString("jsurlv2"));
                }
                if (jSONObject.has("loginUrl")) {
                    siteConfigInfo.o(jSONObject.getString("loginUrl"));
                }
                if (jSONObject.has("userAgent")) {
                    siteConfigInfo.p(jSONObject.getString("userAgent"));
                }
                if (jSONObject.has("logo")) {
                    siteConfigInfo.l(jSONObject.getString("logo"));
                }
                if (jSONObject.has("successUrl")) {
                    siteConfigInfo.k(jSONObject.getString("successUrl"));
                }
                if (jSONObject.has("loginType")) {
                    siteConfigInfo.j(jSONObject.getString("loginType"));
                }
                if (jSONObject.has("subType")) {
                    siteConfigInfo.i(jSONObject.getString("subType"));
                }
                if (jSONObject.has("insuranceName")) {
                    siteConfigInfo.h(jSONObject.getString("insuranceName"));
                }
                if (jSONObject.has("fields")) {
                    siteConfigInfo.f(jSONObject.getString("fields"));
                }
                if (jSONObject.has("bankId")) {
                    siteConfigInfo.e(jSONObject.getString("bankId"));
                }
                if (jSONObject.has("bankName")) {
                    siteConfigInfo.d(jSONObject.getString("bankName"));
                }
                if (jSONObject.has("preLoadJS")) {
                    siteConfigInfo.c(jSONObject.getString("preLoadJS"));
                }
                if (jSONObject.has("preLoadDelay")) {
                    siteConfigInfo.a(jSONObject.getInt("preLoadDelay"));
                }
                if (jSONObject.has("preLoadTimes")) {
                    siteConfigInfo.b(jSONObject.getInt("preLoadTimes"));
                }
                if (jSONObject.has("loginUrlV3")) {
                    siteConfigInfo.b(jSONObject.getString("loginUrlV3"));
                }
                if (jSONObject.has("jsurlv3")) {
                    siteConfigInfo.a(jSONObject.getString("jsurlv3"));
                }
                if (jSONObject.has("domain")) {
                    JSONArray jSONArray = new JSONArray(jSONObject.getString("domain"));
                    List arrayList = new ArrayList();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        arrayList.add(jSONArray.getString(i));
                    }
                    siteConfigInfo.a(arrayList);
                }
                siteConfigItem.c = siteConfigInfo;
            }
            return siteConfigItem;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
