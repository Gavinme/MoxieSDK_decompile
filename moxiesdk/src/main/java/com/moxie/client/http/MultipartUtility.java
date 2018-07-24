package com.moxie.client.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/* compiled from: TbsSdkJava */
public class MultipartUtility {
    private final String a = ("===" + System.currentTimeMillis() + "===");
    private HttpURLConnection b;
    private String c;
    private OutputStream d;
    private PrintWriter e;

    public MultipartUtility(String str, String str2, HashMap<String, String> hashMap) throws IOException {
        this.c = str2;
        this.b = (HttpURLConnection) new URL(str).openConnection();
        this.b.setUseCaches(false);
        this.b.setDoOutput(true);
        this.b.setDoInput(true);
        if (hashMap != null) {
            for (String str3 : hashMap.keySet()) {
                this.b.setRequestProperty(str3, (String) hashMap.get(str3));
            }
        }
        this.b.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + this.a);
        this.d = this.b.getOutputStream();
        this.e = new PrintWriter(new OutputStreamWriter(this.d, str2), true);
    }

    public final void a(String str, File file) throws IOException {
        String name = file.getName();
        this.e.append("--" + this.a).append("\r\n");
        this.e.append("Content-Disposition: form-data; name=\"" + str + "\"; filename=\"" + name + "\"").append("\r\n");
        this.e.append("Content-Type: " + URLConnection.guessContentTypeFromName(name)).append("\r\n");
        this.e.append("Content-Transfer-Encoding: binary").append("\r\n");
        this.e.append("\r\n");
        this.e.flush();
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bArr = new byte[4096];
        while (true) {
            int read = fileInputStream.read(bArr);
            if (read != -1) {
                this.d.write(bArr, 0, read);
            } else {
                this.d.flush();
                fileInputStream.close();
                this.e.append("\r\n");
                this.e.flush();
                return;
            }
        }
    }

    public final void a(String str, byte[] bArr) throws IOException {
        this.e.append("--" + this.a).append("\r\n");
        this.e.append("Content-Disposition: form-data; name=\"" + str + "\";").append("\r\n");
        this.e.append("Content-Type: " + URLConnection.guessContentTypeFromName(str)).append("\r\n");
        this.e.append("Content-Transfer-Encoding: binary").append("\r\n");
        this.e.append("\r\n");
        this.e.flush();
        this.d.write(bArr, 0, bArr.length);
        this.d.flush();
        this.e.append("\r\n");
        this.e.flush();
    }

    public final boolean a() throws IOException {
        try {
            this.e.append("\r\n").flush();
            this.e.append("--" + this.a + "--").append("\r\n");
            this.e.close();
            if (this.b.getResponseCode() == 200) {
                this.b.disconnect();
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
