package fr.jerome.climbinggymlog.database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import fr.jerome.climbinggymlog.model.StyleVoie;

/**
 * Created by jerome on 30/01/15.
 */
public class StyleVoieDB extends DBHandler {

    public static final String TABLE_NAME = "Style_voie";

    public static final String ID = "_id";
    public static final String NOM = "nom_style_voie";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public StyleVoieDB(Context context) {

        super(context);
    }

    /**
     * @return  styles : Une liste contenant tous les styles de voie
     */
    public List<StyleVoie> getAllStyles() {

        List<StyleVoie> styles = new ArrayList<StyleVoie>();

        Cursor cur = database.query(TABLE_NAME,
                new String[]{ID, NOM},
                null, null, null, null, null);

        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            StyleVoie styleVoie = new StyleVoie(cur.getLong(0), cur.getString(1));
            styles.add(styleVoie);
            cur.moveToNext();
        }
        cur.close();
        return styles;
    }
}
