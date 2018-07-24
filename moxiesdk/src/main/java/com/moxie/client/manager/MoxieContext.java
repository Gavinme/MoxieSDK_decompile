package com.moxie.client.manager;

import android.content.Context;
import android.view.View;
import com.moxie.client.MainActivity;
import com.proguard.annotation.NotProguard;
import java.lang.ref.WeakReference;

@NotProguard
/* compiled from: TbsSdkJava */
public class MoxieContext {
    private WeakReference<Context> mContextReference;

    public MoxieContext(Context context) {
        this.mContextReference = new WeakReference(context);
    }

    public Context getContext() {
        if (this.mContextReference == null) {
            return null;
        }
        return (Context) this.mContextReference.get();
    }

    public void finish() {
        if (this.mContextReference != null && this.mContextReference.get() != null && (this.mContextReference.get() instanceof MainActivity)) {
            ((MainActivity) this.mContextReference.get()).finish();
        }
    }

    public void addView(View view) {
        if (this.mContextReference != null && this.mContextReference.get() != null && (this.mContextReference.get() instanceof MainActivity)) {
            ((MainActivity) this.mContextReference.get()).addView(view);
        }
    }
}
