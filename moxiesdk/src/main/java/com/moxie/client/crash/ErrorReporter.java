package com.moxie.client.crash;

import android.content.Context;
import android.os.Process;
import android.text.format.Time;
import com.moxie.client.crash.CrashReportData.Type;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: TbsSdkJava */
public class ErrorReporter implements UncaughtExceptionHandler {
    public static String a = ErrorReporter.class.getSimpleName();
    private Context b;
    private CrashReportDataFactory c;
    private UncaughtExceptionHandler d;
    private boolean e = false;
    private boolean f = true;

    /* compiled from: TbsSdkJava */
    public final class ReportBuilder {
        final /* synthetic */ ErrorReporter a;
        private String b;
        private Thread c;
        private Throwable d;
        private String e;
        private boolean f = false;

        public ReportBuilder(ErrorReporter errorReporter) {
            this.a = errorReporter;
        }

        public final ReportBuilder a(String str) {
            this.b = str;
            return this;
        }

        public final ReportBuilder b(String str) {
            this.e = str;
            return this;
        }

        public final ReportBuilder a(Throwable th) {
            this.d = th;
            return this;
        }

        public final ReportBuilder a() {
            this.f = true;
            return this;
        }

        public final void b() {
            if (this.b == null && this.d == null) {
                this.b = "Report requested by developer";
            }
            ErrorReporter.a(this.a, this);
        }

        public final void c() {
            if (this.b == null && this.d == null) {
                this.b = "Report requested by developer";
            }
            ErrorReporter.b(this.a, this);
        }
    }

    public ErrorReporter(Context context, boolean z) {
        this.b = context.getApplicationContext();
        Time time = new Time();
        time.setToNow();
        this.c = new CrashReportDataFactory(this.b, time);
        this.d = Thread.getDefaultUncaughtExceptionHandler();
        this.f = z;
    }

    public final void a() {
        if (this.f && !this.e) {
            Thread.setDefaultUncaughtExceptionHandler(this);
            this.e = true;
        }
    }

    public final void b() {
        if (this.f && this.e && this.d != null) {
            Thread.setDefaultUncaughtExceptionHandler(this.d);
            this.e = false;
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        try {
//            d().c = thread.encode(th).encode().b(Type.encode).c();
        } catch (Throwable th2) {
            if (this.d != null) {
                this.d.uncaughtException(thread, th);
            }
        }
    }

    private void a(Thread thread, Throwable th) {
        new StringBuilder().append(this.b.getPackageName()).append(" fatal error : ").append(th.getMessage());
        if (thread == null || this.d == null) {
            Process.killProcess(Process.myPid());
            System.exit(10);
            return;
        }
        this.d.uncaughtException(thread, th);
    }

    public final void c() {
        String[] a = new CrashReportFinder(this.b).a();
        if (a != null && a.length > 0) {
            a(null);
        }
    }

    private void a(CrashReportData crashReportData) {
        new SendWorker(this.b, crashReportData).start();
    }

    public final ReportBuilder d() {
        return new ReportBuilder(this);
    }

    static /* synthetic */ void a(ErrorReporter errorReporter, ReportBuilder reportBuilder) {
        errorReporter.a(errorReporter.c.a(reportBuilder.e, reportBuilder.b, reportBuilder.d));
        if (reportBuilder.f) {
            errorReporter.a(reportBuilder.c, reportBuilder.d);
        }
    }

    static /* synthetic */ void b(ErrorReporter errorReporter, ReportBuilder reportBuilder) {
        CrashReportData a = errorReporter.c.a(reportBuilder.e, reportBuilder.b, reportBuilder.d);
        Time time = new Time();
        time.setToNow();
        String str = time.toMillis(false) + ".mxstacktrace";
        try {
            new StringBuilder("Writing crash report file ").append(str).append(".");
            new CrashReportPersister(errorReporter.b).a(a, str);
        } catch (Exception e) {
        }
        if (reportBuilder.f) {
            errorReporter.a(reportBuilder.c, reportBuilder.d);
        }
    }
}
