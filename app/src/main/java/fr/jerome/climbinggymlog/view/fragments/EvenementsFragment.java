package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.EvenementsAdapter;
import fr.jerome.climbinggymlog.data.EvenementDB;
import fr.jerome.climbinggymlog.models.Evenement;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class EvenementsFragment extends Fragment {

    private EvenementsAdapter adapter;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_evenement, container, false);

        EvenementDB evenementDB = new EvenementDB(getActivity());
        ArrayList<Evenement> evenements = (ArrayList<Evenement>) evenementDB.getAllEvenements();

        adapter = new EvenementsAdapter(getActivity(), R.layout.row_seance_adapter, evenements);
        ListView listView = (ListView) rootView.findViewById(R.id.events_listview);
        listView.setAdapter(adapter);

        evenementDB.close();

        return rootView;
    }
}
