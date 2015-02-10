package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.controller.VoieAdapter;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.model.Voie;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class VoiesFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.voies_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.voies_listview);

        SeanceDB seanceDB = new SeanceDB(rootView.getContext());

        ArrayList<Voie> voies = seanceDB.getAllSeances().get(0).getVoies();

        VoieAdapter adapter = new VoieAdapter(rootView.getContext(), R.layout.voies_row, voies);
        listView.setAdapter(adapter);

        seanceDB.close();

        return rootView;
    }
}
