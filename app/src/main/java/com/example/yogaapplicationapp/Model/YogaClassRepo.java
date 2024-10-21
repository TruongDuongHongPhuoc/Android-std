package com.example.yogaapplicationapp.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.yogaapplicationapp.ViewModel.NetWork;

import java.util.ArrayList;

public class YogaClassRepo {
    public static final String TABLE_NAME = "yogaclass";
    private Context context;
    private YogaDatabase yogaDatabase;
    private TempRepo tempRepo;
    public YogaClassRepo(Context context) {
        this.context = context;
        this.yogaDatabase = YogaDatabase.getInstance(context);
        tempRepo = new TempRepo(context);
        makeSureTableExists();
    }
    //CREATE
    public void createYogaClass(String name, String teacher, String comment,byte[] image, String date, int currentCapability, int courseID) {
        makeSureTableExists();
        android.database.sqlite.SQLiteDatabase database = yogaDatabase.getWritableDatabase();
        String insertSQL = "INSERT INTO " + TABLE_NAME +
                " (name, teacher, comment, image, date, currentCapability, courseID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(insertSQL);

        statement.bindString(1,name);
        statement.bindString(2,teacher);
        statement.bindString(3,comment);
        statement.bindBlob(4,image);
        statement.bindString(5,date);
        statement.bindLong(6,currentCapability);
        statement.bindLong(7,courseID);

        // Execute statement
        try {
            statement.executeInsert();
            Toast.makeText(context, "ADD YOGA CLASS SUCCESS", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ADD YOGA CLASS FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }
    //UPDATE
    public void updateYogaClass(int id,String name, String teacher, String comment,byte[] image, String date, int currentCapability, int courseID) {
        Toast.makeText(context, "Run UPDATE", Toast.LENGTH_SHORT).show();
        android.database.sqlite.SQLiteDatabase database = yogaDatabase.getWritableDatabase();

        String updateSQL = "UPDATE " + TABLE_NAME + " SET name = ?, teacher = ?, comment = ?, image = ?, date = ?, currentCapability = ?, courseID = ? WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(updateSQL);

        statement.bindString(1, name);
        statement.bindString(2, teacher);
        statement.bindString(3, comment);
        statement.bindBlob(4, image);
        statement.bindString(5, date);
        statement.bindLong(6, currentCapability);
        statement.bindLong(7, courseID);
        statement.bindLong(8, id);

        // Execute Statement
        try {
            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                Toast.makeText(context, "UPDATE YOGA CLASS SUCCESS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No Yoga Class updated. Check if ID exists.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "UPDATE YOGA CLASS FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }
    // Delete a YOGACLASS
    public void deleteYogaClass(int id) {
        SQLiteDatabase database = yogaDatabase.getWritableDatabase();
        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(deleteSQL);
        statement.bindLong(1, id);

        // Execute statement
        try {
            int rowsAffected = statement.executeUpdateDelete();
            if (rowsAffected > 0) {
                //handling Syn yoga class delete
                if(!NetWork.isConnected(context)){
                tempRepo.addSynClass(id);
                }else {
                    YogaFirebaseDatabase yogaFirebaseDatabase = YogaFirebaseDatabase.getInstance(context);
                    yogaFirebaseDatabase.deleteYogaClassWithID(String.valueOf(id));
                }
                Toast.makeText(context, "DELETE YOGACLASS SUCCESS", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No yogaclass deleted. Check if ID exists.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "DELETE YOGACLASS FAIL", Toast.LENGTH_SHORT).show();
        } finally {
            statement.close();
            database.close();
        }
    }
    //GET ALL
    public ArrayList<YogaClass> getAllYogaClasses() {
        ArrayList<YogaClass> yogaClasses = new ArrayList<>();
        SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectAllSQL = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectAllSQL, null);
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                    @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));
                    @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                    @SuppressLint("Range") int currentCapability = cursor.getInt(cursor.getColumnIndex("currentCapability"));
                    @SuppressLint("Range") int courseID = cursor.getInt(cursor.getColumnIndex("courseID"));

                    // Create a YogaClass object and add it to the list
                    YogaClass yogaClass = new YogaClass(id, name, teacher, comment, image, date, currentCapability, courseID);
                    yogaClasses.add(yogaClass);
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

        return yogaClasses;
    }

    public void deleteYogaClassesByCourseID(int courseID) {
        ArrayList<YogaClass> deleteClasses = getAllYogaClassOfCourseID(courseID);
        for (YogaClass y:deleteClasses
             ) {
            deleteYogaClass(y.id);
        }
    }

    public ArrayList<YogaClass> getAllYogaClassOfCourseID(int courseID){
        ArrayList<YogaClass> yogaClasses = new ArrayList<>();
        SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE courseID = ?";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(selectSQL, new String[]{String.valueOf(courseID)});
            if (cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                    @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));
                    @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                    @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                    @SuppressLint("Range") int currentCapability = cursor.getInt(cursor.getColumnIndex("currentCapability"));

                    // Create a YogaClass object and add it to the list
                    YogaClass yogaClass = new YogaClass(id, name, teacher, comment, image, date, currentCapability, courseID);
                    yogaClasses.add(yogaClass);
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

        return yogaClasses;
    }

    public YogaClass getYogaClassWithID(int id) {
        SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        YogaClass yogaClass = null;
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(selectSQL, new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex("date"));
                @SuppressLint("Range") int currentCapability = cursor.getInt(cursor.getColumnIndex("currentCapability"));
                @SuppressLint("Range") int courseID = cursor.getInt(cursor.getColumnIndex("courseID"));

                // Create a YogaClass object
                yogaClass = new YogaClass(id, name, teacher, comment, image, date, currentCapability, courseID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return yogaClass;
    }



    // Ensure the Tasks table exists, if not create one
    private void makeSureTableExists() {
        if (!yogaDatabase.isTableExists(TABLE_NAME)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "teacher TEXT, " +
                    "comment TEXT, " +
                    "image BLOB, " +
                    "date TEXT, " +
                    "currentCapability INTEGER, " +
                    "courseID INTEGER, " +
                    "FOREIGN KEY (courseID) REFERENCES course(id)" +
                    ");";
            yogaDatabase.executeQuery(createTableQuery);
//            Toast.makeText(context, "CREATE YOGA CLASS TABLE SUCCESS", Toast.LENGTH_SHORT).show();
        }else {
//            Toast.makeText(context,"DATABASE YOGA CLASS ALREADY EXITS",Toast.LENGTH_SHORT).show();
        }
    }
    // SEARCH BY TEACHER NAME
    @SuppressLint("Range")
    public ArrayList<YogaClass> searchByTeacherName(String teacherName) {
        ArrayList<YogaClass> result = new ArrayList<>();
        android.database.sqlite.SQLiteDatabase database = yogaDatabase.getReadableDatabase();
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE teacher LIKE ?";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(selectSQL, new String[]{"%" + teacherName + "%"});
            if (cursor.moveToFirst()) {
                do {
                    YogaClass yogaClass = new YogaClass();
                    yogaClass.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    yogaClass.setName(cursor.getString(cursor.getColumnIndex("name")));
                    yogaClass.setTeacher(cursor.getString(cursor.getColumnIndex("teacher")));
                    yogaClass.setComment(cursor.getString(cursor.getColumnIndex("comment")));
                    yogaClass.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                    yogaClass.setDate(cursor.getString(cursor.getColumnIndex("date")));
                    yogaClass.setCurrentCapability(cursor.getInt(cursor.getColumnIndex("currentCapability")));
                    yogaClass.setCourseID(cursor.getInt(cursor.getColumnIndex("courseID")));
                    result.add(yogaClass);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error searching for yoga classes", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }

        return result;
    }

}
