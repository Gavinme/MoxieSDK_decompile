package com.moxie.client.tasks.event;

import com.moxie.client.model.SiteAccountInfo;

/* compiled from: TbsSdkJava */
public class TaskLoginEvent {

    /* compiled from: TbsSdkJava */
    static class LoginEventBase {
        public int a = 0;
        public String b;
        public SiteAccountInfo c;

        public LoginEventBase(int i, String str, SiteAccountInfo siteAccountInfo) {
            this.a = i;
            this.b = str;
            this.c = siteAccountInfo;
        }
    }

    /* compiled from: TbsSdkJava */
    public static class LoginSubmitErrorEvent extends LoginEventBase {
        public LoginSubmitErrorEvent(String str, SiteAccountInfo siteAccountInfo) {
            super(0, str, siteAccountInfo);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class LoginSubmitStart extends LoginEventBase {
        public LoginSubmitStart(String str, SiteAccountInfo siteAccountInfo) {
            super(0, str, siteAccountInfo);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class LoginSubmitSuccessEvent extends LoginEventBase {
        public LoginSubmitSuccessEvent(String str, SiteAccountInfo siteAccountInfo) {
            super(1, str, siteAccountInfo);
        }
    }
}
