package fr.jerome.climbinggymlog.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.controller.SeanceAdapter;
import fr.jerome.climbinggymlog.database.SeanceDB;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeancesFragment extends Fragment {

    View rootView;
    Context context;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.seances_fragment, container, false);
        context = rootView.getContext();
        SeanceDB seanceDB = new SeanceDB(rootView.getContext());

        // Afficher vue vide si aucune séances
        if(seanceDB.getAllSeances().isEmpty())
            rootView = inflater.inflate(R.layout.seance_vide_fragment, container, false);
        else {
            SeanceAdapter adapter = new SeanceAdapter(context, R.layout.seance_row, seanceDB.getAllSeances());

            ListView listView = (ListView) rootView.findViewById(R.id.seances_listview);
            listView.setAdapter(adapter);
        }

        seanceDB.close();



        return rootView;
    }
}
