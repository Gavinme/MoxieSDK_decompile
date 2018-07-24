package com.moxie.client.widget.LoadingDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

/* compiled from: TbsSdkJava */
public abstract class LoadingBaseDialog extends Dialog {

    protected LoadingBaseDialog(Context context) {
        super(context,
                context.getResources().getIdentifier("MoxieSDKLoadingDialog", "style", context.getPackageName()));
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }
}
