package com.moxie.client.restapi;

import android.text.TextUtils;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.model.MxParam;
import com.moxie.client.utils.SharedPreferMgr;
import com.proguard.annotation.NotProguard;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;

@NotProguard
/* compiled from: TbsSdkJava */
public class LoadTenantPermissionApi {
    public static String getTenantPermissonInfo(String str) {
        String str2;
        String str3 = "https://api.51datakey.com/email/v1/tenant/permission";
        HashMap extendParams = GlobalParams.i().a().getExtendParams();
        if (extendParams == null || !extendParams.containsKey(MxParam.PARAM_PERMISIION_URL) || TextUtils.isEmpty((CharSequence) extendParams.get(MxParam.PARAM_PERMISIION_URL))) {
            str2 = str3;
        } else {
            str2 = (String) extendParams.get(MxParam.PARAM_PERMISIION_URL);
        }
        str3 = null;
        for (int i = 0; i < 2; i++) {
            HttpUrlConnection.getinstance();
            str3 = HttpUrlConnection.postString(str2, "", getHttpHeader());
            if (str3 != null) {
                break;
            }
        }
        str2 = str3;
        parsePermissionJson(str2);
        return str2;
    }

    public static ArrayList<String> parsePermissionJson(String str) {
        if (str == null || str == "") {
            SharedPreferMgr.c("moxie_sdk_tenant_permission", "");
            return null;
        }
        try {
            ArrayList<String> arrayList = new ArrayList();
            JSONArray jSONArray = new JSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(jSONArray.getString(i));
            }
            SharedPreferMgr.c("moxie_sdk_tenant_permission", str);
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HashMap<String, String> getHttpHeader() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("Authorization", "apikey " + GlobalParams.i().a().getApiKey());
        return hashMap;
    }
}
