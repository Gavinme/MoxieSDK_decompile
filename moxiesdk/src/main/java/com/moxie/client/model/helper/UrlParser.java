package com.moxie.client.model.helper;

import android.text.TextUtils;
import com.moxie.client.commom.GlobalParams;
import com.moxie.client.model.MxParam;
import java.util.HashMap;

/* compiled from: TbsSdkJava */
public class UrlParser {
    public static String a(String str, boolean z) {
        HashMap extendParams = GlobalParams.i().a().getExtendParams();
        if (extendParams == null || !extendParams.containsKey(MxParam.PAPAM_H5_URL) || TextUtils.isEmpty((CharSequence) extendParams.get(MxParam.PAPAM_H5_URL))) {
            return str;
        }
        String str2 = (String) extendParams.get(MxParam.PAPAM_H5_URL);
        if (z) {
            return str2 + "#/%s/%s?userId=%s&apiKey=%s&themeColor=%s";
        }
        return str2 + "#/%s/?userId=%s&apiKey=%s&themeColor=%s";
    }
}
