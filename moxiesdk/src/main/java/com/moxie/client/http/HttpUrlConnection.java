package com.moxie.client.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.utils.IOUtils;
import com.tencent.smtt.sdk.TbsListener.ErrorCode;

import android.net.Uri;
import android.text.TextUtils;

/* compiled from: TbsSdkJava */
public class HttpUrlConnection {
    private static HttpUrlConnection instance;
    protected static final String CHARSET = "UTF-8";

    public static HttpUrlConnection getinstance() {
        if (instance == null) {
            synchronized(HttpUrlConnection.class) {
                if (instance == null) {
                    instance = new HttpUrlConnection();
                }
            }
        }
        return instance;
    }

    private HttpUrlConnection() {
        HttpsURLConnection.setFollowRedirects(true);
    }

    private static HttpURLConnection createConnection(String str) throws IOException {
        HttpURLConnection httpURLConnection =
                (HttpURLConnection) new URL(Uri.encode(str, "@#&=*+-_.,:!?()/~'%")).openConnection();
        httpURLConnection.setConnectTimeout(6000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestProperty("User-agent",
                "MoxieSDKAndroid/" + GlobalParams.i().h() + " " + System.getProperty("http.agent"));
        return httpURLConnection;
    }

    private static InputStream getInputStream(String str, HashMap<String, String> hashMap) {
        while (true) {
            try {
                HttpURLConnection a = createConnection(str);
                a.setRequestMethod("GET");
                a.setInstanceFollowRedirects(false);
                if (hashMap != null) {
                    for (String str2 : hashMap.keySet()) {
                        a.setRequestProperty(str2, (String) hashMap.get(str2));
                    }
                }
                if (a.getResponseCode() != ErrorCode.ERROR_UNMATCH_TBSCORE_VER_THIRDPARTY
                        && a.getResponseCode() != 301) {
                    return a.getInputStream();
                }
                str = a.getHeaderField("Location");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static String getString(String url, HashMap<String, String> hashMap) {
        String result = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = getInputStream(url, hashMap);
            br = new BufferedReader(new InputStreamReader(is, CHARSET));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }

        return result;
    }

    public static String postString(String url, String params, HashMap<String, String> hashMap) {
        String result = null;
        OutputStream os = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            HttpURLConnection conn = createConnection(url);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);// POST方式不能缓存数据
            // conn.setRequestProperty(field, newValue);//header

            conn.setRequestProperty("Content-Type", "application/json; CHARSET=" + CHARSET);

            // // 设置请求的头
            // conn.setRequestProperty("Connection", "keep-alive");
            // // 设置请求的头
            // conn.setRequestProperty("User-Agent",
            // "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0");

            if (hashMap != null) {
                Iterator it = hashMap.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    conn.setRequestProperty(key, hashMap.get(key));
                }
            }

            if (params != null) {
                os = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.write(params.getBytes(CHARSET));
                dos.flush();
                dos.close();
            }

            is = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, CHARSET));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException ignored) {
            }
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ignored) {
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ignored) {
            }
        }

        return result;
    }

    public static String getStringByCode(String url, HashMap<String, String> hashMap, String charset) throws Throwable {
        String result = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = getInputStream(url, hashMap);
            if (TextUtils.isEmpty(charset)) {
                charset = CHARSET;
            }
            br = new BufferedReader(new InputStreamReader(is, charset));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }

        return result;
    }

    public static byte[] getPostStringByCode(String str, String str2, HashMap<String, String> hashMap, int code) throws
            Throwable {
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        Exception e;
        Throwable th;
        Closeable closeable = null;
        try {
            HttpURLConnection a = createConnection(str);
            a.setRequestMethod("POST");
            a.setDoOutput(true);
            a.setDoInput(true);
            a.setUseCaches(false);
            if (code >= ErrorCode.ERROR_CODE_LOAD_BASE && code < ErrorCode.INFO_CODE_BASE) {
                a.setInstanceFollowRedirects(false);
            }
            if (hashMap != null) {
                for (String str3 : hashMap.keySet()) {
                    a.setRequestProperty(str3, (String) hashMap.get(str3));
                }
            }
            if (str2 != null) {
                DataOutputStream dataOutputStream = new DataOutputStream(a.getOutputStream());
                dataOutputStream.write(str2.getBytes("UTF-8"));
                dataOutputStream.flush();
                dataOutputStream.close();
            }
            int responseCode = a.getResponseCode();
            if (responseCode == 200 || responseCode == ErrorCode.EXCEED_UNZIP_RETRY_NUM) {
                inputStream = a.getInputStream();
                if (inputStream == null) {
                    IOUtils.a(null);
                    IOUtils.a(inputStream);
                    return null;
                }
                Map<String, List<String>> headerFields;
                byte[] bArr;
                try {
                    headerFields = a.getHeaderFields();
                    bArr = new byte[8192];
                    byteArrayOutputStream = new ByteArrayOutputStream();
                } catch (Exception e2) {
                    e = e2;
                    byteArrayOutputStream = null;
                    try {
                        e.printStackTrace();
                        IOUtils.close(byteArrayOutputStream);
                        IOUtils.close(inputStream);
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        closeable = byteArrayOutputStream;
                        IOUtils.close(closeable);
                        IOUtils.close(inputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    IOUtils.close(closeable);
                    IOUtils.close(inputStream);
                    throw th;
                }
                try {
                    if (headerFields.containsKey("content-encoding")) {
                        boolean obj = false;
                        for (String str32 : headerFields.get("content-encoding")) {
                            if ("gzip".equalsIgnoreCase(str32)) {
                                obj = true;
                                break;
                            }
                        }
                        if (obj) {
                            inputStream = new GZIPInputStream(inputStream);
                        }
                    }
                    while (true) {
                        responseCode = inputStream.read(bArr);
                        if (responseCode == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, responseCode);
                    }
                    byte[] toByteArray = byteArrayOutputStream.toByteArray();
                    IOUtils.close(byteArrayOutputStream);
                    IOUtils.close(inputStream);
                    return toByteArray;
                } catch (Exception e3) {
                    e = e3;
                    e.printStackTrace();
                    IOUtils.close(byteArrayOutputStream);
                    IOUtils.close(inputStream);
                    return null;
                }
            } else if (responseCode >= code) {
                IOUtils.a(null);
                IOUtils.a(null);
                return null;
            } else {
                IOUtils.a(null);
                IOUtils.a(null);
                return null;
            }
        } catch (Exception e4) {
            e = e4;
            byteArrayOutputStream = null;
            inputStream = null;
            e.printStackTrace();
            IOUtils.close(byteArrayOutputStream);
            IOUtils.close(inputStream);
            return null;
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            IOUtils.close(closeable);
            IOUtils.close(inputStream);
            throw th;
        }
    }
}
