package com.wuxl.interviewquestions.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * 一条直线，从左到右逐渐显示
 * Author:wuxianglong;
 * Time:2017/1/10.
 */
public class LineView extends View {

    private static final String TAG = "TAG";
    private Paint linePaint;

    private float fromX = 0;

    private ValueAnimator valueAnimator;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.parseColor("#FF666666"));
        linePaint.setStyle(Paint.Style.FILL);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        valueAnimator = ValueAnimator.ofFloat(0, w);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                fromX = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(700);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new AccelerateInterpolator(1.2f));
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0f, 1f, fromX, 1f, linePaint);
    }

}
