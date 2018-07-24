package com.moxie.client.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/* compiled from: TbsSdkJava */
public final class PackageManagerWrapper {
    private final Context a;

    public PackageManagerWrapper(Context context) {
        this.a = context;
    }

    public final PackageInfo a() {
        PackageInfo packageInfo = null;
        PackageManager packageManager = this.a.getPackageManager();
        if (packageManager != null) {
            try {
                packageInfo = packageManager.getPackageInfo(this.a.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                ErrorHandle.a("PackageManagerWrapper", "Failed to find PackageInfo for current App : " + this.a.getPackageName());
            } catch (RuntimeException e2) {
            }
        }
        return packageInfo;
    }

    public final ApplicationInfo b() {
        ApplicationInfo applicationInfo = null;
        PackageManager packageManager = this.a.getPackageManager();
        if (packageManager != null) {
            try {
                applicationInfo = packageManager.getApplicationInfo(this.a.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                ErrorHandle.a("PackageManagerWrapper", "Failed to find PackageInfo for current App : " + this.a.getPackageName());
            } catch (RuntimeException e2) {
            }
        }
        return applicationInfo;
    }
}
