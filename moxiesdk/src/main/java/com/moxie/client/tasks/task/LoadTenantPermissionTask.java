package com.moxie.client.tasks.task;

import org.greenrobot.eventbus.EventBus;

import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.fragment.FragmentEvent.PermissionJson;
import com.moxie.client.restapi.LoadTenantPermissionApi;
import com.moxie.client.utils.ErrorHandle;

/* compiled from: TbsSdkJava */
public class LoadTenantPermissionTask extends CommonAsyncTask<Void, Void, String> {
    private String c;

    @Override
    protected String doInBackground(Void... paramsArr) {
        return d();
    }

    protected final /* synthetic */ void onPostExecute(String obj) {
        super.onPostExecute(obj);
        EventBus.getDefault().post(new PermissionJson(obj));
    }

    public LoadTenantPermissionTask(String str) {
        this.c = str;
    }

    private String d() {
        try {
            return LoadTenantPermissionApi.getTenantPermissonInfo(this.c);
        } catch (Throwable e) {
            ErrorHandle.b("LoadTenantPermissionTask getTenantPermissonInfo error", e);
            return null;
        }
    }
}
