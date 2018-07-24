package com.moxie.client.encrypt;

import com.proguard.annotation.NotProguard;

@NotProguard
/* compiled from: TbsSdkJava */
public class Encrypt {
    private OnEncryptListener mOnClientListener = null;

    @NotProguard
    /* compiled from: TbsSdkJava */
    public interface OnEncryptListener {
        String onEncrypt(String str);
    }

    /* compiled from: TbsSdkJava */
    private static class SingletonHolder {
        public static final Encrypt a = new Encrypt();

        private SingletonHolder() {
        }
    }

    public static Encrypt getInstance() {
        return SingletonHolder.a;
    }

    public String encyptBody(String str) {
        try {
            if (this.mOnClientListener != null) {
                str = this.mOnClientListener.onEncrypt(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void setOnEncryptListener(OnEncryptListener onEncryptListener) {
        this.mOnClientListener = onEncryptListener;
    }
}
