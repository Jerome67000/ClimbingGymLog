package fr.jerome.climbinggymlog.data;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.models.Cotation;

/**
 * Created by jerome on 28/01/15.
 */
public class CotationDB extends DBHandler{

    public static final String TABLE_NAME = "Cot_fr";

    public static final String ID = "_id";
    public static final String NAME = "name_cot";
    public static final String NUMBER_COT = "number_cot";
    public static final String LETTER_COT = "letter_cot";
    public static final String PLUS_COT = "plus_cot";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT, " +
            NUMBER_COT + " INT, " +
            LETTER_COT + " TEXT, " +
            PLUS_COT + " BOOL);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public CotationDB(Context context) {

        super(context);
    }

    /**
     * @return  cotations : Une liste contenant toutes les cotations
     */
    public List<Cotation> getAllCotations() {

        List<Cotation> cotations = new ArrayList<Cotation>();

        Cursor c = database.query(TABLE_NAME,
                new String[]{ID, NAME, NUMBER_COT, LETTER_COT, PLUS_COT},
                null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            boolean isPlus = c.getInt(4) != 0;
            Cotation cotation = new Cotation(c.getLong(0), c.getString(1), c.getInt(2), c.getString(3), isPlus);
            cotations.add(cotation);
            c.moveToNext();
        }
        c.close();

        return cotations;
    }
}
