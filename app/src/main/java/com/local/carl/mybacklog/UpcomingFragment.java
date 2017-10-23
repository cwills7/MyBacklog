package com.local.carl.mybacklog;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.local.carl.mybacklog.db.BacklogDb;

import org.w3c.dom.Text;

/**
 * Created by carlr on 10/22/2017.
 */

public class UpcomingFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private TextView tv;
    BacklogDb db;

    public UpcomingFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.planets, android.R.layout.simple_list_item_1);
//        setListAdapter(adapter);
//        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
}
