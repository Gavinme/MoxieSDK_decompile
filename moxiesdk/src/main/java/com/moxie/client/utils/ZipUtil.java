package com.moxie.client.utils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: TbsSdkJava */
public class ZipUtil {
    /**
     * zip包压缩
     * @param hashMap
     * @param str
     * @return
     * @throws IOException
     */
    public static boolean a(HashMap<String, byte[]> hashMap, String str) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(str)));
        if (hashMap != null) {
            try {
                for (String str2 : hashMap.keySet()) {
                    byte[] bArr = (byte[]) hashMap.get(str2);
                    zipOutputStream.putNextEntry(new ZipEntry(str2));
                    zipOutputStream.write(bArr, 0, bArr.length);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                zipOutputStream.close();
            }
        }
        zipOutputStream.close();
        return true;
    }
}
