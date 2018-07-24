package com.moxie.client.widget.wave;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/* compiled from: TbsSdkJava */
public class UiUtils {
    public static int a(Context context, int i) {
        return (int) ((((float) i) * b(context)) + 0.5f);
    }

    private static float b(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.density;
        } catch (Exception e) {
            return 160.0f;
        }
    }

    public static int a(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            return displayMetrics.densityDpi;
        } catch (Exception e) {
            return 160;
        }
    }
}
