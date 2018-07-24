package com.moxie.client.utils;

import android.content.Context;
import android.text.TextUtils;
import com.moxie.client.crash.CrashReportData.Type;
import com.moxie.client.crash.MxACRA;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;

/* compiled from: TbsSdkJava */
public class ErrorHandle {
    public static final String a = ErrorHandle.class.getSimpleName();

    public static void a(String str, Throwable th) {
        if (!TextUtils.isEmpty(str)) {
            new StringBuilder("Exception:  ").append(th.getMessage());
        }
    }

    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            TextUtils.isEmpty(str);
        }
    }

    public static void a(Context context, Throwable th) {
        if (MoxieSDK.getInstance().getMoxieCallBack() != null) {
            MoxieSDK.getInstance().getMoxieCallBack().onError(new MoxieContext(context), 1, th);
        }
    }

    public static void b(String str, Throwable th) {
        a(a, str);
        MxACRA.a().d().a(th).a(str).b(Type.b).b();
    }
}
