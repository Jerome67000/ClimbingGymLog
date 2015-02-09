package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.controller.SeanceAdapter;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.model.Seance;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeancesFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.seances_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.seances_listview);

        SeanceDB seanceDB = new SeanceDB(rootView.getContext());

        SeanceAdapter adapter = new SeanceAdapter(rootView.getContext());
        listView.setAdapter(adapter);

        return rootView;
    }
}
