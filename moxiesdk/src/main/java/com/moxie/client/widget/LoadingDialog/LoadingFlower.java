package com.moxie.client.widget.LoadingDialog;

import java.util.Timer;
import java.util.TimerTask;

import com.moxie.client.widget.LoadingDialog.views.FlowerView;
import com.tencent.smtt.sdk.WebView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;

/* compiled from: TbsSdkJava */
public class LoadingFlower extends LoadingBaseDialog {
    private Builder a;
    private FlowerView b;
    private int c;
    private Timer d;

    /* compiled from: TbsSdkJava */
    public static class Builder {
        private Context a;
        private float b = 0.25f;
        private float c = 0.55f;
        private float d = 0.27f;
        private int e = WebView.NIGHT_MODE_COLOR;
        private int f = -1;
        private int g = -12303292;
        private int h = 12;
        private int i = 9;
        private float j = 0.5f;
        private float k = 20.0f;
        private float l = 0.5f;
        private int m = 100;
        private float n = 9.0f;
        private String o = null;
        private int p = -1;
        private float q = 0.5f;
        private float r = 40.0f;
        private int s = 40;
        private boolean t = true;

        public Builder(Context context) {
            this.a = context;
        }

        public final Builder a() {
            this.f = -1;
            return this;
        }

        public final Builder b() {
            this.g = -12303292;
            return this;
        }

        public final Builder c() {
            this.m = 100;
            return this;
        }

        public final LoadingFlower d() {
            return new LoadingFlower(this);
        }
    }

    private LoadingFlower(Builder builder) {
        super(builder.a);
        this.c = 0;
        this.a = builder;
        setOnDismissListener(new OnDismissListener() {
            final /* synthetic */ LoadingFlower a;

            {
                this.a = LoadingFlower.this;
            }

            public void onDismiss(DialogInterface dialogInterface) {
                if (this.a.d != null) {
                    this.a.d.cancel();
                    this.a.d = null;
                }
                this.a.c = 0;
                this.a.b = null;
            }
        });
    }

    public void show() {
        if (this.b == null) {
            int min;
            Display defaultDisplay = ((WindowManager) this.a.a.getSystemService("window")).getDefaultDisplay();
            if (VERSION.SDK_INT >= 13) {
                Point point = new Point();
                defaultDisplay.getSize(point);
                min = Math.min(point.x, point.y);
            } else {
                min = Math.min(defaultDisplay.getWidth(), defaultDisplay.getHeight());
            }
            this.b = new FlowerView(this.a.a, (int) (((float) min) * this.a.b), this.a.e, this.a.l, this.a.k, this.a.i,
                    this.a.h, this.a.j, this.a.c, this.a.d, this.a.f, this.a.g, this.a.o, this.a.r, this.a.p, this.a.q,
                    this.a.s, this.a.t);
        }
        super.setContentView(this.b);
        super.show();
        long s = (long) (1000.0f / this.a.n);
        this.d = new Timer();
        this.d.scheduleAtFixedRate(new TimerTask() {
            final /* synthetic */ LoadingFlower a;

            {
                this.a = LoadingFlower.this;
            }

            public void run() {
                int d = this.a.c % this.a.a.h;
                if (this.a.a.m == 100) {
                    this.a.b.a(d);
                } else {
                    this.a.b.a((this.a.a.h - 1) - d);
                }
                if (d == 0) {
                    this.a.c = 1;
                } else {
                    this.a.c = this.a.c + 1;
                }
            }
        }, s, s);
    }
}
