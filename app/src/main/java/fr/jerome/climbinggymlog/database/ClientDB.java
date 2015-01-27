package fr.jerome.climbinggymlog.database;

import android.content.ContentValues;
import android.content.Context;

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
     * @param client le client à ajouter à la base
     */
    public void ajouterClient(Client client) {

        ContentValues value = new ContentValues();

        value.put(ID_U, client.getId());
        value.put(NOM_U, client.getNom());
        value.put(PRENOM_U, client.getPrenom());
        value.put(NUM_U, client.getNumClient());
        value.put(DATE_AJ_U, client.getDateAjout().toString());
        value.put(SALLE_U, client.getIdSalle());

        db.insert(TABLE_NAME, null, value);
    }

    /**
     * @param id l'identifiant du client à supprimer
     */
    public void supprimer(long id) {

    }

    /**
     * @param client le client modifié
     */
    public void modifier(Client client) {

    }

    /**
     * @param id l'identifiant du métier à récupérer
     */
    public Client selectionner(long id) {
        return null;
    }
}
