package com.moxie.client.utils;

/* compiled from: TbsSdkJava */
public class Assert {
    public static <T> T a(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }
}
