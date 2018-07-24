package com.moxie.client.fragment;

import static android.content.Context.WINDOW_SERVICE;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.moxie.client.commom.GlobalParams;
import com.moxie.client.manager.StatusViewListener;
import com.moxie.client.model.MxParam.TaskStatus;
import com.moxie.client.widget.wave.DynamicWave;
import com.moxie.client.widget.wave.UiUtils;
import com.moxie.client.widget.wave.WaterWaveView;
import com.proguard.annotation.NotProguard;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@NotProguard
/* compiled from: TbsSdkJava */
public class ImportResultFragment extends Fragment implements StatusViewListener {
    public static final String TAG = "ImportResultFragment";
    private TextView TextView_Mail_Name;
    MyBaseApdater adapter;
    String[] colors = new String[] {"#99ffffff", "#4dffffff", "#1affffff"};
    private TextView customText;
    private DynamicWave dynamicWave;
    ListView listTypes;
    LayoutInflater mInflater;
    List<ImportResult> resultMsg;
    private WaterWaveView waterWaveView;

    /* compiled from: TbsSdkJava */
    class ImportResult {
        final /* synthetic */ ImportResultFragment a;
        private String b = "";
        private int c;

        public ImportResult(ImportResultFragment importResultFragment, String str, int i) {
            this.a = importResultFragment;
            this.b = str;
            this.c = i;
        }
    }

    /* compiled from: TbsSdkJava */
    class MyBaseApdater extends BaseAdapter {
        List<ImportResult> a;
        final /* synthetic */ ImportResultFragment b;

        public /* synthetic */ Object getItem(int i) {
            return a(i);
        }

        public MyBaseApdater(ImportResultFragment importResultFragment, List<ImportResult> list) {
            this.b = importResultFragment;
            this.a = list;
        }

        public int getCount() {
            return this.a.size();
        }

        private ImportResult a(int i) {
            return (ImportResult) this.a.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                ViewHolder viewHolder2 = new ViewHolder(this.b);
                view = this.b.mInflater.inflate(viewHolder2.a, viewGroup, false);
                viewHolder2.b = (TextView) view.findViewById(viewHolder2.c.getResources()
                        .getIdentifier("TextView_Result", "id", viewHolder2.c.getActivity().getPackageName()));
                view.setTag(viewHolder2);
                viewHolder = viewHolder2;
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            ImportResult a = a(i);
            viewHolder.b.setText(a.b);
            viewHolder.b.setTextColor(a.c);
            return view;
        }
    }

    /* compiled from: TbsSdkJava */
    class ViewHolder {
        int a;
        TextView b;
        ImportResultFragment c = null;

        ViewHolder(ImportResultFragment importResultFragment) {
            this.c = importResultFragment;
            a = importResultFragment.getResources().getIdentifier("moxie_client_view_result_item", "layout",
                    importResultFragment.getActivity().getPackageName());
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mInflater = layoutInflater;
        View inflate = layoutInflater.inflate(getResources()
                        .getIdentifier("moxie_client_fragment_import_result", "layout", getActivity().getPackageName()),
                viewGroup, false);
        this.TextView_Mail_Name = (TextView) inflate
                .findViewById(getResources().getIdentifier("TextView_Mail_Name", "id", getActivity().getPackageName()));
        this.customText = (TextView) inflate
                .findViewById(getResources().getIdentifier("custom_txt", "id", getActivity().getPackageName()));
        this.listTypes = (ListView) inflate
                .findViewById(getResources().getIdentifier("ListView_Result", "id", getActivity().getPackageName()));
        this.resultMsg = new ArrayList();
        this.adapter = new MyBaseApdater(this, this.resultMsg);
        this.listTypes.setAdapter(this.adapter);
        try {
            int parseColor = Color.parseColor(GlobalParams.i().a().getThemeColor());
            this.dynamicWave = (DynamicWave) inflate
                    .findViewById(getResources().getIdentifier("DynamicWave", "id", getActivity().getPackageName()));
            this.dynamicWave.a(parseColor);
            this.waterWaveView = (WaterWaveView) inflate
                    .findViewById(getResources().getIdentifier("moxieWave", "id", getActivity().getPackageName()));
            this.waterWaveView.a(parseColor);
            this.waterWaveView.a();
            WindowManager windowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
            parseColor = Math.min(windowManager.getDefaultDisplay().getWidth() / 2,
                    windowManager.getDefaultDisplay().getHeight() / 4);
            RelativeLayout relativeLayout = (RelativeLayout) inflate
                    .findViewById(getResources().getIdentifier("moxieAccount", "id", getActivity().getPackageName()));
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams.setMargins(0, (parseColor * 2) + 60, 0, 0);
            relativeLayout.setLayoutParams(layoutParams);
            relativeLayout = (RelativeLayout) inflate.findViewById(
                    getResources().getIdentifier("customTextLayout", "id", getActivity().getPackageName()));
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams2.setMargins(0, 0, 0, this.dynamicWave.a() - UiUtils.a(getActivity(), 55));
            relativeLayout.setLayoutParams(layoutParams2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inflate;
    }

    public void viewTaskStatusWithCustomText(String str, String str2) {
        this.listTypes.setVisibility(View.GONE);
        this.customText.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(str)) {
            this.customText.setText(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            this.waterWaveView.a(str2);
        }
    }

    public void updateProgress(JSONObject jSONObject) {
        try {
            jSONObject.toString();
            String optString = jSONObject.optString(TaskStatus.PERCENT);
            String optString2 = jSONObject.optString(TaskStatus.MESSAGE);
            String optString3 = jSONObject.optString(TaskStatus.ACCOUNT);
            if (!TextUtils.isEmpty(optString3)) {
                this.TextView_Mail_Name.setText("当前账号：" + optString3);
            }
            if (!TextUtils.isEmpty(optString)) {
                this.waterWaveView.a(optString);
            }
            if (!TextUtils.isEmpty(optString2)) {
                this.listTypes.setVisibility(View.VISIBLE);
                this.customText.setVisibility(View.GONE);
                if (this.resultMsg.size() <= 0) {
                    this.resultMsg.add(new ImportResult(this, optString2, Color.parseColor("#ffffffff")));
                } else if (!((ImportResult) this.resultMsg.get(this.resultMsg.size() - 1)).b
                        .equalsIgnoreCase(optString2)) {
                    int i = 0;
                    int size = this.resultMsg.size();
                    while (size > 0) {
                        int i2 = i + 1;
                        ((ImportResult) this.resultMsg.get(size - 1)).c = Color.parseColor(this.colors[i]);
                        size--;
                        i = i2;
                    }
                    this.resultMsg.add(new ImportResult(this, optString2, Color.parseColor("#ffffffff")));
                }
                if (this.resultMsg.size() > 3) {
                    this.resultMsg.remove(0);
                }
                this.adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
