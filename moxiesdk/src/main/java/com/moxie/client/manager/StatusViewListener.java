package com.moxie.client.manager;

import com.proguard.annotation.NotProguard;
import org.json.JSONObject;

@NotProguard
/* compiled from: TbsSdkJava */
public interface StatusViewListener {
    void updateProgress(JSONObject jSONObject);
}
