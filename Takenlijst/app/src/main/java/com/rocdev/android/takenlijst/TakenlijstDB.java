package com.rocdev.android.takenlijst;

/**
 * Created by piet on 23-09-15.
 */
public class TakenlijstDB {

    //database constanten
    public static final String DB_NAAM  = "takenlijst.db";
    public static final int DB_VERSIE = 1;

    //lijst-tabel constanten
    public static final String LIJST_TABEL = "lijst";

    public static final String LIJST_ID = "_id";
    public static final int LIJST_ID_COL = 0;

    public static final String LIJST_NAAM = "lijst_naam";
    public static final int LIJST_NAAM_COL = 1;

    //taak-tabel constanten
    public static final String TAAK_TABEL = "taak";

    public static final String TAAK_ID = "_id";
    public static final int TAAK_ID_COL = 0;

    public static final String TAAK_LIJST_ID = "lijst_id";
    public static final int TAAK_LIJST_ID_COL = 1;

    public static final String TAAK_NAAM = "taak_naam";
    public static final int TAAK_NAAM_COL = 2;

    public static final String TAAK_NOTITIE = "taak_notitie";
    public static final int TAAK_NOTITIE_COL = 3;

    public static final String TAAK_AFGEROND = "taak_afgerond";
    public static final int TAAK_AFGEROND_COL = 4;

    public static final String TAAK_VERBORGEN = "taak_verborgen";
    public static final int TAAK_VERBORGEN_COL = 5;

    //CREATE and DROP TABLE statements
    public static final String CREATE_LIJST_TABLE =
            "CREATE TABLE " + LIJST_TABEL + " (" +
                    LIJST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LIJST_NAAM + " TEXT NOT NULL UNIQUE);";
    public static final String CREATE_TAAK_TABLE =
            "CREATE TABLE " + TAAK_TABEL + "(" +
                    TAAK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TAAK_LIJST_ID + " INTEGER NOT NULL, " +
                    TAAK_NAAM + " TEXT NOT NULL, " +
                    TAAK_NOTITIE + " TEXT, " +
                    TAAK_AFGEROND + " TEXT, " +
                    TAAK_VERBORGEN + " TEXT);";

    public static final String DROP_LIJST_TABEL =
            "DROP TABLE IF EXISTS " + LIJST_TABEL;

    public static final String DROP_TAAK_TABEL =
            "DROP TABLE IF EXISTS " + LIJST_TABEL;

}
