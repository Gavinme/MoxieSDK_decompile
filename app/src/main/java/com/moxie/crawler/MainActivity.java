package com.moxie.crawler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;


public class MainActivity extends FragmentActivity implements RadioButton.OnCheckedChangeListener{

    private String mUserId = "1111";                                  //合作方系统中的客户ID
    private String mApiKey = "00a4be26195d4856965c5293629b84b2";      //获取任务状态时使用

    private String mBannerColor = "#000000"; //标题栏背景色
    private String mTextColor = "#ffffff";  //标题栏字体颜色
    private String mThemeColor = "#ff9500"; //页面主色调
    private String mAgreementUrl = "https://api.51datakey.com/h5/agreement.html"; //协议URL

    FragmentTransaction transaction;
    BigdataFragment bigdataFragment;
    AboutmeFragment aboutmeFragment;

    private String mVersion = "";
    private boolean mUpdate = false;

    private RadioButton rbhome;
    private RadioButton rbproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rbhome = (RadioButton) findViewById(R.id.rbhome);
        rbproduct = (RadioButton) findViewById(R.id.rbproduct);
        rbhome.setOnCheckedChangeListener(this);
        rbproduct.setOnCheckedChangeListener(this);

        mVersion = getAppVersionName(this);

        transaction = getSupportFragmentManager().beginTransaction();
        bigdataFragment = new BigdataFragment();
        transaction.replace(R.id.frame, bigdataFragment, "BigData");
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton radioButton, boolean isChecked) {
        boolean checked = isChecked;
        int id = radioButton.getId();
        try {

            if (id == R.id.rbhome) {
                if (checked) {

                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    if (bigdataFragment == null)
                        bigdataFragment = new BigdataFragment();
                    transaction.replace(R.id.frame, bigdataFragment);
                    transaction.commit();

                    rbhome.setTextColor(0xffff9500);
                    rbproduct.setTextColor(0xff8e8e93);

                }

            } else if(id == R.id.rbproduct){
                if (checked) {
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    if (aboutmeFragment == null)
                        aboutmeFragment = new AboutmeFragment();
                    transaction.replace(R.id.frame, aboutmeFragment);
                    transaction.commit();

                    rbproduct.setTextColor(0xffff9500);
                    rbhome.setTextColor(0xff8e8e93);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 身份验证
     * @param itemType
     */
    public void IdentityVerification(String itemType, String title){

        String openUrl = String.format(
                "https://api.51datakey.com/h5/vertification/%s/index.html?token=%s",
                itemType,
                getSharedPreferValue("token"));

        Bundle bundle = new Bundle();
        bundle.putString("openUrl", openUrl);
        bundle.putString("title", title);
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public String getSharedPreferValue(String key) {
        String defValue = "";
        switch (key){
            case "userId":
                defValue = mUserId;
                break;
            case "apiKey":
                defValue = mApiKey;
                break;
            case "bannerBgColor":
                defValue = mBannerColor;
                break;
            case "bannerTxtColor":
                defValue = mTextColor;
                break;
            case "themeColor":
                defValue = mThemeColor;
                break;
            case "agreementUrl":
                defValue = mAgreementUrl;
                break;
        }
        return getSharedPreferValue(key, defValue);
    }

    private String getSharedPreferValue(String key, String defValue) {
        SharedPreferences sp = this.getSharedPreferences("apikey_and_token", Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public boolean haveUpdate(){
        return mUpdate;
    }

    public String getVersionName() {
        return mVersion;
    }
}
