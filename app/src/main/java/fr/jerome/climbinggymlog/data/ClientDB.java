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
    public static final String NOM = "nom_u";
    public static final String PRENOM = "prenom_u";
    public static final String NUM = "num_u";
    public static final String EMAIL = "email_u";
    public static final String SALLE_ID = "salle_u";
    public static final String DATE_AJ = "date_aj_u";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                NOM + " TEXT, " +
                                PRENOM + " TEXT, " +
                                NUM + " INTEGER, " +
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

        value.put(NOM, client.getNom());
        value.put(PRENOM, client.getPrenom());
        value.put(NUM, client.getNumClient());
        value.put(DATE_AJ, client.getDateAjout().toString());
        value.put(SALLE_ID, client.getIdSalle());

        // récupération de l'id pour le setter dans l'objet
        long insertId = database.insert(TABLE_NAME, null, value);
        client.setId(insertId);
        Log.d("SQL", "Ajout du client " + client.getNom() + " id : " +client.getId() + " à la table Client");
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
                new String[]{ID, NOM, PRENOM, NUM, EMAIL, SALLE_ID, DATE_AJ},
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
        Client client = new Client();
        //FIXME utiliser le constructeur
        client.setId(c.getLong(0));
        client.setNom(c.getString(1));
        client.setPrenom(c.getString(2));
        client.setNumClient(c.getString(3));
        client.setIdSalle(c.getLong(5));
        client.setDateAjout(new java.sql.Date(date.getTime()));

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
