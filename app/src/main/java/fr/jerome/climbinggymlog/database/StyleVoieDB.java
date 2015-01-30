package fr.jerome.climbinggymlog.database;

/**
 * Created by jerome on 30/01/15.
 */
public class StyleVoieDB {

    public static final String TABLE_NAME = "Style_voie";

    public static final String ID_STYLE_VOIE = "_id";
    public static final String NOM_STYLE_VOIE = "nom_style_voie";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID_STYLE_VOIE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM_STYLE_VOIE + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
