package com.nutrons.autopilot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectWaypointsActivity extends AppCompatActivity {
    private Button generateTrajectoryButton;
    private Button clearButton;
    private TrajDrawingView trajView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_waypoints);

        trajView = (TrajDrawingView) findViewById(R.id.view);

        Intent intent = getIntent();
        final double maxAccel = intent.getDoubleExtra("MaxAccel", 0.0);
        final double maxJerk = intent.getDoubleExtra("MaxJerk", 0.0);
        final double maxVel = intent.getDoubleExtra("MaxVel", 0.0);
        final double kWheelbaseWidth = intent.getDoubleExtra("kWheelbaseWidth", 0.0);
        final String pathName = intent.getStringExtra("pathName");
        final String pathDescription = intent.getStringExtra("pathDescription");

        this.generateTrajectoryButton = (Button) findViewById(R.id.generateTrajectoryButton);
        generateTrajectoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWaypointsActivity.this, TrajectoryGeneratorActivity.class);

                intent.putExtra("MaxAccel", maxAccel);
                intent.putExtra("MaxJerk", maxJerk);
                intent.putExtra("MaxVel", maxVel);
                intent.putExtra("kWheelbaseWidth", kWheelbaseWidth);
                intent.putExtra("pathName", pathName);
                intent.putExtra("pathDescription", pathDescription);

                startActivity(intent);
            }
        });

        this.clearButton = (Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trajView.clear();
            }
        });
    }
}