package com.moxie.client.tasks.task;

import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.restapi.LoadSiteConfigApi;
import com.moxie.client.utils.ErrorHandle;

/* compiled from: TbsSdkJava */
public class LoadSiteConfigTask extends CommonAsyncTask<Void, Void, Void> {
    private String c = "";

    protected final /* synthetic */ Void doInBackground(Void[] objArr) {
        return d();
    }

    public LoadSiteConfigTask(String str) {
        this.c = str;
    }

    private Void d() {
        try {
            LoadSiteConfigApi.a(this.c);
        } catch (Throwable e) {
            ErrorHandle.b("LoadSiteConfigTask loadSiteConfig error", e);
        }
        return null;
    }
}
