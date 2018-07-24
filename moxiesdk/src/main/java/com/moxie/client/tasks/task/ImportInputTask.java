package com.moxie.client.tasks.task;

import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.restapi.ImportCrawlApi;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeErrorEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeSuccessEvent;
import com.moxie.client.utils.ErrorHandle;
import org.greenrobot.eventbus.EventBus;

/* compiled from: TbsSdkJava */
public class ImportInputTask extends CommonAsyncTask<Void, Void, Boolean> {
    private SiteAccountInfo c;
    private String d;
    private String e;

    protected final /* synthetic */ Boolean doInBackground(Void[] objArr) {
        return d();
    }

    protected final /* synthetic */ void onPostExecute(Boolean obj) {
        obj = (Boolean) obj;
        super.onPostExecute(obj);
        if (obj.booleanValue()) {
            EventBus.getDefault().post(new TaskStatusVerifycodeSuccessEvent(0, "提交成功", this.c, null));
        } else {
            EventBus.getDefault().post(new TaskStatusVerifycodeErrorEvent("提交失败", this.c));
        }
    }

    public ImportInputTask(SiteAccountInfo siteAccountInfo, String str, String str2) {
        this.c = siteAccountInfo;
        this.d = str;
        this.e = str2;
    }

    private Boolean d() {
        try {
            return ImportCrawlApi.a(this.c, this.d, this.e);
        } catch (Throwable e) {
            ErrorHandle.b("ImportInputTask inputVerifyCode error", e);
            return null;
        }
    }
}
