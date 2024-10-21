package com.example.yogaapplicationapp.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.util.ArrayList;

public class TypeRepo {
    public static final String TABLE_NAME = "type";
    private Context context;
    private YogaDatabase yogaDatabase;
    public TypeRepo(Context context) {
        this.context = context;
        this.yogaDatabase = YogaDatabase.getInstance(context);
        makeSureTableExists();
    }

    // Create a TYPE
    public void createType(String name) {
        makeSureTableExists();
        android.database.sqlite.SQLiteDatabase database = yogaDatabase.getWritableDatabase();
        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (name) VALUES (?)";
        SQLiteStatement statement = database.compileStatement(insertSQL);
        statement.bindString(1, name);

        //Execute statement
        try {
            statement.executeInsert();
            Toast.makeText(context, "ADD TYPE SUCCESS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ADD TYPE FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }

    public void UpdateType(int id,String name){
        Toast.makeText(context, "Run UPDATE", Toast.LENGTH_SHORT).show();
        android.database.sqlite.SQLiteDatabase database = yogaDatabase.getWritableDatabase();

        String updateSQL = "UPDATE " + TABLE_NAME + " SET name = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(updateSQL);

        statement.bindString(1, name);
        statement.bindLong(2, id);

        // Execute Statement
        try {
            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                Toast.makeText(context, "UPDATE TYPE SUCCESS", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, "No Type updated. Check if ID exists.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "UPDATE TYPE FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }

    public void deleteType(){
        SQLiteDatabase database = null; // Declare the database variable
        try {
            database = yogaDatabase.getWritableDatabase(); // Get writable database
            String deleteSQL = "DELETE FROM " + TABLE_NAME; // Prepare the delete statement
            database.execSQL(deleteSQL); // Execute the delete statement
            Toast.makeText(context, "DELETE ALL TYPE SUCCESS", Toast.LENGTH_SHORT).show(); // Success message
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            Toast.makeText(context, "DELETE ALL TYPE FAIL", Toast.LENGTH_SHORT).show(); // Failure message
        } finally {
            if (database != null && database.isOpen()) {
                database.close(); // Close the database if it’s open
            }
        }
    }
    public ArrayList<Type> getAllType(){
        ArrayList<Type> types = new ArrayList<>();
        SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectAllSQL = "SELECT * FROM "+TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectAllSQL, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                    // Create a Task object and add it to the list
                    Type type1 = new Type(id, name);
                    types.add(type1);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return types;
    }

    private void makeSureTableExists() {
        if (!yogaDatabase.isTableExists(TABLE_NAME)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT)";
            yogaDatabase.executeQuery(createTableQuery);
            Toast.makeText(context, "CREATE TYPE TABLE SUCCESS", Toast.LENGTH_SHORT).show();
        }
    }

}
