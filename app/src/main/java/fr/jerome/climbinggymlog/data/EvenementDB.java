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
import fr.jerome.climbinggymlog.models.Client;
import fr.jerome.climbinggymlog.models.Evenement;
import fr.jerome.climbinggymlog.models.Seance;

/**
 * Created by jerome on 25/03/15.
 */
public class EvenementDB extends DBHandler {

    public static final String TABLE_NAME = "Evenements";

    public static final String ID = "_id";
    public static final String TITRE = "titre";
    public static final String DESCRIPTION = "description";
    public static final String DATE = "date";
    public static final String HORAIRE = "horaire";
    public static final String SALLE_ID = "salle_id";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITRE + " TEXT, " +
            DESCRIPTION + " TEXT, " +
            DATE + " DATE, " +
            HORAIRE + " INTEGER, " +
            SALLE_ID + " DATE);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public EvenementDB(Context context) {

        super(context);
    }

    /**
     * @return  evenements : Une liste contenant toutes les seances
     */
    public List<Evenement> getAllEvenements() {

        List<Evenement> evenements = new ArrayList<Evenement>();

        Cursor c = database.query(TABLE_NAME,
                new String[]{ID, TITRE, DESCRIPTION, DATE, HORAIRE, SALLE_ID},
                null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {

            String textDate = c.getString(3);

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = null;
            try {
                date = df.parse(textDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            Evenement evenement = new Evenement(c.getInt(0), c.getString(1), c.getString(2), new java.sql.Date(date.getTime()), c.getInt(4), c.getInt(5));
            evenements.add(evenement);
            c.moveToNext();
        }
        c.close();

        return evenements;
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
        value.put(HORAIRE, evenement.getHeure());
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
