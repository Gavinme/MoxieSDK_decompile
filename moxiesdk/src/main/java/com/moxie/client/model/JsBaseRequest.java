package com.moxie.client.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.smtt.sdk.TbsListener.ErrorCode;

/* compiled from: TbsSdkJava */
public class JsBaseRequest {
    public String type = "";
    public String url = "";
    public String headers = "";
    public String data = "";
    public String itemName = "";
    public String encoding = "UTF-8";
    public int failCode = ErrorCode.INFO_CODE_BASE;
    public String responseId = "";

    public static JsBaseRequest getJsRequest(String str) {
        JsBaseRequest jsBaseRequest = new JsBaseRequest();
        JSONObject jSONObject = null;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsBaseRequest.type = jSONObject.optString("type");
        jsBaseRequest.url = jSONObject.optString("url");
        jsBaseRequest.headers = jSONObject.optString("headers");
        jsBaseRequest.data = jSONObject.optString("data");
        jsBaseRequest.itemName = jSONObject.optString("itemName");
        jsBaseRequest.responseId = jSONObject.optString("responseId");
        if (jSONObject.has("encoding")) {
            jsBaseRequest.encoding = jSONObject.optString("encoding");
            if (jsBaseRequest.encoding.equalsIgnoreCase("UTF8")) {
                jsBaseRequest.encoding = "UTF-8";
            }
        }
        if (jSONObject.has("failCode")) {
            jsBaseRequest.failCode = jSONObject.optInt("failCode");
        }
        return jsBaseRequest;
    }
}
