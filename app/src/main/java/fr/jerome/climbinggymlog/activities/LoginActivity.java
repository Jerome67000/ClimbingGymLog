package fr.jerome.climbinggymlog.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.ClientDB;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Client;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ACT_ID = 0;
    public static final int CLIENT_FINDED = 1;
    public static final int CLIENT_NOT_FIND = 2;

    Client client;
    Activity self = this;
    private Client clientTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = (Button) findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login_button) {
            String nom = ((EditText) findViewById(R.id.login_nom)).getText().toString();
            String prenom = ((EditText) findViewById(R.id.login_prenom)).getText().toString();
            String numClient = ((EditText) findViewById(R.id.login_num_client)).getText().toString();
            String email = ((EditText) findViewById(R.id.login_email)).getText().toString();

            clientTmp = new Client(nom, prenom, 0, numClient, new Date(AppManager.sysTime), AppManager.salleId);
            clientTmp.setEmail(email);

            new GetClients().execute("http://clymbinggym.vacau.com/php/getClientFromNum.php?" + ClientDB.NUM_CLIENT + "=" + numClient);
        }
    }

    private class GetClients extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... url) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;

            // Get the Request answer into a StringBuilder
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(url[0]));
                // Http message
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.getMessage());
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Parse Json in a ArrayList of Evenements objects
            try {
                JSONObject jObject = new JSONObject(stringBuilder.toString());
                JSONArray jArray = jObject.getJSONArray("Clients");

                if(jArray.length() > 0) {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jClient = jArray.getJSONObject(i);
                        int id = jClient.getInt(ClientDB.ID);
                        String nom = jClient.getString(ClientDB.NOM);
                        String prenom = jClient.getString(ClientDB.PRENOM);
                        String numClient = jClient.getString(ClientDB.NUM_CLIENT);
                        String email = jClient.getString(ClientDB.EMAIL);
                        int salleId = jClient.getInt(ClientDB.SALLE_ID);
                        String textDate = jClient.getString(ClientDB.DATE_AJ);
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = null;
                        try {
                            date = df.parse(textDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        assert date != null;
                        Client c = new Client(id, nom, prenom, numClient, new java.sql.Date(date.getTime()), salleId);
                        c.setEmail(email);
                        client = c;
                    }
                }
                else {
                    client = new Client();
                    client.setNumClient("-");
                    client.setEmail("-");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override protected void onPostExecute(ArrayList<String> s) {

            ClientDB clientDB = new ClientDB(self);

            if (client.getNumClient().equals(clientTmp.getNumClient())) {
                if (client.getEmail().equals(clientTmp.getEmail())) {
                    // Insertion du client en bdd web en bdd locale
                    Toast.makeText(self, "Compte existant, chargement des données", Toast.LENGTH_LONG).show();
                    AppManager.setClient(client);
                    clientDB.insert(client);

                    // Récupération des séances et des voies existantes
                    new GetSeances().execute("http://clymbinggym.vacau.com/php/getSeancesFromClient.php?clientid=" + client.getId());
                    new GetVoies().execute("http://clymbinggym.vacau.com/php/getVoiesFromClientId.php?clientid=" + client.getId());

                    // Sauvegarde des SharedPref pour SHOW_LOG_ACT et le num client
                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(MainActivity.PREFKEY_NUM_CLIENT, clientTmp.getNumClient());
                    editor.putBoolean(MainActivity.PREFKEY_SHOW_LOG_ACT, false);
                    editor.commit();

                    // Définit le résultat de l'activity et fin de l'activity
                    setResult(CLIENT_FINDED);
                    finish();
                }
                else {
                    Toast.makeText(self, "Numéro de client déjà associé à une autre addresse email, vérifier vos entrées", Toast.LENGTH_LONG).show();
                    setResult(CLIENT_NOT_FIND);
                }
            }
            else {
                new PutClient().execute("http://clymbinggym.vacau.com/php/putClient.php?"   + ClientDB.NOM + "=" + clientTmp.getNom().trim() + "&"
                        + ClientDB.PRENOM + "=" + clientTmp.getPrenom().trim() + "&"
                        + ClientDB.NUM_CLIENT + "=" + clientTmp.getNumClient().trim() + "&"
                        + ClientDB.EMAIL + "=" + clientTmp.getEmail().trim() + "&"
                        + ClientDB.SALLE_ID +  "=" + AppManager.salleId + "&"
                        + ClientDB.DATE_AJ +  "=" + "2015-01-01");
            }
        }
    }

    // AsyncTask pour insérer le nouveau client dans la BDD en ligne
    private class PutClient extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;

            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(url[0]));
                // Http message
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.getMessage());
            }
            finally {
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(self, "Votre compte vient d'etre créé avec le numéro " + s, Toast.LENGTH_LONG).show();
            clientTmp.setId(Integer.valueOf(s));

            // Set du client pour la session en cours et insertion dans la BDD pour les prochains lancement
            AppManager.setClient(clientTmp);
            new ClientDB(self).insert(clientTmp);

            // Sauvegarde des SharedPref pour SHOW_LOG_ACT et le num client
            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(MainActivity.PREFKEY_SHOW_LOG_ACT, false);
            editor.putString(MainActivity.PREFKEY_NUM_CLIENT, clientTmp.getNumClient());
            editor.commit();

            // Définit le résultat de l'activity et fin de l'activity
            self.setResult(CLIENT_FINDED);
            self.finish();
        }
    }

    // AsyncTask pour obtenir les seances d'un utilisateur existant
    private class GetSeances extends AsyncTask<String, Void, ArrayList<String>> {

        ArrayList<Seance> seances = new ArrayList<>();
        @Override
        protected ArrayList<String> doInBackground(String... url) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;

            // Get the Request answer into a StringBuilder
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(url[0]));
                // Http message
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.getMessage());
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Parse Json in a ArrayList of Evenements objects
            try {
                JSONObject jObject = new JSONObject(stringBuilder.toString());
                JSONArray jArray = jObject.getJSONArray("Seances");

                if(jArray.length() > 0) {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jSeances = jArray.getJSONObject(i);
                        int id = jSeances.getInt(SeanceDB.ID);
                        String nom = jSeances.getString(SeanceDB.NOM);
                        String textDate = jSeances.getString(SeanceDB.DATE);
                        String nomSalle = jSeances.getString(SeanceDB.NOM_SALLE);
                        String note = jSeances.getString(SeanceDB.NOTE);

                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date date = null;
                        try {
                            date = df.parse(textDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        assert date != null;
                        seances.add(new Seance(id, nom, new java.sql.Date(date.getTime()), nomSalle, note, client));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Insertion des séances existantes dans la BDD locale
        @Override protected void onPostExecute(ArrayList<String> s) {
            SeanceDB seanceDB = new SeanceDB(self);
            for (Seance seance : seances) {
                seanceDB.insert(seance);
            }
        }
    }

    // AsyncTask pour obtenir les voies d'un utilisateur existant
    private class GetVoies extends AsyncTask<String, Void, ArrayList<String>> {

        ArrayList<Voie> voies = new ArrayList<>();
        @Override
        protected ArrayList<String> doInBackground(String... url) {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream inputStream = null;

            // Get the Request answer into a StringBuilder
            try {
                HttpResponse httpResponse = new DefaultHttpClient().execute(new HttpGet(url[0]));
                // Http message
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
                Log.e("error", e.getMessage());
            }
            finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Parse Json in a ArrayList of Evenements objects
            try {
                JSONObject jObject = new JSONObject(stringBuilder.toString());
                JSONArray jArray = jObject.getJSONArray("Voies");

                if(jArray.length() > 0) {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jVoies = jArray.getJSONObject(i);
                        int id = jVoies.getInt(VoieDB.ID);
                        String nom = jVoies.getString(VoieDB.NOM);
                        int cotationId = jVoies.getInt(VoieDB.COTATION_ID);
                        int typeEscId = jVoies.getInt(VoieDB.ID_TYPE_ESCALADE);
                        int styleVoieId = jVoies.getInt(VoieDB.ID_STYLE_VOIE);
                        boolean reussi = jVoies.getInt(VoieDB.REUSSIE) != 0;
                        boolean aVue = jVoies.getInt(VoieDB.A_VUE) != 0 ;
                        String note = jVoies.getString(VoieDB.NOTE);
                        int seanceId = jVoies.getInt(VoieDB.ID_SEANCE_VOIE);

                        voies.add(new Voie(id, nom, AppManager.cotations.get(cotationId-1), AppManager.typesEsc.get(typeEscId-1), AppManager.styleVoies.get(styleVoieId-1), reussi, aVue, note, seanceId));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        // Insertion des séances existantes dans la BDD locale
        @Override protected void onPostExecute(ArrayList<String> s) {
            VoieDB voieDB = new VoieDB(self);
            for (Voie voie : voies) {
                voieDB.insert(voie);
                Log.d("voies inséré en local", voie.toString());
            }
        }
    }
}
