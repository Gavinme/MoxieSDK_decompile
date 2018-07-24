package com.moxie.client.widget;

import com.proguard.annotation.NotProguard;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@NotProguard
/* compiled from: TbsSdkJava */
public class CustomVerifyDialog extends Dialog {
    private EditText mEditVerify;
    private ImageView mImageVerify;
    private RelativeLayout mRelativeVerify;
    private TextView mTextLeftButton;
    private TextView mTextLoading;
    private TextView mTextRightButton;
    private TextView mTextTitle;

    public CustomVerifyDialog(Context context) {
        super(context, context.getResources()
                .getIdentifier("MoxieMailImport_Custom_Dialog", "style", context.getPackageName()));
        setContentView(context.getResources()
                .getIdentifier("moxie_client_dialog_input_verify_code", "layout", context.getPackageName()));
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        Window window = getWindow();
        window.setGravity(17);
        LayoutParams attributes = window.getAttributes();
        attributes.alpha = 0.9f;
        window.setAttributes(attributes);
        this.mTextTitle = (TextView) findViewById(
                context.getResources().getIdentifier("TextView_Title", "id", context.getPackageName()));
        this.mEditVerify = (EditText) findViewById(
                context.getResources().getIdentifier("EditText_Verify_Code", "id", context.getPackageName()));
        this.mImageVerify = (ImageView) findViewById(
                context.getResources().getIdentifier("ImageView_Verify_Code", "id", context.getPackageName()));
        this.mTextLoading = (TextView) findViewById(
                context.getResources().getIdentifier("TextView_Verify_Loading", "id", context.getPackageName()));
        this.mRelativeVerify = (RelativeLayout) findViewById(
                context.getResources().getIdentifier("RelativeLayout_Verify_Place", "id", context.getPackageName()));
        this.mTextLeftButton = (TextView) findViewById(
                context.getResources().getIdentifier("TextView_Left_Button", "id", context.getPackageName()));
        this.mTextRightButton = (TextView) findViewById(
                context.getResources().getIdentifier("TextView_Right_Button", "id", context.getPackageName()));
    }

    public TextView getTitle() {
        return this.mTextTitle;
    }

    public EditText getVerifyEdit() {
        return this.mEditVerify;
    }

    public ImageView getVerifyImage() {
        return this.mImageVerify;
    }

    public TextView getLoadingText() {
        return this.mTextLoading;
    }

    public RelativeLayout getVerifyRelative() {
        return this.mRelativeVerify;
    }

    public TextView getLeftButton() {
        return this.mTextLeftButton;
    }

    public TextView getRightButton() {
        return this.mTextRightButton;
    }

    public void setTitle(String str) {
        this.mTextTitle.setText(str);
    }

    public void setTitle(int i) {
        this.mTextTitle.setText(i);
    }

    public void setEditVerifyHint(String str) {
        this.mEditVerify.setHint(str);
    }

    public void setEditVerifyHint(int i) {
        this.mEditVerify.setHint(i);
    }

    public void setTextLoading(String str) {
        this.mTextLoading.setText(str);
    }

    public void setTextLoading(int i) {
        this.mTextLoading.setText(i);
    }

    public void setLeftButtonText(String str) {
        this.mTextLeftButton.setText(str);
    }

    public void setLeftButtonText(int i) {
        this.mTextLeftButton.setText(i);
    }

    public void setRightButtonText(String str) {
        this.mTextRightButton.setText(str);
    }

    public void setRightButtonText(int i) {
        this.mTextRightButton.setText(i);
    }

    public void showVerifyPlace() {
        this.mRelativeVerify.setVisibility(0);
    }

    public void hideVerifyPlace() {
        this.mRelativeVerify.setVisibility(8);
    }

    public void showVerifyLoading() {
        this.mTextLoading.setVisibility(0);
    }

    public void hideVerifyLoading() {
        this.mTextLoading.setVisibility(8);
    }

    public void setLeftButtonListener(View.OnClickListener onClickListener) {
        this.mTextLeftButton.setOnClickListener(onClickListener);
    }

    public void setRightButtonListener(View.OnClickListener onClickListener) {
        this.mTextRightButton.setOnClickListener(onClickListener);
    }
}
