package fr.jerome.climbinggymlog.view.graphs;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Représente l'évolution de l'utilisateur sous forme de courbe, chaque point
 * prenant comme valeur la cotation moyenne des 3 dernières séances
 * Created by rcdsm06 on 13/04/2015.
 */
public class LineChartProgress extends LineChart {

    private SeanceDB seanceDB;
    private VoieDB voieDB;

    public LineChartProgress(Context context) {
        super(context);
        voieDB = new VoieDB(context);
        seanceDB = new SeanceDB(context);
        setupGraph();
    }

    public LineChartProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        voieDB = new VoieDB(context);
        seanceDB = new SeanceDB(context);
        setupGraph();
    }

    public LineChartProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        voieDB = new VoieDB(context);
        seanceDB = new SeanceDB(context);
        setupGraph();
    }

    public void setupGraph() {

        this.setDrawGridBackground(false);
        this.setDescription("");
        this.setNoDataText("Pas assez de séances effectuées");
        this.setNoDataTextDescription("Encore un petit effort");
        this.setDrawGridBackground(false);
        this.setTouchEnabled(false);
        this.setDrawGridBackground(false);
        this.getXAxis().setEnabled(false);

        // Setup de l'axis Y
        this.getAxisRight().setEnabled(false);
        YAxis leftAxis = this.getAxisLeft();
        leftAxis.setSpaceTop(45.0f);

        setValues();
        this.invalidate();
    }

    private void setValues() {

        List<Seance> seances = seanceDB.getAllSeances();
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> vals1 = new ArrayList<Entry>();

        if (!seances.isEmpty()) {
            for (Seance s : seances) {
                int score = 0;
                List<Voie> voies = voieDB.getAllVoiesFromSeanceId(s.getId());
                for (Voie v : voies) {
                    score += v.getCotation().getId();
                }
                vals1.add(new Entry(score / voies.size(), (int) s.getId() - 1));
                xVals.add(s.getNom());
            }

            // LineDataSet setup
            LineDataSet set1 = new LineDataSet(vals1, "Progression");
            set1.setDrawCubic(true);
            set1.setLineWidth(4f);
            set1.setColor(Color.rgb(104, 241, 175));

            // Data setup
            LineData data = new LineData(xVals, set1);
            data.setDrawValues(false);
            this.setData(data);
        }
    }
}
