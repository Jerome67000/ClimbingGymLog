package fr.jerome.climbinggymlog.view.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.R;
import fr.jerome.climbinggymlog.adapters.EvenementsAdapter;
import fr.jerome.climbinggymlog.data.EvenementDB;
import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Evenement;

/**
 * Created by rcdsm06 on 09/02/2015.
 */
public class EvenementsFragment extends Fragment {

    private EvenementsAdapter adapter;
    private EvenementDB evenementDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        evenementDB = new EvenementDB(getActivity());
        new GetEvenements().execute("http://clymbinggym.vacau.com/php/getEvenements.php?"
                                    + "salleid="  + AppManager.salleId
                                    + "&lasteventid=" + evenementDB.getLastEventId());
        super.onCreate(savedInstanceState);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_evenement, container, false);
        adapter = new EvenementsAdapter(getActivity(), R.layout.row_seance_adapter, evenementDB.getAllEvenements());
        ListView listView = (ListView) rootView.findViewById(R.id.events_listview);
        listView.setAdapter(adapter);

        return rootView;
    }

    // AsyncTask to get the server response and refreshView the EditText in the UI thread
    private class GetEvenements extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<Evenement> allWebEvents = new ArrayList<Evenement>();
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
                if (inputStream != null)
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            // Parse Json in a ArrayList of Evenements objects
            try {
                JSONObject jObject = new JSONObject(stringBuilder.toString());
                JSONArray jArray = jObject.getJSONArray("Evenements");

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject jEvenement = jArray.getJSONObject(i);

                    int id = jEvenement.getInt(EvenementDB.ID);
                    String titre = jEvenement.getString(EvenementDB.TITRE);
                    String description = jEvenement.getString(EvenementDB.DESCRIPTION);
                    int heure = jEvenement.getInt(EvenementDB.HORAIRE);
                    String textDate = jEvenement.getString(EvenementDB.DATE);
                    int salleId = jEvenement.getInt(EvenementDB.SALLE_ID);

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = null;
                    try {
                        date = df.parse(textDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    assert date != null;
                    Evenement evenement = new Evenement(id, titre, description, new java.sql.Date(date.getTime()), heure, salleId);
                    allWebEvents.add(evenement);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override protected void onPostExecute(ArrayList<String> s) {

            // Ajouter seulement les nouveaux evenements à la bdd locale
            int lastLocalEventId = evenementDB.getLastEventId();
            for (Evenement e : allWebEvents) {
                if (lastLocalEventId < e.getId())
                    evenementDB.insert(e);
            }

            // Refresh listview
            adapter.clear();
            adapter.addAll(evenementDB.getAllEvenements());
            evenementDB.close();
            adapter.notifyDataSetChanged();
        }
    }
}
