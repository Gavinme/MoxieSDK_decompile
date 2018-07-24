package com.moxie.client.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: TbsSdkJava */
public class TaskStatusResult implements Parcelable {
    public static final Creator<TaskStatusResult> CREATOR = new Creator<TaskStatusResult>() {
        public final /* bridge */ /* synthetic */ TaskStatusResult[] newArray(int i) {
            return new TaskStatusResult[i];
        }

        public final /* synthetic */ TaskStatusResult createFromParcel(Parcel parcel) {
            return new TaskStatusResult(parcel);
        }
    };
    private int a;
    private String b;
    private String c;
    private String d;
    private Integer e;
    private String f;
    private Long g;
    private Integer h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private Long n;

    public final int a() {
        return this.a;
    }

    public final void a(int i) {
        this.a = i;
    }

    public final void a(String str) {
        this.b = str;
    }

    public final String b() {
        return this.c;
    }

    public final void b(String str) {
        this.c = str;
    }

    public final String c() {
        return this.d;
    }

    public final void c(String str) {
        this.d = str;
    }

    public final void a(Integer num) {
        this.e = num;
    }

    public final void d(String str) {
        this.f = str;
    }

    public final void a(Long l) {
        this.g = l;
    }

    public final Integer d() {
        return this.h;
    }

    public final void b(Integer num) {
        this.h = num;
    }

    public final void e(String str) {
        this.i = str;
    }

    public final String e() {
        return this.j;
    }

    public final void f(String str) {
        this.j = str;
    }

    public final String f() {
        return this.l;
    }

    public final void g(String str) {
        this.l = str;
    }

    public final String g() {
        return this.m;
    }

    public final void h(String str) {
        this.m = str;
    }

    public final void i(String str) {
        this.k = str;
    }

    public final void b(Long l) {
        this.n = l;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e.intValue());
        parcel.writeString(this.f);
        parcel.writeLong(this.g.longValue());
        parcel.writeInt(this.h.intValue());
        parcel.writeString(this.i);
        parcel.writeString(this.j);
        parcel.writeString(this.k);
        parcel.writeString(this.l);
        parcel.writeString(this.m);
        parcel.writeLong(this.n.longValue());
    }

    public TaskStatusResult() {
        this.a = 200;
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = Integer.valueOf(0);
        this.f = "";
        this.g = Long.valueOf(0);
        this.h = Integer.valueOf(0);
        this.i = "";
        this.j = "";
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = Long.valueOf(0);
    }

    private TaskStatusResult(Parcel parcel) {
        this.a = 200;
        this.b = "";
        this.c = "";
        this.d = "";
        this.e = Integer.valueOf(0);
        this.f = "";
        this.g = Long.valueOf(0);
        this.h = Integer.valueOf(0);
        this.i = "";
        this.j = "";
        this.k = "";
        this.l = "";
        this.m = "";
        this.n = Long.valueOf(0);
        this.a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = Integer.valueOf(parcel.readInt());
        this.f = parcel.readString();
        this.g = Long.valueOf(parcel.readLong());
        this.h = Integer.valueOf(parcel.readInt());
        this.i = parcel.readString();
        this.j = parcel.readString();
        this.k = parcel.readString();
        this.l = parcel.readString();
        this.m = parcel.readString();
        this.n = Long.valueOf(parcel.readLong());
    }

    public String toString() {
        return "TaskStatusResult{code=" + this.a + ", msg='" + this.b + '\'' + ", phase='" + this.c + '\'' + ", phaseStatus=" + this.d + ", progress='" + this.e + '\'' + ", taskId='" + this.f + '\'' + ", waitSeconds='" + this.g + '\'' + ", finished='" + this.h + '\'' + ", mailId='" + this.i + '\'' + ", description='" + this.j + '\'' + ", inputParamName='" + this.k + '\'' + ", inputType='" + this.l + '\'' + ", inputValue='" + this.m + '\'' + ", inputWaitSeconds='" + this.n + '\'' + '}';
    }
}
