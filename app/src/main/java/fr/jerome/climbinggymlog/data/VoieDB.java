package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;
import fr.jerome.climbinggymlog.models.Voie;

/**
 * Created by jerome on 28/01/15.
 */
public class VoieDB extends DBHandler {

    public static final String TABLE_NAME = "Voie";

    public static final String ID = "_id";
    public static final String NOM = "nom";
    public static final String COTATION = "cotation_id";
    public static final String TYPE_ESCALADE = "type_escalade_id";
    public static final String STYLE = "style_voie_id";
    public static final String REUSSIE = "reussie";
    public static final String A_VUE = "a_vue";
    public static final String NOTE = "note";
    public static final String ID_SEANCE_VOIE = "seance_id";
    public static final String PHOTO_NOM = "photo_nom";

    public static final String  CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "(" +
                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM + " TEXT, " +
                                COTATION + " INTEGER, " +
                                TYPE_ESCALADE + " INTEGER, " +
                                STYLE + " INTEGER, " +
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
     * @return  seances : Une liste contenant toutes les seances
     */
    public List<Voie> getAllVoiesFromSeanceId(long id) {

        List<Voie> voies = new ArrayList<Voie>();

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_SEANCE_VOIE + "=?", new String[]{String.valueOf(id)});

        c.moveToFirst();
        while (!c.isAfterLast()) {

            boolean isReussi = c.getInt(5) > 0;
            boolean isAVue = c.getInt(6) > 0;

            // l'id de la cotation - 1 car l'index d'une list commence à zéro
            Cotation cot = AppManager.cotations.get(c.getInt(2)-1);

            Voie voie = new Voie(c.getLong(0), c.getString(1), cot, AppManager.typesEsc.get(c.getInt(3)),
                    AppManager.styleVoies.get(c.getInt(4)), isReussi, isAVue, c.getString(7), c.getLong(8));
            voies.add(voie);
            c.moveToNext();
        }
        c.close();
        return voies;
    }

    /**
     * @param voie la voie inserée dans la table
     */
    public Voie insert(Voie voie) {

        ContentValues value = new ContentValues();

        value.put(NOM, voie.getNom());
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
