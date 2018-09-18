package com.moxie.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.moxie.client.commom.GlobalConstants;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.crash.MxACRA;
import com.moxie.client.fragment.BaseWebViewFragment;
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
import com.moxie.client.fragment.ImportResultFragment;
import com.moxie.client.fragment.ScreenCaptureFragment;
import com.moxie.client.fragment.WebViewAgreementH5Fragment;
import com.moxie.client.fragment.WebViewECV3Fragment;
import com.moxie.client.fragment.WebViewMoxieH5Fragment;
import com.moxie.client.fragment.WebViewOfficialH5Fragment;
import com.moxie.client.http.UploadFile;
import com.moxie.client.manager.LogTracker;
import com.moxie.client.manager.MoxieCallBackData;
import com.moxie.client.manager.MoxieContext;
import com.moxie.client.manager.MoxieSDK;
import com.moxie.client.manager.StatusViewHandler;
import com.moxie.client.manager.StatusViewListener;
import com.moxie.client.model.FragmentEnum;
import com.moxie.client.model.LogTrackInfo;
import com.moxie.client.model.MxParam;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.model.SiteAccountInfo;
import com.moxie.client.model.TaskStatusResult;
import com.moxie.client.model.helper.MxResponseHelper;
import com.moxie.client.model.helper.UrlParser;
import com.moxie.client.restapi.LoadSiteConfigApi;
import com.moxie.client.restapi.LoadTenantPermissionApi;
import com.moxie.client.tasks.event.CanLeaveEvent;
import com.moxie.client.tasks.event.OpenThirdPartEvent;
import com.moxie.client.tasks.event.ScreenCaptureEvent;
import com.moxie.client.tasks.event.SetResultEvent;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitErrorEvent;
import com.moxie.client.tasks.event.TaskLoginEvent.LoginSubmitSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusFinishErrorEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusFinishSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusProgressEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeErrorEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusVerifycodeSuccessEvent;
import com.moxie.client.tasks.event.TaskStatusEvent.TaskStatusWorkingEvent;
import com.moxie.client.tasks.event.UploadFileEvent;
import com.moxie.client.tasks.model.SiteConfigItem;
import com.moxie.client.tasks.model.SiteConfigsResponse;
import com.moxie.client.tasks.task.CheckAuthUDCreditTask;
import com.moxie.client.tasks.task.LoadSiteConfigTask;
import com.moxie.client.tasks.task.LoadTenantPermissionTask;
import com.moxie.client.utils.CommonMethod;
import com.moxie.client.utils.ErrorHandle;
import com.moxie.client.utils.SharedPreferMgr;
import com.moxie.client.widget.CustomVerifyDialog;
import com.moxie.client.widget.LoadingDialog.LoadingFlower;
import com.moxie.client.widget.LoadingDialog.LoadingFlower.Builder;
import com.moxie.client.widget.TitleLayout;
import com.proguard.annotation.NotProguard;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

@NotProguard
/* compiled from: TbsSdkJava */
public class MainActivity extends MainEventActivity implements OnClickListener {
    public static final String TAG = "MainActivity";
    private FrameLayout frameLayout;
    private FrameLayout frameLayoutAgreement;
    private FrameLayout frameLayoutImporting;
    private volatile boolean isAttach = false;
    private boolean isBackClicked = false;
    private Boolean isSDKStart = Boolean.valueOf(false);
    boolean loginDone = false;
    private String mAccount = "";
    private String mAppendResult;
    private CustomVerifyDialog mCustomVerifyDialog;
    private Boolean mDirect = false;
    private FragmentEnum mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_AGREEMENT;
    private FragmentEnum mFragmentBefore = FragmentEnum.MX_CLIENT_FRAGMENT_AGREEMENT;
    private Fragment mImportResultCustomFragment = null;
    private ImportResultFragment mImportResultFragment = null;
    private boolean mIsUploaded = false;
    private LoadingFlower mLoadingFlower = null;
    private String mLoginCode = "";
    private SiteAccountInfo mMailBoxAccountInfo;
    private MxParam mParam = null;
    private int mResult = -1;
    private String mResultMessage = "";
    private String mTaskId = "";
    private String mTitleContent = "";
    private TitleLayout mTitleLayout;
    private int mTitleResId = 0;
    private String mUDCreditResult = "";
    private WebViewAgreementH5Fragment mWebViewAgreementH5Fragment = null;
    private WebViewMoxieH5Fragment mWebViewCommonFragment = null;
    private WebViewECV3Fragment mWebViewECH5Fragment = null;
    private WebViewOfficialH5Fragment mWebViewOfficialH5Fragment = null;
    FrameLayout thirdPartyViewContainer;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getResources().getIdentifier("moxie_client_activity_main", "layout", getPackageName()));
        logInitParams();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.mParam = (MxParam) extras.getParcelable("param");
        }
        if (this.mParam == null || TextUtils.isEmpty(this.mParam.getUserId()) || TextUtils
                .isEmpty(this.mParam.getApiKey()) || TextUtils.isEmpty(this.mParam.getFunction())) {
            toast("缺少必填参数，userId、apiKey、function三者缺一不可!");
            return;
        }
        new StringBuilder().append(this.mParam.getApiKey()).append("-").append(this.mParam.getFunction()).append("-")
                .append(this.mParam.getUserId()).append("-").append(this.mParam.getBannerBgColor()).append("-")
                .append(this.mParam.getBannerTxtColor()).append("-").append(this.mParam.getThemeColor()).append("-")
                .append(this.mParam.getAgreementUrl());
        GlobalParams.i().a(this.mParam);
        GlobalParams.i().g(GlobalConstants.a);
        init();
        if (GlobalParams.i().a().getLoginCustom() != null) {
            if (GlobalParams.i().a().getLoginCustom().containsKey(MxParam.PARAM_CUSTOM_LOGIN_CODE)) {
                this.mLoginCode = (String) GlobalParams.i().a().getLoginCustom().get(MxParam.PARAM_CUSTOM_LOGIN_CODE);
                this.mDirect = Boolean.valueOf(true);
            }
        } else if (!GlobalParams.i().a().getItemCode().equalsIgnoreCase("")) {
            this.mLoginCode = GlobalParams.i().a().getItemCode();
            this.mDirect = Boolean.valueOf(true);
        }
        new StringBuilder("mDirect=").append(this.mDirect);
        loadConfig();
        this.mLoadingFlower = new Builder(this).c().a().b().d();
        this.mLoadingFlower.show();
        this.mLoadingFlower.setCancelable(false);
        checkPermission();
    }

    private void init() {
        initTitle();
        initSoftInputMode();
        SharedPreferMgr.a((Context) this);
        MxACRA.b();
    }

    private void loadConfig() {//只跳支付宝
        if (this.mParam.getSubType().equalsIgnoreCase("sdk")) {
            new LoadSiteConfigTask("ec").execute(new Void[0]);
        }
    }

    private void checkPermission() {
        if (GlobalConstants.f.booleanValue()) {
            new CheckAuthUDCreditTask(this.mParam.getApiKey(), this.mParam.getUserId(),
                    this.mParam.getFunction().toLowerCase()).execute(new Void[0]);
        } else {
            new LoadTenantPermissionTask(this.mParam.getApiKey()).execute(new Void[0]);
        }
    }

    private void logInitParams() {
    }

    private void initTitle() {
        this.frameLayout = (FrameLayout) findViewById(
                getResources().getIdentifier("moxie_client_fragment_main", "id", getPackageName()));
        this.frameLayoutAgreement = (FrameLayout) findViewById(
                getResources().getIdentifier("moxie_client_fragment_agreement", "id", getPackageName()));
        this.frameLayoutImporting = (FrameLayout) findViewById(
                getResources().getIdentifier("moxie_client_fragment_importing", "id", getPackageName()));
        this.mTitleLayout = (TitleLayout) findViewById(
                getResources().getIdentifier("MoxieClientTitleLayout", "id", getPackageName()));
        this.mTitleLayout.initTitleLayout();
        this.mTitleLayout.getRelativeLayout_Title_Left().setOnClickListener(this);
        this.mTitleLayout.getRelativeLayout_Title_Right().setOnClickListener(this);
        this.mTitleLayout.getLeftTextView().setOnClickListener(this);
        this.mTitleLayout.getLeftImage().setOnClickListener(this);
        this.mTitleLayout.setImageRightVisibility(8);
        this.mTitleLayout.getRightImage().setOnClickListener(this);
    }

    private void initSoftInputMode() {
        if (GlobalParams.i().a().getTitleParams() == null || !GlobalParams.i().a().getTitleParams()
                .isImmersedEnable()) {
            getWindow().setSoftInputMode(18);
        } else if (VERSION.SDK_INT >= 19) {
            getWindow().setSoftInputMode(32);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        try {
            if (id == getResources().getIdentifier("Moxie_Client_RelativeLayout_Title_Left", "id", getPackageName())
                    || id == getResources().getIdentifier("Moxie_Client_ImageView_Left", "id", getPackageName())) {
                onBackInternal();
            } else if (id == getResources()
                    .getIdentifier("Moxie_Client_RelativeLayout_Title_Right", "id", getPackageName())
                    || id == getResources().getIdentifier("Moxie_Client_ImageView_Right", "id", getPackageName())) {
                refreshFragementRightImage();
            } else if (id == getResources().getIdentifier("moxie_client_actionbar_left_text", "id", getPackageName())) {
                internalFinish();
            }
        } catch (Throwable e) {
            ErrorHandle.b("onClick fail", e);
        }
    }

    private void start() {
        /*  339 */
        this.isSDKStart = Boolean.valueOf(true);
        /*      */
        /*  342 */
        getSysConfigs();
        /*      */
        /*  344 */
        switch (this.mParam.getFunction()) {
            /*      */
            case "email":
                /*  346 */
                if (this.mDirect.booleanValue()) {
                    /*  347 */
                    openMailImportWebView();
                    return;
                    /*      */
                }
                /*  349 */
                openCommonWebView(Boolean.valueOf(false));
                /*  350 */
                return;
            /*      */
            case "bank":
                /*  352 */
                openCommonWebView(this.mDirect);
                /*  353 */
                return;
            /*      */
            case "qq":
                /*  355 */
                openQQZone();
                /*  356 */
                return;
            /*      */
            case "linkedin":
                /*  358 */
                openLinkedInWebView();
                /*  359 */
                return;
            /*      */
            /*      */
            case "jingdong":
            case "taobao":
            case "alipay"://// TODO: 2017/9/14
                initEC(this.mParam.getFunction());
                /*  364 */
                return;
            /*      */
            case "carrier":
                /*      */
            case "chsi":
                /*      */
            case "fund":
                /*      */
            case "insurance":
                /*      */
            case "maimai":
                /*      */
            case "sametrade":
                /*      */
            case "security":
                /*      */
            case "shixincourt":
                /*      */
            case "tax":
                /*      */
            case "zhengxin":
                /*      */
            case "zhixingcourt":
                /*  376 */
                openCommonWebView(this.mDirect);
                /*  377 */
                return;
            /*      */
        }
        /*  379 */
        toast("缺少必填参数，userId、apiKey、function三者缺一不可!");
        /*      */
    }

    private void initEC(String paramString) {
        android.util.Log.e("gq", "initEC");
        /*  395 */
        String str = String.format(
                UrlParser.a("https://api.51datakey.com/h5/importV3/index.html#/%s/?userId=%s&apiKey=%s&themeColor=%s",
                        /*  393 */       this.mDirect.booleanValue()),
                /*  395 */       new Object[] {paramString, this.mParam
                        /*  397 */.getUserId(), this.mParam
                        /*  398 */.getApiKey(), this.mParam
                        /*  399 */.getThemeColor().replace("#", "")});
        /*      */
        /*  401 */
        if (paramString.equalsIgnoreCase("alipay"))
            /*  402 */ {//// TODO: 2017/9/14
            this.mTitleContent = getResources().getText(
                    getResources().getIdentifier("moxie_client_alipay_webview_title", "string", getPackageName()))
                    .toString();
        }
        /*  403 */
        else if (paramString.equalsIgnoreCase("jingdong"))
            /*  404 */ {
            this.mTitleContent = getResources().getText(
                    getResources().getIdentifier("moxie_client_jingdong_webview_title", "string", getPackageName()))
                    .toString();
        }
        /*      */
        else {
            /*  406 */
            this.mTitleContent = getResources().getText(
                    getResources().getIdentifier("moxie_client_taobao_webview_title", "string", getPackageName()))
                    .toString();
            /*      */
        }
        /*  408 */
        if (this.mParam.getSubType().equalsIgnoreCase("api")) {
            /*  409 */
            showWebViewCommonFragment(this.mTitleContent, str);
            return;
            /*      */
        }
        /*  411 */
        if (paramString.equalsIgnoreCase("jingdong"))
            /*  412 */ {
            paramString = "jd";
        }
        /*      */
        Object localObject;
        /*  415 */
        if (!TextUtils.isEmpty(SharedPreferMgr.b("moxie_sdk_all_ec_config")))
            /*      */ {
            /*      */
            try
                /*      */ {
                /*  418 */
                if ((
                        /*  418 */           localObject = LoadSiteConfigApi
                        .a((String) SharedPreferMgr.b("moxie_sdk_all_ec_config"), "ec"))
                        != null)
                    /*      */ {
                    /*  420 */
                    if ((
                            /*  420 */             localObject = ((SiteConfigsResponse) localObject).a) != null)
                        /*      */ {
                        /*  421 */
                        for (localObject = ((ArrayList) localObject).iterator(); ((Iterator) localObject).hasNext(); ) {
                            SiteConfigItem localSiteConfigItem = (SiteConfigItem) ((Iterator) localObject).next();
                            /*  422 */
                            if ((paramString + ".com").equalsIgnoreCase(localSiteConfigItem.b)) {
                                /*  423 */
                                if (localSiteConfigItem.c.k().equalsIgnoreCase("1"))
                                    /*      */ {
                                    /*  425 */
                                    showWebViewECFragment(paramString + ".com");
                                    return;
                                    /*  428 */
                                }
                                /*      */
                                showWebViewCommonFragment(this.mTitleContent, str);
                                /*      */
                                return;
                                /*      */
                            }
                        }
                        /*      */
                    }
                    /*      */
                }
                /*      */
                return;
                /*      */
            } catch (Exception localException) {
                /*  435 */
                ErrorHandle.b("try open ec fail", localException);
                /*      */
            }
            /*      */
        }
        /*      */
    }

    public void addView(View view) {
        if (this.thirdPartyViewContainer == null) {
            this.thirdPartyViewContainer = (FrameLayout) findViewById(
                    getResources().getIdentifier("third_party_view_container", "id", getPackageName()));
        }
        this.thirdPartyViewContainer.removeAllViews();
        this.thirdPartyViewContainer.addView(view);
    }

    private void openQQZone() {
        String b = SharedPreferMgr.b("moxie_sdk_all_qzone_config");
        if (!TextUtils.isEmpty(b)) {
            try {
                SiteConfigsResponse a = LoadSiteConfigApi.a(b, "qzone");
                if (a != null) {
                    ArrayList arrayList = a.a;
                    if (arrayList != null) {
                        Iterator it = arrayList.iterator();
                        if (!it.hasNext()) {
                            return;
                        }
                        if (((SiteConfigItem) it.next()).c.k().equalsIgnoreCase("1")) {
                            showWebViewOfficialH5Fragment("qzone.com", getResources()
                                    .getIdentifier("moxie_client_qzone_webview_title", "string", getPackageName()));
                        } else {
                            showWebViewMoxieH5Fragment(getResources()
                                    .getIdentifier("moxie_client_qzone_webview_title", "string", getPackageName()));
                        }
                    }
                }
            } catch (Throwable e) {
                ErrorHandle.b("try open qqZone fail", e);
            }
        }
    }

    private void openMailImportWebView() {
        String b = SharedPreferMgr.b("moxie_sdk_all_mail_config");
        if (!TextUtils.isEmpty(b)) {
            try {
                SiteConfigsResponse a = LoadSiteConfigApi.a(b, "mailbox");
                if (a != null) {
                    ArrayList arrayList = a.a;
                    if (arrayList != null) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            SiteConfigItem siteConfigItem = (SiteConfigItem) it.next();
                            if (this.mLoginCode.equalsIgnoreCase(siteConfigItem.b)) {
                                if (siteConfigItem.c.k().equalsIgnoreCase("1")) {
                                    showWebViewOfficialH5Fragment(this.mLoginCode, getResources()
                                            .getIdentifier("moxie_client_email_webview_title", "string",
                                                    getPackageName()));
                                    return;
                                } else {
                                    showWebViewMoxieH5Fragment(getResources()
                                            .getIdentifier("moxie_client_email_webview_title", "string",
                                                    getPackageName()));
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                ErrorHandle.b("try open mailImportWebView fail", e);
            }
            showWebViewMoxieH5Fragment(
                    getResources().getIdentifier("moxie_client_email_webview_title", "string", getPackageName()));
        }
    }

    private void openLinkedInWebView() {
        String b = SharedPreferMgr.b("moxie_sdk_all_linkedin_config");
        if (!TextUtils.isEmpty(b)) {
            try {
                SiteConfigsResponse a = LoadSiteConfigApi.a(b, MxParam.PARAM_FUNCTION_LINKEDIN);
                if (a != null) {
                    ArrayList arrayList = a.a;
                    if (arrayList != null) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            SiteConfigItem siteConfigItem = (SiteConfigItem) it.next();
                            if (siteConfigItem.b.equalsIgnoreCase(this.mParam.getFunction())) {
                                if (siteConfigItem.c.k().equalsIgnoreCase("1")) {
                                    showWebViewOfficialH5Fragment(this.mParam.getFunction(), getResources()
                                            .getIdentifier("moxie_client_linkedin_webview_title", "string",
                                                    getPackageName()));
                                    return;
                                } else {
                                    showWebViewMoxieH5Fragment(getResources()
                                            .getIdentifier("moxie_client_linkedin_webview_title", "string",
                                                    getPackageName()));
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                ErrorHandle.b("try open linkedInWebView fail", e);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void openCommonWebView(Boolean paramBoolean) {
        //do nothing deleted by gq
    }

    public void onEventMainThread(ScreenCaptureEvent screenCaptureEvent) {
        super.onEventMainThread(screenCaptureEvent);
        try {
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            ScreenCaptureFragment screenCaptureFragment =
                    (ScreenCaptureFragment) getFragmentManager().findFragmentByTag("capture");
            if (screenCaptureFragment == null) {
                Fragment screenCaptureFragment2 = new ScreenCaptureFragment();
                beginTransaction.addToBackStack("capture");
                beginTransaction.add(screenCaptureFragment2, "capture");
            } else {
                beginTransaction.show(screenCaptureFragment);
                screenCaptureFragment.a();
            }
            beginTransaction.commit();
        } catch (Throwable e) {
            ErrorHandle.b("onEventMainThread#ScreenCaptureEvent", e);
        }
    }

    public void onEventMainThread(SetResultEvent setResultEvent) {
        this.mAppendResult = setResultEvent.a;
    }

    public void onEventMainThread(CheckAuthUDCredit checkAuthUDCredit) {
        String str;
        super.onEventMainThread(checkAuthUDCredit);
        String str2 = "";
        if (TextUtils.isEmpty(checkAuthUDCredit.b)) {
            str = "鉴权失败，请稍候再试!";
        } else {
            str = str2;
        }
        HashMap a = MxResponseHelper.a(checkAuthUDCredit.b);
        if (a == null) {
            str = "鉴权失败，请稍候再试!";
        } else if (((String) a.get("result")).toString().equalsIgnoreCase("false")) {
            if (a.containsKey(TaskStatus.MESSAGE)) {
                str = (String) a.get(TaskStatus.MESSAGE);
            }
            if (a.containsKey("errorcode")) {
                this.mUDCreditResult = (String) a.get("errorcode");
            }
        }
        if (str.equalsIgnoreCase("")) {
            new LoadTenantPermissionTask(this.mParam.getApiKey()).execute(new Void[0]);
            return;
        }
        this.mLoadingFlower.dismiss();
        this.mResultMessage = str;
        toast(str);
    }

    public void onEventMainThread(CanLeaveEvent canLeaveEvent) {
        this.loginDone = canLeaveEvent.a;
        attachImportResultFragment();
    }

    public void onEventMainThread(OpenThirdPartEvent openThirdPartEvent) {
        if (!TextUtils.isEmpty(openThirdPartEvent.a)) {
            CharSequence charSequence = "";
            try {
                JSONObject jSONObject = new JSONObject(openThirdPartEvent.a);
                if (jSONObject.has("url")) {
                    String string = jSONObject.getString("url");
                    String string2 = jSONObject.getString(TaskStatus.MESSAGE);
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(string)));
                }
            } catch (Exception e) {
                if (!TextUtils.isEmpty(charSequence)) {
                    Toast.makeText(this, charSequence, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onEventMainThread(PermissionJson permissionJson) {
        super.onEventMainThread(permissionJson);
        new StringBuilder("permission=").append(permissionJson.b);
        this.mLoadingFlower.dismiss();
        if (permissionJson.b == null) {
            toast("网络异常，请稍候再试!");
        } else if (checkPermission(permissionJson.b)) {
            runOnUiThread(new Runnable() {/*      */
                public void run() {
                    /*      */
                    try {
                        /*  877 */
                        int i = 50;
                        /*  878 */
                        while (i > 0) {
                            /*  879 */
                            Thread.sleep(200L);
                            /*  880 */
                            String str1 = "";
                            /*  881 */
                            switch (MainActivity.this.mParam.getFunction()) {
                                /*      */
                                case "bank":
                                    /*  883 */
                                    if (GlobalConstants.c.booleanValue()) {
                                        /*  884 */
                                        str1 = SharedPreferMgr.b("moxie_sdk_all_onlinebank_config");
                                        /*      */
                                    } else {
                                        MainActivity.this.start();
                                        /*      */
                                        return;
                                        /*      */
                                    }
                                    /*      */
                                    break;
                                /*      */
                                case "email":
                                    /*  891 */
                                    str1 = SharedPreferMgr.b("moxie_sdk_all_mail_config");
                                    /*  892 */
                                    break;
                                /*      */
                                case "alipay":
                                    /*      */
                                case "jingdong":
                                    /*      */
                                case "taobao":
                                    /*  896 */
                                    if (MainActivity.this.mParam.getSubType().equalsIgnoreCase("api")) {
                                        /*  897 */
                                        MainActivity.this.start();
                                        /*  898 */
                                        return;
                                        /*      */
                                    }
                                    /*  900 */
                                    str1 = SharedPreferMgr.b("moxie_sdk_all_ec_config");
                                    /*  901 */
                                    break;
                                /*      */
                                case "qq":
                                    /*  903 */
                                    str1 = SharedPreferMgr.b("moxie_sdk_all_qzone_config");
                                    /*  904 */
                                    break;
                                /*      */
                                case "linkedin":
                                    /*  906 */
                                    str1 = SharedPreferMgr.b("moxie_sdk_all_linkedin_config");
                                    /*  907 */
                                    break;
                                /*      */
                                case "carrier":
                                    /*      */
                                case "chsi":
                                    /*      */
                                case "fund":
                                    /*      */
                                case "insurance":
                                    /*      */
                                case "maimai":
                                    /*      */
                                case "sametrade":
                                    /*      */
                                case "security":
                                    /*      */
                                case "shixincourt":
                                    /*      */
                                case "tax":
                                    /*      */
                                case "zhengxin":
                                    /*      */
                                case "zhixingcourt":
                                    /*  919 */
                                    MainActivity.this.start();
                                    /*  920 */
                                    return;
                                /*      */
                            }
                            /*      */
                            /*  923 */
                            if (!TextUtils.isEmpty(str1)) {
                                /*  924 */
                                MainActivity.this.start();
                                /*  925 */
                                return;
                                /*      */
                            }
                            /*  927 */
                            i -= 1;
                            /*      */
                        }
                        /*  929 */
                        if (i <= 0)
                            /*  930 */ {
                            MainActivity.this.toast("网络异常，请稍候再试!");
                        }
                        /*      */
                        return;
                        /*      */
                    } catch (Exception localException) {
                        /*  933 */
                        ErrorHandle.b("MainActivity checkPermission error", localException);
                        /*      */
                    }
                    /*      */
                }
            });
        } else {
            toast("抱歉，您未开通此功能!");
        }
    }

    public boolean checkPermission(String str) {
        if (!TextUtils.isEmpty(str)) {
            ArrayList parsePermissionJson = LoadTenantPermissionApi.parsePermissionJson(str);
            if (parsePermissionJson != null) {
                Iterator it = parsePermissionJson.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    if (this.mParam.getFunction().equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
                        return true;
                    }
                    if (str2.contains(this.mParam.getFunction())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void onEventMainThread(NoPermission noPermission) {
        super.onEventMainThread(noPermission);
        toast("抱歉，您未开通此功能!");
    }

    private void showTaskStatus(String str) {
        showTaskStatus(str, null);
    }

    private void showTaskStatus(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TaskStatus.MESSAGE, str);
            jSONObject.put(TaskStatus.PERCENT, str2);
            jSONObject.put(TaskStatus.ACCOUNT, this.mAccount);
            jSONObject.put(TaskStatus.LOGINDONE, this.loginDone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showTaskStatus(jSONObject);
    }

    private void showTaskStatus(final JSONObject jSONObject) {
        if (jSONObject != null) {
            runOnUiThread(new Runnable() {
                final /* synthetic */ MainActivity b = MainActivity.this;

                public void run() {
                    if (this.b.mImportResultFragment != null) {
                        if (TextUtils.isEmpty(GlobalParams.i().a().getLoadingViewText())) {
                            this.b.mImportResultFragment.updateProgress(jSONObject);
                        } else {
                            this.b.mImportResultFragment
                                    .viewTaskStatusWithCustomText(GlobalParams.i().a().getLoadingViewText(),
                                            jSONObject.optString(TaskStatus.PERCENT));
                        }
                    } else if (this.b.mImportResultCustomFragment != null) {
                        ((StatusViewListener) this.b.mImportResultCustomFragment).updateProgress(jSONObject);
                    }
                }
            });
        }
    }

    private void showSubmitErrorToast(final String str) {
        this.mResultMessage = str;
        runOnUiThread(new Runnable() {
            final /* synthetic */ MainActivity b = MainActivity.this;

            public void run() {
                if (this.b.mFragment == FragmentEnum.MX_CLIENT_FRAGMENT_IMPORTING) {
                    this.b.toast(str);
                }
            }
        });
    }

    private void showVerifyDialog(String str, TaskStatusResult taskStatusResult) {
        final int i;
        String str2;
        this.mCustomVerifyDialog = new CustomVerifyDialog(this);
        this.mCustomVerifyDialog.setLeftButtonText("取消");
        this.mCustomVerifyDialog.setRightButtonText("提交");
        final EditText verifyEdit = this.mCustomVerifyDialog.getVerifyEdit();
        ImageView verifyImage = this.mCustomVerifyDialog.getVerifyImage();
        this.mCustomVerifyDialog.getLoadingText();
        if (taskStatusResult.f().equals("img")) {
            this.mCustomVerifyDialog.showVerifyPlace();
            this.mCustomVerifyDialog.showVerifyLoading();
            this.mCustomVerifyDialog.setEditVerifyHint("请输入验证码");
            Bitmap a = CommonMethod.a(taskStatusResult.g());
            if (a != null) {
                verifyImage.setImageBitmap(a);
            }
            i = 1;
            str2 = "请输入验证码";
        } else if (taskStatusResult.f().equals("sms")) {
            this.mCustomVerifyDialog.hideVerifyPlace();
            this.mCustomVerifyDialog.setEditVerifyHint("请输入手机验证码");
            i = 3;
            str2 = "请输入手机验证码";
        } else {
            this.mCustomVerifyDialog.hideVerifyPlace();
            this.mCustomVerifyDialog.setEditVerifyHint("请输入独立密码");
            i = 2;
            str2 = "请输入独立密码";
        }
        if (!TextUtils.isEmpty(str2)) {
            this.mCustomVerifyDialog.setTitle(str2);
        }
        this.mCustomVerifyDialog.setRightButtonListener(new OnClickListener() {
            final /* synthetic */ MainActivity c = MainActivity.this;

            public void onClick(View view) {
                String str = null;
                if (verifyEdit != null) {
                    str = verifyEdit.getText().toString();
                }
                if (!TextUtils.isEmpty(str)) {
                    String str2 = "";
                    String str3 = "";
                    if (i == 1) {
                        String str4 = str3;
                        str3 = str;
                        str = str4;
                    } else if (i == 2) {
                        this.c.mMailBoxAccountInfo.n(str);
                        str = str3;
                        str3 = str2;
                    } else if (i == 3) {
                        str3 = str2;
                    } else {
                        str = str3;
                        str3 = str2;
                    }
                    this.c.mCustomVerifyDialog.dismiss();
                    this.c.inputVerifyCode(this.c.mMailBoxAccountInfo, str3, str);
                } else if (i == 1) {
                    this.c.toast("验证码为空");
                } else if (i == 2) {
                    this.c.toast("独立密码为空");
                } else if (i == 3) {
                    this.c.toast("手机验证码为空");
                }
            }
        });
        this.mCustomVerifyDialog.setLeftButtonListener(new OnClickListener() {

            public void onClick(View view) {
                MainActivity.this.mCustomVerifyDialog.dismiss();
            }
        });
        this.mCustomVerifyDialog.show();
    }

    private void toast(String str) {
        if (!TextUtils.isEmpty(str)) {
            CommonMethod.b(getApplicationContext(), str);
        }
    }

    public void onEventMainThread(SiteAccountInfo siteAccountInfo) {
        super.onEventMainThread(siteAccountInfo);
        this.mAccount = siteAccountInfo.m();
        if (siteAccountInfo.i().toLowerCase().equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
            this.mIsUploaded = false;
        }
        showImportingFragment();
    }

    public void onEventMainThread(LoginSubmitErrorEvent loginSubmitErrorEvent) {
        super.onEventMainThread(loginSubmitErrorEvent);
        this.mResult = -3;
        if (loginSubmitErrorEvent.c.i().toLowerCase().equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
            this.mWebViewECH5Fragment.e().a(loginSubmitErrorEvent.a, loginSubmitErrorEvent.c);
        }
        showTaskStatus(loginSubmitErrorEvent.b);
        showSubmitErrorToast(loginSubmitErrorEvent.b);
    }

    public void onEventMainThread(LoginSubmitSuccessEvent loginSubmitSuccessEvent) {
        super.onEventMainThread(loginSubmitSuccessEvent);
        this.mTaskId = loginSubmitSuccessEvent.c.j();
        if (loginSubmitSuccessEvent.c.i().toLowerCase().equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
            this.mWebViewECH5Fragment.e().a(loginSubmitSuccessEvent.a, loginSubmitSuccessEvent.c);
        }
        showTaskStatus(loginSubmitSuccessEvent.b);
    }

    public void onEventMainThread(TaskStatusWorkingEvent taskStatusWorkingEvent) {
        super.onEventMainThread(taskStatusWorkingEvent);
        this.mResult = 2;
        new StringBuilder("mIsUpload=").append(this.mIsUploaded).append("  siteAccount=")
                .append(taskStatusWorkingEvent.c).append(" mResult=").append(this.mResult);
        if (!this.mIsUploaded && taskStatusWorkingEvent.c == null) {
            showTaskStatus(taskStatusWorkingEvent.e.a, taskStatusWorkingEvent.e.b);
        } else if (this.mIsUploaded || !taskStatusWorkingEvent.c.i().toLowerCase()
                .equalsIgnoreCase(MxParam.PARAM_FUNCTION_EC)) {
            showTaskStatus(taskStatusWorkingEvent.e.a, taskStatusWorkingEvent.e.b);
        } else if (this.mParam.isQuitDisable()) {
            this.mResult = 2;
        } else {
            this.mResult = 0;
        }
    }

    public void onEventMainThread(TaskStatusProgressEvent taskStatusProgressEvent) {
        showTaskStatus(taskStatusProgressEvent.e.a, taskStatusProgressEvent.e.b);
    }

    public void onEventMainThread(TaskStatusFinishErrorEvent taskStatusFinishErrorEvent) {
        super.onEventMainThread(taskStatusFinishErrorEvent);
        this.mResult = 0;
        showTaskStatus(taskStatusFinishErrorEvent.b);
        showSubmitErrorToast(taskStatusFinishErrorEvent.b);
    }

    public void onEventMainThread(TaskStatusFinishSuccessEvent taskStatusFinishSuccessEvent) {
        super.onEventMainThread(taskStatusFinishSuccessEvent);
        HashMap extendParams = GlobalParams.i().a().getExtendParams();
        if (extendParams == null || !extendParams.containsKey(MxParam.PARAM_QUIT_LOGIN_DONE)) {
            if (GlobalParams.i().a().getQuitLoginDone().equalsIgnoreCase(MxParam.PARAM_COMMON_NO)) {
                this.mResult = 1;
            }
        } else if (((String) extendParams.get(MxParam.PARAM_QUIT_LOGIN_DONE))
                .equalsIgnoreCase(MxParam.PARAM_COMMON_NO)) {
            this.mResult = 1;
        }
        internalFinish();
    }

    /**
     * 文件上传成功后（upload）在通知
     *
     * @param uploadFileEvent
     */
    public void onEventMainThread(final UploadFileEvent uploadFileEvent) {
        super.onEventMainThread(uploadFileEvent);
        new Thread(new Runnable() {
            final /* synthetic */ MainActivity b = MainActivity.this;

            public void run() {
                boolean isWifi = true;
                LogTrackInfo logTrackInfo;
                try {
                    String str;
                    boolean bool;
                    if (CommonMethod.f(this.b)) {//wifi
                        str = "https://api.51datakey.com" + "/gateway/v1/tasks/{task_id}/upload1"
                                .replace("{task_id}", this.b.mTaskId);
                    } else {
                        str = "https://api.51datakey.com" + "/gateway/v1/tasks/{task_id}/upload"
                                .replace("{task_id}", this.b.mTaskId) + "?fileType=zip";
                        isWifi = false;
                    }
                    UploadFile uploadFile = new UploadFile();
                    HashMap hashMap = new HashMap();
                    hashMap.put("Authorization", "apikey " + this.b.mParam.getApiKey());
                    if (TextUtils.isEmpty(uploadFileEvent.zippedFile)) {
                        bool = uploadFile.a(uploadFileEvent.b, str, hashMap, isWifi);
                    } else {
                        bool = uploadFile.a(this.b, uploadFileEvent.zippedFile, str, hashMap, isWifi);
                    }
                    if (bool) {
                        this.b.mIsUploaded = true;
                        this.b.mResult = 2;
                    } else {
                        this.b.mResult = 0;
                        this.b.showSubmitErrorToast("导入失败,请稍后重试！");
                    }
                    try {
                        logTrackInfo = new LogTrackInfo();
                        logTrackInfo.d(this.b.mParam.getFunction());
                        logTrackInfo.a();
                        if (this.b.mIsUploaded) {
                            logTrackInfo.a("文件上传成功");
                            logTrackInfo.c("DONE_WITH_SUCC");
                            logTrackInfo.b("CR-20000-00");
                        } else {
                            logTrackInfo.a("文件上传失败");
                            logTrackInfo.c("DONE_WITH_FAIL");
                            logTrackInfo.b("CR-20339-20");
                        }
                        this.b.uploadLogTracking(logTrackInfo.b());
                    } catch (Throwable e) {
                        ErrorHandle.b("Upload File tracking error", e);
                    }
                } catch (Throwable e2) {
                    ErrorHandle.b("Upload File error", e2);
                    try {
                        logTrackInfo = new LogTrackInfo();
                        logTrackInfo.d(this.b.mParam.getFunction());
                        logTrackInfo.a();
                        if (this.b.mIsUploaded) {
                            logTrackInfo.a("文件上传成功");
                            logTrackInfo.c("DONE_WITH_SUCC");
                            logTrackInfo.b("CR-20000-00");
                        } else {
                            logTrackInfo.a("文件上传失败");
                            logTrackInfo.c("DONE_WITH_FAIL");
                            logTrackInfo.b("CR-20339-20");
                        }
                        this.b.uploadLogTracking(logTrackInfo.b());
                    } catch (Throwable e22) {
                        ErrorHandle.b("Upload File tracking error", e22);
                    }
                }
            }
        }).start();
    }

    public void onEventMainThread(List<JSONObject> list) {
        super.onEventMainThread((List) list);
        LogTracker.a().a((List) list, this.mTaskId);
    }

    private void uploadLogTracking(JSONObject jSONObject) {
        if (!TextUtils.isEmpty(this.mTaskId)) {
            LogTracker.a().a(jSONObject, this.mTaskId);
        }
    }

    public void onEventMainThread(TaskStatusVerifycodeEvent taskStatusVerifycodeEvent) {
        super.onEventMainThread(taskStatusVerifycodeEvent);
        this.mMailBoxAccountInfo = taskStatusVerifycodeEvent.c;
        showVerifyDialog(taskStatusVerifycodeEvent.b, taskStatusVerifycodeEvent.d);
    }

    public void onEventMainThread(TaskStatusVerifycodeSuccessEvent taskStatusVerifycodeSuccessEvent) {
        super.onEventMainThread(taskStatusVerifycodeSuccessEvent);
        showTaskStatus(taskStatusVerifycodeSuccessEvent.b);
    }

    public void onEventMainThread(TaskStatusVerifycodeErrorEvent taskStatusVerifycodeErrorEvent) {
        super.onEventMainThread(taskStatusVerifycodeErrorEvent);
        showTaskStatus(taskStatusVerifycodeErrorEvent.b);
        showSubmitErrorToast(taskStatusVerifycodeErrorEvent.b);
    }

    public void onEventMainThread(ViewH5ImortResult viewH5ImortResult) {
        super.onEventMainThread(viewH5ImortResult);
        this.mResult = viewH5ImortResult.a;
        if (viewH5ImortResult.a == 1) {
            setResultToCalledActivity();
            internalFinish();
        }
    }

    public void onEventMainThread(BackToClose backToClose) {
        super.onEventMainThread(backToClose);
        runOnUiThread(new Runnable() {

            public void run() {
                MainActivity.this.onBackInternal();
            }
        });
    }

    public void onEventMainThread(SaveTaskId saveTaskId) {
        super.onEventMainThread(saveTaskId);
        this.mTaskId = saveTaskId.b;
        this.mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_IMPORTING;
    }

    public void onEventMainThread(SaveAccountInfo saveAccountInfo) {
        super.onEventMainThread(saveAccountInfo);
        this.mAccount = saveAccountInfo.b;
        statusCallback("", "");
    }

    public void statusCallback(final String str, final String str2) {
        runOnUiThread(new Runnable() {
            final /* synthetic */ MainActivity c = MainActivity.this;

            public void run() {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TaskStatus.ACCOUNT, this.c.mAccount);
                    jSONObject.put(TaskStatus.LOGINDONE, this.c.loginDone);
                    jSONObject.put(TaskStatus.MESSAGE, str);
                    jSONObject.put(TaskStatus.PERCENT, str2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (this.c.mImportResultCustomFragment != null
                        && (this.c.mImportResultCustomFragment instanceof StatusViewListener)) {
                    ((StatusViewListener) this.c.mImportResultCustomFragment).updateProgress(jSONObject);
                }
            }
        });
    }

    public void onEventMainThread(BackToFinish backToFinish) {
        super.onEventMainThread(backToFinish);
        try {
            new StringBuilder("BackToFinish ").append(backToFinish.b);
            String str = "";
            if (backToFinish.b.startsWith("{")) {
                JSONObject jSONObject = new JSONObject(backToFinish.b);
                if (jSONObject.has("code")) {
                    this.mResult = Integer.parseInt(jSONObject.getString("code"));
                }
                if (jSONObject.has("status")) {
                    str = jSONObject.getString("status");
                }
                if (jSONObject.has("msg")) {
                    this.mResultMessage = jSONObject.getString("msg");
                }
                statusCallback(this.mResultMessage, "");
                new StringBuilder("BackToFinish ").append(str.toUpperCase()).append("--")
                        .append(this.mParam.getQuitOnFail());
                if (str.toUpperCase().equalsIgnoreCase("SUCC") || this.mParam.getQuitOnFail()
                        .equalsIgnoreCase(MxParam.PARAM_COMMON_YES)) {
                    internalFinish();
                    return;
                }
                return;
            }
            this.mResult = 1;
            internalFinish();
        } catch (Throwable e) {
            ErrorHandle.b("MainActivity H5 backToFinish error", e);
        }
    }

    public void onEventMainThread(RefreshStatus refreshStatus) {
        super.onEventMainThread(refreshStatus);
        try {
            if (refreshStatus.b.startsWith("{")) {
                JSONObject jSONObject = new JSONObject(refreshStatus.b);
                if (jSONObject.has("code")) {
                    this.mResult = Integer.parseInt(jSONObject.getString("code"));
                }
                if (jSONObject.has("msg")) {
                    this.mResultMessage = jSONObject.getString("msg");
                }
            } else {
                this.mResult = Integer.parseInt(refreshStatus.b);
            }
            statusCallback(this.mResultMessage, "");
        } catch (Throwable e) {
            ErrorHandle.b("MainActivity RefreshStatus error", e);
        }
    }

    public void onEventMainThread(final RefreshTitle refreshTitle) {
        super.onEventMainThread(refreshTitle);
        runOnUiThread(new Runnable() {
            final /* synthetic */ MainActivity b = MainActivity.this;

            public void run() {
                try {
                    if (refreshTitle.b.startsWith("{")) {
                        JSONObject jSONObject = new JSONObject(refreshTitle.b);
                        if (!jSONObject.has("title") || !TextUtils.isEmpty(this.b.mParam.getBannerTxtContent())) {
                            return;
                        }
                        if (this.b.mParam.getTitleParams() == null || TextUtils
                                .isEmpty(this.b.mParam.getTitleParams().getTitle())) {
                            this.b.mTitleLayout.setTitle(jSONObject.getString("title"));
                            this.b.mTitleContent = jSONObject.getString("title");
                        }
                    }
                } catch (Throwable e) {
                    ErrorHandle.b("MainActivity RefreshTitle error", e);
                }
            }
        });
    }

    public void onEventMainThread(final OpenAgreement openAgreement) {
        super.onEventMainThread(openAgreement);
        runOnUiThread(new Runnable() {
            final /* synthetic */ MainActivity b = MainActivity.this;

            public void run() {
                try {
                    String agreementUrl = this.b.mParam.getAgreementUrl();
                    String str = "";
                    String str2 = "";
                    String str3 = "";
                    JSONObject jSONObject = new JSONObject(openAgreement.b);
                    if (jSONObject.has("url") && !TextUtils.isEmpty(jSONObject.getString("url"))) {
                        str = jSONObject.getString("url");
                    }
                    if (!(jSONObject.has("type") && jSONObject.getString("type").equalsIgnoreCase("agreement")
                                  && !TextUtils.isEmpty(agreementUrl))) {
                        agreementUrl = str;
                    }
                    if (jSONObject.has("title") && !TextUtils.isEmpty(jSONObject.getString("title"))) {
                        str2 = jSONObject.getString("title");
                    }
                    if (jSONObject.has("script") && !TextUtils.isEmpty(jSONObject.getString("script"))) {
                        str3 = jSONObject.getString("script");
                    }
                    this.b.showWebViewAgreementH5Fragment(agreementUrl, str2, str3);
                } catch (Throwable e) {
                    ErrorHandle.b("MainActivity OpenAgreement error", e);
                }
            }
        });
    }

    public void onEventMainThread(final ShowOrHiddenWebView showOrHiddenWebView) {
        super.onEventMainThread(showOrHiddenWebView);
        runOnUiThread(new Runnable() {
            final /* synthetic */ MainActivity b = MainActivity.this;

            public void run() {
                if (showOrHiddenWebView.d) {
                    this.b.frameLayout.setVisibility(View.VISIBLE);
                    this.b.frameLayoutImporting.setVisibility(View.GONE);
                    return;
                }
                this.b.frameLayout.setVisibility(View.VISIBLE);
                this.b.frameLayoutImporting.setVisibility(View.GONE);
            }
        });
    }

    public void onEventMainThread(OpenWebView openWebView) {
        super.onEventMainThread(openWebView);
        if (openWebView.a == 0) {
            showWebViewOfficialH5Fragment(openWebView.b.toString(),
                    getResources().getIdentifier("moxie_client_email_webview_title", "string", getPackageName()));
        } else if (openWebView.a == 1) {
            showWebViewMoxieH5Fragment(
                    getResources().getIdentifier("moxie_client_email_webview_title", "string", getPackageName()));
        }
    }

    public void onEventMainThread(OpenOnlieBankWebView openOnlieBankWebView) {
        super.onEventMainThread(openOnlieBankWebView);
        showWebViewCommonFragment(openOnlieBankWebView.c, String.format(UrlParser
                        .a("https://api.51datakey.com/h5/importV3/index.html#/%s/%s?userId=%s&apiKey=%s&themeColor=%s",
                                this.mDirect),
                new Object[] {this.mParam.getFunction(), openOnlieBankWebView.b, this.mParam.getUserId(),
                        this.mParam.getApiKey(), this.mParam.getThemeColor().replace("#", "")}));
    }

    public void onEventMainThread(OpenInsuranceWebView openInsuranceWebView) {
        super.onEventMainThread(openInsuranceWebView);
        showWebViewCommonFragment(openInsuranceWebView.c, String.format(UrlParser
                        .a("https://api.51datakey.com/h5/importV3/index.html#/%s/%s?userId=%s&apiKey=%s&themeColor=%s",
                                this.mDirect),
                new Object[] {this.mParam.getFunction(), openInsuranceWebView.b, this.mParam.getUserId(),
                        this.mParam.getApiKey(), this.mParam.getThemeColor().replace("#", "")}));
    }

    public void onEventMainThread(OpenECWebView openECWebView) {
        super.onEventMainThread(openECWebView);
        showWebViewECFragment(openECWebView.b);
    }

    /**
     * login
     *
     * @param openOfficialWebView
     */
    public void onEventMainThread(OpenOfficialWebView openOfficialWebView) {
        if (!TextUtils.isEmpty(openOfficialWebView.b)) {
            try {
                JSONObject jSONObject = new JSONObject(openOfficialWebView.b);
                showWebViewOfficialH5Fragment(jSONObject.optString("host"), jSONObject.optString(TaskStatus.ACCOUNT),
                        jSONObject.optString("password"),
                        getResources().getIdentifier("moxie_client_email_webview_title", "string", getPackageName()));
            } catch (Throwable e) {
                ErrorHandle.b("MainActivity OpenOfficialWebView error", e);
            }
        }
    }

    private void showWebViewCommonFragment(String str, String str2) {
        try {
            this.mWebViewCommonFragment = new WebViewMoxieH5Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", str2);
            this.mWebViewCommonFragment.setArguments(bundle);
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            beginTransaction.replace(getResources().getIdentifier("moxie_client_fragment_main", "id", getPackageName()),
                    this.mWebViewCommonFragment, "WebViewCommon");
            beginTransaction.commitAllowingStateLoss();
            this.mTitleLayout.setImageRightVisibility(0);
            if (TextUtils.isEmpty(this.mParam.getBannerTxtContent()) && (this.mParam.getTitleParams() == null
                                                                                 || TextUtils
                    .isEmpty(this.mParam.getTitleParams().getTitle()))) {
                if (!(this.mParam.getFunction().equalsIgnoreCase("bank") || this.mParam.getFunction()
                        .equalsIgnoreCase(MxParam.PARAM_FUNCTION_FUND) || this.mParam.getFunction()
                        .equalsIgnoreCase(MxParam.PARAM_FUNCTION_INSURANCE))) {
                    this.mTitleLayout.setTitle(str);
                    this.mTitleContent = str;
                }
            } else if (this.mParam.getTitleParams() == null || TextUtils
                    .isEmpty(this.mParam.getTitleParams().getTitle())) {
                this.mTitleLayout.setTitle(this.mParam.getBannerTxtContent());
                this.mTitleContent = this.mParam.getBannerTxtContent();
            } else {
                this.mTitleLayout.setTitle(this.mParam.getTitleParams().getTitle());
                this.mTitleContent = this.mParam.getTitleParams().getTitle();
            }
            this.mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_IMPORT_MX;
        } catch (Throwable e) {
            ErrorHandle.b("showWebViewCommonFragment fail, taskType=" + this.mParam.getFunction(), e);
            ErrorHandle.a((Context) this, e);
        }
    }

    private void setTitle(String str, String str2) {
        if (TextUtils.isEmpty(this.mParam.getBannerTxtContent()) && (this.mParam.getTitleParams() == null || TextUtils
                .isEmpty(this.mParam.getTitleParams().getTitle()))) {
            this.mTitleLayout.setTitle(str);
            this.mTitleContent = str2;
        } else if (this.mParam.getTitleParams() == null || TextUtils.isEmpty(this.mParam.getTitleParams().getTitle())) {
            this.mTitleLayout.setTitle(this.mParam.getBannerTxtContent());
            this.mTitleContent = this.mParam.getBannerTxtContent();
        } else {
            this.mTitleLayout.setTitle(this.mParam.getTitleParams().getTitle());
            this.mTitleContent = this.mParam.getTitleParams().getTitle();
        }
    }

    private void showWebViewAgreementH5Fragment(String str, String str2, String str3) {
        this.mWebViewAgreementH5Fragment = new WebViewAgreementH5Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putString("script", str3);
        this.mWebViewAgreementH5Fragment.setArguments(bundle);
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction
                .replace(getResources().getIdentifier("moxie_client_fragment_agreement", "id", getPackageName()),
                        this.mWebViewAgreementH5Fragment, "WebViewAgreementH5");
        beginTransaction.commitAllowingStateLoss();
        this.mTitleLayout.setTitle(str2);
        this.frameLayout.setVisibility(View.GONE);
        this.frameLayoutAgreement.setVisibility(View.VISIBLE);
        this.mFragmentBefore = this.mFragment;
        this.mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_AGREEMENT;
    }

    private void showImportingFragment() {
        attachImportResultFragment();
        this.mTitleLayout.setImageRightVisibility(8);
        this.mTitleLayout
                .setTitle(getResources().getIdentifier("moxie_client_email_result_title", "string", getPackageName()));
        this.mTitleResId = getResources().getIdentifier("moxie_client_email_result_title", "string", getPackageName());
        this.frameLayout.setVisibility(View.GONE);
        this.frameLayoutAgreement.setVisibility(View.GONE);
        this.frameLayoutImporting.setVisibility(View.GONE);
        this.mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_IMPORTING;
    }

    private void attachImportResultFragment() {
        StatusViewHandler statusViewHandler = MoxieSDK.getInstance().getStatusViewHandler();
        if (statusViewHandler == null && this.mImportResultFragment == null) {
            this.mImportResultFragment = new ImportResultFragment();
            this.mImportResultCustomFragment = this.mImportResultFragment;
        } else if (statusViewHandler != null && this.mImportResultCustomFragment == null) {
            this.mImportResultCustomFragment = statusViewHandler.statusViewForMoxieSDK();
        }
        if (!this.isAttach) {
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            beginTransaction
                    .replace(getResources().getIdentifier("moxie_client_fragment_importing", "id", getPackageName()),
                            this.mImportResultCustomFragment, "ImportResult");
            beginTransaction.commitAllowingStateLoss();
            this.isAttach = true;
        }
    }

    private void showWebViewOfficialH5Fragment(String str, String str2, String str3, final int i) {
        try {
            this.mWebViewOfficialH5Fragment = new WebViewOfficialH5Fragment();
            Bundle bundle = new Bundle();
            bundle.putString("host", str);
            bundle.putString(TaskStatus.ACCOUNT, str2);
            bundle.putString("password", str3);
            if (this.mDirect && GlobalParams.i().a().getLoginCustom() != null) {
                bundle.putString("loginParams",
                        (String) GlobalParams.i().a().getLoginCustom().get(MxParam.PARAM_CUSTOM_LOGIN_PARAMS));
            }
            this.mWebViewOfficialH5Fragment.setArguments(bundle);
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            beginTransaction.replace(getResources().getIdentifier("moxie_client_fragment_main", "id", getPackageName()),
                    this.mWebViewOfficialH5Fragment, "WebViewCommon");
            beginTransaction.commitAllowingStateLoss();
            runOnUiThread(new Runnable() {
                final /* synthetic */ MainActivity b = MainActivity.this;

                public void run() {
                    this.b.mTitleLayout.setImageRightVisibility(0);
                    this.b.setTitle(this.b.getString(i), "");
                    this.b.mTitleResId = i;
                }
            });
            this.mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_IMPORT;
        } catch (Throwable e) {
            ErrorHandle.b("showWebViewOfficialH5Fragment fail", e);
            ErrorHandle.a((Context) this, e);
        }
    }

    private void showWebViewOfficialH5Fragment(String str, int i) {
        showWebViewOfficialH5Fragment(str, null, null, i);
    }

    private void showWebViewMoxieH5Fragment(int i) {
        String str;
        HashMap extendParams;
        String format = String.format(UrlParser
                        .a("https://api.51datakey.com/h5/importV3/index.html#/%s/?userId=%s&apiKey=%s&themeColor=%s",
                                this.mDirect),
                this.mParam.getFunction(), this.mParam.getUserId(), this.mParam.getApiKey(),
                this.mParam.getThemeColor().replace("#", ""));
        if (this.mDirect) {
            HashMap loginCustom = GlobalParams.i().a().getLoginCustom();
            if (loginCustom != null) {
                format = format + "&loginParams=" + ((String) loginCustom.get(MxParam.PARAM_CUSTOM_LOGIN_PARAMS));
                if (loginCustom.containsKey(MxParam.PARAM_CUSTOM_LOGIN_OTHERS_HIDE)) {
                    str = format + "&loginOthersHide=" + ((String) loginCustom
                            .get(MxParam.PARAM_CUSTOM_LOGIN_OTHERS_HIDE));
                    extendParams = GlobalParams.i().a().getExtendParams();
                    if (extendParams == null && extendParams.containsKey(MxParam.PARAM_QUIT_LOGIN_DONE)) {
                        if (((String) extendParams.get(MxParam.PARAM_QUIT_LOGIN_DONE))
                                .equalsIgnoreCase(MxParam.PARAM_COMMON_YES)) {
                            str = str + "&canLeave=YES";
                        }
                    } else if (GlobalParams.i().a().getQuitLoginDone().equalsIgnoreCase(MxParam.PARAM_COMMON_YES)) {
                        str = str + "&canLeave=YES";
                    }
                    showWebViewCommonFragment(getResources().getString(i), str);
                }
            }
        }
        str = format;
        extendParams = GlobalParams.i().a().getExtendParams();
        if (extendParams == null) {
        }
        if (GlobalParams.i().a().getQuitLoginDone().equalsIgnoreCase(MxParam.PARAM_COMMON_YES)) {
            str = str + "&canLeave=YES";
        }
        showWebViewCommonFragment(getResources().getString(i), str);
    }

    private void showWebViewECFragment(final String str) {
        try {
            runOnUiThread(new Runnable() {
                final /* synthetic */ MainActivity b = MainActivity.this;

                @Override
                public void run() {
                    this.b.mWebViewECH5Fragment = new WebViewECV3Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("host", str);
                    this.b.mWebViewECH5Fragment.setArguments(bundle);
                    FragmentTransaction beginTransaction = this.b.getFragmentManager().beginTransaction();
                    beginTransaction.replace(this.b.getResources()
                                    .getIdentifier("moxie_client_fragment_main", "id", this.b.getPackageName()),
                            this.b.mWebViewECH5Fragment, "WebViewAlipay");
                    beginTransaction.commitAllowingStateLoss();
                    this.b.mTitleLayout.setImageRightVisibility(0);
                    String charSequence = this.b.getResources().getText(this.b.getResources()
                            .getIdentifier("moxie_client_alipay_webview_title", "string", this.b.getPackageName()))
                            .toString();
                    if (str.equalsIgnoreCase("taobao.com")) {
                        charSequence = this.b.getResources().getText(this.b.getResources()
                                .getIdentifier("moxie_client_taobao_webview_title", "string", this.b.getPackageName()))
                                .toString();
                    } else if (str.equalsIgnoreCase("jd.com")) {
                        charSequence = this.b.getResources().getText(this.b.getResources()
                                .getIdentifier("moxie_client_jingdong_webview_title", "string",
                                        this.b.getPackageName())).toString();
                    }
                    this.b.setTitle(charSequence, charSequence);
                    this.b.mFragment = FragmentEnum.MX_CLIENT_FRAGMENT_IMPORT;
                }
            });
        } catch (Throwable e) {
            ErrorHandle.b("showWebViewECFragment fail", e);
            ErrorHandle.a((Context) this, e);
        }
    }

    private void refreshFragementRightImage() {
        new StringBuilder("refreshFragementRightImage start func=").append(this.mParam.getFunction());
        if (this.mFragment.equals(FragmentEnum.MX_CLIENT_FRAGMENT_AGREEMENT)) {
            this.mWebViewAgreementH5Fragment.d();
            return;
        }
        String function = this.mParam.getFunction();
        int obj = -1;
        switch (function) {
            case MxParam.PARAM_FUNCTION_ALIPAY:
                obj = 10;
                break;
            case MxParam.PARAM_FUNCTION_ZHENGXIN:
                obj = 3;
                break;
            case MxParam.PARAM_FUNCTION_MAIMAI:
                obj = 4;
                break;
            case MxParam.PARAM_FUNCTION_TAOBAO:
                obj = 11;
                break;
            case MxParam.PARAM_FUNCTION_ZHIXINGCOURT:
                obj = 6;
                break;
            case MxParam.PARAM_FUNCTION_QQ:
                obj = 14;
                break;
            case MxParam.PARAM_FUNCTION_TAX:
                obj = 8;
                break;
            case "bank":
                obj = 16;
                break;
            case MxParam.PARAM_FUNCTION_CHSI:
                obj = 2;
                break;
            case MxParam.PARAM_FUNCTION_FUND:
                obj = 1;
                break;
            case MxParam.PARAM_FUNCTION_INSURANCE:
                obj = 7;
                break;
            case "email":
                obj = 13;
                break;
            case MxParam.PARAM_FUNCTION_SHIXINCOURT:
                obj = 5;
                break;
            case MxParam.PARAM_FUNCTION_CARRIER:
                obj = 0;
                break;
            case MxParam.PARAM_FUNCTION_SECURITY:
                obj = 9;
                break;
            case MxParam.PARAM_FUNCTION_LINKEDIN:
                obj = 15;
                break;
            case MxParam.PARAM_FUNCTION_JINGDONG:
                obj = 12;
                break;
        }
        switch (obj) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                this.mWebViewCommonFragment.d();
                return;
            case 10:
            case IX5WebViewClientExtension.FRAME_LOADTYPE_PREREAD /*11*/:
            case ReaderCallback.NOTIFY_CANDISPLAY /*12*/:
                if (this.mParam.getSubType().equalsIgnoreCase(MxParam.PARAM_SUBTYPE_SDK)) {
                    this.mWebViewECH5Fragment.d();
                    return;
                } else {
                    this.mWebViewCommonFragment.d();
                    return;
                }
            case 13:
            case 14:
            case IX5WebSettings.DEFAULT_CACHE_CAPACITY /*15*/:
                if (this.mFragment.equals(FragmentEnum.MX_CLIENT_FRAGMENT_IMPORT)) {
                    this.mWebViewOfficialH5Fragment.d();
                    return;
                } else {
                    this.mWebViewCommonFragment.d();
                    return;
                }
            case 16:
                if (!GlobalConstants.c.booleanValue() || this.mDirect.booleanValue()) {
                    this.mWebViewCommonFragment.d();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onBackInternal() {
        /* 1991 */
        int i = 1;
        /*      */
        /* 1993 */
        if (!this.isSDKStart.booleanValue()) {
            /* 1994 */
            internalFinish();
            /* 1995 */
            return;
            /*      */
        }
        internalFinish();
        /*      */
    }

    private void handleWebFragmentBack(BaseWebViewFragment baseWebViewFragment) {
        if (this.mResult != -1 || (baseWebViewFragment != null && !baseWebViewFragment.b().booleanValue())) {
            internalFinish();
        } else if (this.isBackClicked) {
            this.mTitleLayout.setLeftTextVisibility(0);
        }
    }

    protected boolean onBack(JSONObject jSONObject) {
        return false;
    }

    private void internalFinish() {
        stopTask();
        if (!onBack(setResultToCalledActivity())) {
            if (MoxieSDK.getInstance().getMoxieCallBack() == null || !MoxieSDK.getInstance().getMoxieCallBack()
                    .callback(new MoxieContext(this), getMoxieCallbackData())) {
                finish();
            }
        }
    }

    private MoxieCallBackData getMoxieCallbackData() {
        MoxieCallBackData moxieCallBackData = new MoxieCallBackData();
        try {
            if (GlobalConstants.f.booleanValue()) {
                moxieCallBackData.setResult(this.mUDCreditResult.equalsIgnoreCase("") ? String.valueOf(this.mResult)
                        : this.mUDCreditResult);
            } else {
                moxieCallBackData.setCode(this.mResult);
            }
            moxieCallBackData.setTaskType(this.mParam.getFunction());
            moxieCallBackData.setTaskId(this.mTaskId);
            moxieCallBackData.setMessage(this.mResultMessage);
            moxieCallBackData.setAccount(this.mAccount);
            moxieCallBackData.setLoginDone(this.loginDone);
            if (!TextUtils.isEmpty(this.mAppendResult)) {
                try {
                    JSONObject jSONObject = new JSONObject(this.mAppendResult);
                    if (jSONObject.has("businessUserId")) {
                        moxieCallBackData.setBusinessUserId(jSONObject.getString("businessUserId"));
                    }
                } catch (Throwable e) {
                    ErrorHandle.a(TAG, e);
                }
            }
        } catch (Throwable e2) {
            ErrorHandle.b("MainActivity getMoxieCallbackData error", e2);
        }
        return moxieCallBackData;
    }

    private void sendUserCancelLogTrack() {
        uploadLogTracking(new LogTrackInfo.Builder().c(this.mParam.getFunction()).a("CR-42901-00").d("用户主动取消").a()
                .b("DONE_WITH_FAIL").b().b());
    }

    public void finish() {
        stopTask();
        setResultToCalledActivity();
        MxACRA.c();
        super.finish();
    }

    private JSONObject setResultToCalledActivity() {
        try {
            JSONObject jSONObject = new JSONObject();
            if (GlobalConstants.f.booleanValue()) {
                jSONObject.put("code", this.mUDCreditResult.equalsIgnoreCase("") ? String.valueOf(this.mResult)
                        : this.mUDCreditResult);
            } else {
                jSONObject.put("code", this.mResult);
            }
            jSONObject.put("taskType", this.mParam.getFunction());
            jSONObject.put("taskId", this.mTaskId);
            jSONObject.put(TaskStatus.MESSAGE, this.mResultMessage);
            jSONObject.put(TaskStatus.ACCOUNT, this.mAccount);
            jSONObject.put(TaskStatus.LOGINDONE, this.loginDone);
            if (!TextUtils.isEmpty(this.mAppendResult)) {
                try {
                    JSONObject jSONObject2 = new JSONObject(this.mAppendResult);
                    if (jSONObject2.has("businessUserId")) {
                        jSONObject.put("businessUserId", jSONObject2.getString("businessUserId"));
                    }
                } catch (Throwable e) {
                    ErrorHandle.a(TAG, e);
                }
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("result", jSONObject.toString());
            intent.putExtras(bundle);
            setResult(-1, intent);
            return jSONObject;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mTitleLayout = null;
        if (this.mLoadingFlower != null) {
            this.mLoadingFlower.dismiss();
            this.mLoadingFlower = null;
        }
        this.mImportResultCustomFragment = null;
        this.mImportResultFragment = null;
    }

    public void onBackPressed() {
        onBackInternal();//// TODO: 2017/9/14  ganquan
        //        onBackInternal();
    }
}
