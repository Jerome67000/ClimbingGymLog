package fr.jerome.climbinggymlog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.AppManager;
import fr.jerome.climbinggymlog.model.Cotation;
import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.model.Voie;

/**
 * Created by jerome on 28/01/15.
 */
public class VoieDB extends DBHandler {

    public static final String TABLE_NAME = "Voie";

    public static final String ID = "_id";
    public static final String NOM_VOIE = "nom_voie";
    public static final String COTATION = "cotation_voie";
    public static final String TYPE_ESCALADE = "type_escalade_voie";
    public static final String STYLE = "style_voie";
    public static final String REUSSIE = "voie_reussie";
    public static final String A_VUE = "voie_a_vue";
    public static final String NOTE = "note_voie";
    public static final String ID_SEANCE_VOIE = "id_seance";

    public static final String  CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "(" +
                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                NOM_VOIE + " TEXT, " +
                                COTATION + " INTEGER, " +
                                TYPE_ESCALADE + " INTEGER, " +
                                STYLE + " INTEGER, " +
                                REUSSIE + " BOOL, " +
                                A_VUE + " BOOL, " +
                                NOTE + " TEXT, " +
                                ID_SEANCE_VOIE + " INTEGER);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public VoieDB(Context context) {

        super(context);
    }

    /**
     * @return  seances : Une liste contenant toutes les seances
     */
    public List<Voie> getAllVoiesFromSeanceId(long id) {

        List<Voie> voies = new ArrayList<Voie>();

//        Cursor c = database.query(TABLE_NAME,
//                new String[]{ID, ID_SEANCE_VOIE, NOM_VOIE, COTATION, TYPE_ESCALADE, STYLE, REUSSIE, A_VUE, NOTE},
//                null, null, null, null, null);

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_SEANCE_VOIE + "=?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        while (!c.isAfterLast()) {

            boolean isReussi = false;
            boolean isAVue = false;

            if (c.getInt(5) < 0)
                isReussi = true;

            if (c.getInt(6) < 0)
                isAVue = true;

            Cotation cot = AppManager.cotations.get(c.getInt(2));

            Voie voie = new Voie(c.getLong(0), c.getString(1), cot,
                        AppManager.typesEsc.get(c.getInt(3)), AppManager.styleVoies.get(c.getInt(4)), isReussi, isAVue, c.getString(7), null);
            voies.add(voie);
            c.moveToNext();
        }
        c.close();
        return voies;
    }

    public boolean isNoVoieFromSeanceId(long id) {

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_SEANCE_VOIE + "=?", new String[]{String.valueOf(id)});

        return c.getCount() == 0;
    }

    /**
     * @param voie la voie à inserer dans la table
     */
    public Voie insert(Voie voie) {

        ContentValues value = new ContentValues();

        value.put(NOM_VOIE, voie.getNom());
        value.put(COTATION, voie.getCotation().getId());
        value.put(TYPE_ESCALADE, voie.getTypeEscalade().getType());
        value.put(STYLE, voie.getStyle().getStyle());
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

    /**
     * @param voie la séance à récupérer dans la base
     * @return The cursor of the seance
     */
    public Cursor select(Voie voie) {

        long id = voie.getId();
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(id)});
    }

    /**
     * @param voie la voie modifiée
     */
    public void update(Voie voie) {

    }

    /**
     * @param voie séance à supprimer
     */
    public void delete(Voie voie) {

        long id = voie.getId();
        database.delete(TABLE_NAME, ID + " = " + id, null);

        Log.d("SQL", "suppression de la voie " + voie.getNom() + " id : " +voie.getId() + " de la table Voie");
    }
}
