package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.view.graphs.PieCotations;



/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class StatistiquesFragment extends Fragment {

    private ResumeSeanceFragment resumeSeanceFragment;
    private SeanceDB seanceDB;
    private PieCotations pieCotations;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seanceDB = new SeanceDB(getActivity());
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        showResumeSeanceFragment();
        pieCotations = (PieCotations) view.findViewById(R.id.pie_cotations);
        pieCotations.createPie();
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
