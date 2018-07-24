package com.moxie.client.tasks.task;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.greenrobot.eventbus.EventBus;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.model.TaskStatusDescription;
import com.moxie.client.model.TaskStatusResult;
import com.moxie.client.restapi.ImportCrawlApi;
import com.moxie.client.tasks.event.CanLeaveEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusFinishErrorEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusFinishSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusWorkingEvent;
import com.moxie.client.utils.ErrorHandle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/* compiled from: TbsSdkJava */
public class ImportStatusTask {
    private SiteAccountInfo a;
    private String b;
    private String c;
    private String d;
    private int e;
    private long f;
    private boolean g;
    private boolean h;
    private Timer i;
    private TimerTask j;
    private MyHandler k = new MyHandler();

    /* compiled from: TbsSdkJava */
    private class MailStatusHanderResult {
        SiteAccountInfo a;
        TaskStatusResult b;
        final /* synthetic */ ImportStatusTask c;

        public MailStatusHanderResult(ImportStatusTask importStatusTask, SiteAccountInfo siteAccountInfo,
                                      TaskStatusResult taskStatusResult) {
            this.c = importStatusTask;
            this.a = siteAccountInfo;
            this.b = taskStatusResult;
        }
    }

    /* compiled from: TbsSdkJava */
    private static class MyHandler extends Handler {
        public void handleMessage(Message message) {
            int i = message.what;
            MailStatusHanderResult mailStatusHanderResult = (MailStatusHanderResult) message.obj;
            TaskStatusResult taskStatusResult = mailStatusHanderResult.b;
            SiteAccountInfo siteAccountInfo = mailStatusHanderResult.a;
            if (i == 1) {
                EventBus.getDefault()
                        .post(new TaskStatusWorkingEvent(new TaskStatusDescription(taskStatusResult.e(), null),
                                mailStatusHanderResult.a, taskStatusResult));
            } else if (i == 2) {
                Bundle data = message.getData();
                if (data != null) {
                    String string = data.getString(TaskStatus.MESSAGE);
                    int i2 = data.getInt("code");
                    boolean z = data.getBoolean("finish");
                    if (z) {
                        TaskStatusFinishSuccessEvent taskStatusFinishSuccessEvent =
                                new TaskStatusFinishSuccessEvent(i2, string, siteAccountInfo, taskStatusResult);
                        taskStatusFinishSuccessEvent.e = z;
                        EventBus.getDefault().post(taskStatusFinishSuccessEvent);
                        return;
                    }
                    TaskStatusFinishErrorEvent taskStatusFinishErrorEvent =
                            new TaskStatusFinishErrorEvent(i2, string, siteAccountInfo, taskStatusResult);
                    taskStatusFinishErrorEvent.e = z;
                    EventBus.getDefault().post(taskStatusFinishErrorEvent);
                }
            } else if (i == 3) {
                EventBus.getDefault().post(new TaskStatusVerifycodeEvent("", siteAccountInfo, taskStatusResult));
            } else if (i == 4) {
                EventBus.getDefault()
                        .post(new TaskStatusVerifycodeSuccessEvent(taskStatusResult.a(), "解析成功", siteAccountInfo,
                                taskStatusResult));
            }
        }
    }

    static /* synthetic */ void a(ImportStatusTask importStatusTask) {
        if (importStatusTask.g) {
            importStatusTask.b();
        } else if (new Date().getTime() / 1000 > importStatusTask.f + 480) {
            TaskStatusResult   r0 = new TaskStatusResult();
            r0.f("导入超时,请稍后重试");
            importStatusTask.a(importStatusTask.k.obtainMessage(2), r0, 0, Boolean.valueOf(false));
            importStatusTask.a();
        } else if (!importStatusTask.h) {
            importStatusTask.h = true;
            TaskStatusResult a = ImportCrawlApi.a(importStatusTask.b, importStatusTask.d);
            if (a != null) {
                a.e(importStatusTask.c);
            }
            importStatusTask.h = false;
            if (importStatusTask.g) {
                importStatusTask.b();
            } else if (a == null) {
                importStatusTask.e++;
                if (importStatusTask.e > 3) {
                    TaskStatusResult r0 = new TaskStatusResult();
                    r0.f("网络错误,请稍后重试");
                    importStatusTask.a(importStatusTask.k.obtainMessage(2), r0, 0, Boolean.valueOf(false));
                    importStatusTask.a();
                }
            } else if (importStatusTask.g) {
                importStatusTask.b();
            } else {
                if (a.a() != 200) {
                    importStatusTask.e++;
                    if (importStatusTask.e > 3) {
                        a.a("导入失败,请稍后重试");
                        importStatusTask.a(importStatusTask.k.obtainMessage(2), a, 0, Boolean.valueOf(false));
                        importStatusTask.a();
                        return;
                    }
                }
                new StringBuilder("discription=").append(a.e()).append(" phase= ").append(a.b());
                importStatusTask.e = 0;
                if (a.d().intValue() == 1 && a.c().equals("DONE_FAIL")) {
                    importStatusTask.a(importStatusTask.k.obtainMessage(2), a, 0, Boolean.valueOf(false));
                    importStatusTask.a();
                    return;
                }
                HashMap extendParams = GlobalParams.i().a().getExtendParams();
                String function = GlobalParams.i().a().getFunction();
                if (MxParam.PARAM_COMMON_YES.equals(GlobalParams.i().a().getQuitLoginDone()) || (extendParams != null
                                                                                                         && extendParams
                        .containsKey(MxParam.PARAM_QUIT_LOGIN_DONE) && ((String) extendParams
                        .get(MxParam.PARAM_QUIT_LOGIN_DONE)).equalsIgnoreCase(MxParam.PARAM_COMMON_YES))) {
                    if (!MxParam.PARAM_FUNCTION_TAOBAO.equals(function) && !MxParam.PARAM_FUNCTION_ALIPAY
                            .equals(function) && !MxParam.PARAM_FUNCTION_JINGDONG.equals(function) && !a.b()
                            .equals("LOGIN")) {
                        EventBus.getDefault().post(new CanLeaveEvent(true));
                        importStatusTask.a(importStatusTask.k.obtainMessage(2), a, 1, Boolean.valueOf(true));
                        importStatusTask.a();
                        return;
                    } else if (!(a.b().equals("LOGIN") || a.b().equals("RECEIVE"))) {
                        EventBus.getDefault().post(new CanLeaveEvent(true));
                        importStatusTask.a(importStatusTask.k.obtainMessage(2), a, 1, Boolean.valueOf(true));
                        importStatusTask.a();
                        return;
                    }
                } else if (!MxParam.PARAM_FUNCTION_TAOBAO.equals(function) && !MxParam.PARAM_FUNCTION_ALIPAY
                        .equals(function) && !MxParam.PARAM_FUNCTION_JINGDONG.equals(function) && !a.b()
                        .equals("LOGIN")) {
                    EventBus.getDefault().post(new CanLeaveEvent(true));
                } else if (!(a.b().equals("LOGIN") || a.b().equals("RECEIVE"))) {
                    EventBus.getDefault().post(new CanLeaveEvent(true));
                }
                if (a.b().equals("DONE")) {
                    if (a.c().equals("DONE_FAIL")) {
                        importStatusTask.a(importStatusTask.k.obtainMessage(2), a, 0, Boolean.valueOf(false));
                        importStatusTask.a();
                        return;
                    }
                    importStatusTask.a(importStatusTask.k.obtainMessage(2), a, 1, Boolean.valueOf(true));
                    importStatusTask.a();
                } else if (a.c().equals("WAIT_CODE")) {
                    importStatusTask.a(importStatusTask.k.obtainMessage(3), a, 6, Boolean.valueOf(false));
                    importStatusTask.a();
                    return;
                } else {
                    importStatusTask.a(importStatusTask.k.obtainMessage(1), a, 4, Boolean.valueOf(false));
                    return;
                }
                if (importStatusTask.g) {
                    importStatusTask.b();
                }
            }
        }
    }

    public final void a(SiteAccountInfo siteAccountInfo) {
        try {
            this.a = siteAccountInfo;
            String j = siteAccountInfo.j();
            String p = siteAccountInfo.p();
            String i = siteAccountInfo.i();
            this.b = j;
            this.c = p;
            this.d = i;
            this.j = new TimerTask() {
                final /* synthetic */ ImportStatusTask a;

                {
                    this.a = ImportStatusTask.this;
                }

                public void run() {
                    ImportStatusTask.a(this.a);
                }
            };
            this.f = new Date().getTime() / 1000;
            this.i = new Timer();
            this.i.schedule(this.j, 0, 2000);
        } catch (Throwable e) {
            ErrorHandle.b("ImportStatusTask run error", e);
        }
    }

    public final void a() {
        this.g = true;
        b();
    }

    private void a(Message message, TaskStatusResult taskStatusResult, int i, Boolean bool) {
        Bundle bundle = new Bundle();
        String str = TaskStatus.MESSAGE;
        String e = taskStatusResult.e();
        if (TextUtils.isEmpty(e)) {
            e = taskStatusResult.b();
        }
        if (TextUtils.isEmpty(e)) {
            e = "";
        }
        bundle.putString(str, e);
        bundle.putInt("code", i);
        bundle.putBoolean("finish", bool.booleanValue());
        message.setData(bundle);
        message.obj = new MailStatusHanderResult(this, this.a, taskStatusResult);
        this.k.sendMessage(message);
    }

    private void b() {
        if (this.i != null) {
            this.i.cancel();
            this.i = null;
        }
        if (this.j != null) {
            this.j.cancel();
            this.j = null;
        }
    }
}
