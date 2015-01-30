package fr.jerome.climbinggymlog;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Date;

import fr.jerome.climbinggymlog.database.ClientDB;
import fr.jerome.climbinggymlog.database.CotationDB;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.database.VoieDB;
import fr.jerome.climbinggymlog.model.Client;
import fr.jerome.climbinggymlog.model.Cotation;
import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.model.Voie;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB Manager
        ClientDB clientDB = new ClientDB(this);
        SeanceDB seanceDB = new SeanceDB(this);
        VoieDB voieDB = new VoieDB(this);

        // Object
        Client client = new Client("GULLY", "Jérome", 20484851, new Date(), 0);

        Seance seance = new Seance("Séance #01", new Date(), new Date(), "Roc en stock", "séance plutot bonne", client.getId());
        Seance seance2 = new Seance("Séance #02", new Date(), new Date(), "Roc en stock", "séance plutot mauvaise", client.getId());
        Seance seance3 = new Seance("Séance #03", new Date(), new Date(), "Roc en stock", "séance plutot moyenne", client.getId());

        clientDB.insert(client);
        seanceDB.insert(seance);
        seanceDB.insert(seance2);
        seanceDB.insert(seance3);

        Cursor c = seanceDB.select(2);
        c.moveToFirst();
        Log.d("Cursor", String.valueOf(c.getInt(0)));
        Log.d("Cursor", c.getString(1));
        Log.d("Cursor", c.getString(5));

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
