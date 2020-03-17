package com.feelings.record.calchart.decorator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;

import static com.prolificinteractive.materialcalendarview.spans.DotSpan.DEFAULT_RADIUS;

public class CustomMultipleDotSpan implements LineBackgroundSpan {
    private float radius;
    private int color;

    CustomMultipleDotSpan(){
        this.radius = DEFAULT_RADIUS;
    }
    CustomMultipleDotSpan(float radius,int color){
        this.radius = radius;
        this.color=color;
    }

    @Override
    public void drawBackground(@NonNull Canvas canvas, @NonNull Paint paint, int left, int right, int top, int baseline, int bottom, @NonNull CharSequence text, int start, int end, int lineNumber) {
        int oldColor = paint.getColor();

        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        canvas.drawCircle(((left + right) / 2),(bottom+top)/2,radius*7,paint);
        paint.setColor(oldColor);
    }
}

