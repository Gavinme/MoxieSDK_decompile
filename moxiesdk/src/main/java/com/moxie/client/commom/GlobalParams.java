package com.moxie.client.commom;

import com.moxie.client.model.MxParam;

/* compiled from: TbsSdkJava */
public class GlobalParams {
    private static volatile GlobalParams r = null;
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "android";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private String j = "";
    private String k = "";
    private String l = "";
    private String m = "";
    private String n = "";
    private String o = "";
    private String p = "";
    private MxParam q = null;

    public final MxParam a() {
        return this.q;
    }

    public final void a(MxParam mxParam) {
        this.q = mxParam;
    }

    public final String b() {
        return this.o;
    }

    public final void a(String str) {
        this.o = str;
    }

    public final String c() {
        return this.p;
    }

    public final void b(String str) {
        this.p = str;
    }

    public final String d() {
        return this.l;
    }

    public final void c(String str) {
        this.l = str;
    }

    public final String e() {
        return this.m;
    }

    public final void d(String str) {
        this.m = str;
    }

    public final String f() {
        return this.n;
    }

    public final void e(String str) {
        this.n = str;
    }

    public final String g() {
        return this.k;
    }

    public final void f(String str) {
        this.k = str;
    }

    public final String h() {
        return this.j;
    }

    public final void g(String str) {
        this.j = str;
    }

    public static GlobalParams i() {
        if (r == null) {
            r = new GlobalParams();
        }
        return r;
    }

    private GlobalParams() {
    }

    public final String j() {
        return this.b;
    }

    public final void h(String str) {
        this.b = str;
    }

    public final String k() {
        return this.c;
    }

    public final void i(String str) {
        this.c = str;
    }

    public final String l() {
        return this.d;
    }

    public final void j(String str) {
        this.d = str;
    }

    public final String m() {
        return this.e;
    }

    public final String n() {
        return this.f;
    }

    public final void k(String str) {
        this.f = str;
    }

    public final String o() {
        return this.g;
    }

    public final void l(String str) {
        this.g = str;
    }

    public final String p() {
        return this.h;
    }

    public final void m(String str) {
        this.h = str;
    }

    public final String q() {
        return this.i;
    }

    public final void n(String str) {
        this.i = str;
    }
}
