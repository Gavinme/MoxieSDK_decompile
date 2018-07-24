package com.moxie.client.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class TitleParams implements Parcelable {
    public static final Creator<TitleParams> CREATOR = new Creator<TitleParams>() {
        public final /* bridge */ /* synthetic */ TitleParams[] newArray(int i) {
            return new TitleParams[i];
        }

        public final /* synthetic */ TitleParams createFromParcel(Parcel parcel) {
            boolean z = true;
            TitleParams titleParams = new TitleParams();
            titleParams.setLeftImgResId(parcel.readInt());
            titleParams.setRightImgResId(parcel.readInt());
            titleParams.setTitleColor(parcel.readInt());
            titleParams.setLeftImgPressedResId(parcel.readInt());
            titleParams.setRightImgPressedResId(parcel.readInt());
            titleParams.setBackgroundColor(parcel.readInt());
            titleParams.setLeftTextColor(parcel.readInt());
            titleParams.setLeftText(parcel.readString());
            titleParams.setTitle(parcel.readString());
            titleParams.setBackgroundDrawable(parcel.readInt());
            if (parcel.readInt() != 1) {
                z = false;
            }
            titleParams.setImmersedEnable(z);
            return titleParams;
        }
    };
    private int backgroundColor;
    private int backgroundDrawable;
    private boolean immersedEnable;
    private int leftImgPressedResId;
    private int leftImgResId;
    private String leftText;
    private int leftTextColor;
    private String mTitle;
    private int rightImgPressedResId;
    private int rightImgResId;
    private int titleColor;

    @NotProguard
    /* compiled from: TbsSdkJava */
    public static class Builder {
        private int backgroundColor = 0;
        private int backgroundDrawable = -1;
        private boolean immersedEnable = false;
        private int leftImgPressedResId = -1;
        private int leftImgResId = -1;
        private String leftText = "";
        private int leftTextColor = -1;
        private String mTitle;
        private int rightImgPressedResId = -1;
        private int rightImgResId = -1;
        private int titleColor = -1;

        public Builder leftNormalImgResId(int i) {
            if (i != -1) {
                this.leftImgResId = i;
            }
            return this;
        }

        public Builder leftPressedImgResId(int i) {
            if (i != -1) {
                this.leftImgPressedResId = i;
            }
            return this;
        }

        public Builder rightNormalImgResId(int i) {
            if (i != -1) {
                this.rightImgResId = i;
            }
            return this;
        }

        public Builder rightPressedImgResId(int i) {
            if (i != -1) {
                this.rightImgPressedResId = i;
            }
            return this;
        }

        public Builder titleColor(int i) {
            this.titleColor = i;
            return this;
        }

        public Builder backgroundColor(int i) {
            this.backgroundColor = i;
            return this;
        }

        public Builder leftText(String str) {
            this.leftText = str;
            return this;
        }

        public Builder leftTextColor(int i) {
            this.leftTextColor = i;
            return this;
        }

        public Builder title(String str) {
            this.mTitle = str;
            return this;
        }

        public Builder backgroundDrawable(int i) {
            this.backgroundDrawable = i;
            return this;
        }

        public Builder immersedEnable(boolean z) {
            this.immersedEnable = z;
            return this;
        }

        public TitleParams build() {
            TitleParams titleParams = new TitleParams();
            titleParams.setTitleColor(this.titleColor);
            titleParams.setLeftImgResId(this.leftImgResId);
            titleParams.setRightImgResId(this.rightImgResId);
            titleParams.setLeftImgPressedResId(this.leftImgPressedResId);
            titleParams.setRightImgPressedResId(this.rightImgPressedResId);
            titleParams.setBackgroundColor(this.backgroundColor);
            titleParams.setLeftText(this.leftText);
            titleParams.setLeftTextColor(this.leftTextColor);
            titleParams.setTitle(this.mTitle);
            titleParams.setBackgroundDrawable(this.backgroundDrawable);
            titleParams.setImmersedEnable(this.immersedEnable);
            return titleParams;
        }
    }

    private TitleParams() {
    }

    public String getTitle() {
        return this.mTitle;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public String getLeftText() {
        return this.leftText;
    }

    public void setLeftText(String str) {
        this.leftText = str;
    }

    public int getLeftTextColor() {
        return this.leftTextColor;
    }

    public void setLeftTextColor(int i) {
        this.leftTextColor = i;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
    }

    public int getLeftImgResId() {
        return this.leftImgResId;
    }

    public void setLeftImgResId(int i) {
        this.leftImgResId = i;
    }

    public int getRightImgResId() {
        return this.rightImgResId;
    }

    public void setRightImgResId(int i) {
        this.rightImgResId = i;
    }

    public int getTitleColor() {
        return this.titleColor;
    }

    public void setTitleColor(int i) {
        this.titleColor = i;
    }

    public int getLeftImgPressedResId() {
        return this.leftImgPressedResId;
    }

    public void setLeftImgPressedResId(int i) {
        this.leftImgPressedResId = i;
    }

    public int getRightImgPressedResId() {
        return this.rightImgPressedResId;
    }

    public void setRightImgPressedResId(int i) {
        this.rightImgPressedResId = i;
    }

    public int getBackgroundDrawable() {
        return this.backgroundDrawable;
    }

    public void setBackgroundDrawable(int i) {
        this.backgroundDrawable = i;
    }

    public boolean isImmersedEnable() {
        return this.immersedEnable;
    }

    public void setImmersedEnable(boolean z) {
        this.immersedEnable = z;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.leftImgResId);
        parcel.writeInt(this.rightImgResId);
        parcel.writeInt(this.titleColor);
        parcel.writeInt(this.leftImgPressedResId);
        parcel.writeInt(this.rightImgPressedResId);
        parcel.writeInt(this.backgroundColor);
        parcel.writeInt(this.leftTextColor);
        parcel.writeString(this.leftText);
        parcel.writeString(this.mTitle);
        parcel.writeInt(this.backgroundDrawable);
        parcel.writeInt(this.immersedEnable ? 1 : 0);
    }

    public int describeContents() {
        return 0;
    }
}
