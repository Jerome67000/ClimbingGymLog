package fr.jerome.climbinggymlog.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import fr.jerome.climbinggymlog.model.Seance;
import fr.jerome.climbinggymlog.model.Voie;

/**
 * Created by jerome on 28/01/15.
 */
public class VoieDB extends DBHandler {

    public static final String TABLE_NAME = "Voie";

    public static final String ID_VOIE = "_id";
    public static final String NOM_VOIE = "nom_voie";
    public static final String COTATION_VOIE = "cotation_voie";
    public static final String TYPE_ESCALADE_VOIE = "type_escalade_voie";
    public static final String STYLE_VOIE = "style_voie";
    public static final String VOIE_REUSSIE = "voie_reussie";
    public static final String VOIE_A_VUE = "voie_a_vue";
    public static final String NOTE_VOIE = "note_voie";
    public static final String ID_SEANCE_VOIE = "id_seance";

    public static final String  CREATE_TABLE =  "CREATE TABLE " + TABLE_NAME + "(" +
                                ID_VOIE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                NOM_VOIE + " TEXT, " +
                                COTATION_VOIE + " INTEGER, " +
                                TYPE_ESCALADE_VOIE + " INTEGER, " +
                                STYLE_VOIE + " INTEGER, " +
                                VOIE_REUSSIE + " BOOL, " +
                                VOIE_A_VUE + " BOOL, " +
                                NOTE_VOIE + " TEXT, " +
                                ID_SEANCE_VOIE + " INTEGER);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public VoieDB(Context context) {

        super(context);
    }

    /**
     * @param voie la voie à inserer dans la table
     */
    public Voie insert(Voie voie) {

        ContentValues value = new ContentValues();

        value.put(NOM_VOIE, voie.getNom());
        value.put(COTATION_VOIE, voie.getCotation().getId());
        value.put(TYPE_ESCALADE_VOIE, voie.getTypeEscalade());
        value.put(STYLE_VOIE, voie.getStyle());
        value.put(VOIE_REUSSIE, voie.isReussi());
        value.put(VOIE_A_VUE, voie.isAVue());
        value.put(NOTE_VOIE, voie.getNote());
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
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=?", new String[]{String.valueOf(id)});
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
        database.delete(TABLE_NAME, ID_VOIE  + " = " + id, null);

        Log.d("SQL", "suppression de la voie " + voie.getNom() + " id : " +voie.getId() + " de la table Voie");
    }
}
