package fr.jerome.climbinggymlog.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.shamanland.fab.FloatingActionButton;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.VoieAdapter;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;
import fr.jerome.climbinggymlog.view.dialog.AddVoieDialog;

/**
 * Created by jerome on 17/02/15.
 */
public class VoieActivity extends ActionBarActivity implements AddVoieDialog.AddVoieDialogListener {

    private VoieAdapter voieAdapter;
    private int seanceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * ActionBar
         **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color)));
            }
        }

        Bundle bundle = this.getIntent().getExtras();
        seanceId = bundle.getInt(AddSeanceDialog.ARG_IDSEANCE_KEY, -1);

        VoieDB voieDB = new VoieDB(this);

        // TODO layout différent si aucune voies
        /** Si liste voie de la séance non vide, on affiche une listView */
//        if(voieDB.isVoiesFromSeanceId(seanceId)) {
        setContentView(R.layout.fragment_voies);
        ArrayList<Voie> voies = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(seanceId);
        voieAdapter = new VoieAdapter(this, R.layout.adapter_seance, voies);
        ListView listView = (ListView) findViewById(R.id.voies_listview);
        listView.setAdapter(voieAdapter);
//        }
//        else {
//            setContentView(R.layout.fragment_voie_vide);
//        }

        /** Ajout du fap nouvelle voie */
        FloatingActionButton fap = (FloatingActionButton) findViewById(R.id.fap_add_voie);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddVoieDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddVoieDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddVoieDialog addVoieDialog = AddVoieDialog.newInstance(seanceId, voieAdapter.getCount());
        addVoieDialog.show(fm, AddVoieDialog.KEY_PREFIX);
    }

    @Override
    public void onFinishAddVoieDialog(Voie newVoie) {

        voieAdapter.add(newVoie);
        voieAdapter.notifyDataSetChanged();
    }
}
