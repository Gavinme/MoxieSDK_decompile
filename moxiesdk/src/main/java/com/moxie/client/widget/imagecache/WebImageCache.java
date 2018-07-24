package com.moxie.client.widget.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: TbsSdkJava */
public class WebImageCache {
    private ConcurrentHashMap<String, SoftReference<Bitmap>> a = new ConcurrentHashMap();
    private String b;
    private boolean c = false;
    private ExecutorService d;

    public WebImageCache(Context context) {
        this.b = context.getApplicationContext().getCacheDir().getAbsolutePath() + "/web_image_cache/";
        File file = new File(this.b);
        file.mkdirs();
        this.c = file.exists();
        this.d = Executors.newSingleThreadExecutor();
    }

    public final Bitmap a(String str) {
        Bitmap bitmap;
        SoftReference softReference = (SoftReference) this.a.get(c(str));
        if (softReference != null) {
            bitmap = (Bitmap) softReference.get();
        } else {
            bitmap = null;
        }
        if (bitmap == null) {
            if (this.c) {
                String str2 = this.b + c(str);
                if (new File(str2).exists()) {
                    bitmap = BitmapFactory.decodeFile(str2);
                    if (bitmap != null) {
                        b(str, bitmap);
                    }
                }
            }
            bitmap = null;
            if (bitmap != null) {
                b(str, bitmap);
            }
        }
        return bitmap;
    }

    public final void a(final String str, final Bitmap bitmap)   {
        b(str, bitmap);
        this.d.execute(new Runnable() {
            final /* synthetic */ WebImageCache c=WebImageCache.this;

            public void run() {
                FileNotFoundException e;
                Throwable th;
                if (this.c.c) {
                    BufferedOutputStream bufferedOutputStream = null;
                    BufferedOutputStream bufferedOutputStream2;
                    try {
                        bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(new File(this.c.b, WebImageCache.c(str))), 2048);

                        bitmap.compress(CompressFormat.PNG, 100, bufferedOutputStream2);
                        try {
                            bufferedOutputStream2.flush();
                            bufferedOutputStream2.close();
                        } catch (IOException e2) {
                        }
                    } catch (FileNotFoundException e6) {
                        e = e6;
                        bufferedOutputStream2 = null;
                        e.printStackTrace();
                        if (bufferedOutputStream2 != null) {
//                            bufferedOutputStream2.flush();
//                            bufferedOutputStream2.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (bufferedOutputStream != null) {
//                            bufferedOutputStream.flush();
//                            bufferedOutputStream.close();
                        }
                    }
                }
            }
        });
    }

    private void b(String str, Bitmap bitmap) {
        this.a.put(c(str), new SoftReference(bitmap));
    }

    private static String c(String str) {
        if (str != null) {
            return str.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
        }
        throw new RuntimeException("Null url passed in");
    }
}
