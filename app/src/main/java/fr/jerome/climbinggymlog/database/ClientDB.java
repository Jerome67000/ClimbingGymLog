package fr.jerome.climbinggymlog.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import fr.jerome.climbinggymlog.model.Client;

/**
 * Created by jerome on 27/01/15.
 */
public class ClientDB extends DBHandler {

    public static final String TABLE_NAME = "User";

    public static final String ID_U = "_id";
    public static final String NOM_U = "nom_u";
    public static final String PRENOM_U = "prenom_u";
    public static final String NUM_U = "num_u";
    public static final String EMAIL_U = "email_u";
    public static final String SALLE_U = "salle_u";
    public static final String DATE_AJ_U = "date_aj_u";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                                ID_U + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                NOM_U + " TEXT, " +
                                PRENOM_U + " TEXT, " +
                                NUM_U + " INTEGER, " +
                                EMAIL_U + " TEXT, " +
                                SALLE_U + " INTEGER, " +
                                DATE_AJ_U + " DATE);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ClientDB(Context context) {

        super(context);
    }

    /**
     * @param client le client à inserer dans la base
     */
    public void insert(Client client) {

        ContentValues value = new ContentValues();

        value.put(ID_U, client.getId());
        value.put(NOM_U, client.getNom());
        value.put(PRENOM_U, client.getPrenom());
        value.put(NUM_U, client.getNumClient());
        value.put(DATE_AJ_U, client.getDateAjout().toString());
        value.put(SALLE_U, client.getIdSalle());

        database.insert(TABLE_NAME, null, value);

        Log.d("SQL", "Ajout du client " + client.getNom() + " id : " +client.getId() + " à la table Client");
    }

    /**
     * @param id l'identifiant du client à récupérer
     */
    public Client select(long id) {
        return null;
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

        int id = client.getId();
        System.out.println("Client supprimé avec l'id: " + id);
        database.delete(TABLE_NAME, ID_U  + " = " + id, null);

        Log.d("SQL", "suppression du client " + client.getNom() + " id : " +client.getId() + " de la table Client");
    }
}
