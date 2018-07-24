package com.moxie.client.utils;

import android.content.Context;

/* compiled from: TbsSdkJava */
public class SharedPreferMgr {
    private static SharedPreferMgr a = null;
    private static Context b;

    public static void a(Context context) {
        if (a == null) {
            a = new SharedPreferMgr(context.getApplicationContext());
        }
    }

    private SharedPreferMgr(Context context) {
        b = context;
    }

    public static void a(String str, String str2) {
        try {
            SharedPreferUtil.b(b, "share_name", "mail_host_" + str, str2);
        } catch (Exception e) {
        }
    }

    public static String a(String str) {
        try {
            return SharedPreferUtil.a(b, "share_name", "mail_host_" + str, "");
        } catch (Exception e) {
            return null;
        }
    }

    public static void b(String str, String str2) {
        try {
            SharedPreferUtil.b(b, "share_name", "mail_js_" + str, str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String b(String str) {
        try {
            return SharedPreferUtil.a(b, "share_name", "mail_js_" + str, "");
        } catch (Exception e) {
            return null;
        }
    }

    public static void c(String str, String str2) {
        try {
            SharedPreferUtil.b(b, "share_name", str, str2);
        } catch (Exception e) {
        }
    }

    public static void d(String str, String str2) {
        try {
            SharedPreferUtil.b(b, "share_name", str, str2);
        } catch (Exception e) {
        }
    }

    public static String e(String str, String str2) {
        try {
            return SharedPreferUtil.a(b, "share_name", str, str2);
        } catch (Exception e) {
            return "";
        }
    }
}
