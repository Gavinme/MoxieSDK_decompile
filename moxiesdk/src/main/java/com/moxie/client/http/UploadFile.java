package com.moxie.client.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import com.moxie.client.commom.GlobalConstants;
import com.moxie.client.file.FileConstant;
import com.moxie.client.utils.AESCBC;
import com.moxie.client.utils.AESECB;
import com.moxie.client.utils.Base64;
import com.moxie.client.utils.ErrorHandle;
import com.moxie.client.utils.IOUtils;
import com.moxie.client.utils.RsaUtil;

import android.content.Context;
import android.text.TextUtils;

/* compiled from: TbsSdkJava */
public class UploadFile {
    public String a = "UTF-8";

    public final boolean a(Context context, String str, String str2, HashMap<String, String> hashMap, boolean z) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        //        Closeable closeable;
        Exception e;
        Throwable th;
        FileInputStream closeable2 = null;
        boolean z2 = false;
        try {
            MultipartUtility multipartUtility = new MultipartUtility(str2, this.a, hashMap);
            File a = FileConstant.a(context, str);
            int i;
            if (z) {
                if (a != null) {
                    try {
                        if (a.isDirectory()) {
                            File[] listFiles = a.listFiles();
                            byte[] bArr = new byte[4096];
                            byteArrayOutputStream = new ByteArrayOutputStream();
                            i = 0;
                            //                            closeable = null;
                            while (i < listFiles.length) {
                                try {
                                    closeable2 = new FileInputStream(listFiles[i]);
                                    while (true) {
                                        try {
                                            int read = closeable2.read(bArr);
                                            if (read == -1) {
                                                break;
                                            }
                                            byteArrayOutputStream.write(bArr, 0, read);
                                        } catch (Exception e2) {
                                            e = e2;
                                            //                                            closeable =
                                            // byteArrayOutputStream;
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    }
                                    i++;
                                    //                                    closeable = closeable2;
                                } catch (Exception e3) {
                                    e = e3;
                                    //                                    closeable2 = closeable;
                                    //                                    closeable = byteArrayOutputStream;
                                } catch (Throwable th3) {
                                    th = th3;
                                    //                                    closeable2 = closeable;
                                }
                            }
                            byteArrayOutputStream.flush();
                            a(byteArrayOutputStream.toByteArray(), multipartUtility);
                            //                            closeable2 = closeable;
                            IOUtils.close(byteArrayOutputStream);
                            IOUtils.close(closeable2);
                        }
                    } catch (Exception e4) {
                        e = e4;
                        //                        closeable = null;
                        try {
                            e.printStackTrace();
                            //                            IOUtils.close(closeable);
                            //                            IOUtils.encode(closeable2);
                            FileConstant.b(context, str);
                            z2 = multipartUtility.a();
                            return z2;
                        } catch (Throwable th4) {
                            th = th4;
                            //                            byteArrayOutputStream = closeable;
                            IOUtils.close(byteArrayOutputStream);
                            IOUtils.close(closeable2);
                            //                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        byteArrayOutputStream = null;
                        IOUtils.close(byteArrayOutputStream);
                        IOUtils.close(closeable2);
                        //                        throw th;
                    }
                }
                byteArrayOutputStream = null;
                IOUtils.close(byteArrayOutputStream);
                IOUtils.close(closeable2);
            } else if (a != null && a.isDirectory()) {
                File[] listFiles2 = a.listFiles();
                for (i = 0; i < listFiles2.length; i++) {
                    multipartUtility.a(listFiles2[i].getName(), listFiles2[i]);
                }
            }
            FileConstant.b(context, str);
            z2 = multipartUtility.a();
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        return z2;
    }

    public final boolean a(HashMap<String, byte[]> hashMap, String urlString, HashMap<String, String> hashMap2, boolean iswifi) {
        Throwable th;
        Exception e;
        try {
            MultipartUtility multipartUtility = new MultipartUtility(urlString, this.a, hashMap2);
            if (iswifi) {
                ByteArrayOutputStream closeable = null;
                if (hashMap != null) {
                    ByteArrayOutputStream byteArrayOutputStream = null;
                    try {
                        byteArrayOutputStream = new ByteArrayOutputStream();
                    } catch (Throwable th2) {

                        IOUtils.close(byteArrayOutputStream);
                        byteArrayOutputStream = null;
                    }
                    try {
                        for (String str2 : hashMap.keySet()) {
                            byteArrayOutputStream
                                    .write(hashMap.get(str2), 0, hashMap.get(str2).length);
                        }
                        closeable = byteArrayOutputStream;
                    } catch (Exception e2) {
                        e = e2;
                        closeable = byteArrayOutputStream;
                        try {
                            e.printStackTrace();
                            IOUtils.close(closeable);
                            return multipartUtility.a();
                        } catch (Throwable th3) {
                            th = th3;
                            byteArrayOutputStream = closeable;
                            IOUtils.close(byteArrayOutputStream);
                            //                            throw th;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        IOUtils.close(byteArrayOutputStream);
                        //                        throw th;
                    }
                }
                if (closeable != null) {
                    try {
                        a(closeable.toByteArray(), multipartUtility);
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        IOUtils.close(closeable);
                        return multipartUtility.a();
                    }
                }
                IOUtils.close(closeable);
            } else if (hashMap != null) {
                for (String str3 : hashMap.keySet()) {
                    multipartUtility.a(str3, (byte[]) hashMap.get(str3));
                }
            }
            return multipartUtility.a();
        } catch (Exception e4) {
            e4.printStackTrace();
            return false;
        }
    }

    private void a(byte[] bArr, MultipartUtility multipartUtility) {
        try {
            String a = RsaUtil.a(bArr, GlobalConstants.e);
            String a2 = AESECB.a();
            String a3 =
                    new AESCBC(Base64.encode(a2.getBytes())).a(Base64.encode(bArr) + "." + Base64.encode(a.getBytes()));
            String a4 = RsaUtil.a(Base64.encode(a2.getBytes()), GlobalConstants.b, this.a);
            if (!TextUtils.isEmpty(a3) && !TextUtils.isEmpty(a4)) {
                multipartUtility.a("k", a4.getBytes());
                multipartUtility.a("c", a3.getBytes());
            }
        } catch (Throwable e) {
            ErrorHandle.a("UploadFile", e);
        }
    }
}
