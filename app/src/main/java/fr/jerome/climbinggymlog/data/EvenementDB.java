package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.jerome.climbinggymlog.models.Client;
import fr.jerome.climbinggymlog.models.Evenement;

/**
 * Created by jerome on 25/03/15.
 */
public class EvenementDB extends DBHandler {

    public static final String TABLE_NAME = "Evenements";

    public static final String ID = "_id";
    public static final String TITRE = "titre";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String HEURE = "heure";
    public static final String SALLE_ID = "salle_id";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITRE + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            DATE + " DATE, " +
            HEURE + " INTEGER, " +
            SALLE_ID + " DATE);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public EvenementDB(Context context) {

        super(context);
    }

    /**
     * @param evenement l'évenement à inserer dans la base
     */
    public void insert(Evenement evenement) {

        ContentValues value = new ContentValues();

        value.put(ID, evenement.getId());
        value.put(TITRE, evenement.getTitre());
        value.put(DESCRIPTION, evenement.getDescription());
        value.put(DATE, evenement.getDate().toString());
        value.put(HEURE, evenement.getHeure());
        value.put(SALLE_ID, evenement.getSalleId());

        database.insert(TABLE_NAME, null, value);
        Log.d("SQL", "Ajout de l'évenement " + evenement.getTitre() + " id : " + evenement.getId() + " à la table Evenements");
    }

//    public boolean isTableEmpty() {
//
//        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//        return c.getCount() == 0;
//    }

    /**
     * @param evenement la séance à récupérer dans la base
     */
    public Cursor select(Evenement evenement) {

        long id = evenement.getId();
        return database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id=?", new String[]{String.valueOf(id)});
    }

    /**
     * @param evenement l'évenement à modifier
     */
    public void update(Evenement evenement) {

    }

    /**
     * @param evenement client à supprimer
     */
    public void delete(Evenement evenement) {

        int id = evenement.getId();
        database.delete(TABLE_NAME, ID  + " = " + id, null);

        Log.d("SQL", "suppression de l'évenement " + evenement.getTitre() + " id : " +evenement.getId() + " de la table Evenements");
    }
}
