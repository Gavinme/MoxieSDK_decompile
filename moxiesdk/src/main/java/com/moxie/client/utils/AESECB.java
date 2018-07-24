package com.moxie.client.utils;

import java.util.Random;

/* compiled from: TbsSdkJava */
public class AESECB {
    public static byte[] a = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};

    public static String a() {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < 16; i++) {
            String str2 = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(str2)) {
                str = str + ((char) ((random.nextInt(2) % 2 == 0 ? 65 : 97) + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(str2)) {
                str = str + String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }
}
