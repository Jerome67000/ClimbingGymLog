package fr.jerome.climbinggymlog.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerome on 27/01/15.
 */
public abstract class DBHandler {

    protected DBHelper DBHelper = null;
    protected SQLiteDatabase db = null;

    public DBHandler(Context context) {

        this.DBHelper = new DBHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        db = DBHelper.getWritableDatabase();
    }

    public SQLiteDatabase reOpen() {

        db = DBHelper.getWritableDatabase();
        return db;
    }

    public void close() {

        db.close();
    }

    /** Classe interne gérant la création et la mise à jour de la BDD */
    private static class DBHelper extends SQLiteOpenHelper {

        public static final String DB_NAME = "climbingGymLog.sqlite";
        public static final int DB_VERSION = 1;

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(ClientDB.CREATE_TABLE);
            }
            catch (SQLiteException e) {
                Log.e("SQL", "error on creating SQL DB", e);
            }

            Log.d("SQL", "BD créée avec succès");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("SQL", "Mise à jour de la BD avec onUpgrade()");
        }
    }
}