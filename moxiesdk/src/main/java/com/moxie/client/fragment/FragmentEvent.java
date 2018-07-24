package com.moxie.client.fragment;

/* compiled from: TbsSdkJava */
public class FragmentEvent {

    /* compiled from: TbsSdkJava */
    static class FragmentEventBase {
        public int a;
        public String b;
        public String c;

        public FragmentEventBase() {
            this.a = 0;
            this.b = "qq.com";
            this.c = "";
        }

        public FragmentEventBase(String str, String str2) {
            this.a = 0;
            this.b = "qq.com";
            this.c = "";
            this.b = str;
            this.c = str2;
        }

        public FragmentEventBase(String str) {
            this.a = 0;
            this.b = "qq.com";
            this.c = "";
            this.a = 0;
            this.b = str;
        }
    }

    /* compiled from: TbsSdkJava */
    public static class BackToClose extends FragmentEventBase {
    }

    /* compiled from: TbsSdkJava */
    public static class BackToFinish extends FragmentEventBase {
        public BackToFinish(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class CheckAuthUDCredit extends FragmentEventBase {
        public CheckAuthUDCredit(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class NoPermission extends FragmentEventBase {
        public NoPermission() {
            super("");
        }
    }

    /* compiled from: TbsSdkJava */
    public static class OpenAgreement extends FragmentEventBase {
        public OpenAgreement(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class OpenECWebView extends FragmentEventBase {
    }

    /* compiled from: TbsSdkJava */
    public static class OpenInsuranceWebView extends FragmentEventBase {
    }

    /* compiled from: TbsSdkJava */
    public static class OpenOfficialWebView extends FragmentEventBase {
        public OpenOfficialWebView(String str) {
            super(str, "");
        }
    }

    /* compiled from: TbsSdkJava */
    public static class OpenOnlieBankWebView extends FragmentEventBase {
    }

    /* compiled from: TbsSdkJava */
    public static class OpenWebView extends FragmentEventBase {
    }

    /* compiled from: TbsSdkJava */
    public static class PermissionJson extends FragmentEventBase {
        public PermissionJson(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class RefreshStatus extends FragmentEventBase {
        public RefreshStatus(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class RefreshTitle extends FragmentEventBase {
        public RefreshTitle(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class SaveAccountInfo extends FragmentEventBase {
        public SaveAccountInfo(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class SaveTaskId extends FragmentEventBase {
        public SaveTaskId(String str) {
            super(str);
        }
    }

    /* compiled from: TbsSdkJava */
    public static class ShowOrHiddenWebView extends FragmentEventBase {
        public boolean d = false;

        public ShowOrHiddenWebView(boolean z) {
            super("");
            this.d = z;
        }
    }

    /* compiled from: TbsSdkJava */
    public static class ViewH5ImortResult extends FragmentEventBase {
    }
}
