package com.moxie.client.tasks.task;

import android.text.TextUtils;
import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.fragment.BaseWebViewFragment;
import com.moxie.client.utils.ErrorHandle;
import com.moxie.client.utils.IOUtils;
import java.lang.ref.WeakReference;
import java.net.URL;

/* compiled from: TbsSdkJava */
public class LoadJsTask extends CommonAsyncTask<String, Void, String> {
    WeakReference<BaseWebViewFragment> c;
    private String d = "";
    private String e = "";
    private String f = "";

    protected final /* synthetic */ void onPostExecute(String obj) {
        super.onPostExecute(obj);
        if (this.c != null && this.c.get() != null) {
            ((BaseWebViewFragment) this.c.get()).a(this.d, obj, this.f);
        }
    }

    public LoadJsTask(BaseWebViewFragment baseWebViewFragment) {
        this.c = new WeakReference(baseWebViewFragment);
    }

    protected String doInBackground(String... strArr) {
        this.d = strArr[0];
        this.e = strArr[1];
        this.f = strArr[2];
        if (TextUtils.isEmpty(this.d) || TextUtils.isEmpty(this.e) || TextUtils.isEmpty(this.f)) {
            throw new RuntimeException("缺少必要的参数！！");
        }
        String str = "";
        try {
            if (!TextUtils.isEmpty(this.e)) {
                str = IOUtils.a(new URL(this.e).openStream());
                new StringBuilder("JSUrl=").append(this.e);
            }
        } catch (Throwable e) {
            ErrorHandle.b("LoadJsTask error", e);
        }
        return str;
    }
}
