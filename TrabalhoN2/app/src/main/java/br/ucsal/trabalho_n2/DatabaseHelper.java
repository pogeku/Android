package br.ucsal.trabalho_n2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trails.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_TRAILS = "trails";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_DISTANCE = "distance";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_AVG_SPEED = "avg_speed";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";

    private static final String CREATE_TRAILS_TABLE =
            "CREATE TABLE " + TABLE_TRAILS + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_START_DATE + " TEXT,"
                    + COLUMN_DISTANCE + " REAL,"
                    + COLUMN_DURATION + " REAL,"
                    + COLUMN_AVG_SPEED + " REAL,"
                    + COLUMN_LATITUDE + " REAL,"
                    + COLUMN_LONGITUDE + " REAL)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TRAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAILS);
        onCreate(db);
    }

    public void addTrail(String startDate, double distance, double duration, double avgSpeed, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_START_DATE, startDate);
        values.put(COLUMN_DISTANCE, distance);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_AVG_SPEED, avgSpeed);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.insert(TABLE_TRAILS, null, values);
        db.close();
    }

    public List<Trail> getAllTrails() {
        List<Trail> trails = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Trail trail = new Trail();
                trail.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                trail.setStartDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE)));
                trail.setDistance(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DISTANCE)));
                trail.setDuration(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DURATION)));
                trail.setAvgSpeed(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AVG_SPEED)));
                trail.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)));
                trail.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)));
                trails.add(trail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return trails;
    }

    public void deleteTrail(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAILS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
