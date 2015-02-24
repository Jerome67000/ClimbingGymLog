package fr.jerome.climbinggymlog.database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.model.Cotation;

/**
 * Created by jerome on 28/01/15.
 */
public class CotationDB extends DBHandler{

    public static final String TABLE_NAME = "Cot_fr";

    public static final String ID = "_id";
    public static final String DIFF = "diff_cot";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DIFF + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public CotationDB(Context context) {

        super(context);
    }

    /**
     * @return  cotations : Une liste contenant toutes les cotations
     */
    public List<Cotation> getAllCotations() {

        List<Cotation> cotations = new ArrayList<Cotation>();

        Cursor cur = database.query(TABLE_NAME,
                new String[]{ID, DIFF},
                null, null, null, null, null);

        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            Cotation cotation = new Cotation(cur.getLong(0), cur.getString(1));
            cotations.add(cotation);
            cur.moveToNext();
        }
        cur.close();

        return cotations;
    }
}
