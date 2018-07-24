package com.moxie.client.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: TbsSdkJava */
public class AESCBC {
    private SecretKey a;

    public AESCBC(String str) {
        if (str != null && str.length() != 0) {
            try {
                byte[] obj = new byte[16];
                System.arraycopy(MessageDigest.getInstance("SHA-1").digest(str.trim().getBytes("UTF-8")), 0, obj, 0, 16);
                this.a = new SecretKeySpec(obj, "AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e2) {
            }
        }
    }

    public final String a(String str) throws Exception {
        byte[] obj = new byte[16];
        new SecureRandom().nextBytes(obj);
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(obj);
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(1, this.a, ivParameterSpec);
        byte[] doFinal = instance.doFinal(str.getBytes("UTF-8"));
        byte[] bArr = new byte[(doFinal.length + 16)];
        System.arraycopy(obj, 0, bArr, 0, 16);
        System.arraycopy(doFinal, 0, bArr, 16, doFinal.length);
        return Base64.encode(bArr);
    }

    public final String b(String str) throws Exception {
        byte[] a = Base64.decode(str);
        byte[] obj = new byte[16];
        System.arraycopy(a, 0, obj, 0, 16);
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(obj);
        int length = a.length - 16;
        byte[] obj2 = new byte[length];
        System.arraycopy(a, 16, obj2, 0, length);
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, this.a, ivParameterSpec);
        return new String(instance.doFinal(obj2));
    }
}
