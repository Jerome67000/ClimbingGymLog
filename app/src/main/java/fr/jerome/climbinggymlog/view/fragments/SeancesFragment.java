package fr.jerome.climbinggymlog.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.shamanland.fab.FloatingActionButton;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.controller.SeanceAdapter;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeancesFragment extends Fragment {

    View rootView;
    Context context;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_seance_vide, container, false);
        context = rootView.getContext();
        SeanceDB seanceDB = new SeanceDB(rootView.getContext());

        // Si liste séance non vide, on affiche une listView
        if(!seanceDB.getAllSeances().isEmpty()) {

            rootView = inflater.inflate(R.layout.fragment_seances, container, false);
            context = rootView.getContext();

            SeanceAdapter adapter = new SeanceAdapter(context, R.layout.row_seance, seanceDB.getAllSeances());

            ListView listView = (ListView) rootView.findViewById(R.id.seances_listview);
            listView.setAdapter(adapter);
        }

        /** Ajout du bouton Add */
        FloatingActionButton fap = (FloatingActionButton) rootView.findViewById(R.id.fap_add_seance);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "Ajout", Toast.LENGTH_SHORT).show();
                new AddSeanceDialog().show(getFragmentManager(), "0");
            }
        });

        seanceDB.close();

        return rootView;
    }
}
