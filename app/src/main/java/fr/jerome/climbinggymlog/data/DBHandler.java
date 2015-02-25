package fr.jerome.climbinggymlog.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerome on 27/01/15.
 */
public class DBHandler {

    protected DBHelper dBHelper = null;
    protected SQLiteDatabase database = null;

    public DBHandler(Context context) {

        this.dBHelper = new DBHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        database = dBHelper.getWritableDatabase();
    }

    public void reOpen() {

        database = dBHelper.getWritableDatabase();
    }

    public void close() {

        database.close();
    }

    /**
     * Classe interne gérant la création et la mise à jour de la BDD
     */
    private static class DBHelper extends SQLiteOpenHelper {

        public static final String DB_NAME = "climbingGymLog.sqlite3";
        public static final int DB_VERSION = 1;

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(ClientDB.CREATE_TABLE);
                db.execSQL(SeanceDB.CREATE_TABLE);
                db.execSQL(VoieDB.CREATE_TABLE);
                db.execSQL(CotationDB.CREATE_TABLE);
                db.execSQL(TypeEscDB.CREATE_TABLE);
                db.execSQL(StyleVoieDB.CREATE_TABLE);
            } catch (SQLiteException e) {
                Log.e("SQLite", "erreur pendant la création des tables SQLite", e);
            }

            Log.d("SQLite", "DB créée avec succès");

            addDataCotation(db);
            addDataTypeEsc(db);
            addDataStlyeVoie(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("SQLite", "Mise à jour de la BD avec onUpgrade()");
        }

//        private void addDataCotation(SQLiteDatabase db) {
//
//            ContentValues value = new ContentValues();
//            int num = 2;
//            String lettre;
//            String lettreA = "a";
//            String lettreB = "b";
//            String lettreC = "c";
//
//            while (num < 10) {
//
//                lettre = lettreA;
//                value.put(CotationDB.NAME, num + lettre);
//                db.insert(CotationDB.TABLE_NAME, null, value);
//                value.put(CotationDB.NAME, num + lettre + "+");
//                db.insert(CotationDB.TABLE_NAME, null, value);
//
//                lettre = lettreB;
//                value.put(CotationDB.NAME, num + lettre);
//                db.insert(CotationDB.TABLE_NAME, null, value);
//                value.put(CotationDB.NAME, num + lettre + "+");
//                db.insert(CotationDB.TABLE_NAME, null, value);
//
//                lettre = lettreC;
//                value.put(CotationDB.NAME, num + lettre);
//                db.insert(CotationDB.TABLE_NAME, null, value);
//                value.put(CotationDB.NAME, num + lettre + "+");
//                db.insert(CotationDB.TABLE_NAME, null, value);
//
//                num++;
//            }
//
//            Log.d("SQLite", "Cotations ajoutées à la DB");
//        }

        private void addDataCotation(SQLiteDatabase db) {

            int numberStart = 3;
            String plus;

            ContentValues value = new ContentValues();
            while (numberStart < 10) {

                int lettre = (int) 'a';
                while (lettre < (int) 'd') {

                    plus = "";
                    value.put(CotationDB.NUMBER_COT, numberStart);
                    value.put(CotationDB.LETTER_COT, String.valueOf((char)lettre));
                    value.put(CotationDB.PLUS_COT, plus.equals("") ? 0 : 1);
                    value.put(CotationDB.NAME, numberStart + String.valueOf((char)lettre) + plus);
                    db.insert(CotationDB.TABLE_NAME, null, value);

                    plus = "+";
                    value.put(CotationDB.NUMBER_COT, numberStart);
                    value.put(CotationDB.LETTER_COT, String.valueOf((char)lettre));
                    value.put(CotationDB.PLUS_COT, plus.equals("") ? 0 : 1);
                    value.put(CotationDB.NAME, numberStart + String.valueOf((char)lettre) + plus);
                    db.insert(CotationDB.TABLE_NAME, null, value);
                    lettre++;
                }
                numberStart++;
            }
            Log.d("SQLite", "Cotations ajoutées à la DB");
        }

        private void addDataTypeEsc(SQLiteDatabase db) {

            ContentValues value = new ContentValues();

            value.put(TypeEscDB.NOM, "En tete");
            db.insert(TypeEscDB.TABLE_NAME, null, value);
            value.put(TypeEscDB.NOM, "Moulinette");
            db.insert(TypeEscDB.TABLE_NAME, null, value);
            value.put(TypeEscDB.NOM, "Solo");
            db.insert(TypeEscDB.TABLE_NAME, null, value);

            Log.d("SQLite", "Type d'escalade ajoutées à la DB");
        }

        private void addDataStlyeVoie(SQLiteDatabase db) {

            ContentValues value = new ContentValues();
            value.put(StyleVoieDB.NOM, "Dalle");
            db.insert(StyleVoieDB.TABLE_NAME, null, value);
            value.put(StyleVoieDB.NOM, "Verticale");
            db.insert(StyleVoieDB.TABLE_NAME, null, value);
            value.put(StyleVoieDB.NOM, "Léger dévers");
            db.insert(StyleVoieDB.TABLE_NAME, null, value);
            value.put(StyleVoieDB.NOM, "Gros dévers");
            db.insert(StyleVoieDB.TABLE_NAME, null, value);
            value.put(StyleVoieDB.NOM, "Toit");
            db.insert(StyleVoieDB.TABLE_NAME, null, value);
            value.put(StyleVoieDB.NOM, "Bloc");
            db.insert(StyleVoieDB.TABLE_NAME, null, value);

            Log.d("SQLite", "Style de voie ajoutées à la DB");
        }
    }
}