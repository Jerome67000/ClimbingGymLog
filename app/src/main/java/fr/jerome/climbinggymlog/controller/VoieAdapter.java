package fr.jerome.climbinggymlog.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.model.Voie;

/**
 * Created by jerome on 19/02/15.
 */
public class VoieAdapter extends ArrayAdapter {

    private Context context;
    private List<Voie> voies;

    public VoieAdapter(Context context, int resource, List<Voie> voies) {

        super(context, resource, voies);
        this.context = context;
        this.voies = voies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Voie voie = voies.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_voie, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_voie);
        txTitre.setText(voie.getNom());

        return rowView;
    }


}
