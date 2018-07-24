package com.moxie.client.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.moxie.client.MainActivity;
import com.moxie.client.model.MxParam;
import com.tencent.smtt.utils.TbsLog;

/* compiled from: TbsSdkJava */
public class ActivityDispatcher implements IDispatcher {
    private Activity a;

    /* compiled from: TbsSdkJava */
    public static class RequestCode {
    }

    ActivityDispatcher(Activity activity) {
        this.a = activity;
    }

    public final Intent a(MxParam mxParam) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("param", mxParam);
        Intent intent = new Intent(this.a, MainActivity.class);
        intent.putExtras(bundle);
        this.a.startActivityForResult(intent, TbsLog.TBSLOG_CODE_SDK_BASE);
        return intent;
    }
}
