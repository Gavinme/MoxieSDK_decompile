package com.moxie.client.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/* compiled from: TbsSdkJava */
public class RsaUtil {
    public static String a(byte[] bArr, String str) throws Exception {
        PrivateKey generatePrivate =
                KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str)));
        Signature instance = Signature.getInstance("SHA256WithRSA");
        instance.initSign(generatePrivate);
        instance.update(bArr);
        return Base64.encode(instance.sign());
    }

    public static String a(String str, String str2, String str3) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        try {
            Key generatePublic =
                    KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str2)));
            Cipher instance = Cipher.getInstance("RSA/None/PKCS1Padding");
            instance.init(1, generatePublic);
            InputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes(str3));
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[128];
                while (true) {
                    int read = byteArrayInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byte[] bArr2;
                    if (128 == read) {
                        bArr2 = bArr;
                    } else {
                        bArr2 = new byte[read];
                        for (int i = 0; i < read; i++) {
                            bArr2[i] = bArr[i];
                        }
                    }
                    byteArrayOutputStream.write(instance.doFinal(bArr2));
                }
                String str4 = new String(Base64.encode(byteArrayOutputStream.toByteArray()));
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return str4;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            //            throw th;
            return null;
        }
        return null;
    }
}
