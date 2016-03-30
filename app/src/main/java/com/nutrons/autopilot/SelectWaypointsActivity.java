package com.nutrons.autopilot;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class SelectWaypointsActivity extends AppCompatActivity {
    private Button generateTrajectoryButton;
    private Button clearButton;
    private TrajDrawingView trajView;
    double[] waypointArrayX;
    double[] waypointArrayY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_waypoints);

        trajView = (TrajDrawingView) findViewById(R.id.view);

        double imageWidth = trajView.getWidth();
        double imageHeight = trajView.getHeight();

        //field y=56.2 ft, x = 26.7 ft

        final double kXPixelsPerFoot = imageWidth / 26.7;
        final double kYPixelsPerFoot = imageHeight / 56.2;

        Intent intent = getIntent();
        final double maxAccel = intent.getDoubleExtra("MaxAccel", 0.0);
        final double maxJerk = intent.getDoubleExtra("MaxJerk", 0.0);
        final double maxVel = intent.getDoubleExtra("MaxVel", 0.0);
        final double kWheelbaseWidth = intent.getDoubleExtra("kWheelbaseWidth", 0.0);
        final String pathName = intent.getStringExtra("pathName");

        this.generateTrajectoryButton = (Button) findViewById(R.id.generateTrajectoryButton);

        generateTrajectoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWaypointsActivity.this, TrajectoryGeneratorActivity.class);
                waypointArrayX = new double[trajView.circlePoints.size()];
                waypointArrayY = new double[trajView.circlePoints.size()];

                for (int i = 1; i < trajView.circlePoints.size() - 1; i++) {
                    waypointArrayX[0] = 0;
                    waypointArrayY[0] = 0;
                    waypointArrayX[i - 1] = (trajView.circlePoints.get(i).x) / kXPixelsPerFoot;
                    waypointArrayY[i - 1] = (trajView.circlePoints.get(i).y) / kYPixelsPerFoot;
                }

                intent.putExtra("MaxAccel", maxAccel);
                intent.putExtra("MaxJerk", maxJerk);
                intent.putExtra("MaxVel", maxVel);
                intent.putExtra("kWheelbaseWidth", kWheelbaseWidth);
                intent.putExtra("pathName", pathName);
                intent.putExtra("waypointArrayX", waypointArrayX);
                intent.putExtra("waypointArrayY", waypointArrayY);

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