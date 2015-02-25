package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shamanland.fab.FloatingActionButton;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.controller.SeanceAdapter;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeancesFragment extends Fragment {

    private SeanceAdapter seanceAdapter;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;

        SeanceDB seanceDB = new SeanceDB(getActivity().getBaseContext());

        // Si liste séance non vide, on affiche une listView, sinon vue vide
//        if(!seanceDB.getAllSeances().isEmpty()) {

            rootView = inflater.inflate(R.layout.fragment_seances, container, false);

            seanceAdapter = new SeanceAdapter(getActivity(), R.layout.row_seance, seanceDB.getAllSeances());
            ListView listView = (ListView) rootView.findViewById(R.id.seances_listview);
            listView.setAdapter(seanceAdapter);
            seanceDB.close();
//        }
//        else {
//            rootView = inflater.inflate(R.layout.fragment_seance_vide, container, false);
//        }

        /** Ajout du fap nouvelle séance */
        FloatingActionButton fap = (FloatingActionButton) rootView.findViewById(R.id.fap_add_seance);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddSeanceDialog();            }
        });

        return rootView;
    }

    private void showAddSeanceDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // FIXME lorsqu'on veut ajouter 2 séances à la suite, la vue n'est pas raffraichie et le getCount est faussé
        AddSeanceDialog addSeanceDialog = AddSeanceDialog.newInstance(seanceAdapter.getCount() + 1);
        addSeanceDialog.show(fm, AddSeanceDialog.KEY_PREFIX);
    }

//    @Override
//    public void onFinishAddSeanceDialog(Seance newSeance) {
//        seanceAdapter.add(newSeance);
//        seanceAdapter.notifyDataSetChanged();
//    }
}
