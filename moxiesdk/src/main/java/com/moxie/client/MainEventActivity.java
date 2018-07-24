package com.moxie.client;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.fragment.FragmentEvent.BackToClose;
import com.moxie.client.fragment.FragmentEvent.BackToFinish;
import com.moxie.client.fragment.FragmentEvent.CheckAuthUDCredit;
import com.moxie.client.fragment.FragmentEvent.NoPermission;
import com.moxie.client.fragment.FragmentEvent.OpenAgreement;
import com.moxie.client.fragment.FragmentEvent.OpenECWebView;
import com.moxie.client.fragment.FragmentEvent.OpenInsuranceWebView;
import com.moxie.client.fragment.FragmentEvent.OpenOfficialWebView;
import com.moxie.client.fragment.FragmentEvent.OpenOnlieBankWebView;
import com.moxie.client.fragment.FragmentEvent.OpenWebView;
import com.moxie.client.fragment.FragmentEvent.PermissionJson;
import com.moxie.client.fragment.FragmentEvent.RefreshStatus;
import com.moxie.client.fragment.FragmentEvent.RefreshTitle;
import com.moxie.client.fragment.FragmentEvent.SaveAccountInfo;
import com.moxie.client.fragment.FragmentEvent.SaveTaskId;
import com.moxie.client.fragment.FragmentEvent.ShowOrHiddenWebView;
import com.moxie.client.fragment.FragmentEvent.ViewH5ImortResult;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.tasks.event.CanLeaveEvent;
import com.moxie.client.tasks.event.OpenThirdPartEvent;
import com.moxie.client.tasks.event.ScreenCaptureEvent;
import com.moxie.client.tasks.event.SetResultEvent;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitErrorEvent;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitStart;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusFinishErrorEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusFinishSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusProgressEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeErrorEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusWorkingEvent;
import com.moxie.client.tasks.event.UploadFileEvent;
import com.moxie.client.tasks.task.ImportInputTask;
import com.moxie.client.tasks.task.ImportLoginTask;
import com.moxie.client.tasks.task.ImportStatusTask;
import com.moxie.client.utils.CommonMethod;
import com.proguard.annotation.NotProguard;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

@NotProguard
/* compiled from: TbsSdkJava */
public class MainEventActivity extends Activity {
    private static final String TAG = "MainEventActivity";
    protected ImportLoginTask importLoginTask = null;
    protected ImportStatusTask importStatusTask = null;

    /* compiled from: TbsSdkJava */
    static class Log {
        Log() {
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EventBus.getDefault().register(this);
    }

    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(SetResultEvent setResultEvent) {
    }

    @Subscribe
    public void onEventMainThread(TaskStatusProgressEvent taskStatusProgressEvent) {
    }

    @Subscribe
    public void onEventMainThread(ScreenCaptureEvent screenCaptureEvent) {
    }

    @Subscribe
    public void onEventMainThread(CanLeaveEvent canLeaveEvent) {
    }

    @Subscribe
    public void onEventMainThread(OpenThirdPartEvent openThirdPartEvent) {
    }

    @Subscribe
    public void onEventMainThread(PermissionJson permissionJson) {
    }

    @Subscribe
    public void onEventMainThread(CheckAuthUDCredit checkAuthUDCredit) {
    }

    @Subscribe
    public void onEventMainThread(NoPermission noPermission) {
    }

    @Subscribe
    public void onEventMainThread(SiteAccountInfo siteAccountInfo) {
        siteAccountInfo.b(Integer.valueOf(0));
    }

    @Subscribe
    public void onEventMainThread(LoginSubmitStart loginSubmitStart) {
        submitLogin(loginSubmitStart.c);
    }

    @Subscribe
    public void onEventMainThread(LoginSubmitErrorEvent loginSubmitErrorEvent) {
    }

    @Subscribe
    public void onEventMainThread(LoginSubmitSuccessEvent loginSubmitSuccessEvent) {
        if (loginSubmitSuccessEvent.c.r().intValue() == 0) {
            checkMailStatus(loginSubmitSuccessEvent.c);
        }
    }

    @Subscribe
    public void onEventMainThread(TaskStatusWorkingEvent taskStatusWorkingEvent) {
    }

    @Subscribe
    public void onEventMainThread(TaskStatusFinishErrorEvent taskStatusFinishErrorEvent) {
    }

    @Subscribe
    public void onEventMainThread(TaskStatusFinishSuccessEvent taskStatusFinishSuccessEvent) {
    }

    @Subscribe
    public void onEventMainThread(String str) {
    }

    @Subscribe
    public void onEventMainThread(HashMap<String, byte[]> hashMap) {
    }

    @Subscribe
    public void onEventMainThread(UploadFileEvent uploadFileEvent) {
    }

    @Subscribe
    public void onEventMainThread(TaskStatusVerifycodeEvent taskStatusVerifycodeEvent) {
    }

    @Subscribe
    public void onEventMainThread(List<JSONObject> list) {
    }

    @Subscribe
    public void onEventMainThread(TaskStatusVerifycodeSuccessEvent taskStatusVerifycodeSuccessEvent) {
        checkMailStatus(taskStatusVerifycodeSuccessEvent.c);
    }

    @Subscribe
    public void onEventMainThread(TaskStatusVerifycodeErrorEvent taskStatusVerifycodeErrorEvent) {
    }

    @Subscribe
    public void onEventMainThread(OpenWebView openWebView) {
    }

    @Subscribe
    public void onEventMainThread(OpenOnlieBankWebView openOnlieBankWebView) {
    }

    @Subscribe
    public void onEventMainThread(OpenInsuranceWebView openInsuranceWebView) {
    }

    @Subscribe
    public void onEventMainThread(OpenECWebView openECWebView) {
    }

    @Subscribe
    public void onEventMainThread(ViewH5ImortResult viewH5ImortResult) {
    }

    @Subscribe
    public void onEventMainThread(BackToClose backToClose) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BackToFinish backToFinish) {
    }

    @Subscribe
    public void onEventMainThread(SaveTaskId saveTaskId) {
    }

    @Subscribe
    public void onEventMainThread(SaveAccountInfo saveAccountInfo) {
    }

    @Subscribe
    public void onEventMainThread(RefreshStatus refreshStatus) {
    }

    @Subscribe
    public void onEventMainThread(RefreshTitle refreshTitle) {
    }

    @Subscribe
    public void onEventMainThread(OpenAgreement openAgreement) {
    }

    @Subscribe
    public void onEventMainThread(ShowOrHiddenWebView showOrHiddenWebView) {
    }

    @Subscribe
    public void onEventMainThread(OpenOfficialWebView openOfficialWebView) {
    }

    public void submitLogin(SiteAccountInfo siteAccountInfo) {
        if (TextUtils.isEmpty(siteAccountInfo.m())) {
            String[] split = siteAccountInfo.q().split(";");
            if (split != null) {
                for (String split2 : split) {
                    String[] split3 = split2.split("=");
                    if (split3 != null && split3.length == 2 && split3[0].trim().toLowerCase().equals("login_usernumber") && split3[1].trim().length() == 13) {
                        siteAccountInfo.l(split3[1].trim().substring(2) + "@139.com");
                    }
                }
            }
        }
        siteAccountInfo.i(GlobalParams.i().a().getUserId());
        siteAccountInfo.k(GlobalParams.i().a().getApiKey());
        siteAccountInfo.j("");
        siteAccountInfo.f("");
        siteAccountInfo.e("");
        this.importLoginTask = new ImportLoginTask(siteAccountInfo);
        this.importLoginTask.execute( new Void[0]);
    }

    public void checkMailStatus(SiteAccountInfo siteAccountInfo) {
        this.importStatusTask = new ImportStatusTask();
        this.importStatusTask.a(siteAccountInfo);
    }

    public void inputVerifyCode(SiteAccountInfo siteAccountInfo, String str, String str2) {
        new ImportInputTask(siteAccountInfo, str, str2).execute( new Void[0]);
    }

    protected void getSysConfigs() {
        new Thread(new Runnable() {
            final /* synthetic */ MainEventActivity a;

            {
                this.a = MainEventActivity.this;
            }

            public void run() {
                try {
                    GlobalParams.i().h(CommonMethod.e(this.a.getApplicationContext()));
                    GlobalParams.i().m(CommonMethod.a(this.a.getApplicationContext()));
                    GlobalParams.i().n(CommonMethod.b(this.a.getApplicationContext()));
                    GlobalParams.i().j(CommonMethod.b());
                    GlobalParams.i().i(CommonMethod.c(this.a.getApplicationContext()));
                    GlobalParams.i().k(CommonMethod.c());
                    GlobalParams.i().l(CommonMethod.g(this.a.getApplicationContext()));
                    GlobalParams.i().f(CommonMethod.i(this.a.getApplicationContext()));
                    GlobalParams.i().c(CommonMethod.h(this.a.getApplicationContext()));
                    String d = CommonMethod.d(this.a.getApplicationContext());
                    if (!d.equalsIgnoreCase("") && d.contains(",")) {
                        GlobalParams.i().e(d.split(",")[0]);
                        GlobalParams.i().d(d.split(",")[1]);
                    }
                    Location j = CommonMethod.j(this.a.getApplicationContext());
                    if (j != null) {
                        GlobalParams.i().b(String.valueOf(j.getLatitude()));
                        GlobalParams.i().a(String.valueOf(j.getLongitude()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void stopTask() {
        try {
            if (this.importLoginTask != null) {
                this.importLoginTask.d();
            }
            if (this.importStatusTask != null) {
                this.importStatusTask.a();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
