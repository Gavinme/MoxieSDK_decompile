package com.moxie.client.utils;

import android.content.Context;

/* compiled from: TbsSdkJava */
public class DisplayUtil {
    private DisplayUtil() {
    }

    public static int a(Context context) {
        return (int) ((context.getResources().getDisplayMetrics().density * 70.0f) + 0.5f);
    }
}
