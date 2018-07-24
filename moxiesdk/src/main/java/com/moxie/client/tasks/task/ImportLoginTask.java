package com.moxie.client.tasks.task;

import android.text.TextUtils;
import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.model.TaskCreateResponse;
import com.moxie.client.restapi.ImportCrawlApi;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitErrorEvent;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitSuccessEvent;
import com.moxie.client.utils.ErrorHandle;
import java.util.Date;
import org.greenrobot.eventbus.EventBus;

/* compiled from: TbsSdkJava */
public class ImportLoginTask extends CommonAsyncTask<Void, Void, TaskCreateResponse> {
    private SiteAccountInfo c;
    private boolean d;

    protected final /* synthetic */ TaskCreateResponse doInBackground(Void[] objArr) {
        return e();
    }

    protected final /* synthetic */ void onPostExecute(TaskCreateResponse obj) {
        obj = (TaskCreateResponse) obj;
        super.onPostExecute(obj);
        if (obj != null) {
            SiteAccountInfo siteAccountInfo = this.c;
            siteAccountInfo.o(obj.b());
            siteAccountInfo.g(obj.c());
            siteAccountInfo.h(String.valueOf(new Date().getTime() / 1000));
            siteAccountInfo.b(obj.a());
            EventBus.getDefault().post(new LoginSubmitSuccessEvent("登录成功", siteAccountInfo));
        }
    }

    public ImportLoginTask(SiteAccountInfo siteAccountInfo) {
        this.c = siteAccountInfo;
    }

    private TaskCreateResponse e() {
        Object obj = 1;
        try {
            SiteAccountInfo siteAccountInfo = this.c;
            if (siteAccountInfo == null || TextUtils.isEmpty(siteAccountInfo.m())) {
                obj = null;
            }
            if (obj == null) {
                EventBus.getDefault().post(new LoginSubmitErrorEvent("提交登录失败!", this.c));
                return null;
            }
            TaskCreateResponse a = ImportCrawlApi.a(this.c);
            if (a == null) {
                EventBus.getDefault().post(new LoginSubmitErrorEvent("提交登录失败!", this.c));
                return null;
            }
            if (!TextUtils.isEmpty(this.c.q())) {
                this.c.a(Integer.valueOf(1));
            }
            if (!TextUtils.isEmpty(this.c.p())) {
                this.c.o(a.b());
            }
            if (!TextUtils.isEmpty(this.c.j())) {
                this.c.g(a.c());
            }
            return a;
        } catch (Throwable e) {
            ErrorHandle.b("ImportLoginTask submitLoging error", e);
            return null;
        }
    }

    public final void d() {
        this.d = true;
    }
}
