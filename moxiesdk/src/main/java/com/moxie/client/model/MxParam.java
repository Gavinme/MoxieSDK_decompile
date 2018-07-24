package com.moxie.client.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.proguard.annotation.NotProguard;
import java.util.HashMap;

@NotProguard
/* compiled from: TbsSdkJava */
public class MxParam implements Parcelable {
    public static final Creator<MxParam> CREATOR = new Creator<MxParam>() {
        public final /* bridge */ /* synthetic */ MxParam[] newArray(int i) {
            return new MxParam[i];
        }

        public final /* synthetic */ MxParam createFromParcel(Parcel parcel) {
            MxParam mxParam = new MxParam();
            mxParam.setFunction(parcel.readString());
            mxParam.setUserId(parcel.readString());
            mxParam.setApiKey(parcel.readString());
            mxParam.setItemCode(parcel.readString());
            mxParam.setItemType(parcel.readString());
            mxParam.setBannerBgColor(parcel.readString());
            mxParam.setBannerTxtColor(parcel.readString());
            mxParam.setBannerTxtContent(parcel.readString());
            mxParam.setThemeColor(parcel.readString());
            mxParam.setAgreementUrl(parcel.readString());
            mxParam.setAgreementEntryText(parcel.readString());
            mxParam.setSubType(parcel.readString());
            mxParam.setQuitOnFail(parcel.readString());
            mxParam.setCacheDisable(parcel.readString());
            mxParam.setLoginOthersHide(parcel.readString());
            mxParam.setLoginVersion(parcel.readString());
            mxParam.setLoginType(parcel.readString());
            mxParam.setQuitLoginDone(parcel.readString());
            mxParam.setLoadingViewText(parcel.readString());
            mxParam.setQuitDisable(parcel.readByte() != (byte) 0);
            mxParam.setExtendParams(parcel.readHashMap(HashMap.class.getClassLoader()));
            mxParam.setLoginCustom(parcel.readHashMap(HashMap.class.getClassLoader()));
            mxParam.setUserBaseInfo(parcel.readHashMap(HashMap.class.getClassLoader()));
            mxParam.setTitleParams((TitleParams) parcel.readParcelable(TitleParams.class.getClassLoader()));
            return mxParam;
        }
    };
    public static final String PAPAM_H5_URL = "H5_URL";
    public static final String PARAM_CACHE_DISABLE = "cache_disbale";
    public static final String PARAM_CARRIER_EDITABLE = "carrier_editable";
    public static final String PARAM_CARRIER_IDCARD = "carrier_idcard";
    public static final String PARAM_CARRIER_NAME = "carrier_name";
    public static final String PARAM_CARRIER_PHONE = "carrier_phone";
    public static final String PARAM_COMMON_EDITABLE = "editable";
    public static final String PARAM_COMMON_NO = "NO";
    public static final String PARAM_COMMON_YES = "YES";
    public static final String PARAM_CONFIG_URL = "config_url";
    public static final String PARAM_CREATE_TASK_URL = "create_task_url";
    public static final String PARAM_CUSTOM_LOGIN_CODE = "login_code";
    public static final String PARAM_CUSTOM_LOGIN_OTHERS_HIDE = "login_others_hide";
    public static final String PARAM_CUSTOM_LOGIN_PARAMS = "login_params";
    public static final String PARAM_CUSTOM_LOGIN_PARAMS_CARDNO = "CARDNO";
    public static final String PARAM_CUSTOM_LOGIN_PARAMS_IDCARD = "IDCARD";
    public static final String PARAM_CUSTOM_LOGIN_PARAMS_MOBILE = "MOBILE";
    public static final String PARAM_CUSTOM_LOGIN_TYPE = "login_type";
    public static final String PARAM_FUNCTION_ALIPAY = "alipay";
    public static final String PARAM_FUNCTION_BANK = "bank";
    public static final String PARAM_FUNCTION_CARRIER = "carrier";
    public static final String PARAM_FUNCTION_CHSI = "chsi";
    public static final String PARAM_FUNCTION_EC = "ec";
    public static final String PARAM_FUNCTION_EMAIL = "email";
    public static final String PARAM_FUNCTION_FUND = "fund";
    public static final String PARAM_FUNCTION_INSURANCE = "insurance";
    public static final String PARAM_FUNCTION_JINGDONG = "jingdong";
    public static final String PARAM_FUNCTION_LINKEDIN = "linkedin";
    public static final String PARAM_FUNCTION_MAIL = "email";
    public static final String PARAM_FUNCTION_MAIMAI = "maimai";
    public static final String PARAM_FUNCTION_ONLINEBANK = "bank";
    public static final String PARAM_FUNCTION_QQ = "qq";
    public static final String PARAM_FUNCTION_SAMETRADE = "sametrade";
    public static final String PARAM_FUNCTION_SECURITY = "security";
    public static final String PARAM_FUNCTION_SHIXINCOURT = "shixincourt";
    public static final String PARAM_FUNCTION_TAOBAO = "taobao";
    public static final String PARAM_FUNCTION_TAX = "tax";
    public static final String PARAM_FUNCTION_ZHENGXIN = "zhengxin";
    public static final String PARAM_FUNCTION_ZHIXINGCOURT = "zhixingcourt";
    public static final String PARAM_IDCARD = "idcard";
    public static final String PARAM_ITEM_TYPE_CREDITCARD = "CREDITCARD";
    public static final String PARAM_ITEM_TYPE_DEBITCARD = "DEBITCARD";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_ONLINEBANK_HEADER_TITLE = "onlinebank_header_title";
    public static final String PARAM_PERMISIION_URL = "permission_url";
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_QUIT_LOGIN_DONE = "quit_login_done";
    public static final String PARAM_SUBTYPE_API = "api";
    public static final String PARAM_SUBTYPE_SDK = "sdk";
    public static final String PARAM_TASK_STATUS_URL = "task_status_url";
    public static final String PARAM_USER_BASEINFO_IDCARD = "id_card";
    public static final String PARAM_USER_BASEINFO_MOBILE = "mobile";
    public static final String PARAM_USER_BASEINFO_REALNAME = "real_name";
    private String agreementEntryText = "";
    private String agreementUrl = "https://api.51datakey.com/h5/agreement.html";
    private String apiKey = "";
    private String bannerBgColor = "#000000";
    private String bannerTxtColor = "#ffffff";
    private String bannerTxtContent = "";
    private String cacheDisable = PARAM_COMMON_NO;
    private HashMap<String, String> extendParams = new HashMap();
    private String function = "";
    private String itemCode = "";
    private String itemType = "";
    private String loadingViewText = null;
    private HashMap<String, String> loginCustom = null;
    private String loginOthersHide = PARAM_COMMON_NO;
    private String loginType = "";
    private String loginVersion = "";
    private boolean quitDisable = false;
    private String quitLoginDone = PARAM_COMMON_NO;
    private String quitOnFail = PARAM_COMMON_NO;
    private String subType = PARAM_SUBTYPE_SDK;
    private String themeColor = "#ff9500";
    private TitleParams titleParams = null;
    private HashMap<String, String> userBaseInfo = new HashMap();
    private String userId = "";

    @NotProguard
    /* compiled from: TbsSdkJava */
    public interface ErrorCode {
        public static final int SDK_OPEN_FAIL = 1;
    }

    @NotProguard
    /* compiled from: TbsSdkJava */
    public interface ResultCode {
        public static final int IMPORTING = 2;
        public static final int IMPORT_FAIL = 0;
        public static final int IMPORT_SUCCESS = 1;
        public static final int IMPORT_UNSTART = -1;
        public static final int MOXIE_SERVER_ERROR = -3;
        public static final int THIRD_PARTY_SERVER_ERROR = -2;
        public static final int USER_INPUT_ERROR = -4;
    }

    @NotProguard
    /* compiled from: TbsSdkJava */
    public interface TaskStatus {
        public static final String ACCOUNT = "account";
        public static final String LOGINDONE = "loginDone";
        public static final String MESSAGE = "message";
        public static final String PERCENT = "percent";
    }

    public String getQuitLoginDone() {
        return this.quitLoginDone;
    }

    public void setQuitLoginDone(String str) {
        this.quitLoginDone = str;
    }

    public HashMap<String, String> getLoginCustom() {
        return this.loginCustom;
    }

    public void setLoginCustom(HashMap<String, String> hashMap) {
        this.loginCustom = hashMap;
    }

    public String getLoginVersion() {
        return this.loginVersion;
    }

    public void setLoginVersion(String str) {
        this.loginVersion = str;
    }

    public String getLoginOthersHide() {
        return this.loginOthersHide;
    }

    public void setLoginOthersHide(String str) {
        this.loginOthersHide = str;
    }

    public String getCacheDisable() {
        return this.cacheDisable;
    }

    public void setCacheDisable(String str) {
        this.cacheDisable = str;
    }

    public String getAgreementEntryText() {
        return this.agreementEntryText;
    }

    public void setAgreementEntryText(String str) {
        this.agreementEntryText = str;
    }

    public String getLoginType() {
        return this.loginType;
    }

    public void setLoginType(String str) {
        this.loginType = str;
    }

    public String getQuitOnFail() {
        return this.quitOnFail;
    }

    public void setQuitOnFail(String str) {
        this.quitOnFail = str;
    }

    @Deprecated
    public String getBannerTxtContent() {
        return this.bannerTxtContent;
    }

    @Deprecated
    public void setBannerTxtContent(String str) {
        this.bannerTxtContent = str;
    }

    public String getSubType() {
        return this.subType;
    }

    public void setSubType(String str) {
        this.subType = str;
    }

    public String getBannerBgColor() {
        return this.bannerBgColor;
    }

    @Deprecated
    public void setBannerBgColor(String str) {
        this.bannerBgColor = str;
    }

    public String getBannerTxtColor() {
        return this.bannerTxtColor;
    }

    @Deprecated
    public void setBannerTxtColor(String str) {
        this.bannerTxtColor = str;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String str) {
        this.apiKey = str;
    }

    @Deprecated
    public String getItemCode() {
        return this.itemCode;
    }

    @Deprecated
    public void setItemCode(String str) {
        this.itemCode = str;
    }

    @Deprecated
    public String getItemType() {
        return this.itemType;
    }

    @Deprecated
    public void setItemType(String str) {
        this.itemType = str;
    }

    public String getThemeColor() {
        return this.themeColor;
    }

    public void setThemeColor(String str) {
        this.themeColor = str;
    }

    public String getAgreementUrl() {
        return this.agreementUrl;
    }

    public void setAgreementUrl(String str) {
        this.agreementUrl = str;
    }

    public HashMap<String, String> getExtendParams() {
        return this.extendParams;
    }

    public void setExtendParams(HashMap<String, String> hashMap) {
        this.extendParams = hashMap;
    }

    public HashMap<String, String> getUserBaseInfo() {
        return this.userBaseInfo;
    }

    public void setUserBaseInfo(HashMap<String, String> hashMap) {
        this.userBaseInfo = hashMap;
    }

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String str) {
        this.function = str;
    }

    public void setLoadingViewText(String str) {
        this.loadingViewText = str;
    }

    public String getLoadingViewText() {
        return this.loadingViewText;
    }

    public boolean isQuitDisable() {
        return this.quitDisable;
    }

    public void setQuitDisable(boolean z) {
        this.quitDisable = z;
    }

    public TitleParams getTitleParams() {
        return this.titleParams;
    }

    public void setTitleParams(TitleParams titleParams) {
        this.titleParams = titleParams;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.function);
        parcel.writeString(this.userId);
        parcel.writeString(this.apiKey);
        parcel.writeString(this.itemCode);
        parcel.writeString(this.itemType);
        parcel.writeString(this.bannerBgColor);
        parcel.writeString(this.bannerTxtColor);
        parcel.writeString(this.bannerTxtContent);
        parcel.writeString(this.themeColor);
        parcel.writeString(this.agreementUrl);
        parcel.writeString(this.agreementEntryText);
        parcel.writeString(this.subType);
        parcel.writeString(this.quitOnFail);
        parcel.writeString(this.cacheDisable);
        parcel.writeString(this.loginOthersHide);
        parcel.writeString(this.loginVersion);
        parcel.writeString(this.loginType);
        parcel.writeString(this.quitLoginDone);
        parcel.writeString(this.loadingViewText);
        parcel.writeByte((byte) (this.quitDisable ? 1 : 0));
        parcel.writeMap(this.extendParams);
        parcel.writeMap(this.loginCustom);
        parcel.writeMap(this.userBaseInfo);
        parcel.writeParcelable(this.titleParams, 0);
    }
}
