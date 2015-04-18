package fr.jerome.climbinggymlog.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shamanland.fab.FloatingActionButton;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.SeanceDetailAdapter;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;
import fr.jerome.climbinggymlog.view.dialog.AddVoieDialog;
import fr.jerome.climbinggymlog.view.dialog.EditVoieDialog;
import fr.jerome.climbinggymlog.view.fragments.ResumeSeanceFragment;

/**
 * Created by jerome on 17/02/15.
 */
public class SeanceDetailActivity   extends ActionBarActivity
                                    implements  AddVoieDialog.AddVoieDialogListener,
                                                EditVoieDialog.EditVoieDialogListener,
                                                OnItemClickListener {

    private SeanceDetailAdapter seanceDetailAdapter;
    private int seanceId;
    private int lastEditPosition;
    private ResumeSeanceFragment resumeSeanceFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fm = getSupportFragmentManager();

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

        // TODO layout différent si aucune voies
        setContentView(R.layout.fragment_detail_seance);

        VoieDB voieDB = new VoieDB(this);

        ArrayList<Voie> voies = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(seanceId);
        seanceDetailAdapter = new SeanceDetailAdapter(this, R.layout.row_seance_adapter, voies);
        ListView listView = (ListView) findViewById(R.id.voies_listview);
        listView.setAdapter(seanceDetailAdapter);
        listView.setOnItemClickListener(this);

        /** Ajout du fap nouvelle voie */
        FloatingActionButton fap = (FloatingActionButton) findViewById(R.id.fap_add_voie);
        fap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddVoieDialog();
            }
        });

        showResumeSeanceFragment();
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
        AddVoieDialog addVoieDialog = AddVoieDialog.newInstance(seanceId, seanceDetailAdapter.getCount());
        addVoieDialog.show(fm, AddVoieDialog.KEY_PREFIX);
    }

    @Override
    public void onFinishAddVoieDialog(Voie newVoie) {
        seanceDetailAdapter.add(newVoie);
        seanceDetailAdapter.notifyDataSetChanged();
        resumeSeanceFragment.refreshView();
    }

    private void showResumeSeanceFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        resumeSeanceFragment = new ResumeSeanceFragment();
        resumeSeanceFragment.setSeanceId(seanceId);
        ft.replace(R.id.fragment_resume_seance, resumeSeanceFragment);
        ft.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lastEditPosition = position;
        Voie voieToEdit = (Voie) seanceDetailAdapter.getItem(position);
        EditVoieDialog editVoieDialog = EditVoieDialog.newInstance((int) voieToEdit.getId(), position);
        editVoieDialog.show(fm, EditVoieDialog.KEY_PREFIX);
    }

    @Override
    public void onFinishEditVoieDialog(Voie editedVoie) {

        seanceDetailAdapter.clear();
        VoieDB voieDB = new VoieDB(this);
        seanceDetailAdapter.addAll(voieDB.getAllVoiesFromSeanceId(seanceId));
        voieDB.close();

        seanceDetailAdapter.notifyDataSetChanged();
        resumeSeanceFragment.refreshView();
    }
}
