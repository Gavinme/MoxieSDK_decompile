package com.moxie.client.restapi;

import com.moxie.client.http.HttpUrlConnection;
import com.moxie.client.tasks.model.BaseConfig;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class LoadBaseConfigApi {
    public static BaseConfig a() {
        HttpUrlConnection.getinstance();
        return a(HttpUrlConnection.getString("https://api.51datakey.com/conf/api/v3/sdkconf?key=MoxieSDKAndroid/1.3.2.1", null));
    }

    private static BaseConfig a(String str) {
        BaseConfig baseConfig = new BaseConfig();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("config")) {
                JSONObject jSONObject2 = new JSONObject(jSONObject.getString("config"));
                if (jSONObject2.has("X5Enable")) {
                    baseConfig.a = jSONObject2.getBoolean("X5Enable");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseConfig;
    }
}
