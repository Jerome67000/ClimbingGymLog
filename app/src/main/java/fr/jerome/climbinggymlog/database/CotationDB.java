package fr.jerome.climbinggymlog.database;

/**
 * Created by jerome on 28/01/15.
 */
public class CotationDB {

    public static final String TABLE_NAME = "Cot_fr";

    public static final String ID_COT = "_id";
    public static final String DIFF_COT = "diff_cot";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            ID_COT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DIFF_COT + " TEXT);";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

}
