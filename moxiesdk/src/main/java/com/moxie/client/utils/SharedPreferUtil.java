package com.moxie.client.utils;

import android.content.Context;

/* compiled from: TbsSdkJava */
public class SharedPreferUtil {
    public static String a(Context context, String str, String str2, String str3) {
        return context.getSharedPreferences(str, 0).getString(str2, str3);
    }

    public static void b(Context context, String str, String str2, String str3) {
        context.getSharedPreferences(str, 0).edit().putString(str2, str3).commit();
    }
}
