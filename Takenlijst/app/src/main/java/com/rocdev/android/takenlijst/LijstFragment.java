package com.rocdev.android.takenlijst;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LijstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LijstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LijstFragment extends Fragment {
    //    TextView taakTextView;
    ListView taakListView;
    int tabPos;


    public LijstFragment() {
        // Required empty public constructor
    }



    public void setTabPos(int tabPos) {
        this.tabPos = tabPos;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lijst, container, false);

        // get references to widget
        taakListView = (ListView) view.findViewById (R.id.takenlijst_listView);
        refreshTaskList();
        return view;
    }

    public void refreshTaskList() {
        String[] lijsten = {"Persoonlijk", "Zakelijk"};
        // get the database object
        TakenlijstDB db = new TakenlijstDB(getActivity().getApplicationContext());

        // get the tasks
        ArrayList<Taak> taken = db.getTaken(lijsten[tabPos]);


        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        for (Taak taak : taken) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("naam", taak.getNaam());
            map.put("notitie", taak.getNotitie());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_taak;
        String[] from = {"naam", "notitie"};
        int[] to = { R.id.NaamTextView, R.id.NotitiesTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(getActivity(), data, resource, from, to);
        taakListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshTaskList();
    }

}
