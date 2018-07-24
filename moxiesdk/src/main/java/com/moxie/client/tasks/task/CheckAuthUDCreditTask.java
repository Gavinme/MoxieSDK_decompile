package com.moxie.client.tasks.task;

import com.moxie.client.commom.CommonAsyncTask;
import com.moxie.client.fragment.FragmentEvent.CheckAuthUDCredit;
import com.moxie.client.restapi.CheckAuthUDCreditApi;
import com.moxie.client.utils.ErrorHandle;
import org.greenrobot.eventbus.EventBus;

/* compiled from: TbsSdkJava */
public class CheckAuthUDCreditTask extends CommonAsyncTask<Void, Void, String> {
    private String c;
    private String d;
    private String e;

    protected final /* synthetic */ String doInBackground(Void[] objArr) {
        return d();
    }

    protected final /* synthetic */ void onPostExecute(String obj) {
        obj = (String) obj;
        super.onPostExecute(obj);
        EventBus.getDefault().post(new CheckAuthUDCredit(obj));
    }

    public CheckAuthUDCreditTask(String str, String str2, String str3) {
        this.c = str;
        this.d = str2;
        this.e = str3;
    }

    private String d() {
        try {
            return CheckAuthUDCreditApi.excute(this.c, this.d, this.e);
        } catch (Throwable e) {
            ErrorHandle.b("CheckAuthUDCreditTask CheckAuthUDCreditApi error", e);
            return null;
        }
    }
}
