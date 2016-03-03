package com.nutrons.autopilot;

import android.content.Context;
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

import java.io.File;

public class PathsFragment extends ListFragment {
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
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.download:
                                    String lDirectory = pathFiles[itemPosition].getPath();
                                    String[] params = {lDirectory, "admin", "10.1.25.25", "/home/lvuser"};
                                    ScpTo.main(params);
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
}