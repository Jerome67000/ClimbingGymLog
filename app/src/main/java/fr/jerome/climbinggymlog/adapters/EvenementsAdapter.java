package fr.jerome.climbinggymlog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.models.Evenement;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 25/03/15.
 */
public class EvenementsAdapter extends ArrayAdapter {

    private Context context;
    private List<Evenement> evenements;

    public EvenementsAdapter(Context context, int resource, List<Evenement> evenements) {
        super(context, resource, evenements);
        this.context = context;
        this.evenements = evenements;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Evenement evenement = evenements.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_event_adapter, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_event);
        TextView txDateAj = (TextView) rowView.findViewById(R.id.date_event);

        txTitre.setText(evenement.getTitre());
        txDateAj.setText(evenement.getDate().toString());

        return rowView;
    }
}
