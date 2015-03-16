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
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 14/03/15.
 */
public class ResumeSeanceFragment extends Fragment {

    public static final String KEY_PREFIX = "fr.jerome.climbinggymlog.view.dialog.ResumeSeanceFragment.";
    public static final String ARG_SEANCEADAPTER_KEY = KEY_PREFIX + "seanceAdapter-key";

    private TextView txNbVoiesReussies;
    private TextView txMeilleureVoie;
    private TextView txCotationMoyenne;
    private SeanceDetailAdapter seanceDetailAdapter;

//    public static ResumeSeanceFragment newInstance(SeanceAdapter seanceAdapter) {
//        ResumeSeanceFragment fragment = new ResumeSeanceFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SEANCEADAPTER_KEY, seanceAdapter);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resume_seance, container, false);

        txNbVoiesReussies = (TextView) view.findViewById(R.id.val_voies_reussies);
        txMeilleureVoie = (TextView) view.findViewById(R.id.val_meilleure_voie);
        txCotationMoyenne = (TextView) view.findViewById(R.id.val_cot_moyenne);

        refreshResumeSeance();

        return view;
    }

    public void setSeanceDetailAdapter(SeanceDetailAdapter seanceDetailAdapter) {

        this.seanceDetailAdapter = seanceDetailAdapter;
    }

    private void refreshResumeSeance() {

        int nbVoiesReussies = 0;
        int cotationMoyenne = 0;

        Cotation meilleureCotation;

        ArrayList<Voie> voies = new ArrayList<Voie>();

        for (int i=0; i < seanceDetailAdapter.getCount(); i++) {
            voies.add((Voie) seanceDetailAdapter.getItem(i));
        }

        meilleureCotation = voies.get(0).getCotation();

        for (Voie v : voies) {
            if (v.isReussi())
                nbVoiesReussies++;

            if (meilleureCotation.getId() < v.getCotation().getId())
                meilleureCotation = v.getCotation();

            cotationMoyenne += v.getCotation().getId();

        }

        Cotation cotMoyenne = AppManager.cotations.get((int) (cotationMoyenne/voies.size()));

        txNbVoiesReussies.setText(nbVoiesReussies + " sur " + seanceDetailAdapter.getCount());
        txMeilleureVoie.setText(meilleureCotation.getNom());
        txCotationMoyenne.setText(String.valueOf(cotMoyenne.getNom()));

    }
}
