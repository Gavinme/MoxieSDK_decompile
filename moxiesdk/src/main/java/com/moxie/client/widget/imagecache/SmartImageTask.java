package com.moxie.client.widget.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

/* compiled from: TbsSdkJava */
public class SmartImageTask implements Runnable {
    private boolean a;
    private OnCompleteHandler b;
    private SmartImage c;
    private Context d;

    /* compiled from: TbsSdkJava */
    public static class OnCompleteHandler extends Handler {
        public void handleMessage(Message message) {
            a((Bitmap) message.obj);
        }

        public void a(Bitmap bitmap) {
        }
    }

    /* compiled from: TbsSdkJava */
    public static abstract class OnCompleteListener {
    }

    public void run() {
        if (this.c != null) {
            Bitmap a = this.c.a(this.d);
            if (!(this.b == null || this.a)) {
                this.b.sendMessage(this.b.obtainMessage(0, a));
            }
            this.d = null;
        }
    }
}
