package com.nutrons.autopilot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lydia on 2/19/2016.
 */
public class TrajDrawingView extends View {
    private double mPreviousX;
    private double mPreviousY;
    private Paint paint = new Paint();
    private Path path = new Path();

    public TrajDrawingView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                getAngle(mPreviousX, mPreviousY, eventX, eventY);
                System.out.println(-(getAngle(mPreviousX, mPreviousY, eventX, eventY)));
                System.out.println("\n");
                System.out.println(eventX + " - " + eventY);
                path.lineTo(eventX, eventY);
                break;
            default:
                return false;
        }
        mPreviousX = eventX;
        mPreviousY = eventY;

        invalidate();
        return true;
    }

    private double getAngle(double x1, double y1, double x2, double y2) {
        return Math.atan2(y2 - y1, x2 - x1);
    }
}