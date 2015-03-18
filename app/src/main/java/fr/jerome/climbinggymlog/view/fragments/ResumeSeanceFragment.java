package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.SeanceDetailAdapter;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 14/03/15.
 */
public class ResumeSeanceFragment extends Fragment {

//    public static final String KEY_PREFIX = "fr.jerome.climbinggymlog.view.dialog.ResumeSeanceFragment.";
//    public static final String ARG_SEANCEADAPTER_KEY = KEY_PREFIX + "seanceAdapter-key";

    private TextView txNbVoiesReussies;
    private TextView txMeilleureVoie;
    private TextView txCotationMoyenne;
    private SeanceDetailAdapter seanceDetailAdapter;

    private int seanceId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        VoieDB voieDB = new VoieDB(getActivity());

        ArrayList<Voie> voies = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(seanceId);

        seanceDetailAdapter = new SeanceDetailAdapter(getActivity(), R.layout.adapter_seance, voies);

        voieDB.close();

        View view = inflater.inflate(R.layout.fragment_resume_seance, container, false);
        txNbVoiesReussies = (TextView) view.findViewById(R.id.val_voies_reussies);
        txMeilleureVoie = (TextView) view.findViewById(R.id.val_meilleure_voie);
        txCotationMoyenne = (TextView) view.findViewById(R.id.val_cot_moyenne);
        refreshResumeSeance();

        return view;
    }

    public void setSeanceId(int id) {

        this.seanceId = id;
    }

    public void refreshResumeSeance() {

        ArrayList<Voie> voiesSeance = new ArrayList<Voie>();

        // Récupère les voies de la séance
        for (int i=0; i < seanceDetailAdapter.getCount(); i++) {
            voiesSeance.add((Voie) seanceDetailAdapter.getItem(i));
        }

        if (!voiesSeance.isEmpty()) {

            int nbVoiesReussies = 0;
            int nbVoiesTotal = seanceDetailAdapter.getCount();
            Cotation meilleureCotation;
            Cotation cotMoyenne;
            int numCotMoyenne = 0;

            meilleureCotation = voiesSeance.get(0).getCotation();

            for (Voie v : voiesSeance) {
                if (v.isReussi())
                    nbVoiesReussies++;

                if (meilleureCotation.getId() < v.getCotation().getId())
                    meilleureCotation = v.getCotation();

                numCotMoyenne += v.getCotation().getId();
            }

            cotMoyenne = AppManager.cotations.get((int) (numCotMoyenne / voiesSeance.size()));

            // Met à jour les TextView
            txNbVoiesReussies.setText(nbVoiesReussies + " sur " + nbVoiesTotal);
            txMeilleureVoie.setText(meilleureCotation.getNom());
            txCotationMoyenne.setText(String.valueOf(cotMoyenne.getNom()));
        }
    }
}
