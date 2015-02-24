package fr.jerome.climbinggymlog.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.VoieActivity;
import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;


/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeanceAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List<Seance> seances;

    public SeanceAdapter(Context context, int resource, List<Seance> seances) {

        super(context, resource, seances);
        this.context = context;
        this.seances = seances;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ((ListView) parent).setOnItemClickListener(this);

        Seance seance = seances.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_seance, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_seance);
        TextView txDateAj = (TextView) rowView.findViewById(R.id.date_seance);

        txTitre.setText(seance.getNom());
        txDateAj.setText(seance.getDateAjout().toString());

        return rowView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("position ", String.valueOf(position));
        Log.d("id ", String.valueOf(id));

        // Ouverture de la liste des voies concernant cette séance
        Intent i = new Intent(context, VoieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AddSeanceDialog.ARG_IDSEANCE_KEY, position+1);
        i.putExtras(bundle);
        context.startActivity(i);
    }
}
