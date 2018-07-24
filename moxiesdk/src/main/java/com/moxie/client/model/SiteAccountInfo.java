package com.moxie.client.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: TbsSdkJava */
public class SiteAccountInfo implements Parcelable {
    public static final Creator<SiteAccountInfo> CREATOR = new Creator<SiteAccountInfo>() {
        public final /* bridge */ /* synthetic */ SiteAccountInfo[] newArray(int i) {
            return new SiteAccountInfo[i];
        }

        public final /* synthetic */ SiteAccountInfo createFromParcel(Parcel parcel) {
            return new SiteAccountInfo(parcel);
        }
    };
    private String a;
    private String b;
    private String c;
    private String d;
    private Integer e;
    private long f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private Integer l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;
    private String z;

    public final String a() {
        return this.z;
    }

    public final String b() {
        return this.x;
    }

    public final String c() {
        return this.y;
    }

    public final String d() {
        return this.w;
    }

    public final void a(String str) {
        this.w = str;
    }

    public final String e() {
        return this.v;
    }

    public final void b(String str) {
        this.v = str;
    }

    public final String f() {
        return this.u;
    }

    public final void c(String str) {
        this.u = str;
    }

    public final String g() {
        return this.s;
    }

    public final String h() {
        return this.r;
    }

    public final String i() {
        return this.t;
    }

    public final void d(String str) {
        this.t = str;
    }

    public final void e(String str) {
        this.o = str;
    }

    public final void f(String str) {
        this.p = str;
    }

    public final String j() {
        return this.j;
    }

    public final void g(String str) {
        this.j = str;
    }

    public final void h(String str) {
        this.q = str;
    }

    public SiteAccountInfo() {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.g = "";
        this.h = "";
        this.i = "";
        this.j = "";
        this.k = "";
        this.l = Integer.valueOf(0);
        this.m = "";
        this.n = "";
        this.o = "";
        this.p = "";
        this.q = "";
        this.r = "";
        this.s = "";
        this.t = "";
        this.u = "";
        this.v = "";
        this.w = "";
        this.x = "";
        this.y = "";
        this.z = "";
    }

    public final String k() {
        return this.b;
    }

    public final void i(String str) {
        this.b = str;
    }

    public final void j(String str) {
        this.d = str;
    }

    public final void k(String str) {
        this.c = str;
    }

    public final Integer l() {
        return this.e;
    }

    public final void a(Integer num) {
        this.e = num;
    }

    public final String m() {
        return this.g;
    }

    public final void l(String str) {
        this.g = str;
    }

    public final String n() {
        return this.h;
    }

    public final void m(String str) {
        this.h = str;
    }

    public final String o() {
        return this.i;
    }

    public final void n(String str) {
        this.i = str;
    }

    public final String p() {
        return this.k;
    }

    public final void o(String str) {
        this.k = str;
    }

    public final String q() {
        return this.m;
    }

    public final void p(String str) {
        this.m = str;
    }

    public final Integer r() {
        return this.l;
    }

    public final void b(Integer num) {
        this.l = num;
    }

    public String toString() {
        return "SiteAccountInfo [userId=" + this.b + ", tanentId=" + this.a + ", autoId=" + this.f + ", mailAccount=" + this.g + ", lastWebAccessTime=" + this.n + ", encryptedPwd=" + this.h + ", independentPwd=" + this.i + ", mailId=" + this.k + "]";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.f);
        parcel.writeString(this.b);
        parcel.writeString(this.a);
        parcel.writeString(this.d);
        parcel.writeString(this.g);
        parcel.writeString(this.n);
        parcel.writeString(this.h);
        parcel.writeString(this.i);
        parcel.writeString(this.k);
        parcel.writeString(this.m);
    }

    private SiteAccountInfo(Parcel parcel) {
        this.a = "";
        this.b = "";
        this.c = "";
        this.d = "";
        this.g = "";
        this.h = "";
        this.i = "";
        this.j = "";
        this.k = "";
        this.l = Integer.valueOf(0);
        this.m = "";
        this.n = "";
        this.o = "";
        this.p = "";
        this.q = "";
        this.r = "";
        this.s = "";
        this.t = "";
        this.u = "";
        this.v = "";
        this.w = "";
        this.x = "";
        this.y = "";
        this.z = "";
        this.f = parcel.readLong();
        this.b = parcel.readString();
        this.a = parcel.readString();
        this.d = parcel.readString();
        this.g = parcel.readString();
        this.n = parcel.readString();
        this.h = parcel.readString();
        this.i = parcel.readString();
        this.k = parcel.readString();
        this.m = parcel.readString();
    }
}
