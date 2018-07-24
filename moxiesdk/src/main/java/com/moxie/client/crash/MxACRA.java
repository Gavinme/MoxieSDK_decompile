package com.moxie.client.crash;

import android.content.Context;

/* compiled from: TbsSdkJava */
public class MxACRA {
    private static ErrorReporter a;

    public static void a(Context context, boolean z) {
        if (a == null) {
            ErrorReporter errorReporter = new ErrorReporter(context.getApplicationContext(), z);
            a = errorReporter;
            errorReporter.c();
        }
    }

    public static ErrorReporter a() {
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("Please invoke MoxieSDK.init() in Application.onCreate() first");
    }

    public static void b() {
        if (a == null) {
            throw new IllegalStateException("Please invoke MoxieSDK.init() in Application.onCreate() first");
        }
        a.a();
    }

    public static void c() {
        if (a == null) {
            throw new IllegalStateException("Please invoke MoxieSDK.init() in Application.onCreate() first");
        }
        a.b();
    }
}
