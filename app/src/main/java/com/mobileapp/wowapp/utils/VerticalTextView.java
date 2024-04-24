package com.mobileapp.wowapp.utils;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class VerticalTextView extends AppCompatTextView {

    private TextPaint textPaint;
    private float textWidth;
    private float textHeight;

    public VerticalTextView(Context context) {
        super(context);
        initialize();
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setAntiAlias(true);
        setTextSize(getTextSize());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldw, oldh);
        textWidth = h;
        textHeight = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        canvas.drawText(getText().toString(), 0, getTextSize(), textPaint);
    }
}