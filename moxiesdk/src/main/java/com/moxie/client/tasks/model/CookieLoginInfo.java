package com.moxie.client.tasks.model;

import java.io.Serializable;
import java.util.List;

/* compiled from: TbsSdkJava */
public class CookieLoginInfo implements Serializable {
    private String a = "";
    private String b = "";
    private String c = "";
    private String d = "";
    private String e = "";
    private String f = "";
    private String g = "";
    private String h = "";
    private String i = "";
    private boolean j = false;
    private String k = "";
    private String l = "";
    private String m = "";
    private int n = 25;
    private int o = 30;
    private List<String> p;

    public final List<String> a() {
        return this.p;
    }

    public final void a(List<String> list) {
        this.p = list;
    }

    public final String b() {
        return this.f;
    }

    public final void a(String str) {
        this.f = str;
    }

    public final String c() {
        return this.c;
    }

    public final void b(String str) {
        this.c = str;
    }

    public final String d() {
        return this.m;
    }

    public final void c(String str) {
        this.m = str;
    }

    public final int e() {
        return this.n;
    }

    public final void a(int i) {
        this.n = i;
    }

    public final int f() {
        return this.o;
    }

    public final void b(int i) {
        this.o = i;
    }

    public final void d(String str) {
        this.e = str;
    }

    public final String g() {
        return this.k;
    }

    public final void e(String str) {
        this.k = str;
    }

    public final String h() {
        return this.l;
    }

    public final void f(String str) {
        this.l = str;
    }

    public final void g(String str) {
        this.i = str;
    }

    public final void h(String str) {
        this.h = str;
    }

    public final void a(boolean z) {
        this.j = z;
    }

    public final String i() {
        return this.d;
    }

    public final void i(String str) {
        this.d = str;
    }

    public final String j() {
        return this.b;
    }

    public final void j(String str) {
        this.b = str;
    }

    public final String k() {
        return this.g;
    }

    public final void k(String str) {
        this.g = str;
    }

    public final void l(String str) {
        this.a = str;
    }

    public String toString() {
        return "{isCanUseWebLogin=" + this.j + ", hostName='" + this.a + '\'' + ", loginUrl='" + this.b + '\'' + ", loginJsUrl='" + this.d + '\'' + ", loginJsUrlv2='" + this.e + '\'' + ", logo='" + this.h + '\'' + ", userAgentStr='" + this.g + '\'' + ", successUrl='" + this.i + '\'' + '}';
    }
}
