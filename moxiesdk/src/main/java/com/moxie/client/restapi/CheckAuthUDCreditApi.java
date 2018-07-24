package com.moxie.client.restapi;

import org.json.JSONObject;

import com.moxie.client.commom.GlobalConstants;
import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.utils.CommonMethod;
import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class CheckAuthUDCreditApi {
    public static String excute(String str, String str2, String str3) {
        String format = String.format("https://da.udcredit.com/frontserver/4.2/partner/auth_check/signature/%s",
                CommonMethod.b(getRequestBody(str, str2, str3) + "|" + GlobalConstants.d));
        String str4 = null;
        for (int i = 0; i < 2; i++) {
            HttpUrlConnection.getinstance();
            str4 = HttpUrlConnection.getString(format, null);
            if (str4 != null) {
                break;
            }
        }
        return str4;
    }

    private static String getRequestBody(String str, String str2, String str3) {
        String str4 = "";
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("pub_key", str);
            jSONObject.put("user_id", str2);
            jSONObject.put("product_code", str3);
            str4 = jSONObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str4;
    }
}
