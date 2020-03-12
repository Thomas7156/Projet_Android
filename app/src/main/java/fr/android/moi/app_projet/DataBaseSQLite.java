package fr.android.moi.app_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseSQLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDD.db";
    private static final String TABLE_LOCATION = "Localisation";
    private static final String TABLE_SCORE = "ScorePlayer";
    private static final String TABLE_STATISTICS = "Statistics";
    private static final String TABLE_MATCH = "Matchs";
    private static final String TABLE_PICTURES = "Pictures";
    private static final int VERSION = 1;

    // ******** Fields ********
    private static final String COLUMS_ID = "ID";

    //Location
    private static final String COLUMS_Longitude = "longitude";
    private static final String COLUMS_Latitude = "latitude";

    //Score
    private static final String COLUMS_First = "FirstSet";
    private static final String COLUMS_Second = "SecondSet";
    private static final String COLUMS_Third = "ThirdSet";
    private static final String COLUMS_Forth = "ForthSet";
    private static final String COLUMS_Fifth = "FifthSet";

    //Stats
    private static final String COLUMS_Points = "PointsWin";
    private static final String COLUMS_Sets = "SetsWin";
    private static final String COLUMS_FirstBall = "FirstBall";
    private static final String COLUMS_Aces = "AcesPerformed";
    private static final String COLUMS_DirectFouls = "DirectFouls";

    //Matchs
    private static final String COLUMS_P1 = "Player1";
    private static final String COLUMS_P2 = "Player2";
    private static final String COLUMS_Duration = "Duration";
    private static final String COLUMS_Date = "Date";
    private static final String COLUMS_Location = "Location";
    private static final String COLUMS_ScoreP1 = "ScoreP1";
    private static final String COLUMS_ScoreP2 = "ScoreP2";
    private static final String COLUMS_StatsP1 = "StatsP1";
    private static final String COLUMS_StatsP2 = "StatsP2";

    //Pictures
    private static final String COLUMS_Path = "PathToPpicture";
    private static final String COLUMS_Match = "IDMatch";

    SQLiteDatabase database;

    public DataBaseSQLite(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_LOCATION + "("
                + COLUMS_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMS_Longitude + "FLOAT,"
                + COLUMS_Latitude + "FLOAT)");

        db.execSQL("CREATE TABLE " + TABLE_SCORE + "("
                + COLUMS_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMS_First + "INTEGER,"
                + COLUMS_Second + "INTEGER,"
                + COLUMS_Third + "INTEGER,"
                + COLUMS_Forth + "INTEGER,"
                + COLUMS_Fifth + "INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_STATISTICS + "("
                + COLUMS_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMS_Points + "INTEGER,"
                + COLUMS_Sets + "INTEGER,"
                + COLUMS_FirstBall + "INTEGER,"
                + COLUMS_Aces + "INTEGER,"
                + COLUMS_DirectFouls + "INTEGER);");

        db.execSQL("CREATE TABLE " + TABLE_MATCH + "("
                + COLUMS_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMS_P1 + "INTEGER,"
                + COLUMS_P2 + "INTEGER,"
                + COLUMS_Duration + "INTEGER,"
                + COLUMS_Date + "DATE,"
                + COLUMS_Location + "INTEGER,"
                + COLUMS_ScoreP1 + "INTEGER,"
                + COLUMS_ScoreP2 + "INTEGER,"
                + COLUMS_StatsP1 + "INTEGER,"
                + COLUMS_StatsP2 + "INTEGER,"
                + "FOREIGN KEY (" + COLUMS_Location + ") REFERENCES " + TABLE_LOCATION + " (" + COLUMS_ID + "), "
                + "FOREIGN KEY (" + COLUMS_ScoreP1 + ") REFERENCES " + TABLE_SCORE + " (" + COLUMS_ID + "), "
                + "FOREIGN KEY (" + COLUMS_ScoreP2 + ") REFERENCES " + TABLE_SCORE + " (" + COLUMS_ID + "), "
                + "FOREIGN KEY (" + COLUMS_StatsP1 + ") REFERENCES " + TABLE_STATISTICS + " (" + COLUMS_ID + "), "
                + "FOREIGN KEY (" + COLUMS_StatsP2 + ") REFERENCES " + TABLE_STATISTICS + " (" + COLUMS_ID + "));");

        db.execSQL("CREATE TABLE " + TABLE_PICTURES + " ( "
                + COLUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_Path + " INTEGER, "
                + COLUMS_Match + " INTEGER,"
                + "FOREIGN KEY (" + COLUMS_Match + ") REFERENCES " + TABLE_MATCH + "(" + COLUMS_ID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCALISATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETS);
        onCreate(db);
    }

    // Ajout d'une localisation pour un match localisations
    public boolean add_data_localiqations(String id, String longitude, Float latitude) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_ID, id);
        contentValues.put(COLUMS_Longitude, longitude);
        contentValues.put(COLUMS_Latitude, latitude);
        long result = database.insert(TABLE_LOCALISATION, null, contentValues);

        if (result == -1) return false;
        else return true;
    }

    // Ajout d'une localisation pour un match localisations
    public boolean add_data_sets(String id, int un, int deux, int trois, int quatre, int cinq) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_ID, id);
        contentValues.put(COLUMS_un, un);
        contentValues.put(COLUMS_deux, deux);
        contentValues.put(COLUMS_trois, trois);
        contentValues.put(COLUMS_quatre, quatre);
        contentValues.put(COLUMS_cinq, cinq);
        long result = database.insert(TABLE_SETS, null, contentValues);

        if (result == -1) return false;
        else return true;
    }


    public Cursor getAllData() {
        database = this.getReadableDatabase();
        String requete = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMS_date + " DESC";
        Cursor cursor = database.rawQuery(requete, null);

        return cursor;
    }

    public Cursor getDateBetween(String dateStart, String dateEnd) {
        database = this.getReadableDatabase();

        if (!dateStart.equals(dateEnd)) {
            String requete = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMS_date
                    + " BETWEEN date('" + dateStart + "') AND date('" + dateEnd + "') ORDER BY " + COLUMS_date + " DESC";
            Cursor cursor = database.rawQuery(requete, null);

            return cursor;
        } else {
            String requete = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMS_date
                    + " = date('" + dateStart + "') ORDER BY " + COLUMS_date + " DESC";
            Cursor cursor = database.rawQuery(requete, null);

            return cursor;
        }
    }

    public Cursor getDateUp(String dateStart) {
        database = this.getReadableDatabase();
        String requete = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMS_date
                + " > date('" + dateStart + "') ORDER BY " + COLUMS_date + " DESC";
        Cursor cursor = database.rawQuery(requete, null);

        return cursor;
    }

    public Cursor getDateDown(String dateEnd) {
        database = this.getReadableDatabase();
        String requete = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMS_date
                + " < date('" + dateEnd + "') ORDER BY " + COLUMS_date + " DESC";
        Cursor cursor = database.rawQuery(requete, null);

        return cursor;
    }

    public Cursor getAllValue() {
        database = this.getReadableDatabase();
        String requete = "SELECT sum(" + COLUMS_value + ") FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(requete, null);

        return cursor;
    }
}
