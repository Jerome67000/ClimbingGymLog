package fr.jerome.climbinggymlog.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fr.jerome.climbinggymlog.data.ClientDB;
import fr.jerome.climbinggymlog.data.SeanceDB;
import fr.jerome.climbinggymlog.data.VoieDB;
import fr.jerome.climbinggymlog.models.Client;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 20/06/15.
 */
public class JSONParser {

    public static Client parseClient(StringBuilder datasFromWebService) {

        Client client = null;

        try {
            JSONObject jObject = new JSONObject(datasFromWebService.toString());
            JSONArray jArray = jObject.getJSONArray("Clients");


            if (jArray.length() > 0) {
                JSONObject jClient = jArray.getJSONObject(0);
                client = JObjectToClient(jClient);
            } else {
                client = new Client();
                client.setNumClient("-");
                client.setEmail("-");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return client;
    }

    private static Client JObjectToClient(JSONObject jClient) throws JSONException {

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
        Client clientCreated = new Client(id, nom, prenom, numClient, new java.sql.Date(date.getTime()), salleId);
        clientCreated.setEmail(email);
        return clientCreated;
    }

    public static ArrayList<Seance> parseSeances(StringBuilder datasFromWebService) {

        ArrayList<Seance> seances = new ArrayList<Seance>();

        try {
            JSONObject jObject = new JSONObject(datasFromWebService.toString());
            JSONArray jArray = jObject.getJSONArray("Seances");

            if (jArray.length() > 0) {
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jSeance = jArray.getJSONObject(i);
                    Seance seance = JObjectToSeance(jSeance);
                    seances.add(seance);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return seances;
    }

    private static Seance JObjectToSeance(JSONObject jSeance) throws JSONException {
        String nom = jSeance.getString(SeanceDB.NOM);
        String textDate = jSeance.getString(SeanceDB.DATE);
        String nomSalle = jSeance.getString(SeanceDB.NOM_SALLE);
        String note = jSeance.getString(SeanceDB.NOTE);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        Seance seance = new Seance(nom, new java.sql.Date(date.getTime()), nomSalle, note, AppManager.client.getId());
        return seance;
    }

    public static ArrayList<Voie> parseVoies(StringBuilder datasFromWebService) {

        ArrayList<Voie> voies = new ArrayList<Voie>();

        try {
            JSONObject jObject = new JSONObject(datasFromWebService.toString());
            JSONArray jArray = jObject.getJSONArray("Voies");

            if (jArray.length() > 0) {
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jVoie = jArray.getJSONObject(i);
                    Voie voie = JObjectToVoie(jVoie);
                    voies.add(voie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return voies;
    }

    private static Voie JObjectToVoie(JSONObject jVoie) throws JSONException {
        String nom = jVoie.getString(VoieDB.NOM);
        int cotationId = jVoie.getInt(VoieDB.COTATION_ID);
        int typeEscId = jVoie.getInt(VoieDB.ID_TYPE_ESCALADE);
        int styleVoieId = jVoie.getInt(VoieDB.ID_STYLE_VOIE);
        boolean reussi = jVoie.getInt(VoieDB.REUSSIE) != 0;
        boolean aVue = jVoie.getInt(VoieDB.A_VUE) != 0;
        String note = jVoie.getString(VoieDB.NOTE);
        int seanceId = jVoie.getInt(VoieDB.ID_SEANCE_VOIE);

        Voie voie = new Voie(nom, AppManager.cotations.get(cotationId - 1), AppManager.typesEsc.get(typeEscId - 1), AppManager.styleVoies.get(styleVoieId - 1), reussi, aVue, note, seanceId, AppManager.client.getId());
        return voie;
    }
}
