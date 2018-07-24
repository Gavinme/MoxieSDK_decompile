package com.moxie.client.crash;

import android.content.Context;
import com.moxie.client.http.HttpUrlConnection;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/* compiled from: TbsSdkJava */
final class SendWorker extends Thread {
    private final Context a;
    private CrashReportData b;

    public SendWorker(Context context) {
        this(context, null);
    }

    public SendWorker(Context context, CrashReportData crashReportData) {
        this.a = context;
        this.b = crashReportData;
    }

    public final void run() {
        int i;
        int i2 = 0;
        Context context = this.a;
        String str = ErrorReporter.a;
        String[] a = new CrashReportFinder(context).a();
        Arrays.sort(a);
        if (this.b != null) {
            a(this.b);
            i = 1;
        } else {
            i = 0;
        }
        int length = a.length;
        while (i2 < length) {
            String str2 = a[i2];
            if (i >= 5) {
                break;
            }
            String str3 = ErrorReporter.a;
            try {
                a(new CrashReportPersister(context).a(str2));
                a(context, str2);
            } catch (RuntimeException e) {
                str = ErrorReporter.a;
                a(context, str2);
            } catch (IOException e2) {
                str = ErrorReporter.a;
                a(context, str2);
            } catch (Exception e3) {
                str2 = ErrorReporter.a;
            }
            i++;
            i2++;
        }
        str = ErrorReporter.a;
    }

    private static void a(CrashReportData crashReportData) {
        if (crashReportData != null) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("Authorization", "service 26854f1fab6142a5b93f3436a5ce6e2a");
                hashMap.put("Content-Encoding", "gzip");
                String str = ErrorReporter.a;
                new StringBuilder("CrashReportData=").append(JSONReportBuilder.a(crashReportData).toString());
                HttpUrlConnection.getinstance();
                HttpUrlConnection.postString("https://log.51datakey.com:48125/metrics-gateway/api/v1/crashlog/android", JSONReportBuilder.a(crashReportData).toString(), hashMap);
            } catch (Exception e) {
                String str2 = ErrorReporter.a;
            }
        }
    }

    private static void a(Context context, String str) {
        if (!context.deleteFile(str)) {
            String str2 = ErrorReporter.a;
        }
    }
}
