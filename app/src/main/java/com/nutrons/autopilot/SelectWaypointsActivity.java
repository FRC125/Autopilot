package com.nutrons.autopilot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //field y=56.2, x = 26.7

        final double kXPixelsPerFoot = width/26.7;
        final double kYPixelsPerFoot = height/56.2;

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
                waypointArrayX = new double[trajView.circlePoints.size()];
                waypointArrayY = new double[trajView.circlePoints.size()];

                for(int i=0; i<trajView.circlePoints.size(); i++){
                    waypointArrayX[i]= (trajView.circlePoints.get(i).x)/kXPixelsPerFoot;
                    waypointArrayY[i]= (trajView.circlePoints.get(i).y)/kYPixelsPerFoot;
                }

                System.out.println(kXPixelsPerFoot + " - " + kYPixelsPerFoot);

                for(int i=0; i<waypointArrayX.length; i++){
                    System.out.println(waypointArrayX[i]);
                }

                intent.putExtra("MaxAccel", maxAccel);
                intent.putExtra("MaxJerk", maxJerk);
                intent.putExtra("MaxVel", maxVel);
                intent.putExtra("kWheelbaseWidth", kWheelbaseWidth);
                intent.putExtra("pathName", pathName);
                intent.putExtra("pathDescription", pathDescription);
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