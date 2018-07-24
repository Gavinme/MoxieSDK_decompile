package com.moxie.client.fragment.mvp.contract;

import com.moxie.client.fragment.mvp.BasePresenter;
import com.moxie.client.fragment.mvp.BaseView;
import com.moxie.client.model.SiteAccountInfo;

/* compiled from: TbsSdkJava */
public class WebViewECV3Contract {

    /* compiled from: TbsSdkJava */
    public interface View extends BaseView<Presenter> {
    }

    /* compiled from: TbsSdkJava */
    public interface Presenter extends BasePresenter {
        void a(int i, SiteAccountInfo siteAccountInfo);
    }
}
