package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 14/03/15.
 */
public class ResumeSeanceFragment extends Fragment {

    private ArrayList<Voie> voies;
    private TextView txNbVoiesReussies;
    private TextView txMeilleureVoie;
    private TextView txCotationMoyenne;

    private int seanceId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resume_seance, container, false);
        txNbVoiesReussies = (TextView) view.findViewById(R.id.val_voies_reussies);
        txMeilleureVoie = (TextView) view.findViewById(R.id.val_meilleure_voie);
        txCotationMoyenne = (TextView) view.findViewById(R.id.val_cot_moyenne);

        refreshView();

        return view;
    }

    /**
     * Met à jour les TextView avec les valeurs calculées pour le résumé
     */
    public void refreshView() {

        updateListeVoies();

        if (!voies.isEmpty()) {

            int nbVoiesReussies = 0;
            int nbVoiesTotal = voies.size();
            Cotation meilleureCotation = voies.get(0).getCotation();
            Cotation cotMoyenne;
            int numCotMoyenne = 0;

            // Récupère les valeurs depuis la liste des voies
            for (Voie v : voies) {
                if (v.isReussi())
                    nbVoiesReussies++;

                if (meilleureCotation.getId() < v.getCotation().getId())
                    meilleureCotation = v.getCotation();

                numCotMoyenne += v.getCotation().getId();
            }

            //FIXME out of the box nb cotation
            cotMoyenne = AppManager.cotations.get((int) (numCotMoyenne / voies.size()));

            // Met à jour les TextView
            txNbVoiesReussies.setText(nbVoiesReussies + " sur " + nbVoiesTotal);
            txMeilleureVoie.setText(meilleureCotation.getNom());
            txCotationMoyenne.setText(String.valueOf(cotMoyenne.getNom()));
        }
    }

    /**
     * Définit l'id de la séance affiché en tant que résumé
     * @param id de la séance
     */
    public void setSeanceId(int id) {
        this.seanceId = id;
    }

    /**
     * Update de la liste des voies de la séance
     */
    private void updateListeVoies() {
        VoieDB voieDB = new VoieDB(getActivity());
        voies = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(seanceId);
        voieDB.close();
    }
}
