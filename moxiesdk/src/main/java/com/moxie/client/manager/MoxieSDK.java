package com.moxie.client.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.moxie.client.crash.MxACRA;
import com.moxie.client.model.MxParam;
import com.moxie.client.tasks.task.LoadBaseConfigTask;
import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class MoxieSDK {
    private static MoxieSDK INSTANCE;
    private static boolean hasInit = false;
    private Context mContext = null;
    private StatusViewHandler mStatusViewHandler = null;
    private MoxieCallBack moxieCallBack = null;
    private MxParam mxParam = null;

    public static void init(@NonNull Application application) {
        init(application, true);
    }

    public static void init(@NonNull Application application, boolean z) {
        if (!hasInit) {
            INSTANCE = new MoxieSDK(application);
            MxACRA.a(application, z);
            new LoadBaseConfigTask(application).execute( new Void[0]);
            hasInit = true;
        }
    }

    public static MoxieSDK getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoxieSDK();
        }
        return INSTANCE;
    }

    private MoxieSDK() {
    }

    private MoxieSDK(Application application) {
        this.mContext = application.getApplicationContext();
    }

    public void start(@NonNull Activity activity, @NonNull MxParam mxParam, @Nullable MoxieCallBack moxieCallBack) {
        if (this.mContext == null) {
            this.mContext = activity.getApplicationContext();
        }
        this.mxParam = mxParam;
        this.moxieCallBack = moxieCallBack;
        new ActivityDispatcher(activity).a(this.mxParam);
    }

    public void setMoxieParam(MxParam mxParam) {
        this.mxParam = mxParam;
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public MoxieCallBack getMoxieCallBack() {
        return this.moxieCallBack;
    }

    public void setMoxieCallBack(MoxieCallBack moxieCallBack) {
        this.moxieCallBack = moxieCallBack;
    }

    public MxParam getMxParam() {
        return this.mxParam;
    }

    public void setMxParam(MxParam mxParam) {
        this.mxParam = mxParam;
    }

    public void setStatusViewHandler(StatusViewHandler statusViewHandler) {
        this.mStatusViewHandler = statusViewHandler;
    }

    public StatusViewHandler getStatusViewHandler() {
        return this.mStatusViewHandler;
    }

    public void clear() {
        this.moxieCallBack = null;
        this.mStatusViewHandler = null;
    }
}
