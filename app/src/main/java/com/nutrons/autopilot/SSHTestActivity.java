package com.nutrons.autopilot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class SSHTestActivity extends AppCompatActivity {
    static PrintStream commander;
    static ByteArrayOutputStream baos = new ByteArrayOutputStream();
    static Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sshtest);
        String directory = getApplicationContext().getDir("NUTRONsCAT", CONTEXT_IGNORE_SECURITY).getPath();
        final File file = new File(directory);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    executeRemoteCommand("admin","admin","10.1.25.2", 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                commander.println("ls");
                commander.println("put" + file.getPath() + "/home/lvuser");
            }
        });
    }

    public static String executeRemoteCommand(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelShell channelssh = (ChannelShell)
        session.openChannel("shell");
        OutputStream inputstream_for_the_channel = channelssh.getOutputStream();
        commander = new PrintStream(inputstream_for_the_channel, true);

        channelssh.setOutputStream(baos);

        channelssh.setOutputStream(System.out, true);

        channelssh.connect();

        commander.close();
        session.disconnect();
        channelssh.disconnect();
        return baos.toString();
    }
}