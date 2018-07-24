package com.moxie.client.utils;

import com.moxie.client.utils.base64utils.Base64Utils;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;

/* compiled from: TbsSdkJava */
public final class Base64 {
    private static final byte[] a = new byte[128];
    private static final char[] b = new char[64];

    static {
        int i;
        int i2 = 0;
        for (i = 0; i < 128; i++) {
            a[i] = (byte) -1;
        }
        for (i = 90; i >= 65; i--) {
            a[i] = (byte) (i - 65);
        }
        for (i = ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR; i >= 97; i--) {
            a[i] = (byte) ((i - 97) + 26);
        }
        for (i = 57; i >= 48; i--) {
            a[i] = (byte) ((i - 48) + 52);
        }
        a[43] = (byte) 62;
        a[47] = (byte) 63;
        for (i = 0; i <= 25; i++) {
            b[i] = (char) (i + 65);
        }
        int i3 = 26;
        i = 0;
        while (i3 <= 51) {
            b[i3] = (char) (i + 97);
            i3++;
            i++;
        }
        i = 52;
        while (i <= 61) {
            b[i] = (char) (i2 + 48);
            i++;
            i2++;
        }
        b[62] = '+';
        b[63] = '/';
    }

    private static boolean encode(char c) {
        return c == '=';
    }

    private static boolean b(char c) {
        return c < '' && a[c] != (byte) -1;
    }

    public static String encode(byte[] bArr) {
        return new String(Base64Utils.encode(bArr));
    }

    public static byte[] decode(String str) {
        return Base64Utils.decode(str);
    }
}
