package com.nutrons.autopilot;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutrons.autopilot.lib.trajectory.Path;
import com.nutrons.autopilot.lib.trajectory.PathGenerator;
import com.nutrons.autopilot.lib.trajectory.TrajectoryGenerator;
import com.nutrons.autopilot.lib.trajectory.WaypointSequence;
import com.nutrons.autopilot.lib.trajectory.io.TextFileSerializer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    public TestFragment() {
        // Required empty public constructor
    }

    public static String joinPath(String path1, String path2) {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }

    private static boolean writeFile(String path, String data) {
        try {
            File file = new File(path);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //need to figure out how to convert getContext.getFilesDir() into a string
        String directory = getContext().getFilesDir().getPath();

        //if (args.length >= 1) {
        //    directory = args[0];
        //}
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();

        config.dt = .01;
        config.max_acc = 10.0;
        config.max_jerk = 60.0;
        config.max_vel = 15.0;

        final double kWheelbaseWidth = 25.5 / 12;

        {
            config.dt = .01;
            config.max_acc = 8.0;
            config.max_jerk = 50.0;
            config.max_vel = 10.0;
            // Path name must be a valid Java class name.
            final String path_name = "Test";

            // Description of this auto mode path.
            WaypointSequence p = new WaypointSequence(10);
            p.addWaypoint(new WaypointSequence.Waypoint(0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(7.0, 0, 0));
            p.addWaypoint(new WaypointSequence.Waypoint(14.0, 1.0, Math.PI / 12.0));

            Path path = PathGenerator.makePath(p, config,
                    kWheelbaseWidth, path_name);

            // Outputs to the directory supplied as the first argument.
            TextFileSerializer js = new TextFileSerializer();
            String serialized = js.serialize(path);
            //System.out.print(serialized);

            String fullpath = joinPath(directory, path_name + ".txt");

            if (!writeFile(fullpath, serialized)) {
                System.err.println(fullpath + " could not be written!!!!1");
                System.exit(1);
            } else {
                System.out.println("Wrote " + fullpath);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}
