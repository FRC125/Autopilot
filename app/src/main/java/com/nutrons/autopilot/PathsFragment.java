package com.nutrons.autopilot;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PathsFragment extends ListFragment {
    File[] pathFiles;
    int itemPosition;

    UsbManager manager;
    UsbAccessory accessory;
    ParcelFileDescriptor fileDescriptor;
    FileOutputStream outputStream;
    BufferedReader br;
    File downloadFile;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

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
        manager = (UsbManager) getContext().getSystemService(Context.USB_SERVICE);
        final PendingIntent permissionIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        getContext().registerReceiver(mUsbReceiver, filter);

        pathFiles = getContext().getDir("NUTRONsCAT", Context.MODE_PRIVATE).listFiles();

        if (getContext().fileList() == null) {
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
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
                        public boolean onMenuItemClick(final MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.download:
                                    new AsyncTask<Integer, Void, Void>() {
                                        @Override
                                        protected Void doInBackground(Integer... params) {
                                            try {
                                                //String lDirectory = pathFiles[itemPosition].getPath();
                                                //String[] config = {lDirectory, "admin", "10.1.25.25", "/home/lvuser"};
                                                downloadFile = pathFiles[itemPosition];
                                                manager.requestPermission(accessory, permissionIntent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            return null;
                                        }
                                    }.execute(1);

                                    return true;
                                case R.id.delete:
                                    boolean ifDeleted = pathFiles[position].delete();
                                    if (ifDeleted) {
                                        Toast.makeText(getActivity(), "Deleted! (Sorry view doesn't refresh)", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Whoops! Not deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                    getListView().invalidate();
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

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    accessory = (UsbAccessory) intent.getParcelableExtra(UsbManager.EXTRA_ACCESSORY);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (accessory != null) {
                            //All the downloading stuffs, opening, etc
                            fileDescriptor = manager.openAccessory(accessory);
                            if (fileDescriptor != null) {
                                FileDescriptor fd = fileDescriptor.getFileDescriptor();
                                outputStream = new FileOutputStream(fd);
                                FileWriter fw = null;
                                try {
                                    fw = new FileWriter("CustomTrajectory.txt", true);
                                } catch (IOException e) {
                                    System.out.println("File not found");
                                }
                                BufferedWriter bw = new BufferedWriter(fw);
                                try {
                                    FileReader fr = new FileReader(downloadFile);
                                    br = new BufferedReader(fr);
                                    String line;
                                    while ((line = br.readLine()) != null) {
                                        bw.write(line);
                                        bw.newLine();
                                    }
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found");
                                } catch (IOException e) {
                                    System.out.println("Couldn't read the file");
                                } finally {
                                    try {
                                        br.close();
                                    } catch (IOException e) {
                                        System.out.println("couldn't close br");
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Denied USB Permission");
                    }
                }
            }
        }
    };
}