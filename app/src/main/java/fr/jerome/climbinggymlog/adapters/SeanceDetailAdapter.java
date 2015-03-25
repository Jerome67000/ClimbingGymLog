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
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 19/02/15.
 */
public class SeanceDetailAdapter extends ArrayAdapter {

    private Context context;
    private List<Voie> voies;

    public SeanceDetailAdapter(Context context, int resource, List<Voie> voies) {
        super(context, resource, voies);
        this.context = context;
        this.voies = voies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Voie voie = voies.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_voie_adapter, parent, false);

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
