package com.moxie.client.fragment;

import com.moxie.client.manager.ScreenCapturer;
import com.moxie.client.utils.ErrorHandle;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjectionManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/* compiled from: TbsSdkJava */
public class ScreenCaptureFragment extends Fragment {
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(getResources().getIdentifier("moxie_client_activity_main", "layout",
                getActivity().getPackageName()), viewGroup, false);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        a();
    }

    public final void a() {
        if (VERSION.SDK_INT >= 21) {
            startActivityForResult(((MediaProjectionManager) getActivity().getSystemService("media_projection"))
                    .createScreenCaptureIntent(), 1);
        } else {
            Toast.makeText(getActivity(), "版本过低，无法截屏", 0).show();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 1) {
            return;
        }
        if (i2 != -1) {
            Toast.makeText(getActivity(), "截屏失败，请确认是否已经开启权限", 0).show();
        } else if (getActivity() != null) {
            try {
                new ScreenCapturer(getActivity(), intent).a();
            } catch (Throwable e) {
                ErrorHandle.b("截屏失败！", e);
            }
        }
    }
}
