package com.nutrons.autopilot;


import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PathsFragment extends ListFragment implements AdapterView.OnItemClickListener{
    public PathsFragment() {
        // Required empty public constructor
    }

    @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_paths, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.PathNames, R.layout.list_red_text);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Placeholder for click stuff" + position, Toast.LENGTH_SHORT).show();
    }
}
