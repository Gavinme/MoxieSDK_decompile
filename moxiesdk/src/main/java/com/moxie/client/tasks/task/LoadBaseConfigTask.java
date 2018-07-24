package com.moxie.client.tasks.task;

import android.app.Application;
import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.restapi.LoadBaseConfigApi;
import com.moxie.client.tasks.model.BaseConfig;
import com.moxie.client.utils.ErrorHandle;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.QbSdk.PreInitCallback;

/* compiled from: TbsSdkJava */
public class LoadBaseConfigTask extends CommonAsyncTask<Void, BaseConfig, BaseConfig> {
    private Application c;

    protected final /* synthetic */ BaseConfig doInBackground(Void[] objArr) {
        return d();
    }

    protected final /* synthetic */ void onPostExecute(BaseConfig obj) {
        obj = (BaseConfig) obj;
        super.onPostExecute(obj);
        if (obj != null && obj.a) {
            QbSdk.initX5Environment(this.c.getApplicationContext(), new PreInitCallback() {
                final /* synthetic */ LoadBaseConfigTask a;

                {
                    this.a = LoadBaseConfigTask.this;
                }

                public void onViewInitFinished(boolean z) {
                }

                public void onCoreInitFinished() {
                }
            });
        }
    }

    public LoadBaseConfigTask(Application application) {
        this.c = application;
    }

    private static BaseConfig d() {
        try {
            return LoadBaseConfigApi.a();
        } catch (Throwable e) {
            ErrorHandle.b("LoadBaseConfigTask", e);
            return null;
        }
    }
}
