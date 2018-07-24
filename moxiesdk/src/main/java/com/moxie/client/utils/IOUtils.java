package com.moxie.client.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* compiled from: TbsSdkJava */
public class IOUtils {
    public static String a(InputStream inputStream) throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        BufferedReader bufferedReader2 = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        stringBuilder.append(readLine);
                    } else {
                        break;
                    }
                } catch (IOException e2) {
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    bufferedReader2 = bufferedReader;
                    th = th3;
                }
            }
            bufferedReader.close();
        } catch (IOException e3) {
            bufferedReader = null;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e4) {
                }
            }
            return stringBuilder.toString();
        } catch (Throwable th4) {
            th = th4;
            if (bufferedReader2 != null) {
                try {
                    bufferedReader2.close();
                } catch (IOException e5) {
                }
            }
            throw th;
        }
        return stringBuilder.toString();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                ErrorHandle.a("Closeable", e);
            }
        }
    }
}
