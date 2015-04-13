package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Seance;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class StatistiquesFragment extends Fragment {

    private ResumeSeanceFragment resumeSeanceFragment;
    private SeanceDB seanceDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seanceDB = new SeanceDB(getActivity());
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        // Affichage du nombre total de voies effectuées
        VoieDB voieDB = new VoieDB(getActivity());
        int nbVoiesTotal = 0;
        List<Seance> seances = seanceDB.getAllSeances();
        for (Seance s : seances) {
            nbVoiesTotal += voieDB.getAllVoiesFromSeanceId(s.getId()).size();
        }
        ((TextView) view.findViewById(R.id.nb_voies_total)).setText("Nombre de voies grimpées : " + nbVoiesTotal);

        showResumeSeanceFragment();

        voieDB.close();
        seanceDB.close();

        return view;
    }

    private void showResumeSeanceFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        resumeSeanceFragment = new ResumeSeanceFragment();
        resumeSeanceFragment.setSeanceId(seanceDB.getLastSeanceId());
        ft.replace(R.id.fragment_resume_seance, resumeSeanceFragment);
        ft.commit();
    }

    public ResumeSeanceFragment getResumeSeanceFragment() {
        return resumeSeanceFragment;
    }
}
