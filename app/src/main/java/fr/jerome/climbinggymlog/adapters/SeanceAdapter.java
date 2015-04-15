package fr.jerome.climbinggymlog.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.activities.SeanceDetailActivity;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;
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

        ((ListView)parent).setOnItemClickListener(this);

        Seance seance = seances.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_seance_adapter, parent, false);

        TextView txTitre = (TextView) rowView.findViewById(R.id.titre_seance);
        TextView txDateAj = (TextView) rowView.findViewById(R.id.date_seance);
        View cotationRect = rowView.findViewById(R.id.cotation_color_rect);
        txTitre.setText(seance.getNom());
        txDateAj.setText(seance.getDateAjout().toString());

        // Couleur devant chaque Séance
        VoieDB voieDB = new VoieDB(context);
        List<Voie> voiesFromSeanceId = voieDB.getAllVoiesFromSeanceId(seance.getId());
        voieDB.close();
        if (!voiesFromSeanceId.isEmpty()) {

            int cotMoyenne = 0;
            for (Voie v : voiesFromSeanceId) {
                cotMoyenne += v.getCotation().getId();
            }
            Cotation cotation = AppManager.cotations.get(cotMoyenne / voiesFromSeanceId.size());
            cotationRect.setBackgroundColor(cotation.getCouleur());
        }

        return rowView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Ouverture de la liste des voies concernant cette séance
        Intent i = new Intent(context, SeanceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AddSeanceDialog.ARG_IDSEANCE_KEY, position+1);
        i.putExtras(bundle);
        context.startActivity(i);
    }
}
