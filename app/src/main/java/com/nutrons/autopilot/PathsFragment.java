package com.nutrons.autopilot;

import android.support.v4.app.ListFragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PathsFragment extends ListFragment implements AdapterView.OnItemClickListener {

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
            System.out.println("No paths yet!");
        }else{
            emptyMessage.setVisibility(View.GONE);
            String[] test = getContext().fileList();
            ArrayAdapter adapter = new ArrayAdapter<String>(getContext(), R.layout.list_red_text, test);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Placeholder for click stuff", Toast.LENGTH_SHORT).show();
    }
}