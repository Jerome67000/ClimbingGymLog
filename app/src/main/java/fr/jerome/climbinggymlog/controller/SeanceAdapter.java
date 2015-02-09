package fr.jerome.climbinggymlog.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fr.jerome.climbinggymlog.R;


/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class SeanceAdapter extends ArrayAdapter {

    private Context context;

    public SeanceAdapter(Context context) {
        super(context, R.layout.seance_row);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.seance_row, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_seance);
        TextView txDateAj = (TextView) rowView.findViewById(R.id.date_seance);
//        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);

        txTitre.setText("Titreee");
        txDateAj.setText("Dateee");

        return rowView;
    }
}
