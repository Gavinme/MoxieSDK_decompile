package com.moxie.crawler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by taoweisong on 16/7/26.
 */
public class AboutmeFragment extends Fragment implements View.OnClickListener{

    TextView tvVersion;
    ImageView ivVersionNew;

    private boolean mUpdate = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_aboutme, container, false);
        tvVersion = (TextView) view.findViewById(R.id.tvVersion);
        ivVersionNew = (ImageView) view.findViewById(R.id.ivVersionNew);
        tvVersion.setOnClickListener(this);
        ivVersionNew.setOnClickListener(this);
        view.findViewById(R.id.rlSetting).setOnClickListener(this);

        MainActivity mainActivity = (MainActivity) getActivity();
        mUpdate = mainActivity.haveUpdate();
        if(mUpdate){
            ivVersionNew.setVisibility(View.VISIBLE);
        }
        tvVersion.setText(mainActivity.getVersionName());

        return view;
    }


    public void onClick(View v) {

        int id = v.getId();
        try {
            if (id == R.id.rlSetting) {
                MainActivity mainActivity = (MainActivity)getActivity();
                Bundle bundle = new Bundle();
                bundle.putString("apiKey", mainActivity.getSharedPreferValue("apiKey"));
                bundle.putString("token", mainActivity.getSharedPreferValue("token"));
                bundle.putString("userId", mainActivity.getSharedPreferValue("userId"));
                bundle.putString("bannerBgColor", mainActivity.getSharedPreferValue("bannerBgColor"));
                bundle.putString("bannerTxtColor", mainActivity.getSharedPreferValue("bannerTxtColor"));
                bundle.putString("themeColor", mainActivity.getSharedPreferValue("themeColor"));
                bundle.putString("agreementUrl", mainActivity.getSharedPreferValue("agreementUrl"));
                Intent intent = new Intent(getActivity(), InputActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else if (id == R.id.tvVersion || id == R.id.ivVersionNew) {

                if(mUpdate){
                    Uri uri = Uri.parse("http://pre.im/mxsdk");
                    Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
