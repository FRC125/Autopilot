package com.nutrons.autopilot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class SelectWaypointsActivity extends AppCompatActivity {
    private double mPreviousX;
    private double mPreviousY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_waypoints);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        double x = event.getX();
        double y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                getAngle(mPreviousX, mPreviousY, x, y);
                System.out.println(-(getAngle(mPreviousX, mPreviousY, x, y)));
                break;
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    private double getAngle(double x1, double y1, double x2, double y2) {
        return Math.atan2(y2 - y1, x2 - x1);
    }
}
