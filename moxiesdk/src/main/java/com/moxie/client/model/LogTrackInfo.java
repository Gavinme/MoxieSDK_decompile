package com.moxie.client.model;

import android.text.TextUtils;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.utils.ErrorHandle;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: TbsSdkJava */
public class LogTrackInfo {
    private String a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    private Map<String, String> f;

    /* compiled from: TbsSdkJava */
    public static class Builder {
        String a;
        String b;
        String c;
        String d;
        boolean e = false;
        Map<String, String> f;

        public final Builder a(String str) {
            this.b = str;
            return this;
        }

        public final Builder b(String str) {
            this.c = str;
            return this;
        }

        public final Builder c(String str) {
            this.d = str;
            return this;
        }

        public final Builder d(String str) {
            this.a = str;
            return this;
        }

        public final Builder a() {
            this.e = true;
            return this;
        }

        public final LogTrackInfo b() {
            return new LogTrackInfo(this.a, this.b, this.c, this.d, this.e, this.f);
        }
    }

    /* compiled from: TbsSdkJava */
    public interface ErrorCode {
    }

    /* compiled from: TbsSdkJava */
    public interface PhaseStatus {
    }

    public LogTrackInfo() {
        this.e = false;
    }

    private LogTrackInfo(String str, String str2, String str3, String str4, boolean z, Map<String, String> map) {
        this.e = false;
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str3)) {
            throw new IllegalArgumentException("errorCode, function, phaseStatus三者不能为空");
        }
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.f = map;
        this.e = z;
    }

    public final void a(String str) {
        this.a = str;
    }

    public final void b(String str) {
        this.b = str;
    }

    public final void c(String str) {
        this.c = str;
    }

    public final void d(String str) {
        this.d = str;
    }

    public final void a() {
        this.e = true;
    }

    public final JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(this.a)) {
                jSONObject.put(TaskStatus.MESSAGE, this.a);
            }
            if (!TextUtils.isEmpty(this.c)) {
                jSONObject.put("phaseStatus", this.c);
            }
            if (!(TextUtils.isEmpty(this.d) || TextUtils.isEmpty(this.b))) {
                String obj = "";
                if (MxParam.PARAM_FUNCTION_ALIPAY.equalsIgnoreCase(this.d)) {
                    obj = "AP";
                } else if (MxParam.PARAM_FUNCTION_TAOBAO.equalsIgnoreCase(this.d)) {
                    obj = "TB";
                } else if (MxParam.PARAM_FUNCTION_JINGDONG.equalsIgnoreCase(this.d)) {
                    obj = "JD";
                }
                if (!TextUtils.isEmpty(obj)) {
                    jSONObject.put("errorCode", obj + this.b);
                }
            }
            jSONObject.put("visible", this.e);
            if (this.f != null) {
                JSONObject jSONObject2 = new JSONObject();
                for (String str : this.f.keySet()) {
                    jSONObject2.put(str, (String) this.f.get(str));
                }
                jSONObject.put("tags", jSONObject2);
            }
        } catch (Throwable e) {
            ErrorHandle.a("LogTrackInfo", e);
        }
        return jSONObject;
    }
}
