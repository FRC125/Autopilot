package com.nutrons.autopilot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TrajDrawingView extends View {
    private Paint paint = new Paint();
    public List<Point> circlePoints;

    public TrajDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(10f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);

        circlePoints = new ArrayList<Point>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 20, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                circlePoints.add(new Point(Math.round(eventX), Math.round(eventY)));
                break;
        }
        postInvalidate();
        return true;
    }

    public void clear() {
        circlePoints.clear();

        Context context = getContext();
        CharSequence text = "Select a new waypoint";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}