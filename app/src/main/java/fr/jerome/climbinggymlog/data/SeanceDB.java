package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Seance;

/**
 * Created by jerome on 28/01/15.
 */
public class SeanceDB extends DBHandler {

    public static final String TABLE_NAME = "Seance";

    public static final String ID = "_id";
    public static final String NOM = "nom";
    public static final String DATE = "date_seance";
    public static final String DATE_AJ = "date_aj";
    public static final String NOM_SALLE = "nom_salle";
    public static final String NOTE = "note";
    public static final String CLIENT_ID = "client_id";

    public static final String   CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM + " TEXT, " +
            DATE + " DATE, " +
            DATE_AJ + " DATE, " +
            NOM_SALLE + " TEXT, " +
            NOTE + " TEXT, " +
            CLIENT_ID + " INTEGER);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public SeanceDB(Context context) {

        super(context);
    }

    /**
     * @param seance la séance à inserer dans la table
     */
    public long insert(Seance seance) {

        ContentValues value = new ContentValues();

        value.put(NOM, seance.getNom());
        value.put(DATE, seance.getDateSeance().toString());
        value.put(DATE_AJ, seance.getDateAjout().toString());
        value.put(NOM_SALLE, seance.getNomSalle());
        value.put(NOTE, seance.getNote());
        value.put(CLIENT_ID, seance.getClient().getId());

        // récupération de l'id pour le setter à l'objet
        long insertId = database.insert(TABLE_NAME, null, value);
        seance.setId(insertId);
        Log.d("SQL", "Ajout de la séance " + seance.getNom() + " id : " + seance.getId() + " à la table Seance");

        return insertId;
    }

    /**
     * @param seance la séance à récupérer dans la base
     */
    public Cursor select(Seance seance) {

        long id = seance.getId();
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=?", new String[]{String.valueOf(id)});
    }

    /**
     * @param seance la séance modifié
     */
    public void update(Seance seance) {

    }

    /**
     * @param seance séance à supprimer
     */
    public void delete(Seance seance) {

        long id = seance.getId();
        database.delete(TABLE_NAME, ID + " = " + id, null);

        Log.d("SQLite", "Suppression de la séance : " + seance.getNom() + "avec l'id : " + seance.getId() + " de la table Seance");
    }

    /**
     * @return  seances : Une liste contenant toutes les seances
     */
    public List<Seance> getAllSeances() {

        List<Seance> seances = new ArrayList<Seance>();

        Cursor c = database.query(TABLE_NAME,
                new String[]{ID, NOM, DATE, DATE_AJ, NOM_SALLE, NOTE, CLIENT_ID},
                null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {

            String textDate = c.getString(2);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = null;
            try {
                date = df.parse(textDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            assert date != null;
            Seance seance = new Seance(c.getLong(0), c.getString(1), new java.sql.Date(date.getTime()), new java.sql.Date(date.getTime()), c.getString(4), c.getString(5), AppManager.client);
            seances.add(seance);
            c.moveToNext();
        }
        c.close();
        return seances;
    }
}
