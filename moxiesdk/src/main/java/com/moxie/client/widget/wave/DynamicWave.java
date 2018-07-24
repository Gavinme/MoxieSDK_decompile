package com.moxie.client.widget.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

/* compiled from: TbsSdkJava */
public class DynamicWave extends View {
    private int a;
    private float b;
    private int c;
    private int d;
    private float[] e;
    private float[] f;
    private float[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private Paint l = new Paint();
    private DrawFilter m;

    public DynamicWave(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.h = UiUtils.a(context, 7);
        this.i = UiUtils.a(context, 5);
        this.l.setAntiAlias(true);
        this.l.setStyle(Style.FILL);
        this.l.setColor(-27392);
        this.m = new PaintFlagsDrawFilter(0, 3);
        this.a = (UiUtils.a(context) / 240) * 200;
    }

    public final void a(int i) {
        this.l.setColor(i);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(this.m);
        int length = this.e.length - this.j;
        System.arraycopy(this.e, this.j, this.f, 0, length);
        System.arraycopy(this.e, 0, this.f, length, this.j);
        length = this.e.length - this.k;
        System.arraycopy(this.e, this.k, this.g, 0, length);
        System.arraycopy(this.e, 0, this.g, length, this.k);
        for (int i = 0; i < this.c; i++) {
            canvas.drawLine((float) i, (((float) this.d) - this.f[i]) - ((float) this.a), (float) i, (float) this.d, this.l);
            canvas.drawLine((float) i, (((float) this.d) - this.g[i]) - ((float) this.a), (float) i, (float) this.d, this.l);
        }
        this.j += this.h;
        this.k += this.i;
        if (this.j >= this.c) {
            this.j = 0;
        }
        if (this.k > this.c) {
            this.k = 0;
        }
        postInvalidate();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.c = i;
        this.d = i2;
        this.e = new float[this.c];
        this.f = new float[this.c];
        this.g = new float[this.c];
        this.b = (float) (6.283185307179586d / ((double) this.c));
        for (int i5 = 0; i5 < this.c; i5++) {
            this.e[i5] = (float) ((20.0d * Math.sin((double) (this.b * ((float) i5)))) + 0.0d);
        }
    }

    public final int a() {
        return this.a;
    }
}
