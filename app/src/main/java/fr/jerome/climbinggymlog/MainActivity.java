package fr.jerome.climbinggymlog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Date;

import fr.jerome.climbinggymlog.controller.AppManager;
import fr.jerome.climbinggymlog.database.ClientDB;
import fr.jerome.climbinggymlog.database.CotationDB;
import fr.jerome.climbinggymlog.database.SeanceDB;
import fr.jerome.climbinggymlog.database.StyleVoieDB;
import fr.jerome.climbinggymlog.database.TypeEscDB;
import fr.jerome.climbinggymlog.database.VoieDB;
import fr.jerome.climbinggymlog.model.Client;
import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.model.StyleVoie;
import fr.jerome.climbinggymlog.model.TypeEsc;
import fr.jerome.climbinggymlog.model.Voie;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long sysTime = System.currentTimeMillis();

        /**  AppManager pour les objets statiques  */
        AppManager.setClient(new Client("GULLY", "Jérome", 20484851, new Date(sysTime), 0));
        AppManager.setCotations(new CotationDB(this).getAllCotations());
        AppManager.setTypeEsc(new TypeEscDB(this).getAllTypes());
        AppManager.setStyleVoie(new StyleVoieDB(this).getAllStyles());

        /**  DBHandlers pour manipuler la DB  */
        ClientDB clientDB = new ClientDB(this);
        SeanceDB seanceDB = new SeanceDB(this);
        VoieDB voieDB = new VoieDB(this);

        Client client = AppManager.client;

        Seance seance = new Seance("Séance #01", new Date(sysTime), new Date(sysTime), "Roc en stock", "séance plutot bonne", client);
        Seance seance2 = new Seance("Séance #022", new Date(sysTime), new Date(sysTime), "Roc en stock", "séance plutot mauvaise", client);
        Seance seance3 = new Seance("Séance #03", new Date(sysTime), new Date(sysTime), "Roc en stock", "séance plutot moyenne", client);

        clientDB.insert(client);
        seanceDB.insert(seance);
        seanceDB.insert(seance2);
        seanceDB.insert(seance3);

        Voie voie = voieDB.insert(new Voie(seance2.getId(), "5c #02", AppManager.cotations.get(10), "Moulinette", "Dalle", true, true, "voie cool", null));
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
