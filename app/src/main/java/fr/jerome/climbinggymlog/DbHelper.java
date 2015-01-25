package fr.jerome.climbinggymlog;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerome on 25/01/15.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cgl_local.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_ADRESSE = "Adresse";
    private static final String COL_ID_ADR = "_id";
    private static final String COL_NUM_ADR = "n um_adr";
    private static final String COL_RUE_ADR = "rue_adr";
    private static final String COL_CP_ADR = "cp_adr";
    private static final String COL_VILLE_ADR = "ville_adr";

    private static final String TABLE_SALLE = "Salle";
    private static final String TABLE_USER = "User";
    private static final String TABLE_SEANCE = "Seance";
    private static final String TABLE_VOIE = "Voie";
    private static final String TABLE_COT_FR = "Cot_fr";
    private static final String TABLE_TYPE_ESC = "Type_esc";
    private static final String TABLE_STYLE_VOIE = "Style_voie";

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DB_NAME, factory, DB_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {

        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
//            db.execSQL(sql);
        }
        catch (SQLiteException e) {
            Log.v("error SQL", "error create SQL", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
