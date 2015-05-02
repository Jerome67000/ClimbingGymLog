package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

    public void putVoieOnWebDB(Voie newVoie) {

        int isReussi = newVoie.isReussi() ? 1 : 0;
        int isAVue = newVoie.isAVue() ? 1 : 0;
        String nomVoie = newVoie.getNom().replace('#','n');

        String url = "http://clymbinggym.vacau.com/php/putVoie.php?"
                + NOM + "=" + nomVoie + "&"
                + COTATION_ID + "=" + newVoie.getCotation().getId() + "&"
                + ID_TYPE_ESCALADE + "=" + newVoie.getTypeEscalade().getId() + "&"
                + ID_STYLE_VOIE + "=" + newVoie.getStyle().getId() + "&"
                + REUSSIE + "=" + isReussi + "&"
                + A_VUE + "=" + isAVue + "&"
                + NOTE + "=" + newVoie.getNote() + "&"
                + ID_SEANCE_VOIE + "=" + newVoie.getIdSeance() + "&"
                + PHOTO_NOM + "=" + "-";

        url = url.replaceAll(" ", "%20");
        new PutVoie().execute(url);
    }


    // AsyncTask pour insérer une nouvelle séance dans la BDD en ligne
    private class PutVoie extends AsyncTask<String, Void, String> {
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
            Log.i("putVoieOnWebDB()", "Voie ajoutée à la BDD en ligne : id " + s);
        }
    }
}
