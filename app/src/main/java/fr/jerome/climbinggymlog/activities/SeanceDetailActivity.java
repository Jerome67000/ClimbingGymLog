package fr.jerome.climbinggymlog.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.shamanland.fab.FloatingActionButton;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.SeanceDetailAdapter;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Voie;
import fr.jerome.climbinggymlog.view.dialog.AddSeanceDialog;
import fr.jerome.climbinggymlog.view.dialog.AddVoieDialog;
import fr.jerome.climbinggymlog.view.dialog.EditVoieDialog;
import fr.jerome.climbinggymlog.view.fragments.ResumeSeanceFragment;

/**
 * Created by jerome on 17/02/15.
 */
public class SeanceDetailActivity   extends AppCompatActivity
                                    implements  AddVoieDialog.AddVoieDialogListener,
                                                EditVoieDialog.EditVoieDialogListener,
                                                OnItemClickListener {

    private SeanceDetailAdapter seanceDetailAdapter;
    private int seanceId;
    private ResumeSeanceFragment resumeSeanceFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO layout différent si aucune voies
        setContentView(R.layout.activity_detail_seance);
        fm = getSupportFragmentManager();
        Bundle bundle = this.getIntent().getExtras();
        seanceId = bundle.getInt(AddSeanceDialog.ARG_IDSEANCE_KEY, -1);
        String seanceName = new SeanceDB(this).select(seanceId).getNom();

        /**
         * Toolbar
         **/
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar_seance);
        toolBar.setTitle(seanceName);
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_color)));
        toolBar.setNavigationIcon(R.drawable.ic_arrow_left);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        VoieDB voieDB = new VoieDB(this);

        ArrayList<Voie> voies = (ArrayList<Voie>) voieDB.getAllVoiesFromSeanceId(seanceId);
        seanceDetailAdapter = new SeanceDetailAdapter(this, R.layout.row_seance_adapter, voies);
        ListView listView = (ListView) findViewById(R.id.voies_listview);
        listView.setAdapter(seanceDetailAdapter);
        listView.setEmptyView(findViewById(R.id.no_data_voie));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_seance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_mod_seance:
                Log.d("test", "modifier");
                break;
            case R.id.action_remove_seance:
                Log.d("test", "remove");
                break;
            case R.id.action_share_seance:
                Log.d("test", "share");
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
