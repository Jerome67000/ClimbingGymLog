package fr.jerome.climbinggymlog.database;

/**
 * Created by jerome on 28/01/15.
 */
public class TypeEscDB {

    public static final String TABLE_NAME = "Type_esc";

    public static final String ID_TYPE_ESC = "_id";
    public static final String NOM_TYPE_ESC = "nom_type_esc";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID_TYPE_ESC + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOM_TYPE_ESC + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
}
