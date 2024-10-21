package com.example.yogaapplicationapp.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class TempRepo {

    public static final String TEMP_COURSE_NAME = "temp_course";
    public static final String TEMP_YOGACLASS_NAME = "temp_yogaclass";
    private  Context context;
    private YogaDatabase yogaDatabase;

    public TempRepo (Context context){
        this.context = context;
        this.yogaDatabase = YogaDatabase.getInstance(context);
        makeSureTableExits();
    }
    public void addSynCourse(int id){
        SQLiteDatabase db = yogaDatabase.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TEMP_COURSE_NAME + " (courseID) VALUES (" + id + ")";
        db.execSQL(insertQuery);
        Toast.makeText(context, "add syn course", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void addSynClass(int id){
        SQLiteDatabase db = yogaDatabase.getWritableDatabase();
        String insertQuery = "INSERT INTO " + TEMP_YOGACLASS_NAME + " (yogaclassID) VALUES (" + id + ")";
        db.execSQL(insertQuery);
        Toast.makeText(context, "add syn yoga class", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public ArrayList<Integer> getAllSynCourse(){
        ArrayList<Integer> courseIds = new ArrayList<>();
        SQLiteDatabase db = yogaDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT courseID FROM " + TEMP_COURSE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int courseId = cursor.getInt(cursor.getColumnIndex("courseID"));
                courseIds.add(courseId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return courseIds;
    }
    public ArrayList<Integer> getAllSynClass(){
        ArrayList<Integer> classIds = new ArrayList<>();
        SQLiteDatabase db = yogaDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT yogaclassID FROM " + TEMP_YOGACLASS_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int classId = cursor.getInt(cursor.getColumnIndex("yogaclassID"));
                classIds.add(classId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return classIds;
    }
    public void clearCourseTemp(){
        SQLiteDatabase db = yogaDatabase.getWritableDatabase();
        String clearTableSQL = "DELETE FROM " + TEMP_COURSE_NAME;
        db.execSQL(clearTableSQL);
        db.close();
    }
    public void clearYogaClassTemp(){
        SQLiteDatabase db = yogaDatabase.getWritableDatabase();
        String clearTableSQL = "DELETE FROM " + TEMP_YOGACLASS_NAME;
        db.execSQL(clearTableSQL);
        db.close();
    }
    public void makeSureTableExits(){
        SQLiteDatabase db = yogaDatabase.getWritableDatabase();

        String createTempCourseTable = "CREATE TABLE IF NOT EXISTS " + TEMP_COURSE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "courseID TEXT NOT NULL" +
                ");";

        String createTempYogaClassTable = "CREATE TABLE IF NOT EXISTS " + TEMP_YOGACLASS_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "yogaclassID TEXT NOT NULL" +
                ");";

        db.execSQL(createTempCourseTable);
        db.execSQL(createTempYogaClassTable);
        db.close();
    }
}
