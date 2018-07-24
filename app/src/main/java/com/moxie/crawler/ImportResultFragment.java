package com.moxie.crawler;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moxie.client.manager.StatusViewListener;
import com.moxie.crawler.widget.wave.DynamicWave;
import com.moxie.crawler.widget.wave.UiUtils;
import com.moxie.crawler.widget.wave.WaterWaveView;
import com.proguard.annotation.NotProguard;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoweisong on 16/3/15.
 */
@NotProguard
public class ImportResultFragment extends Fragment implements StatusViewListener {

    public static final String TAG = "ImportResultFragment";

    private TextView TextView_Mail_Name;
    private TextView customText;
    private DynamicWave dynamicWave;
    private WaterWaveView waterWaveView;

    LayoutInflater mInflater;
    List<ImportResult> resultMsg;
    MyBaseApdater adapter;
    ListView listTypes;
    String[] colors = {"#99ffffff", "#4dffffff", "#1affffff"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState)
    {

        mInflater = inflater;
        View view = inflater.inflate(getResources().getIdentifier("moxie_crawler_fragment_import_result", "layout", getActivity().getPackageName()), container, false);
        TextView_Mail_Name = (TextView) view.findViewById(getResources().getIdentifier("TextView_Mail_Name", "id", getActivity().getPackageName()));

        customText = (TextView) view.findViewById(getResources().getIdentifier("custom_txt", "id", getActivity()
                .getPackageName()));

        listTypes = (ListView) view.findViewById(getResources().getIdentifier("ListView_Result", "id", getActivity().getPackageName()));
        resultMsg = new ArrayList<>();
        adapter = new MyBaseApdater(resultMsg);
        listTypes.setAdapter(adapter);

        try {
            int color = Color.parseColor("#FF4081");
            dynamicWave = (DynamicWave) view.findViewById(getResources().getIdentifier("DynamicWave", "id", getActivity().getPackageName()));
            dynamicWave.setColor(color);

            waterWaveView = (WaterWaveView) view.findViewById(getResources().getIdentifier("moxieWave", "id", getActivity().getPackageName()));
            waterWaveView.setWaveColor(color);
            waterWaveView.resetWave();

			WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

            int waterWaveHeight = Math.min(wm.getDefaultDisplay().getWidth() / 2, wm.getDefaultDisplay().getHeight() / 4);

            RelativeLayout moxieAccount = (RelativeLayout) view.findViewById(getResources().getIdentifier("moxieAccount", "id", getActivity().getPackageName()));
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,waterWaveHeight*2+60,0,0);
            moxieAccount.setLayoutParams(lp);

            RelativeLayout customTxtLayout  = (RelativeLayout) view.findViewById(getResources().getIdentifier("customTextLayout", "id", getActivity().getPackageName()));
            RelativeLayout.LayoutParams clp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            clp.setMargins(0,0,0, dynamicWave.getWaveHeight()- UiUtils.dipToPx(getActivity(),55));
            customTxtLayout.setLayoutParams(clp);

        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void updateProgress(JSONObject jsonObject) {
        try {
            Log.e("ImportResultFragment", jsonObject.toString());
            String percent = jsonObject.optString("percent");
            String message = jsonObject.optString("message");
            String account = jsonObject.optString("account");
            if(!TextUtils.isEmpty(account)) {
                TextView_Mail_Name.setText("当前账号：" + account);
            }
            if(!TextUtils.isEmpty(percent)){
                waterWaveView.setPercent(percent);
            }
            if(TextUtils.isEmpty(message)) return;

            listTypes.setVisibility(View.VISIBLE);
            customText.setVisibility(View.GONE);

            if(resultMsg.size() > 0) {

                if(!resultMsg.get(resultMsg.size()-1).msg.equalsIgnoreCase(message)){
                    int index=0;
                    for(int i=resultMsg.size(); i>0; i--){
                        resultMsg.get(i-1).color = Color.parseColor(colors[index++]);
                    }
                    resultMsg.add(new ImportResult(message, Color.parseColor("#ffffffff")));
                }
            } else {
                resultMsg.add(new ImportResult(message, Color.parseColor("#ffffffff")));
            }
            if(resultMsg.size() > 3) {
                resultMsg.remove(0);
            }
            adapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class MyBaseApdater extends BaseAdapter {
        List<ImportResult> listData;

        public MyBaseApdater(List<ImportResult> listData) {
            this.listData = listData;
        }

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public ImportResult getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(holder.resId, parent, false);
                holder.findView(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.setValue(position, getItem(position));
            return convertView;
        }

    }

    class ViewHolder {
        int resId = getResources().getIdentifier("moxie_crawler_view_result_item", "layout", getActivity().getPackageName());
        TextView tvName;

        public void findView(View convertView) {
            tvName = (TextView) convertView.findViewById(getResources().getIdentifier("TextView_Result", "id", getActivity().getPackageName()));
        }

        public void setValue(int position, final ImportResult importResult) {
            tvName.setText(importResult.msg);
            tvName.setTextColor(importResult.color);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ImportResult {
        private String msg = "";
        private int color;

        public ImportResult(String msg, int color) {
            this.msg = msg;
            this.color = color;
        }
    }
}
