package com.nutrons.autopilot;

import android.content.Context;
import android.content.Intent;
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

import java.io.File;

public class PathsFragment extends ListFragment{
    File[] pathFiles = getContext().getDir("NUTRONsCAT", Context.MODE_PRIVATE).listFiles();

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

        if(getContext().fileList()==null){
            emptyMessage.setVisibility(View.VISIBLE);
        }else{
            emptyMessage.setVisibility(View.GONE);
            String[] paths = getContext().getDir("NUTRONsCAT", Context.MODE_PRIVATE).list();
            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.list_red_text, paths);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    PopupMenu popup = new PopupMenu(getActivity(), view);
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                    popup.show();
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.download:
                                    Intent intent = new Intent(getActivity(), SSHToRoboRIOActivity.class);
                                    intent.putExtra("File", pathFiles[getSelectedItemPosition()].getPath());
                                    startActivity(intent);
                                    Toast.makeText(getActivity(), "Downloading...", Toast.LENGTH_SHORT).show();
                                    return true;
                                case R.id.delete:
                                    getContext().getDir("NUTRONsCAT", Context.MODE_PRIVATE).delete();
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