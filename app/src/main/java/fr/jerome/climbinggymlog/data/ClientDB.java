package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import fr.jerome.climbinggymlog.models.Client;

/**
 * Created by jerome on 27/01/15.
 */
public class ClientDB extends DBHandler {

    public static final String TABLE_NAME = "User";

    public static final String ID = "_id";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String NUM_CLIENT = "num";
    public static final String EMAIL = "email";
    public static final String SALLE_ID = "salle_id";
    public static final String DATE_AJ = "date_aj";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                NOM + " TEXT, " +
                                PRENOM + " TEXT, " +
                                NUM_CLIENT + " TEXT, " +
                                EMAIL + " TEXT, " +
                                SALLE_ID + " INTEGER, " +
                                DATE_AJ + " DATE);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ClientDB(Context context) {

        super(context);
    }

    /**
     * @param client le client à inserer dans la base
     */
    public void insert(Client client) {

        ContentValues value = new ContentValues();

        value.put(ID, client.getId());
        value.put(NOM, client.getNom());
        value.put(PRENOM, client.getPrenom());
        value.put(NUM_CLIENT, client.getNumClient());
        value.put(DATE_AJ, client.getDateAjout().toString());
        value.put(SALLE_ID, client.getIdSalle());
        value.put(EMAIL, client.getEmail());

        Log.d("SQL", "Ajout du client " + client.getNom() + " id : " + client.getId() + " à la table Client");
    }

    public boolean isTableEmpty() {

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return c.getCount() == 0;
    }

    /**
     * @param id du client à récupérer
     */
    public Client select(long id) {

        Cursor c = database.query(TABLE_NAME,
                new String[]{ID, NOM, PRENOM, NUM_CLIENT, EMAIL, SALLE_ID, DATE_AJ},
                null, null, null, null, null);

        c.moveToFirst();
        String textDate = c.getString(6);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        Client client = new Client(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), new java.sql.Date(date.getTime()), c.getLong(5));

        return client;
    }

    /**
     * @param numClient du client à récupérer
     */
    public Client select(String numClient) {

        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + NUM_CLIENT + "=?", new String[]{String.valueOf(numClient)});

        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        String textDate = c.getString(6);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(textDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert date != null;
        Client client = new Client(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), new java.sql.Date(date.getTime()), c.getLong(5));
        client.setEmail(c.getString(4));

        return client;
    }

    /**
     * @param client le client modifié
     */
    public void update(Client client) {

    }

    /**
     * @param client client à supprimer
     */
    public void delete(Client client) {

        long id = client.getId();
        System.out.println("Client supprimé avec l'id: " + id);
        database.delete(TABLE_NAME, ID  + " = " + id, null);

        Log.d("SQL", "suppression du client " + client.getNom() + " id : " +client.getId() + " de la table Client");
    }
}
