package com.moxie.client.crash;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.format.Time;
import com.moxie.client.utils.CommonMethod;
import com.moxie.client.utils.PackageManagerWrapper;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* compiled from: TbsSdkJava */
public class CrashReportDataFactory {
    private final Context a;
    private final Time b;

    public CrashReportDataFactory(Context context, Time time) {
        this.a = context;
        this.b = time;
    }

    public final CrashReportData a(String str, String str2, Throwable th) {
        CrashReportData crashReportData = new CrashReportData();
        DeviceInfo deviceInfo = new DeviceInfo();
        try {
            PackageManagerWrapper packageManagerWrapper = new PackageManagerWrapper(this.a);
            PackageInfo a = packageManagerWrapper.a();
            if (a != null) {
                deviceInfo.h = Integer.toString(a.versionCode);
                deviceInfo.c = a.versionName != null ? a.versionName : "not set";
            } else {
                deviceInfo.c = "Package info unavailable";
            }
            PackageManager packageManager = this.a.getPackageManager();
            ApplicationInfo b = packageManagerWrapper.b();
            if (!(packageManager == null || b == null)) {
                deviceInfo.o = (String) packageManager.getApplicationLabel(b);
            }
            Location j = CommonMethod.j(this.a);
            if (j != null) {
                deviceInfo.b = String.valueOf(j.getLatitude());
                deviceInfo.a = String.valueOf(j.getLongitude());
            }
            deviceInfo.e = Build.MODEL;
            deviceInfo.i = CommonMethod.g(this.a);
            deviceInfo.p = VERSION.RELEASE;
            deviceInfo.d = this.a.getPackageName();
            deviceInfo.l = CommonMethod.d();
            deviceInfo.n = Build.BRAND;
            deviceInfo.k = "1.3.5";
            deviceInfo.r = Build.PRODUCT;
            deviceInfo.f = UUID.randomUUID().toString();
            deviceInfo.t = CommonMethod.c(this.a);
            deviceInfo.g = CommonMethod.a();
            crashReportData.a = deviceInfo;
            Time time = new Time();
            time.setToNow();
            crashReportData.b = CommonMethod.a(time);
            crashReportData.c = str;
            Map hashMap = new HashMap();
            hashMap.put("stackTrace", a(str2, th));
            crashReportData.d = hashMap;
            return crashReportData;
        } catch (Exception e) {
            e.printStackTrace();
            return crashReportData;
        }
    }

    private static String a(String str, Throwable th) {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        if (!(str == null || str.isEmpty())) {
            printWriter.println(str);
        }
        while (th != null) {
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        String obj = stringWriter.toString();
        printWriter.close();
        return obj;
    }
}
