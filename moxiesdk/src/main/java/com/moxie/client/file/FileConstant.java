package com.moxie.client.file;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/* compiled from: TbsSdkJava */
public class FileConstant {
    private final FileConstant a = this;

    public static File a(Context context, String str) {//str : module name eg. ali
        File externalCacheDir;
        if (Environment.getExternalStorageState().equals("mounted")) {
            externalCacheDir = context.getExternalCacheDir();
        } else {
            externalCacheDir = context.getCacheDir();
        }
        if (externalCacheDir == null) {
            return null;
        }
        if (str == null) {
            return externalCacheDir;
        }
        File file = new File(externalCacheDir.getAbsolutePath() + "/" + str);
        if (FileWriter.a(file)) {
            return file;
        }
        return null;
    }

    public static void b(Context context, String str) {
        try {
            File externalCacheDir;
            if (Environment.getExternalStorageState().equals("mounted")) {
                externalCacheDir = context.getExternalCacheDir();
            } else {
                externalCacheDir = context.getCacheDir();
            }
            if (externalCacheDir != null && str != null) {
                FileWriter.a(new File(externalCacheDir.getAbsolutePath() + "/" + str), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
