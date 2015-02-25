package fr.jerome.climbinggymlog.controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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

        Log.d("reussi ?", voie.toString());

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_voie, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_voie);
        txTitre.setText(voie.getNom());
        View reussiRect = rowView.findViewById(R.id.voie_reussi_rect);
        if (voie.isReussi())
            reussiRect.setBackgroundColor(Color.GREEN);
        View cotationRect = rowView.findViewById(R.id.cotation_color_rect);
        cotationRect.setBackgroundColor(voie.getCotation().getCouleur());

        return rowView;
    }


}
