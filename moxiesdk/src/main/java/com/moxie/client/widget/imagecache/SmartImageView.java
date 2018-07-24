package com.moxie.client.widget.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.moxie.client.widget.imagecache.SmartImageTask.OnCompleteHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: TbsSdkJava */
public class SmartImageView extends ImageView {
    private static ExecutorService a = Executors.newFixedThreadPool(4);

    /* compiled from: TbsSdkJava */
    class AnonymousClass1 extends OnCompleteHandler {
         /* synthetic */ Integer a;
         /* synthetic */ SmartImageView b;

        public final void a(Bitmap bitmap) {
            if (bitmap != null) {
                this.b.setImageBitmap(bitmap);
            } else if (this.a != null) {
                this.b.setImageResource(this.a.intValue());
            }
        }
    }

    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SmartImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
