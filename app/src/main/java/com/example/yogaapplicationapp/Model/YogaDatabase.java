package com.example.yogaapplicationapp.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class YogaDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "YogaSQLite";
    private static final int DATABASE_VERSION = 1;
    private static YogaDatabase INSTANCE;
    private final Context context;

    private YogaDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase sqLiteDatabase) {
        // Define the initial database creation logic here.
    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Define the upgrade logic here.
    }

    public static synchronized YogaDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            if (context != null) {
                INSTANCE = new YogaDatabase(context.getApplicationContext());
            } else {
                throw new IllegalArgumentException("Context must not be null");
            }
        }
        return INSTANCE;
    }

    public void executeQuery(String query) {
        try (android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase()) {
            db.execSQL(query);
            Log.d("YogaDatabase", "Query executed successfully: " + query);
        } catch (Exception e) {
            Log.e("YogaDatabase", "Error executing query: " + query, e);
        }
    }

    public boolean isTableExists(String tableName) {
        android.database.sqlite.SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";
        try (Cursor cursor = db.rawQuery(query, new String[]{tableName})) {
            return cursor.getCount() > 0;
        }
    }
    // Method to drop a table
    public void dropTable(String tableName) {
        android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(dropTableSQL);
    }
}
