package com.moxie.client.widget;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.model.TitleParams;
import com.moxie.client.utils.DisplayUtil;
import com.proguard.annotation.NotProguard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@NotProguard
/* compiled from: TbsSdkJava */
public class TitleLayout extends RelativeLayout {
    private Context mContext;
    private ImageView mImageLeft;
    private ImageView mImageRight;
    private TextView mLeftTextView;
    private RelativeLayout mRelativeLayout_Title_Left;
    private RelativeLayout mRelativeLayout_Title_Right;
    private TextView mTextTitle;
    private View mTitleLayoutView;

    public TitleLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public TitleLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public TitleLayout(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(getResources().getIdentifier("moxie_client_common_title", "layout", context.getPackageName()), this);
        this.mTextTitle = (TextView) inflate.findViewById(getResources().getIdentifier("TextView_Title", "id", context.getPackageName()));
        this.mImageLeft = (ImageView) inflate.findViewById(getResources().getIdentifier("Moxie_Client_ImageView_Left", "id", context.getPackageName()));
        this.mImageRight = (ImageView) inflate.findViewById(getResources().getIdentifier("Moxie_Client_ImageView_Right", "id", context.getPackageName()));
        this.mTitleLayoutView = inflate.findViewById(getResources().getIdentifier("Moxie_Client_RelativeLayout_Title", "id", context.getPackageName()));
        this.mRelativeLayout_Title_Left = (RelativeLayout) inflate.findViewById(getResources().getIdentifier("Moxie_Client_RelativeLayout_Title_Left", "id", context.getPackageName()));
        this.mRelativeLayout_Title_Right = (RelativeLayout) inflate.findViewById(getResources().getIdentifier("Moxie_Client_RelativeLayout_Title_Right", "id", context.getPackageName()));
        this.mLeftTextView = (TextView) inflate.findViewById(getResources().getIdentifier("moxie_client_actionbar_left_text", "id", context.getPackageName()));
    }

    public void setTitle(int i) {
        try {
            this.mTextTitle.setText(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTitle(String str) {
        this.mTextTitle.setText(str);
    }

    public View getLeftTextView() {
        return this.mLeftTextView;
    }

    public ImageView getLeftImage() {
        return this.mImageLeft;
    }

    public RelativeLayout getRelativeLayout_Title_Left() {
        return this.mRelativeLayout_Title_Left;
    }

    public RelativeLayout getRelativeLayout_Title_Right() {
        return this.mRelativeLayout_Title_Right;
    }

    public ImageView getRightImage() {
        return this.mImageRight;
    }

    public void setLeftTextVisibility(int i) {
        this.mLeftTextView.setVisibility(i);
    }

    public void setImageRightVisibility(int i) {
        if (GlobalParams.i().a().getTitleParams() == null) {
            String bannerTxtColor = GlobalParams.i().a().getBannerTxtColor();
            if (i != 0) {
                this.mImageRight.setVisibility(8);
            } else if (bannerTxtColor.toLowerCase().equalsIgnoreCase("#ffffff") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#ffffffff") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#000000") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#ff000000")) {
                this.mImageRight.setVisibility(0);
            } else {
                this.mImageRight.setVisibility(8);
            }
        } else if (i == 0) {
            this.mImageRight.setVisibility(0);
        } else {
            this.mImageRight.setVisibility(8);
        }
    }

    public void initTitleLayout() {
        try {
            TitleParams titleParams = GlobalParams.i().a().getTitleParams();
            if (titleParams != null) {
                if (titleParams.isImmersedEnable()) {
                    LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
                    if (VERSION.SDK_INT >= 19) {
                        if (this.mContext instanceof Activity) {
                            ((Activity) this.mContext).getWindow().addFlags(67108864);
                        }
                        layoutParams.height = DisplayUtil.a(this.mContext);
                    }
                    setLayoutParams(layoutParams);
                }
                int leftImgResId = titleParams.getLeftImgResId();
                int rightImgResId = titleParams.getRightImgResId();
                int titleColor = titleParams.getTitleColor();
                int leftImgPressedResId = titleParams.getLeftImgPressedResId();
                int rightImgPressedResId = titleParams.getRightImgPressedResId();
                int backgroundColor = titleParams.getBackgroundColor();
                int backgroundDrawable = titleParams.getBackgroundDrawable();
                int leftTextColor = titleParams.getLeftTextColor();
                CharSequence leftText = titleParams.getLeftText();
                if (leftImgPressedResId != -1 && leftImgResId != -1) {
                    this.mImageLeft.setBackgroundDrawable(createStateDrawable(leftImgResId, leftImgPressedResId));
                } else if (leftImgResId != -1) {
                    this.mImageLeft.setBackgroundDrawable(this.mContext.getResources().getDrawable(this.mContext.getResources().getIdentifier("moxie_client_banner_back", "drawable", this.mContext.getPackageName())));
                }
                if (rightImgPressedResId != -1 && rightImgResId != -1) {
                    this.mImageRight.setBackgroundDrawable(createStateDrawable(rightImgResId, rightImgPressedResId));
                } else if (rightImgResId != -1) {
                    this.mImageRight.setBackgroundDrawable(this.mContext.getResources().getDrawable(this.mContext.getResources().getIdentifier("moxie_client_banner_refresh", "drawable", this.mContext.getPackageName())));
                }
                if (!TextUtils.isEmpty(leftText)) {
                    this.mLeftTextView.setText(leftText);
                }
                this.mLeftTextView.setTextColor(leftTextColor);
                this.mTextTitle.setTextColor(titleColor);
                if (backgroundDrawable != -1) {
                    setBackgroundDrawable(this.mContext.getResources().getDrawable(backgroundDrawable));
                    return;
                } else {
                    setBackgroundColor(backgroundColor);
                    return;
                }
            }
            String bannerTxtColor = GlobalParams.i().a().getBannerTxtColor();
            int parseColor = Color.parseColor(bannerTxtColor);
            if (bannerTxtColor.toLowerCase().equalsIgnoreCase("#ffffff") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#ffffffff") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#000000") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#ff000000")) {
                if (bannerTxtColor.toLowerCase().equalsIgnoreCase("#ffffff") || bannerTxtColor.toLowerCase().equalsIgnoreCase("#ffffffff")) {
                    this.mImageLeft.setBackgroundResource(this.mContext.getResources().getIdentifier("moxie_client_banner_back", "drawable", this.mContext.getPackageName()));
                    this.mImageRight.setBackgroundResource(this.mContext.getResources().getIdentifier("moxie_client_banner_refresh", "drawable", this.mContext.getPackageName()));
                } else {
                    this.mImageLeft.setBackgroundResource(this.mContext.getResources().getIdentifier("moxie_client_banner_back_black", "drawable", this.mContext.getPackageName()));
                    this.mImageRight.setBackgroundResource(this.mContext.getResources().getIdentifier("moxie_client_banner_refresh_black", "drawable", this.mContext.getPackageName()));
                }
                this.mImageLeft.setVisibility(0);
                this.mImageRight.setVisibility(0);
            } else {
                this.mImageLeft.setVisibility(8);
                this.mImageRight.setVisibility(8);
            }
            this.mTitleLayoutView.setBackgroundColor(Color.parseColor(GlobalParams.i().a().getBannerBgColor()));
            this.mTextTitle.setTextColor(parseColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StateListDrawable createStateDrawable(int i, int i2) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable drawable = getResources().getDrawable(i);
        stateListDrawable.addState(View.PRESSED_ENABLED_STATE_SET, getResources().getDrawable(i2));
        stateListDrawable.addState(View.ENABLED_STATE_SET, drawable);
        stateListDrawable.addState(View.EMPTY_STATE_SET, drawable);
        return stateListDrawable;
    }
}
