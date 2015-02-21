package fr.jerome.climbinggymlog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.shamanland.fab.FloatingActionButton;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.controller.VoieAdapter;
import fr.jerome.climbinggymlog.database.VoieDB;
import fr.jerome.climbinggymlog.model.Voie;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;
import fr.jerome.climbinggymlog.view.dialog.AddVoieDialog;

/**
 * Created by jerome on 17/02/15.
 */
public class VoieActivity extends ActionBarActivity {

    private long seanceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        seanceId = bundle.getLong(AddSeanceDialog.EXTRA_SEANCE_ID, -1);

        VoieDB voieDB = new VoieDB(this);

        Log.d("séance id pour voies :", String.valueOf(seanceId));

        // Si liste voie de la séance non vide, on affiche une listView
//        if(voieDB.getAllVoiesFromSeanceId(seanceId).isEmpty()) {

            setContentView(R.layout.fragment_voies);

            ArrayList<Voie> voies = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(seanceId);

            VoieAdapter adapter = new VoieAdapter(this, R.layout.row_seance, voies);

            ListView listView = (ListView) findViewById(R.id.voies_listview);
            listView.setAdapter(adapter);
//        }
//        else {
//            setContentView(R.layout.fragment_voie_vide);
//        }

        /** Ajout du fap nouvelle voie */
        FloatingActionButton fap = (FloatingActionButton) findViewById(R.id.fap_add_voie);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddVoieDialog(seanceId).show(getSupportFragmentManager(), "0");
            }
        });

    }
}
