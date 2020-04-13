package fr.android.moi.app_projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Commande pour la suppression on cascade
 * Need to go in the SQLiteOpenHelper class and enable the constraint foreign key
 * In the method onConfigure(), add this line : db.setForeignKeyConstraintsEnabled(true);
 */

public class DataBaseSQLite extends SQLiteOpenHelper {

    private static final String DB_NAME = "BDD.db";
    private static final String TABLE_LOCATION = "Location";
    private static final String TABLE_SCORE = "ScorePlayer";
    private static final String TABLE_STATISTICS = "Statistics";
    private static final String TABLE_MATCH = "Matchs";
    private static final String TABLE_PICTURES = "Pictures";
    private static final int VERSION = 1;

    // ******** Colums of tables ********
    private static final String COLUMS_ID = "ID";

    //Location
    private static final String COLUMS_Longitude = "longitude";
    private static final String COLUMS_Latitude = "latitude";

    //Score
    private static final String COLUMS_First = "FirstSet";
    private static final String COLUMS_Second = "SecondSet";
    //private static final String COLUMS_Third = "ThirdSet";
    //private static final String COLUMS_Forth = "ForthSet";
    //private static final String COLUMS_Fifth = "FifthSet";

    //Stats
    private static final String COLUMS_Points = "PointsWin";
    private static final String COLUMS_FirstBall = "FirstBall";
    private static final String COLUMS_SecondBall = "SecondBall";
    private static final String COLUMS_Aces = "AcesPerformed";
    private static final String COLUMS_DirectFouls = "DirectFouls";
    private static final String COLUMS_DoubleFault = "DoubleFault";

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
    private static final String COLUMS_IDMatch = "IDMatch";

    SQLiteDatabase database;

    public DataBaseSQLite(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_LOCATION + " ("
                + COLUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_Latitude + " TEXT,"
                + COLUMS_Longitude + " TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SCORE + " ("
                + COLUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_First + " INTEGER,"
                + COLUMS_Second + " INTEGER);");
                /*+ COLUMS_Third + "INTEGER,"
                + COLUMS_Forth + "INTEGER,"
                + COLUMS_Fifth + "INTEGER);");*/

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATISTICS + " ("
                + COLUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_Points + " INTEGER,"
                + COLUMS_FirstBall + " INTEGER,"
                + COLUMS_SecondBall + " INTEGER,"
                + COLUMS_Aces + " INTEGER,"
                + COLUMS_DirectFouls + " INTEGER,"
                + COLUMS_DoubleFault + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MATCH + " ("
                + COLUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMS_P1 + " TEXT,"
                + COLUMS_P2 + " TEXT,"
                + COLUMS_Duration + " INTEGER,"
                + COLUMS_Date + " TEXT,"
                + COLUMS_Location + " INTEGER,"
                + COLUMS_ScoreP1 + " INTEGER,"
                + COLUMS_ScoreP2 + " INTEGER,"
                + COLUMS_StatsP1 + " INTEGER,"
                + COLUMS_StatsP2 + " INTEGER,"
                + "FOREIGN KEY (" + COLUMS_Location + ") REFERENCES " + TABLE_LOCATION + " (" + COLUMS_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMS_ScoreP1 + ") REFERENCES " + TABLE_SCORE + " (" + COLUMS_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMS_ScoreP2 + ") REFERENCES " + TABLE_SCORE + " (" + COLUMS_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMS_StatsP1 + ") REFERENCES " + TABLE_STATISTICS + " (" + COLUMS_ID + ") ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMS_StatsP2 + ") REFERENCES " + TABLE_STATISTICS + " (" + COLUMS_ID + ") ON DELETE CASCADE);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PICTURES + " ( "
                + COLUMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + COLUMS_Path + " TEXT, "
                + COLUMS_IDMatch + " INTEGER,"
                + "FOREIGN KEY (" + COLUMS_IDMatch + ") REFERENCES " + TABLE_MATCH + "(" + COLUMS_ID + ") ON DELETE CASCADE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATISTICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATCH);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURES);
        onCreate(db);
    }

    public boolean addLocation(String latitude, String longitude) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_Latitude, latitude);
        contentValues.put(COLUMS_Longitude, longitude);

        long result = database.insert(TABLE_LOCATION, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addScore(int first, int second) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_First, first);
        contentValues.put(COLUMS_Second, second);
//        contentValues.put(COLUMS_Third, third);
//        contentValues.put(COLUMS_Forth, forth);
//        contentValues.put(COLUMS_Fifth, fifth);

        long result = database.insert(TABLE_SCORE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addStatistics(int pointsWin, int aces, int firstBalls, int secondBalls, int directFouls, int doubleFaults) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_Points, pointsWin);
        contentValues.put(COLUMS_FirstBall, firstBalls);
        contentValues.put(COLUMS_SecondBall, secondBalls);
        contentValues.put(COLUMS_Aces, aces);
        contentValues.put(COLUMS_DirectFouls, directFouls);
        contentValues.put(COLUMS_DoubleFault, doubleFaults);

        long result = database.insert(TABLE_STATISTICS, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addPicture(String path, int idMatch) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_Path, path);
        contentValues.put(COLUMS_IDMatch, idMatch);

        long result = database.insert(TABLE_PICTURES, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addMatch(String P1, String P2, String duration, String date, int location, int scoreP1, int scoreP2, int statsP1, int statsP2) {

        database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMS_P1, P1);
        contentValues.put(COLUMS_P2, P2);
        contentValues.put(COLUMS_Duration, duration);
        contentValues.put(COLUMS_Date, date);
        contentValues.put(COLUMS_Location, location);
        contentValues.put(COLUMS_ScoreP1, scoreP1);
        contentValues.put(COLUMS_ScoreP2, scoreP2);
        contentValues.put(COLUMS_StatsP1, statsP1);
        contentValues.put(COLUMS_StatsP2, statsP2);

        long result = database.insert(TABLE_MATCH, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllPlayers() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_P1 + "," + COLUMS_P2 + " FROM " + TABLE_MATCH;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public Cursor getIDsMatchs() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_ID + " FROM " + TABLE_MATCH;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public Cursor getAllMatchs() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_MATCH;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public Cursor getLocationByID(int ID) {
        database = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + COLUMS_ID + " = " + ID;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public Cursor getScoreByID(int ID) {
        database = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_SCORE + " WHERE " + COLUMS_ID + " = " + ID;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public Cursor getStatisticsByID(int ID) {
        database = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_STATISTICS + " WHERE " + COLUMS_ID + " = " + ID;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public Cursor getPictureByID(int ID) {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_Path + " FROM " + TABLE_PICTURES + " WHERE " + COLUMS_IDMatch + " = " + ID;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;

    }

    public Cursor getMatchByID(int ID) {
        database = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_MATCH + " WHERE " + COLUMS_ID + " = " + ID;
        Cursor cursor = database.rawQuery(countQuery, null);

        return cursor;
    }

    public int getIDLastLocation() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_ID + " FROM " + TABLE_LOCATION + " ORDER BY " + COLUMS_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(countQuery, null);

        cursor.moveToFirst();
        int id = cursor.getInt(0);

        return id;
    }

    public int getIDLastStats() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_ID + " FROM " + TABLE_STATISTICS + " ORDER BY " + COLUMS_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(countQuery, null);

        cursor.moveToFirst();
        int id = cursor.getInt(0);

        return id;
    }

    public int getIDLastScore() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_ID + " FROM " + TABLE_SCORE + " ORDER BY " + COLUMS_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(countQuery, null);

        cursor.moveToFirst();
        int id = cursor.getInt(0);

        return id;
    }

    public int getIDLastMatch() {
        database = this.getReadableDatabase();

        String countQuery = "SELECT " + COLUMS_ID + " FROM " + TABLE_MATCH + " ORDER BY " + COLUMS_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(countQuery, null);

        cursor.moveToFirst();
        int id = cursor.getInt(0);

        return id;
    }

    public void deleteMatch(int idMatch) {
        database = this.getWritableDatabase();

        /*String countQuery = "DELETE * FROM " + TABLE_MATCH + " WHERE " + COLUMS_ID + " = " + idMatch;
        database.rawQuery(countQuery, null);*/

        database.delete(TABLE_MATCH, COLUMS_ID + "=?", new String[]{Integer.toString(idMatch)});
    }
}