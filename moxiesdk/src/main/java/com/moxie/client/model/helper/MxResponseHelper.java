package com.moxie.client.model.helper;

import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.utils.ErrorHandle;
import java.util.HashMap;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class MxResponseHelper {
    public static HashMap<String, String> a(String str) {
        if (str == null || str.equalsIgnoreCase("")) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("result")) {
                jSONObject = (JSONObject) jSONObject.get("result");
                if (jSONObject.has("success") && jSONObject.has(TaskStatus.MESSAGE)) {
                    HashMap<String, String> hashMap = new HashMap();
                    if (jSONObject.get("success").toString().equalsIgnoreCase("true")) {
                        hashMap.put("result", "true");
                    } else {
                        hashMap.put("result", "false");
                        hashMap.put(TaskStatus.MESSAGE, jSONObject.get(TaskStatus.MESSAGE).toString());
                        hashMap.put("errorcode", jSONObject.get("errorcode").toString());
                    }
                    return hashMap;
                }
            }
        } catch (Throwable e) {
            ErrorHandle.b("MainActivity parseResult error", e);
        }
        return null;
    }
}
