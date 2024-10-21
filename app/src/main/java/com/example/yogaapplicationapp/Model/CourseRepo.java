package com.example.yogaapplicationapp.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.yogaapplicationapp.ViewModel.NetWork;

import java.sql.Blob;
import java.util.ArrayList;

public class CourseRepo {
    public static final String TABLE_NAME = "course";
    private Context context;
    private YogaDatabase yogaDatabase;
    private TempRepo tempRepo;
    public CourseRepo(Context context) {
        this.context = context;
        this.yogaDatabase = YogaDatabase.getInstance(context);
        this.tempRepo = new TempRepo(context);
        makeSureTableExists();
    }

    // Create a Course
    public void createCourse(String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek) {
        makeSureTableExists();
        SQLiteDatabase database = yogaDatabase.getWritableDatabase();
        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (name, startTime, duration, capability, pricePerClass, description, image, typeID, daysOfWeek) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(insertSQL);
        statement.bindString(1, name);
        statement.bindString(2, startTime);
        statement.bindLong(3, duration);
        statement.bindLong(4, capability);
        statement.bindDouble(5, pricePerClass);
        statement.bindString(6, description);
        statement.bindBlob(7, image);
        statement.bindLong(8, typeID);
        statement.bindString(9, daysOfWeek);

        // Execute statement
        try {
            statement.executeInsert();
            Toast.makeText(context, "ADD COURSE SUCCESS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ADD COURSE FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }

    // Update a Course
    public void updateCourse(int id, String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek) {
        SQLiteDatabase database = yogaDatabase.getWritableDatabase();
        String updateSQL = "UPDATE " + TABLE_NAME + " SET name = ?, startTime = ?, duration = ?, capability = ?, pricePerClass = ?, description = ?, image = ?, typeID = ?, daysOfWeek = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(updateSQL);
        statement.bindString(1, name);
        statement.bindString(2, startTime);
        statement.bindLong(3, duration);
        statement.bindLong(4, capability);
        statement.bindDouble(5, pricePerClass);
        statement.bindString(6, description);
        statement.bindBlob(7, image);
        statement.bindLong(8, typeID);
        statement.bindString(9, daysOfWeek);
        statement.bindLong(10, id);

        // Execute statement
        try {
            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                Toast.makeText(context, "UPDATE COURSE SUCCESS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No Course updated. Check if ID exists.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "UPDATE COURSE FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }

    // Delete a Course
    public void deleteCourse(int id) {
        SQLiteDatabase database = yogaDatabase.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(deleteSQL);
        statement.bindLong(1, id);

        // Execute statement
        try {
            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                //Hanling Synchronize offline
                if(!NetWork.isConnected(context)) {
                    tempRepo.addSynCourse(id);
                }else{
                    //Delete From Fire base
                    YogaFirebaseDatabase yogaFirebaseDatabase = YogaFirebaseDatabase.getInstance(context);
                    yogaFirebaseDatabase.deleteCourseWithID(String.valueOf(id));
                }
                Toast.makeText(context, "DELETE COURSE SUCCESS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No Course deleted. Check if ID exists.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "DELETE COURSE FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }

    // Get all Courses
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectAllSQL = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectAllSQL, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
                    @SuppressLint("Range") int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                    @SuppressLint("Range") int capability = cursor.getInt(cursor.getColumnIndex("capability"));
                    @SuppressLint("Range") float pricePerClass = cursor.getFloat(cursor.getColumnIndex("pricePerClass"));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex("description"));
                    @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                    @SuppressLint("Range") int typeID = cursor.getInt(cursor.getColumnIndex("typeID"));
                    @SuppressLint("Range") String daysOfWeek = cursor.getString(cursor.getColumnIndex("daysOfWeek"));

                    Course course = new Course(id, name, startTime, duration, capability, pricePerClass, description, image, typeID, daysOfWeek);
                    courses.add(course);
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
        return courses;
    }
    public int countRows() {
        int x  =0;
        ArrayList<Course> courses = new ArrayList<>();
        android.database.sqlite.SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectAllSQL = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectAllSQL, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    x ++;
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

        return x;
    }
    //GET COURSE WITH ID
    public Course getCourseWithID(int id) {
        Course course = null; // Initialize course to null
        SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?"; // Use a parameterized query
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(selectSQL, new String[]{String.valueOf(id)}); // Provide the ID as a parameter
            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range")
                String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range")
                String startTime = cursor.getString(cursor.getColumnIndex("startTime"));
                @SuppressLint("Range")
                int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                @SuppressLint("Range")
                String description = cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range")
                int capability = cursor.getInt(cursor.getColumnIndex("capability"));
                @SuppressLint("Range")
                int pricePerClass = cursor.getInt(cursor.getColumnIndex("pricePerClass"));
                @SuppressLint("Range")
                byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                @SuppressLint("Range")
                int typeID = cursor.getInt(cursor.getColumnIndex("typeID"));
                @SuppressLint("Range")
                String daysOfWeek = cursor.getString(cursor.getColumnIndex("daysOfWeek"));

                // Create a new Course object with the retrieved data
               return new Course(id,name,startTime,duration,capability,pricePerClass,description,image,typeID,daysOfWeek);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return null; // Return the Course object or null if not found
    }


    // Ensure the Tasks table exists, if not create one
    private void makeSureTableExists() {
        if (!yogaDatabase.isTableExists(TABLE_NAME)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "startTime TEXT, " +
                    "duration INTEGER, " +
                    "capability INTEGER, " +
                    "pricePerClass REAL, " +
                    "description TEXT, " +
                    "image BLOB, " +
                    "typeID INTEGER, " +
                    "daysOfWeek TEXT, " +
                    "FOREIGN KEY (typeID) REFERENCES type(id));";
            yogaDatabase.executeQuery(createTableQuery);
            Toast.makeText(context, "CREATE COURSE TABLE SUCCESS", Toast.LENGTH_SHORT).show();
        }
    }
}
