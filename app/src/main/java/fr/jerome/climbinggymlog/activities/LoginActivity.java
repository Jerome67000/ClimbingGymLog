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
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.data.ClientDB;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.helpers.JSONParser;
import fr.jerome.climbinggymlog.helpers.SharedPrefHelper;
import fr.jerome.climbinggymlog.models.Client;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ACT_ID = 0;
    public static final int CLIENT_FINDED = 1;
    public static final int CLIENT_NOT_FIND = 2;

    // Client client;
    Activity self = this;
    private Client tempClient;
//    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonLogin = (Button) findViewById(R.id.login_button);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login_button)
            onLoginButtonClick();
    }

    private void onLoginButtonClick() {

        createTempClientFromUserInput();
        searchExistingClientOnWebDB();
    }

    private void createTempClientFromUserInput() {

        String nom = ((EditText) findViewById(R.id.login_nom)).getText().toString();
        String prenom = ((EditText) findViewById(R.id.login_prenom)).getText().toString();
        String numClient = ((EditText) findViewById(R.id.login_num_client)).getText().toString();
        String email = ((EditText) findViewById(R.id.login_email)).getText().toString();

        tempClient = new Client(nom, prenom, 0, numClient, new Date(AppManager.sysTime), AppManager.salleId);
        tempClient.setEmail(email);
    }

    private void searchExistingClientOnWebDB() {
        new GetClientsFromWebDB().execute("http://clymbinggym.vacau.com/php/getClientFromNum.php?" + ClientDB.NUM_CLIENT + "=" + tempClient.getNumClient());
    }

    private class GetClientsFromWebDB extends AsyncTask<String, Void, Client> {
        @Override
        protected Client doInBackground(String... url) {
            StringBuilder clientDataFromWebDB = new StringBuilder();
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
                    clientDataFromWebDB.append(line);
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

            // Parse Json in a ArrayList of Clients objects
            Client client = JSONParser.parseClient(clientDataFromWebDB);
            return client;
        }

        @Override protected void onPostExecute(Client client) {

            ClientDB clientDB = new ClientDB(self);

            if (clientAlreadyExisting(client)) {

                if (client.getEmail().equals(tempClient.getEmail())) {
                    Toast.makeText(self, "Compte existant, chargement des données", Toast.LENGTH_SHORT).show();
                    AppManager.setClient(client);
                    clientDB.insert(client);

                    // Récupération des séances et des voies existantes
                    new GetSeancesFromWebDB().execute("http://clymbinggym.vacau.com/php/getSeancesFromClientId.php?clientid=" + client.getId());
                    new GetVoiesFromWebDB().execute("http://clymbinggym.vacau.com/php/getVoiesFromClientId.php?clientid=" + client.getId());

                    SharedPrefHelper.saveClientNum(getBaseContext(), tempClient.getNumClient());

                    closeLoginActivityWithStatus(CLIENT_FINDED);
                }
                else {
                    Toast.makeText(self, "Numéro de client déjà associé à une autre addresse email, vérifier vos entrées", Toast.LENGTH_LONG).show();
                    setResult(CLIENT_NOT_FIND);
                }

            }
            else { // Création d'un nouveau client

                List<NameValuePair> values = new ArrayList<NameValuePair>();
                values.add(new BasicNameValuePair(ClientDB.NOM, tempClient.getNom().trim()));
                values.add(new BasicNameValuePair(ClientDB.PRENOM, tempClient.getPrenom().trim()));
                values.add(new BasicNameValuePair(ClientDB.NUM_CLIENT, tempClient.getNumClient().trim()));
                values.add(new BasicNameValuePair(ClientDB.EMAIL,  tempClient.getEmail().trim()));
                values.add(new BasicNameValuePair(ClientDB.SALLE_ID, String.valueOf(AppManager.salleId)));

                new PutClient().execute(values);
            }

        }

        private boolean clientAlreadyExisting(Client client) {

            if (client.getNumClient().equals(tempClient.getNumClient()))
                return true;
            return false;
        }

        private void closeLoginActivityWithStatus(int status) {
            setResult(status);
            finish();
        }
    }

    // AsyncTask pour insérer le nouveau client dans la BDD en ligne
    private class PutClient extends AsyncTask<List<NameValuePair>, Void, String> {
        @Override
        protected String doInBackground(List<NameValuePair>...values) {
            String rep = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://clymbinggym.vacau.com/php/putClient.php");
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(values[0]));
                HttpResponse response = httpClient.execute(httpPost);
                rep = EntityUtils.toString(response.getEntity());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rep;
        }

        @Override
        protected void onPostExecute(String rep) {
            Log.i("putClientOnWebDB()", rep);
            Toast.makeText(self, "Votre compte vient d'etre créé", Toast.LENGTH_LONG).show();
            Log.d("client id retour Web", rep);
            tempClient.setId(Integer.valueOf(rep));

            // Set du client pour la session en cours et insertion dans la BDD pour les prochains lancement
            AppManager.setClient(tempClient);
            new ClientDB(self).insert(tempClient);

            // Sauvegarde des SharedPref pour SHOW_LOG_ACT et le num client
            SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(MainActivity.PREFKEY_SHOW_LOG_ACT, false);
            editor.putString(MainActivity.PREFKEY_NUM_CLIENT, tempClient.getNumClient());
            editor.commit();

            // Définit le résultat de l'activity et fin de l'activity
            self.setResult(CLIENT_FINDED);
            self.finish();
        }
    }

    // AsyncTask pour obtenir les seances d'un utilisateur existant
    private class GetSeancesFromWebDB extends AsyncTask<String, Void, ArrayList<Seance>> {
        ArrayList<Seance> seances = new ArrayList<>();
        @Override
        protected ArrayList<Seance> doInBackground(String... url) {
            StringBuilder seancesDataFromWeb = new StringBuilder();
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
                    seancesDataFromWeb.append(line);
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

            seances = JSONParser.parseSeances(seancesDataFromWeb);
            return seances;
        }

        // Insertion des séances existantes dans la BDD locale
        @Override protected void onPostExecute(ArrayList<Seance> seances) {
            SeanceDB seanceDB = new SeanceDB(self);
            for (Seance seance : seances) {
                seanceDB.insert(seance);
            }
        }
    }

    // AsyncTask pour obtenir les voies d'un utilisateur existant
    private class GetVoiesFromWebDB extends AsyncTask<String, Void, ArrayList<Voie>> {

        ArrayList<Voie> voies = new ArrayList<>();
        @Override
        protected ArrayList<Voie> doInBackground(String... url) {
            StringBuilder voiesDataFromWebDB = new StringBuilder();
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
                    voiesDataFromWebDB.append(line);
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

            // Parse Json in a ArrayList of voies objects
            voies = JSONParser.parseVoies(voiesDataFromWebDB);
            return voies;
        }

        // Insertion des séances existantes dans la BDD locale
        @Override protected void onPostExecute(ArrayList<Voie> voies) {
            VoieDB voieDB = new VoieDB(self);
            for (Voie voie : voies) {
                voieDB.insert(voie);
                Log.d("voies inséré en local", voie.toString());
            }
        }
    }
}
