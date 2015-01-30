package fr.jerome.climbinggymlog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;
import java.util.List;

import fr.jerome.climbinggymlog.controller.AppManager;
import fr.jerome.climbinggymlog.database.ClientDB;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.model.Client;
import fr.jerome.climbinggymlog.model.Seance;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB Manager
        ClientDB clientDB = new ClientDB(this);
        SeanceDB seanceDB = new SeanceDB(this);

        Client client = AppManager.client;

        Seance seance = new Seance("Séance #01", new Date(), new Date(), "Roc en stock", "séance plutot bonne", client);
        Seance seance2 = new Seance("Séance #022", new Date(), new Date(), "Roc en stock", "séance plutot mauvaise", client);
        Seance seance3 = new Seance("Séance #03", new Date(), new Date(), "Roc en stock", "séance plutot moyenne", client);

        clientDB.insert(client);
        seanceDB.insert(seance);
        seanceDB.insert(seance2);
        seanceDB.insert(seance3);

        List<Seance> seances = seanceDB.getAllSeances();

        for (Seance s : seances) {
            Log.w("seance :", s.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
