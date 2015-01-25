package fr.jerome.climbinggymlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.model.Client;

/**
 * Created by jerome on 25/01/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "cgl_local.db";
    public static final int DB_VERSION = 1;

    // ADRESSE TABLE
    public static final String TABLE_ADRESSE = "Adresse";
    public static final String ID_ADR =    "_id";
    public static final String NUM_ADR =   "num_adr";
    public static final String RUE_ADR =   "rue_adr";
    public static final String CP_ADR =    "cp_adr";
    public static final String VILLE_ADR = "ville_adr";

    // SALLE TABLE
    public static final String TABLE_SALLE = "Salle";
    public static final String ID_S = "_id";
    public static final String NOM_S = "nom_s";
    public static final String ADRESSE_S = "adresse_s";
    public static final String TEL_S = "tel_s";
    public static final String HORAIRE_S = "horaire_s";
    public static final String NOM_GERANT_S = "nom_gerant_s";
    public static final String PRENOM_GERANT_S = "prenom_gerant_s";
    public static final String SIRET_S = "siret_s";
    public static final String DATE_AJOUT_S = "date_ajout_s";

    // USER TABLE
    public static final String TABLE_USER = "User";
    public static final String ID_U =  "_id";
    public static final String NOM_U = "nom_u";
    public static final String PRENOM_U = "prenom_u";
    public static final String NUM_U = "num_u";
    public static final String EMAIL_U = "email_u";
    public static final String SALLE_U = "salle_u";
    public static final String DATE_AJ_U = "date_aj_u";

    // SEANCE TABLE
    public static final String TABLE_SEANCE = "Seance";
    public static final String ID_SEANCE = "_id";
    public static final String NOM_SEANCE = "nom_seance";
    public static final String DATE_SEANCE = "date_seance";
    public static final String DATE_AJ_SEANCE = "date_aj_seance";
    public static final String NOM_SALLE_SEANCE = "nom_salle_seance";
    public static final String NOTE_SEANCE = "note_seance";
    public static final String USER_SEANCE = "user_seance";

    // VOIE TABLE
    public static final String TABLE_VOIE = "Voie";
    public static final String ID_VOIE = "_id";
    public static final String NOM_VOIE = "nom_voie";
    public static final String COTATION_VOIE = "cotation_voie";
    public static final String TYPE_ESCALADE_VOIE = "type_escalade_voie";
    public static final String STYLE_VOIE = "style_voie";
    public static final String VOIE_REUSSIE = "voie_reussie";
    public static final String VOIE_A_VUE = "voie_a_vue";
    public static final String NOTE_VOIE = "note_voie";

    // TABLE COTATION FRANCAISE
    public static final String TABLE_COT_FR = "Cot_fr";
    public static final String ID_COT = "_id";
    public static final String DIFF_COT = "diff_cot";
    public static final String COULEUR_COT = "couleur_cot";

    // TABLE TYPE D'ESCALADE
    public static final String TABLE_TYPE_ESC = "Type_esc";
    public static final String ID_TYPE_ESC = "_id";
    public static final String NOM_TYPE_ESC = "nom_type_esc";

    // TABLE STYLE DE VOIE
    public static final String TABLE_STYLE_VOIE =  "Style_voie";
    public static final String ID_STYLE_VOIE = "_id";
    public static final String NOM_STYLE_VOIE = "nom_style_voie";


    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DB_NAME, factory, DB_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {

        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

        onCreate(db);
    }

    public void addClient(Client newClient) {

        ContentValues value = new ContentValues();

        value.put(ID_U, newClient.getId());
        value.put(NOM_U, newClient.getNom());
        value.put(PRENOM_U, newClient.getPrenom());
        value.put(NUM_U, newClient.getNumClient());
        value.put(DATE_AJ_U, newClient.getDateAjout().toString());
        value.put(SALLE_U, newClient.getIdSalle());
        getWritableDatabase().insert(TABLE_USER, null, value);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADRESSE + ";");
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEANCE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOIE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COT_FR + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE_ESC + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STYLE_VOIE + ";");

//        String CREATE_ADRESSE_TABLE =   "CREATE TABLE " + TABLE_ADRESSE + "(" +
//                                        ID_ADR      + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                                        NUM_ADR     + " INTEGER, " +
//                                        RUE_ADR     + " TEXT, " +
//                                        CP_ADR      + " INTEGER, " +
//                                        VILLE_ADR   + " TEXT);";
//
//        String CREATE_SALLE_TABLE =    "CREATE TABLE " + TABLE_SALLE + "(" +
//                                        ID_S        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                                        NOM_S       + " TEXT, " +
//                                        ADRESSE_S   + " INTEGER, " +
//                                        TEL_S       + " INTEGER, " +
//                                        HORAIRE_S      + " TEXT, " +
//                                        NOM_GERANT_S      + " TEXT, " +
//                                        PRENOM_GERANT_S      + " TEXT, " +
//                                        SIRET_S      + " INTEGER, " +
//                                        DATE_AJOUT_S   + " DATE);";

        String CREATE_USER_TABLE =      "CREATE TABLE " + TABLE_USER + "(" +
                                        ID_U        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        NOM_U       + " TEXT, " +
                                        PRENOM_U       + " TEXT, " +
                                        NUM_U   + " INTEGER, " +
                                        EMAIL_U       + " TEXT, " +
                                        SALLE_U      + " INTEGER, " +
                                        DATE_AJ_U  + " DATE);";

        String CREATE_SEANCE_TABLE =      "CREATE TABLE " + TABLE_SEANCE + "(" +
                                        ID_SEANCE        + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        NOM_SEANCE       + " TEXT, " +
                                        DATE_SEANCE       + " DATE, " +
                                        DATE_AJ_SEANCE       + " DATE, " +
                                        NOM_SALLE_SEANCE       + " TEXT, " +
                                        NOTE_SEANCE       + " TEXT, " +
                                        USER_SEANCE       + " INTEGER);";

        String CREATE_VOIE_TABLE =      "CREATE TABLE " + TABLE_VOIE + "(" +
                                        ID_VOIE    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        NOM_VOIE       + " TEXT, " +
                                        COTATION_VOIE      + " INTEGER, " +
                                        TYPE_ESCALADE_VOIE       + " INTEGER, " +
                                        STYLE_VOIE       + " INTEGER, " +
                                        VOIE_REUSSIE       + " BOOL, " +
                                        VOIE_A_VUE       + " BOOL, " +
                                        NOTE_VOIE      + " TEXT);";

        String CREATE_COTATION_TABLE =  "CREATE TABLE " + TABLE_COT_FR + "(" +
                                        ID_COT    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        DIFF_COT       + " TEXT, " +
                                        COULEUR_COT       + " TEXT);"; // FIXME INTEGER ?

        String CREATE_TYPE_ESC_TABLE =  "CREATE TABLE " + TABLE_TYPE_ESC + "(" +
                                        ID_TYPE_ESC    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        NOM_TYPE_ESC       + " TEXT);";

        String CREATE_STYLE_VOIE =  "CREATE TABLE " + TABLE_STYLE_VOIE + "(" +
                                        ID_STYLE_VOIE    + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        NOM_STYLE_VOIE       + " TEXT);";

        try {
//            db.execSQL(CREATE_ADRESSE_TABLE);
//            db.execSQL(CREATE_SALLE_TABLE);
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_SEANCE_TABLE);
            db.execSQL(CREATE_VOIE_TABLE);
            db.execSQL(CREATE_COTATION_TABLE);
            db.execSQL(CREATE_TYPE_ESC_TABLE);
            db.execSQL(CREATE_STYLE_VOIE);
        }
        catch (SQLiteException e) {
            Log.e("SQL", "error on creating SQL DB", e);
        }

        Log.d("SQL", "BD créée avec succès");

        addDataCotationFr(db);
        addDataTypeEsc(db);
        addDataStyleVoie(db);
    }

    private void addDataCotationFr(SQLiteDatabase db) {

        int num = 2;
        String lettre;
        String lettreA = "a";
        String lettreB = "b";
        String lettreC = "c";

        ContentValues value = new ContentValues();

        while (num < 10) {

            lettre = lettreA;
            value.put(DIFF_COT, num + lettre);
            db.insert(TABLE_COT_FR, null, value);
            value.put(DIFF_COT, num + lettre + "+");
            db.insert(TABLE_COT_FR, null, value);

            lettre = lettreB;
            value.put(DIFF_COT, num + lettre);
            db.insert(TABLE_COT_FR, null, value);
            value.put(DIFF_COT, num + lettre + "+");
            db.insert(TABLE_COT_FR, null, value);

            lettre = lettreC;
            value.put(DIFF_COT, num + lettre);
            db.insert(TABLE_COT_FR, null, value);
            value.put(DIFF_COT, num + lettre + "+");
            db.insert(TABLE_COT_FR, null, value);

            num++;
        }


        Log.d("SQL", "Cotations ajoutées à la BDD");
    }

    private void addDataTypeEsc(SQLiteDatabase db) {

        ContentValues value = new ContentValues();
        value.put(NOM_TYPE_ESC, "En tête");
        db.insert(TABLE_TYPE_ESC, null, value);
        value.put(NOM_TYPE_ESC, "Moulinette");
        db.insert(TABLE_TYPE_ESC, null, value);
        value.put(NOM_TYPE_ESC, "Solo");
        db.insert(TABLE_TYPE_ESC, null, value);

        Log.d("SQL", "Types d'escalade ajoutés à la BDD");
    }

    private void addDataStyleVoie(SQLiteDatabase db) {

        ContentValues value = new ContentValues();
        value.put(NOM_STYLE_VOIE, "Dalle");
        db.insert(TABLE_STYLE_VOIE, null, value);
        value.put(NOM_STYLE_VOIE, "Verticale");
        db.insert(TABLE_STYLE_VOIE, null, value);
        value.put(NOM_STYLE_VOIE, "Léger dévers");
        db.insert(TABLE_STYLE_VOIE, null, value);
        value.put(NOM_STYLE_VOIE, "Gros dévers");
        db.insert(TABLE_STYLE_VOIE, null, value);
        value.put(NOM_STYLE_VOIE, "Toit");
        db.insert(TABLE_STYLE_VOIE, null, value);
        value.put(NOM_STYLE_VOIE, "Bloc");
        db.insert(TABLE_STYLE_VOIE, null, value);

        Log.d("SQL", "Style de voie ajoutés à la BDD");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("SQL", "Mise à jour de la BD avec onUpgrade()");
    }
}
