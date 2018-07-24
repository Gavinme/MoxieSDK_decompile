package com.moxie.crawler.common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by taoweisong on 16/8/26.
 */

public class ColorSelectorView extends View {
    Paint paint;
    Shader luar;
    final float[] color = { 1.f, 1.f, 1.f };

    public ColorSelectorView(Context context) {
        this(context, null);
    }

    public ColorSelectorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorSelectorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
            luar = new LinearGradient(0.f, 0.f, 0.f, this.getMeasuredHeight(), 0xffffffff, 0xff000000, Shader.TileMode.REPEAT);
        }
        int rgb = Color.HSVToColor(color);
        Shader dalam = new LinearGradient(0.f, 0.f, this.getMeasuredWidth(), 0.f, 0xffffffff, rgb, Shader.TileMode.REPEAT);
        ComposeShader shader = new ComposeShader(luar, dalam, PorterDuff.Mode.MULTIPLY);
        paint.setShader(shader);
        canvas.drawRect(0.f, 0.f, this.getMeasuredWidth(), this.getMeasuredHeight(), paint);
    }

    void setHue(float hue) {
        color[0] = hue;
        invalidate();
    }
}