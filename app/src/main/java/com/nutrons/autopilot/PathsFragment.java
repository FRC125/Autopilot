package com.nutrons.autopilot;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class PathsFragment extends ListFragment{
    Context context = getContext();
    File[] pathFiles;
    int itemPosition;

    public PathsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paths, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView emptyMessage = (TextView) getActivity().findViewById(R.id.textViewEmptyMessage);

        pathFiles = getContext().getDir("NUTRONsCAT", Context.MODE_PRIVATE).listFiles();

        if(getContext().fileList()==null){
            emptyMessage.setVisibility(View.VISIBLE);
        }else{
            emptyMessage.setVisibility(View.GONE);
            final String[] paths = getContext().getDir("NUTRONsCAT", Context.MODE_PRIVATE).list();
            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.list_red_text, paths);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    itemPosition = position;

                    PopupMenu popup = new PopupMenu(getActivity(), view);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.download:
                                    SSHToRoboRIOTask task = new SSHToRoboRIOTask();
                                    task.execute();
                                    return true;
                                case R.id.delete:
                                    getContext().deleteFile(paths[position]);
                                    getListView().invalidateViews();
                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                }
            });
        }
    }
    private class SSHToRoboRIOTask extends AsyncTask<Void, Void, Void> {
        PrintStream commander;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Session session;

        @Override
        protected void onPreExecute(){
            Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String directory = pathFiles[itemPosition].getPath();
            try {
                executeRemoteCommand("admin","admin","10.1.25.2", 22);
                publishProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            commander.println("put " + directory + " /home/lvuser");

            return null;
        }
        public String executeRemoteCommand(String username, String password, String hostname, int port)
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

        @Override
        protected void onPostExecute(Void v){
            Toast.makeText(getActivity(), "Downloaded!", Toast.LENGTH_SHORT).show();
        }
    }
}