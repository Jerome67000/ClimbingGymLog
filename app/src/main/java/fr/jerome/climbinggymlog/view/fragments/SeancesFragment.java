package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.controller.SeanceAdapter;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeancesFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.seances_fragment, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.seances_listview);

        SeanceDB seanceDB = new SeanceDB(rootView.getContext());

        SeanceAdapter adapter = new SeanceAdapter(rootView.getContext(), R.layout.seance_row, seanceDB.getAllSeances());
        listView.setAdapter(adapter);

        seanceDB.close();

        rootView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddSeanceDialog().show(getFragmentManager(), "0");
                Toast.makeText(rootView.getContext(), "Add voie", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
