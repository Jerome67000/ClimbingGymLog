package fr.jerome.climbinggymlog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.models.Evenement;

/**
 * Created by jerome on 25/03/15.
 */
public class EvenementsAdapter extends ArrayAdapter {

    public EvenementsAdapter(Context context, int resource, List objects) {

        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Evenement evenement = (Evenement) getItem(position);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_event_adapter, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_event);
        TextView txDateAj = (TextView) rowView.findViewById(R.id.date_event);

        txTitre.setText(evenement.getTitre());
        txDateAj.setText(evenement.getDate().toString());

        return rowView;
    }
}
