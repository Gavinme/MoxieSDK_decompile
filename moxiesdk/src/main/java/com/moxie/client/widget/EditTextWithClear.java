package com.moxie.client.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.TextView.BufferType;
import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class EditTextWithClear extends EditText implements TextWatcher, OnFocusChangeListener {
    private boolean hasFoucs;
    private Drawable mClearDrawable;
    private OnClearClickListener mOnClearClickListener;
    private Editable realEditable;
    private String realText;

    /* compiled from: TbsSdkJava */
    public interface OnClearClickListener {
    }

    public EditTextWithClear(Context context) {
        this(context, null);
    }

    public void setOnClearClickListener(OnClearClickListener onClearClickListener) {
        this.mOnClearClickListener = onClearClickListener;
    }

    public void setText(String str, String str2) {
        super.setText(str, BufferType.EDITABLE);
        this.realText = str2;
    }

    public void setRealText(String str) {
        this.realText = str;
    }

    public String getMyText() {
        if (TextUtils.isEmpty(this.realText)) {
            return super.getText().toString();
        }
        return this.realText;
    }

    public EditTextWithClear(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842862);
    }

    public EditTextWithClear(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.realText = "";
        init(context);
    }

    private void init(Context context) {
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = getResources().getDrawable(context.getResources().getIdentifier("moxie_client_search_city_edittext_delete", "drawable", context.getPackageName()));
        }
        this.mClearDrawable.setBounds(0, 0, this.mClearDrawable.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Object obj = 1;
        if (motionEvent.getAction() == 1 && getCompoundDrawables()[2] != null) {
            if (motionEvent.getX() <= ((float) (getWidth() - getTotalPaddingRight())) || motionEvent.getX() >= ((float) (getWidth() - getPaddingRight()))) {
                obj = null;
            }
            if (obj != null) {
                getText().toString();
                setText("");
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void onFocusChange(View view, boolean z) {
        boolean z2 = false;
        this.hasFoucs = z;
        if (z) {
            if (getText().length() > 0) {
                z2 = true;
            }
            setClearIconVisible(z2);
            return;
        }
        setClearIconVisible(false);
    }

    public void setClearIconVisible(boolean z) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], z ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.hasFoucs) {
            setClearIconVisible(charSequence.length() > 0);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void afterTextChanged(Editable editable) {
    }

    public void setShakeAnimation() {
        setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int i) {
        Animation translateAnimation = new TranslateAnimation(0.0f, 10.0f, 0.0f, 0.0f);
        translateAnimation.setInterpolator(new CycleInterpolator((float) i));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z) {
            Spannable text = getText();
            Selection.setSelection(text, text.length());
        }
    }
}
