package com.wuxiankeneng.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class RoundAngleImageView extends AppCompatImageView {
    float width, height;

    public RoundAngleImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public RoundAngleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public RoundAngleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= 24 && height > 24) {
            Path path = new Path();
            //四个圆角
            path.moveTo(24, 0);
            path.lineTo(width - 24, 0);
            path.quadTo(width, 0, width, 24);
            path.lineTo(width, height - 24);
            path.quadTo(width, height, width - 24, height);
            path.lineTo(24, height);
            path.quadTo(0, height, 0, height - 24);
            path.lineTo(0, 24);
            path.quadTo(0, 0, 24, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
