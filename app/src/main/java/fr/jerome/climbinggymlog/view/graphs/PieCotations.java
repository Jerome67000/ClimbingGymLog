package fr.jerome.climbinggymlog.view.graphs;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by rcdsm06 on 13/04/2015.
 * Classe représentant le % des cotations des voies grimpeées sous forme de graph camembert
 */
public class PieCotations extends PieChart {

    private Context context;

    public PieCotations(Context context) {
        super(context);
        this.context = context;
    }

    public PieCotations(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PieCotations(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public void createPie() {

        HashMap<String, Integer> values = getValues();

        this.setDrawCenterText(true);
        this.setDrawHoleEnabled(true);
        this.setUsePercentValues(true);
        this.setTouchEnabled(false);

        this.setCenterText("% Cotation");

        ArrayList<String> xVals = new ArrayList<String>();

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        Iterator<String> keySetIterator = values.keySet().iterator();
        int i = 0;

        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            int value = values.get(key);
            xVals.add(key);
            yVals1.add(new Entry((float) value, i));
            i++;
        }
        PieDataSet dataSet = new PieDataSet(yVals1, "% Cotation");
        dataSet.setSliceSpace(3f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData data = new PieData(xVals, dataSet);

        this.setData(data);
    }

    public HashMap<String, Integer> getValues() {

        SeanceDB seanceDB = new SeanceDB(context);
        VoieDB voieDB = new VoieDB(context);
        // R�cup�re toutes les s�ances
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
        return nombreCotations;
    }
}


