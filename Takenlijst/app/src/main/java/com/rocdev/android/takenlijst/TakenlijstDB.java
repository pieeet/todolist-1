package com.rocdev.android.takenlijst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by piet on 23-09-15.
 */
public class TakenlijstDB {

    //database constanten om de tabellen te creëren of droppen
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
            "CREATE TABLE " + TAAK_TABEL + " (" +
                    TAAK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TAAK_LIJST_ID + " INTEGER NOT NULL, " +
                    TAAK_NAAM + " TEXT NOT NULL, " +
                    TAAK_NOTITIE + " TEXT, " +
                    TAAK_AFGEROND + " INTEGER, " +
                    TAAK_VERBORGEN + " INTEGER);";



    public static final String DROP_LIJST_TABEL =
            "DROP TABLE IF EXISTS " + LIJST_TABEL;

    public static final String DROP_TAAK_TABEL =
            "DROP TABLE IF EXISTS " + TAAK_TABEL;

    //database en databaseHelper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public TakenlijstDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAAM, null, DB_VERSIE);
    }

    //hulpmethodes
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        db.close();
    }

    public ArrayList<Taak> getTaken(String lijstNaam) {

        String where = TAAK_LIJST_ID + "= ? AND " +
                TAAK_VERBORGEN + "!=1";
        int listID = getLijst(lijstNaam).getId();
        String[] whereArgs = { Integer.toString(listID)};
        this.openReadableDB();
        Cursor cursor = db.query(TAAK_TABEL, null, where, whereArgs, null, null, null);
        ArrayList<Taak> taken = new ArrayList<>();
        while (cursor.moveToNext()) {
            taken.add(getTaakVanCursor(cursor));
        }
        return taken;
    }

    public Taak getTaak(int id) {
        String where = TAAK_ID + "= ?";
        String[] whereArgs = { Integer.toString(id) };
        this.openReadableDB();
        Cursor cursor = db.query(TAAK_TABEL, null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Taak taak = getTaakVanCursor(cursor);
        if (cursor != null) {
            this.closeDB();
        }
        return taak;
    }

    public Lijst getLijst(String name) {
        String where = LIJST_NAAM + "= ?";
        String[] whereArgs = { name };

        openReadableDB();
        Cursor cursor = db.query(LIJST_TABEL, null,
                where, whereArgs, null, null, null);
        Lijst lijst = null;
        cursor.moveToFirst();
        lijst = new Lijst(cursor.getInt(LIJST_ID_COL),
                cursor.getString(LIJST_NAAM_COL));
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return lijst;
    }

    private static Taak getTaakVanCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Taak taak = new Taak();
                taak.setTaakId(cursor.getInt(TAAK_ID_COL));
                taak.setLijstId(cursor.getInt(TAAK_LIJST_ID_COL));
                taak.setNaam(cursor.getString(TAAK_NAAM_COL));
                taak.setNotitie(cursor.getString(TAAK_NOTITIE_COL));
                taak.setDatumAfgerond(cursor.getInt(TAAK_AFGEROND_COL));
                taak.setVerborgen(cursor.getInt(TAAK_VERBORGEN_COL));
                return taak;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long voegTaakToe(Taak taak) {
        ContentValues cv = new ContentValues();
        cv.put(TAAK_LIJST_ID, taak.getLijstId());
        cv.put(TAAK_NAAM, taak.getNaam());
        cv.put(TAAK_NOTITIE, taak.getNotitie());
        cv.put(TAAK_AFGEROND, taak.getDatumAfgerond());
        cv.put(TAAK_VERBORGEN, taak.getVerborgen());

        this.openWritableDB();
        long rijId = db.insert(TAAK_TABEL, null, cv);
        this.closeDB();
        return rijId;
    }

    public int updateTaak(Taak taak) {
        ContentValues cv = new ContentValues();
        cv.put(TAAK_LIJST_ID, taak.getLijstId());
        cv.put(TAAK_NAAM, taak.getNaam());
        cv.put(TAAK_NOTITIE, taak.getNotitie());
        cv.put(TAAK_AFGEROND, taak.getDatumAfgerond());
        cv.put(TAAK_VERBORGEN, taak.getVerborgen());

        String where = TAAK_ID + "= ?";
        String[] whereArgs = { String.valueOf(taak.getTaakId()) };

        this.openWritableDB();
        int rijCount = db.update(TAAK_TABEL, cv, where, whereArgs);
        db.close();
        return rijCount;
    }

    public int deleteTaak(long id) {
        String where = TAAK_ID + "= ?";
        String[] whereArgs = { String.valueOf(id)};
        this.openWritableDB();
        int rijCount = db.delete(TAAK_TABEL, where, whereArgs);
        db.close();
        return rijCount;
    }



    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);


        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_LIJST_TABLE);
            db.execSQL(CREATE_TAAK_TABLE);
            //paar default lijsten invoeren
            db.execSQL("INSERT INTO lijst VALUES (1, 'Persoonlijk')");
            db.execSQL("INSERT INTO lijst VALUES (2, 'Zakelijk')");
            //paar default taken invoeren
            db.execSQL("INSERT INTO taak VALUES (1, 1, 'Rekeningen betalen', " +
            "'', 0, 0)");
            db.execSQL("INSERT INTO taak VALUES (2, 1, 'Naar kapper', " +
                    "'', 0, 0)");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Takenlijst", "upgraden db versie van " +
            oldVersion + " naar " + newVersion);
            db.execSQL(DROP_LIJST_TABEL);
            db.execSQL(DROP_TAAK_TABEL);
            onCreate(db);

        }
    }

}
