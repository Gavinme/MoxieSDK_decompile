package com.moxie.client.widget.imagecache;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.Contacts;
import java.io.InputStream;

/* compiled from: TbsSdkJava */
public class ContactImage implements SmartImage {
    private long a;

    public final Bitmap a(Context context) {
        Bitmap bitmap = null;
        try {
            InputStream openContactPhotoInputStream = Contacts.openContactPhotoInputStream(context.getContentResolver(), ContentUris.withAppendedId(Contacts.CONTENT_URI, this.a));
            if (openContactPhotoInputStream != null) {
                bitmap = BitmapFactory.decodeStream(openContactPhotoInputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
