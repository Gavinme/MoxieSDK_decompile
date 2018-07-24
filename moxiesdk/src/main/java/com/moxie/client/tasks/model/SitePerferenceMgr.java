package com.moxie.client.tasks.model;

import android.text.TextUtils;
import com.moxie.client.restapi.LoadSiteConfigApi;
import com.moxie.client.utils.SharedPreferMgr;

/* compiled from: TbsSdkJava */
public class SitePerferenceMgr {
    public static CookieLoginInfo a(String str) {
        CookieLoginInfo cookieLoginInfo = new CookieLoginInfo();
        cookieLoginInfo.l(str);
        String b = SharedPreferMgr.b(str);
        new StringBuilder("content=").append(b).append(" hostName=").append(str);
        if (!TextUtils.isEmpty(b)) {
            try {
                SiteConfigItem b2 = LoadSiteConfigApi.b(b);
                if (b2 != null) {
                    cookieLoginInfo.j(b2.c.m());
                    cookieLoginInfo.h(b2.c.j());
                    cookieLoginInfo.a(b2.c.k().equals("1"));
                    cookieLoginInfo.i(b2.c.l());
                    cookieLoginInfo.d(b2.c.g());
                    cookieLoginInfo.a(b2.c.b());
                    cookieLoginInfo.k(b2.c.n());
                    cookieLoginInfo.g(b2.c.i());
                    cookieLoginInfo.e(b2.c.h());
                    cookieLoginInfo.f(b2.a());
                    cookieLoginInfo.a(b2.c.e());
                    cookieLoginInfo.c(b2.c.d());
                    cookieLoginInfo.b(b2.c.f());
                    cookieLoginInfo.b(b2.c.c());
                    cookieLoginInfo.a(b2.c.a());
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return cookieLoginInfo;
    }

    public static void a(String str, String str2) {
        SharedPreferMgr.b(str, str2);
    }
}
