package com.moxie.client.crash;

import java.io.File;
import java.io.FilenameFilter;

import android.content.Context;

/* compiled from: TbsSdkJava */
final class CrashReportFinder {
    private final Context a;

    public CrashReportFinder(Context context) {
        this.a = context;
    }

    public final String[] a() {
        if (this.a == null) {
            String str = ErrorReporter.a;
            return new String[0];
        }
        File filesDir = this.a.getFilesDir();
        if (filesDir == null) {
            String str = ErrorReporter.a;
            return new String[0];
        }
        String str2 = ErrorReporter.a;
        new StringBuilder("Looking for error files in ").append(filesDir.getAbsolutePath());
        String[] list = filesDir.list(new FilenameFilter() {

            public boolean accept(File file, String str) {
                return str.endsWith(".mxstacktrace");
            }
        });
        return list == null ? new String[0] : list;
    }
}
