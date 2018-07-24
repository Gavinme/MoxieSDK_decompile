package com.moxie.client.tasks.model;

import java.io.Serializable;

/* compiled from: TbsSdkJava */
public class SiteConfigItem implements Serializable {
    public String a;
    public String b;
    public SiteConfigInfo c;
    private String d;
    private String e;
    private Integer f;

    public final void a(Integer num) {
        this.f = num;
    }

    public final String a() {
        return this.e;
    }

    public final void a(String str) {
        this.e = str;
    }

    public final void b(String str) {
        this.d = str;
    }
}
