package com.moxie.client.widget.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TbsSdkJava */
public class WaterWaveView extends View {
    private float a;
    private float b;
    private float c;
    private float d;
    private float e;
    private int f;
    private Interpolator g = new CycleInterpolator(0.5f);
    private float h;
    private float i;
    private final Paint j = new Paint();
    private final Paint k;
    private boolean l;
    private float m;
    private final List<Wave> n;
    private String o;
    private Context p;
    private Wave q;

    /* compiled from: TbsSdkJava */
    class Wave {
        public float a;
        public float b;
        public int c;
        final /* synthetic */ WaterWaveView d;

        public Wave(WaterWaveView waterWaveView) {
            this.d = waterWaveView;
            a();
        }

        public final void a() {
            this.a = (float) ((UiUtils.a(this.d.p) / 240) * 75);
            this.b = this.d.d;
            this.c = this.d.f;
        }

        public String toString() {
            return "Wave [radius=" + this.a + ", width=" + this.b + ", color=" + this.c + "]";
        }
    }

    public WaterWaveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.j.setAntiAlias(true);
        this.j.setStyle(Style.STROKE);
        this.k = new Paint();
        this.k.setAntiAlias(true);
        this.k.setStyle(Style.STROKE);
        this.k.setColor(-27392);
        this.k.setTextSize(50.0f);
        this.l = false;
        this.m = 0.0f;
        this.n = new ArrayList();
        b();
        this.p = context;
    }

    public WaterWaveView(Context context) {
        super(context);
        this.j.setAntiAlias(true);
        this.j.setStyle(Style.STROKE);
        this.k = new Paint();
        this.k.setAntiAlias(true);
        this.k.setStyle(Style.STROKE);
        this.k.setColor(-27392);
        this.k.setTextSize(50.0f);
        this.l = false;
        this.m = 0.0f;
        this.n = new ArrayList();
        b();
        this.p = context;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float sqrt;
        super.onLayout(z, i, i2, i3, i4);
        this.h = (float) (getWidth() / 2);
        this.i = (float) (getHeight() / 4);
        if (this.l) {
            sqrt = (float) Math.sqrt((double) ((this.h * this.h) + (this.i * this.i)));
        } else {
            sqrt = Math.min(this.h, this.i);
        }
        if (this.a != sqrt) {
            this.a = sqrt;
            a();
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Wave wave = this.n.isEmpty() ? null : (Wave) this.n.get(0);
        if (wave == null || wave.a >= this.b) {
            Wave obj;
            if (this.q != null) {
                obj = this.q;
                this.q = null;
                obj.a();
            } else {
                obj = new Wave(this);
            }
            this.n.add(0, obj);
        }
        float f = this.e - this.d;
        int size = this.n.size();
        for (int i = 0; i < size; i++) {
            wave = (Wave) this.n.get(i);
            float f2 = wave.a / this.a;
            if (f2 > 1.0f) {
                f2 = 1.0f;
            }
            wave.b = this.d + (f2 * f);
            wave.a += this.c;
            wave.c = (((int) (this.g.getInterpolation(f2) * 255.0f)) << 24) | (this.f & 16777215);
        }
        wave = (Wave) this.n.get(size - 1);
        if (wave.a > (wave.b / 2.0f) + this.a) {
            this.n.remove(size - 1);
        }
        setBackgroundColor(0);
        for (Wave wave2 : this.n) {
            this.j.setColor(wave2.c);
            this.j.setStrokeWidth(wave2.b);
            canvas.drawCircle(this.h, this.i, wave2.a, this.j);
        }
        if (this.m > 0.0f) {
            canvas.drawCircle(this.h, this.i, this.m, this.k);
        }
        if (TextUtils.isEmpty(this.o)) {
            canvas.drawText("验证中", this.h - (this.k.measureText("验证中") * 0.5f), this.i, this.k);
        } else {
            canvas.drawText(this.o, this.h - (this.k.measureText(this.o) * 0.5f), this.i, this.k);
        }
        postInvalidateDelayed(25);
    }

    public final void a(String str) {
        this.o = str + "%";
        postInvalidate();
    }

    public final void a() {
        this.n.clear();
        postInvalidate();
    }

    private void b() {
        this.b = 220.0f;
        this.c = 4.0f;
        this.d = 20.0f;
        this.e = 20.0f;
        a(-27392);
        a();
    }

    public final void a(int i) {
        this.f = i;
        this.k.setColor(this.f);
    }
}
