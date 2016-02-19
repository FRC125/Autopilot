package com.nutrons.autopilot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SelectWaypointsActivity extends AppCompatActivity {
    private double mPreviousX;
    private double mPreviousY;

    private Button generateTrajectoryButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_waypoints);

        Intent intent = getIntent();
        double maxAccel = intent.getDoubleExtra("MaxAccel", 0.0);
        double maxJerk = intent.getDoubleExtra("MaxJerk", 0.0);
        double maxVel = intent.getDoubleExtra("MaxVel", 0.0);
        double kWheelbaseWidth = intent.getDoubleExtra("kWheelbaseWidth", 0.0);
        String pathname = intent.getStringExtra("pathName");
        String pathDescription = intent.getStringExtra("pathDescription");

        this.generateTrajectoryButton = (Button) findViewById(R.id.generateTrajectoryButton);
        this.clearButton = (Button) findViewById(R.id.clearButton);
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