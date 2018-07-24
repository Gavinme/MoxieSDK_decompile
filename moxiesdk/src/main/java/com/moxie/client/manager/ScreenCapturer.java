package com.moxie.client.manager;

import static android.content.Context.MEDIA_PROJECTION_SERVICE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.nio.Buffer;
import java.util.Date;

import com.moxie.client.utils.ErrorHandle;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.os.AsyncTaskCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.Toast;

@TargetApi(21)
/* compiled from: TbsSdkJava */
public class ScreenCapturer {
    private WeakReference<Activity> a;
    private MediaProjection b;
    private VirtualDisplay c;
    private ImageReader d;

    /* compiled from: TbsSdkJava */
    public class SaveTask extends AsyncTask<Image, Void, String> {
        final /* synthetic */ ScreenCapturer a;

        public SaveTask(ScreenCapturer screenCapturer) {
            this.a = screenCapturer;
        }

        @TargetApi(19)
        protected /* synthetic */ String doInBackground(Image[] objArr) {
            return a((Image[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(String obj) {
            String str = (String) obj;
            super.onPostExecute(str);
            if (this.a.c != null) {
                this.a.c.release();
                this.a.c = null;
            }
            if (this.a.b != null) {
                this.a.b.stop();
                this.a.b = null;
            }
            if (TextUtils.isEmpty(str)) {
                Toast.makeText(this.a.b(), "截屏失败，请尝试系统截屏功能", Toast.LENGTH_SHORT).show();
                return;
            }
            MediaScannerConnection.scanFile(this.a.b(), new String[] {str}, null, new OnScanCompletedListener() {
                //                final /* synthetic */ SaveTask encode;

                public void onScanCompleted(String str, Uri uri) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {


                        public void run() {
                            Toast.makeText(ScreenCapturer.this.a.get(), "截屏成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        @TargetApi(19)
        private String a(Image... imageArr) {
            if (imageArr == null || imageArr.length <= 0 || imageArr[0] == null) {
                return null;
            }
            File file;
            Image image = imageArr[0];
            int width = image.getWidth();
            int height = image.getHeight();
            Plane[] planes = image.getPlanes();
            Buffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            Bitmap createBitmap =
                    Bitmap.createBitmap(((planes[0].getRowStride() - (pixelStride * width)) / pixelStride) + width,
                            height, Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(buffer);
            Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, width, height);
            image.close();
            if (createBitmap2 != null) {
                try {
                    Date date = new Date();
                    DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
                    String str = date + ".jpg";
                    File file2 = new File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Camera/");
                    file2.mkdirs();
                    file = new File(file2, str);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    OutputStream fileOutputStream = new FileOutputStream(file);
                    createBitmap2.compress(CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Throwable e) {
                    ErrorHandle.b("截屏失败", e);
                    file = null;
                }
            } else {
                file = null;
            }
            if (!(createBitmap2 == null || createBitmap2.isRecycled())) {
                createBitmap2.recycle();
            }
            if (file != null) {
                return file.getAbsolutePath();
            }
            return null;
        }
    }

    public ScreenCapturer(Activity activity, Intent intent) {
        this.a = new WeakReference(activity);
        if (VERSION.SDK_INT >= 21) {
            this.b = ((MediaProjectionManager) b().getSystemService(MEDIA_PROJECTION_SERVICE)).getMediaProjection(-1, intent);
            this.d = ImageReader.newInstance(c(), d(), 1, 1);
        }
    }

    private Context b() {
        return (Context) this.a.get();
    }

    private static int c() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private static int d() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public final void a() {
        this.c = this.b.createVirtualDisplay("screen-mirror", c(), d(),
                Resources.getSystem().getDisplayMetrics().densityDpi, 16, this.d.getSurface(), null, null);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                Image acquireLatestImage = ScreenCapturer.this.d.acquireLatestImage();
                AsyncTaskCompat.executeParallel(new SaveTask(ScreenCapturer.this), new Image[] {acquireLatestImage});
            }
        }, 300);
    }
}
