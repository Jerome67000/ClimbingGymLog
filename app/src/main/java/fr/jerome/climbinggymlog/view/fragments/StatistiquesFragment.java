package fr.jerome.climbinggymlog.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.SeanceDetailAdapter;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class StatistiquesFragment extends Fragment {

    private ResumeSeanceFragment resumeSeanceFragment;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        showResumeSeanceFragment();
        return view;
    }

    private void showResumeSeanceFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        resumeSeanceFragment = new ResumeSeanceFragment();
        ft.replace(R.id.fragment_resume_seance, resumeSeanceFragment);
        ft.commit();
    }
}
