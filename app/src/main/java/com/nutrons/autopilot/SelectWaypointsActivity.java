package com.nutrons.autopilot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class SelectWaypointsActivity extends AppCompatActivity {
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
}