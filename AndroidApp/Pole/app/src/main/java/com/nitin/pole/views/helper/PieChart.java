package com.nitin.pole.views.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Nitin on 3/17/2017.
 */

public class PieChart extends View {
    private Paint mPaint;
    private RectF mRectF;
    private float[] values;

    public PieChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setValues(float[] values) {
        this.values = values;
        invalidate();
    }

    private float[] getPieSegments() {
        float[] segments = new float[this.values.length];
        float total = getTotal();
        for (int i = values.length - 1; i >= 0; i--) {
            segments[i] = (this.values[i] / total) * 360;
        }
        return segments;
    }

    private float getTotal() {
        float total = 0f;
        for (float value : values) {
            total += value;
        }
        return total;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Random rnd = new Random();
        if (values != null) {
            int top = 0;
            int left = 0;
            int endBottom = getHeight();
            int endRight = endBottom;
            mRectF = new RectF(left, top, endRight, endBottom);
            float[] segment = getPieSegments();
            float segStartPoint = 0;
            for (int i = 0; i < segment.length; i++) {
                int color = Color.argb(255, (int) segment[i], rnd.nextInt(256), rnd.nextInt(256));
                mPaint.setColor(color);
                canvas.drawArc(mRectF, segStartPoint, segment[i], true, mPaint);
                segStartPoint += segment[i];
            }
        }
        super.onDraw(canvas);
    }
}
