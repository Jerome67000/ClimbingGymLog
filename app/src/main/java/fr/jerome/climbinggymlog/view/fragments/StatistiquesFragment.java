package fr.jerome.climbinggymlog.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class StatistiquesFragment extends Fragment {

    private ResumeSeanceFragment resumeSeanceFragment;
    private SeanceDB seanceDB;
    private PieChart pieChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seanceDB = new SeanceDB(getActivity());
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        showResumeSeanceFragment();
        createPieCotations(view);
        return view;
    }

    private void showResumeSeanceFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        resumeSeanceFragment = new ResumeSeanceFragment();
        resumeSeanceFragment.setSeanceId(seanceDB.getLastSeanceId());
        ft.replace(R.id.fragment_resume_seance, resumeSeanceFragment);
        ft.commit();
    }

    private void createPieCotations(View view) {
        pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);
        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setRotationAngle(0);
        pieChart.setHoleRadius(45f);
        pieChart.setCenterText("% Cotation");

        SeanceDB seanceDB = new SeanceDB(getActivity());
        VoieDB voieDB = new VoieDB(getActivity());
        // Récupère toutes les séances
        ArrayList<Seance> seances = (ArrayList<Seance>) seanceDB.getAllSeances();
        HashMap<String, Integer> nombreCotations = new HashMap<String, Integer>();

        for (Seance s : seances) {
            ArrayList<Voie> voiesDuneSeance = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(s.getId());
            for (Voie v : voiesDuneSeance) {
                String key = v.getCotation().getNom();
                if (nombreCotations.containsKey(key))
                    nombreCotations.put(key, nombreCotations.get(key) + 1);
                else
                    nombreCotations.put(key, 1);
            }
        }

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        Iterator<String> keySetIterator = nombreCotations.keySet().iterator();
        int i = 0;
        while(keySetIterator.hasNext()){
            String key = keySetIterator.next();
            int value = nombreCotations.get(key);
            xVals.add(key);
            yVals1.add(new Entry((float) value, i));
            i++;
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "% Cotation");
        dataSet.setSliceSpace(3f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData(xVals, dataSet);
        pieChart.setData(data);
    }

    public ResumeSeanceFragment getResumeSeanceFragment() {
        return resumeSeanceFragment;
    }
}
