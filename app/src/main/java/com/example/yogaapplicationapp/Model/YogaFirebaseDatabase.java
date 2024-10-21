package com.example.yogaapplicationapp.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YogaFirebaseDatabase {
    private static final String DATABASE_LINK = "https://yogaapplicationapp-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private static YogaFirebaseDatabase instance;
    public FirebaseDatabase db = null;
    public DatabaseReference ref = null;
    private Context context;
    public static YogaFirebaseDatabase getInstance(Context context){
        if (instance == null) {
            instance = new YogaFirebaseDatabase();
            instance.db = FirebaseDatabase.getInstance(DATABASE_LINK);
            instance.ref = instance.db.getReference();
            instance.context = context;
        }
        return instance;
    }
    public void deleteCourseWithID(String id){
        DatabaseReference courseRef = ref.child(CourseRepo.TABLE_NAME).child(id);
        courseRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Course deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete course", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void deleteYogaClassWithID(String id){
        DatabaseReference classRef = ref.child(YogaClassRepo.TABLE_NAME).child(id);
        classRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Yoga class deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete yoga class", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
