package com.moxie.crawler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.moxie.crawler.common.ColorSelectorDialog;
import com.moxie.crawler.common.TitleLayout;
import com.moxie.crawler.common.colorpalette.ColorSelectDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by taoweisong on 16/4/1.
 */
public class InputActivity extends Activity  implements
        View.OnClickListener {

    Button btnSetting;
    EditText editTextApiKey;
    EditText editTextToken;
    EditText editTextUserId;
    View editTextBannerBgColor;
    View editTextBannerTxtColor;
    View editTextThemeColor;
    EditText editTextAgreementUrl;
    TitleLayout mTitleLayout;
    int bannerBgColor = 0xff000000;
    int bannerTxtColor = 0xffffffff;
    int themeColor = 0xffff9500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        //初始化
        init();

        //获取用户传过来的参数
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            editTextApiKey.setText(bundle.getString("apiKey"));
            editTextToken.setText(bundle.getString("token"));
            editTextUserId.setText(bundle.getString("userId"));
            editTextAgreementUrl.setText(bundle.getString("agreementUrl"));

            bannerBgColor = Color.parseColor(bundle.getString("bannerBgColor"));
            editTextBannerBgColor.setBackgroundColor(bannerBgColor);

            bannerTxtColor = Color.parseColor(bundle.getString("bannerTxtColor"));
            editTextBannerTxtColor.setBackgroundColor(bannerTxtColor);

            themeColor = Color.parseColor(bundle.getString("themeColor"));
            editTextThemeColor.setBackgroundColor(themeColor);
        }

        mTitleLayout = (TitleLayout) findViewById(R.id.TitleLayout);
        mTitleLayout.setTitle("设置");
        mTitleLayout.getLeftImage().setOnClickListener(this);
        mTitleLayout.getRightImage().setVisibility(View.GONE);
    }

    private void init(){
        editTextApiKey = (EditText) findViewById(R.id.editTextApiKey);
        editTextToken = (EditText) findViewById(R.id.editTextToken);
        editTextUserId = (EditText) findViewById(R.id.editTextUserId);
        editTextBannerBgColor = (View) findViewById(R.id.editTextBannerBgColor);
        editTextBannerTxtColor = (View) findViewById(R.id.editTextBannerTxtColor);
        editTextThemeColor = (View) findViewById(R.id.editTextThemeColor);
        editTextAgreementUrl = (EditText) findViewById(R.id.editTextAgreementUrl);
        btnSetting = (Button) findViewById(R.id.buttonConfirm);

        editTextBannerBgColor.setOnClickListener(this);
        editTextBannerTxtColor.setOnClickListener(this);
        editTextThemeColor.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        try {
            if (id == R.id.buttonConfirm) {
                if (editTextApiKey.getText().toString().equals("")) {
                    Toast.makeText(this, "apiKey不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextToken.getText().toString().equals("")) {
                    Toast.makeText(this, "token不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextUserId.getText().toString().equals("")) {
                    Toast.makeText(this, "userId不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (editTextAgreementUrl.getText().toString().equals("")) {
                    Toast.makeText(this, "agreementUrl不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                Pattern pattern = Pattern.compile("(https?):\\/\\/([A-z0-9]+[_\\-]?[A-z0-9]+\\.)*[A-z0-9]+\\-?[A-z0-9]+\\.[A-z]{2,}(\\/.*)*\\/?");
                Matcher matcher = pattern.matcher(editTextAgreementUrl.getText().toString());
                if (!matcher.find()) {
                    Toast.makeText(this, "agreementUrl不正确，请重新输入！", Toast.LENGTH_SHORT).show();
                    return;
                }
                setSharedPreferValue("setedApikey", "yes");
                setSharedPreferValue("apiKey", editTextApiKey.getText().toString());
                setSharedPreferValue("token", editTextToken.getText().toString());
                setSharedPreferValue("userId", editTextUserId.getText().toString());
                setSharedPreferValue("agreementUrl", editTextAgreementUrl.getText().toString());

                setSharedPreferValue("bannerBgColor", "#" + Integer.toHexString(bannerBgColor).toUpperCase().substring(2));
                setSharedPreferValue("bannerTxtColor", "#" + Integer.toHexString(bannerTxtColor).toUpperCase().substring(2));
                setSharedPreferValue("themeColor", "#" + Integer.toHexString(themeColor).toUpperCase().substring(2));

                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
                finish();
            }else if(id == R.id.editTextBannerBgColor) {
                openColor(bannerBgColor, editTextBannerBgColor, id);
            }else if(id == R.id.editTextBannerTxtColor) {
                openColor(bannerTxtColor, editTextBannerTxtColor, id);
            }else if(id == R.id.editTextThemeColor) {
                openColor(themeColor, editTextThemeColor, id);
            } else if (id == R.id.TextView_Back) {
                finish();
            }
        } catch (Exception e) {

        }
    }

    private void openColor(int defaultColor, final View view, final int id) {
        ColorSelectDialog  colorSelectDialog = new ColorSelectDialog(this);
        colorSelectDialog.setOnColorSelectListener(new ColorSelectDialog.OnColorSelectListener() {
            @Override
            public void onSelectFinish(int color) {
                view.setBackgroundColor(color);
                switch (id){
                    case R.id.editTextBannerBgColor:
                        bannerBgColor = color;
                        break;
                    case R.id.editTextBannerTxtColor:
                        bannerTxtColor = color;
                        break;
                    case R.id.editTextThemeColor:
                        themeColor = color;
                        break;
                }
            }
        });
        colorSelectDialog.setLastColor(defaultColor);
        colorSelectDialog.show();
    }

    private void openSelectColorDialog(int defaultColor, final View view, final int id) {
        ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(this, defaultColor,
                new ColorSelectorDialog.OnColorSelectorListener() {
                    @Override
                    public void onCancel(ColorSelectorDialog dialog){

                    }
                    @Override
                    public void onOk(ColorSelectorDialog dialog, int color){
                        view.setBackgroundColor(color);
                        switch (id){
                            case R.id.editTextBannerBgColor:
                                bannerBgColor = color;
                                break;
                            case R.id.editTextBannerTxtColor:
                                bannerTxtColor = color;
                                break;
                            case R.id.editTextThemeColor:
                                themeColor = color;
                                break;
                        }
                    }
                });
        colorSelectorDialog.show();
    }

    private void setSharedPreferValue(String key, String value) {
        SharedPreferences sp = this.getSharedPreferences("apikey_and_token", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
