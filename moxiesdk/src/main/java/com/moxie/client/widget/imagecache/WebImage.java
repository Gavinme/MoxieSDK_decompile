package com.moxie.client.widget.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/* compiled from: TbsSdkJava */
public class WebImage implements SmartImage {
    private static WebImageCache a;
    private String b;

    public final Bitmap a(Context context) {
        if (a == null) {
            a = new WebImageCache(context);
        }
        Bitmap bitmap = null;
        if (this.b != null) {
            bitmap = a.a(this.b);
            if (bitmap == null) {
                bitmap = a(this.b);
                if (bitmap != null) {
                    a.a(this.b, bitmap);
                }
            }
        }
        return bitmap;
    }

    private static Bitmap a(String str) {
        try {
            URLConnection openConnection = new URL(str).openConnection();
            openConnection.setConnectTimeout(ReaderCallback.GET_BAR_ANIMATING);
            openConnection.setReadTimeout(10000);
            return BitmapFactory.decodeStream((InputStream) openConnection.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
