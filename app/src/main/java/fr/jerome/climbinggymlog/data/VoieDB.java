package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Seance;
import fr.jerome.climbinggymlog.models.StyleVoie;
import fr.jerome.climbinggymlog.models.TypeEsc;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 28/01/15.
 */
public class VoieDB extends DBHandler {

    public static final String TABLE_NAME = "Voie";

    public static final String ID = "_id";
    public static final String NOM = "nom";
    public static final String COTATION_ID = "cotation_id";
    public static final String ID_TYPE_ESCALADE = "type_escalade_id";
    public static final String ID_STYLE_VOIE = "style_voie_id";
    public static final String REUSSIE = "reussie";
    public static final String A_VUE = "a_vue";
    public static final String NOTE = "note";
    public static final String ID_SEANCE_VOIE = "seance_id";
    public static final String PHOTO_NOM = "photo_nom";

    public static final String  CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "(" +
                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                NOM + " TEXT, " +
                                COTATION_ID + " INTEGER, " +
                                ID_TYPE_ESCALADE + " INTEGER, " +
                                ID_STYLE_VOIE + " INTEGER, " +
                                REUSSIE + " BOOL, " +
                                A_VUE + " BOOL, " +
                                NOTE + " TEXT, " +
                                ID_SEANCE_VOIE + " INTEGER" +
                                PHOTO_NOM + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public VoieDB(Context context) {
        super(context);
    }

    /**
     * @param voie la voie inserée dans la table
     */
    public Voie insert(Voie voie) {

        ContentValues value = new ContentValues();

        value.put(NOM, voie.getNom());
        value.put(COTATION_ID, voie.getCotation().getId());
        value.put(ID_TYPE_ESCALADE, voie.getTypeEscalade().getId());
        value.put(ID_STYLE_VOIE, voie.getStyle().getId());
        value.put(REUSSIE, voie.isReussi());
        value.put(A_VUE, voie.isAVue());
        value.put(NOTE, voie.getNote());
        value.put(ID_SEANCE_VOIE, voie.getIdSeance());

        // récupération de l'id pour le setter dans l'objet
        long insertId =  database.insert(TABLE_NAME, null, value);
        voie.setId(insertId);
        Log.d("SQL", "Ajout de la voie " + voie.getNom() + " id : " + voie.getId() + " à la table Voie");

        return voie;
    }

    public Voie select(int id) {

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(id)});

        c.moveToFirst();

        Log.d("C.tostrin", android.database.DatabaseUtils.dumpCursorToString(c));

        boolean isReussi = c.getInt(5) > 0;
        boolean isAVue = c.getInt(6) > 0;

        // - 1 car l'index d'une list commence à zéro et l'id à 1
        Cotation cot = AppManager.cotations.get(c.getInt(2) - 1);
        TypeEsc typeEsc = AppManager.typesEsc.get(c.getInt(3) - 1);
        StyleVoie styleVoie = AppManager.styleVoies.get(c.getInt(4) - 1);

        Voie voie = new Voie(c.getLong(0), c.getString(1), cot, typeEsc, styleVoie, isReussi, isAVue, c.getString(7), c.getLong(8));
        c.close();
        return voie;
    }

    /**
     * @param voie la voie modifiée
     */
    public void update(Voie voie) {

        ContentValues value = new ContentValues();

        value.put(NOM, voie.getNom());
        value.put(COTATION_ID, voie.getCotation().getId());
        value.put(ID_TYPE_ESCALADE, voie.getTypeEscalade().getId());
        value.put(ID_STYLE_VOIE, voie.getStyle().getId());
        value.put(REUSSIE, voie.isReussi());
        value.put(A_VUE, voie.isAVue());
        value.put(NOTE, voie.getNote());

        database.update(TABLE_NAME, value, ID + "=" + voie.getId(), null);

        Log.d("SQL", "update de la voie " + voie.toString());
    }


    /**
     * @param voie séance à supprimer
     */
    public void delete(Voie voie) {

        long id = voie.getId();
        database.delete(TABLE_NAME, ID + " = " + id, null);

        Log.d("SQL", "suppression de la voie " + voie.getNom() + " id : " + voie.getId() + " de la table Voie");
    }

    /**
     * @return  seances : Une liste contenant toutes les seances
     */
    public List<Voie> getAllVoiesFromSeanceId(long id) {

        List<Voie> voies = new ArrayList<Voie>();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_SEANCE_VOIE + "=?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        while (!c.isAfterLast()) {

            boolean isReussi = c.getInt(5) > 0;
            boolean isAVue = c.getInt(6) > 0;

            Cotation cot = AppManager.cotations.get(c.getInt(2) - 1);
            TypeEsc typeEsc = AppManager.typesEsc.get(c.getInt(3) - 1);
            StyleVoie styleVoie = AppManager.styleVoies.get(c.getInt(4) - 1);

            Voie voie = new Voie(c.getLong(0), c.getString(1), cot, typeEsc, styleVoie, isReussi, isAVue, c.getString(7), c.getLong(8));
            voies.add(voie);
            c.moveToNext();
        }
        c.close();
        return voies;
    }

    public void putVoieOnWebDB(Voie voie) {

        String nomVoie = voie.getNom().replace('#','n');

        List<NameValuePair> values = new ArrayList<NameValuePair>();
        values.add(new BasicNameValuePair(NOM, nomVoie));
        values.add(new BasicNameValuePair(COTATION_ID, String.valueOf(voie.getCotation().getId())));
        values.add(new BasicNameValuePair(ID_TYPE_ESCALADE, String.valueOf(voie.getTypeEscalade().getId())));
        values.add(new BasicNameValuePair(ID_STYLE_VOIE, String.valueOf(voie.getStyle().getId())));
        values.add(new BasicNameValuePair(REUSSIE, voie.isReussi() ? "1" : "0"));
        values.add(new BasicNameValuePair(A_VUE, voie.isAVue() ? "1" : "0"));
        values.add(new BasicNameValuePair(NOTE, voie.getNote()));
        values.add(new BasicNameValuePair(ID_SEANCE_VOIE, String.valueOf(voie.getIdSeance())));
        values.add(new BasicNameValuePair(PHOTO_NOM, "-"));

        new PutVoie().execute(values);
    }

    // AsyncTask pour insérer une nouvelle séance dans la BDD en ligne
    private class PutVoie extends AsyncTask<List<NameValuePair>, Void, String> {
        @Override
        protected String doInBackground(List<NameValuePair>...values) {
            String rep = null;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://clymbinggym.vacau.com/php/putVoie.php");
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
            Log.i("putVoieOnWebDB()", rep);
        }
    }
}
