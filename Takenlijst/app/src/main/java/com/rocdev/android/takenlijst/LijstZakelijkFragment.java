package com.rocdev.android.takenlijst;


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
 */
public class LijstZakelijkFragment extends Fragment {
    ListView taakListView;

    public LijstZakelijkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lijst_zakelijk, container, false);

        taakListView = (ListView) view.findViewById (R.id.takenlijst_zakelijk_listView);
        return view;
    }

    public void refreshTaskList() {
        // get the database object
        TakenlijstDB db = new TakenlijstDB(getActivity().getApplicationContext());

        // get the tasks
        ArrayList<Taak> taken = db.getTaken("Zakelijk");

        // build the string for the tasks
//        String text = "";
//        for (Taak task : tasks) {
//            text += task.getNaam() + "\n";
//        }
//
//        // display the string on the user interface
//        taakTextView.setText(text);

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
