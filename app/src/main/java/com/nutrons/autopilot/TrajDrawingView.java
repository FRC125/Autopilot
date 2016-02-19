package com.nutrons.autopilot;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lydia on 2/19/2016.
 */
public class TrajDrawingView extends View {
    private Button clearButton;
    private Paint paint = new Paint();
    private List<Point> circlePoints;

    public TrajDrawingView(Context context, AttributeSet attributeSet){
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
        for(Point p : circlePoints){
            canvas.drawCircle(p.x, p.y, 20, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                circlePoints.add(new Point(Math.round(eventX), Math.round(eventY)));
                System.out.println(eventX + " - " + eventY);
                System.out.println("\n");
                System.out.println(circlePoints);
                break;
        }
        postInvalidate();
        return true;
    }

    public void clear(){
        circlePoints.clear();

        Context context = getContext();
        CharSequence text = "Select a new waypoint";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}