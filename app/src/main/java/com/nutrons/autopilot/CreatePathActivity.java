package com.nutrons.autopilot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePathActivity extends AppCompatActivity {
    private Button createTrajectoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_path);

        this.createTrajectoryButton = (Button) findViewById(R.id.createTrajectoryButton);

        this.createTrajectoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePathActivity.this, SelectWaypointsActivity.class);

                EditText editMaxAccel = (EditText) findViewById(R.id.editMaxAccel);
                EditText editMaxJerk = (EditText) findViewById(R.id.editMaxJerk);
                EditText editMaxVel = (EditText) findViewById(R.id.editMaxVel);
                EditText editWheelbaseWidth = (EditText) findViewById(R.id.editWheelbaseWidth);
                EditText editPathName = (EditText) findViewById(R.id.editPathName);
                EditText editPathDescription = (EditText) findViewById(R.id.editTextDescription);

                if ((editMaxAccel.getText().toString().trim().equals("")) || (editMaxJerk.getText().toString().trim().equals("")) || (editMaxVel.getText().toString().trim().equals("")) || (editWheelbaseWidth.getText().toString().trim().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Required field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    double maxAccel = Double.parseDouble(editMaxAccel.getText().toString());
                    double maxJerk = Double.parseDouble(editMaxJerk.getText().toString());
                    double maxVel = Double.parseDouble(editMaxVel.getText().toString());
                    double kWheelbaseWidth = Double.parseDouble(editWheelbaseWidth.getText().toString());
                    String pathName = editPathName.getText().toString();
                    String pathDescription = editPathDescription.getText().toString();

                    intent.putExtra("MaxAccel", maxAccel);
                    intent.putExtra("MaxJerk", maxJerk);
                    intent.putExtra("MaxVel", maxVel);
                    intent.putExtra("kWheelbaseWidth", kWheelbaseWidth);
                    intent.putExtra("pathName", pathName);
                    intent.putExtra("pathDescription", pathDescription);

                    startActivity(intent);
                }
            }
        });
    }
}