package com.example.yogaapplicationapp.ViewModel;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.TempRepo;
import com.example.yogaapplicationapp.Model.Type;
import com.example.yogaapplicationapp.Model.TypeRepo;
import com.example.yogaapplicationapp.Model.YogaClass;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.Model.YogaFirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class synchronizeData {
    private Context context;
    List<Course> courses;
    List<YogaClass> yogaClasses;
    List<Type> types;
    public synchronizeData( Context context){
        CourseRepo courseRepo =  new CourseRepo(context);
        YogaClassRepo yogaClassRepo = new YogaClassRepo(context);
        TypeRepo typeRepo = new TypeRepo(context);
        courses = courseRepo.getAllCourses();
        yogaClasses = yogaClassRepo.getAllYogaClasses();
        types = typeRepo.getAllType();
        this.context = context;
    }
    public void Synchronize() {
        if (NetWork.isConnected(context)) {
            // syn course
            for (Course c : courses
            ) {
                pushCourse(c);
            }
            // syn class
            for (YogaClass y : yogaClasses
            ) {
                pushClass(y);
            }
            for(Type t : types){
                pushType(t);
            }
        }else{
            Toast.makeText(context, "please connect to internet first", Toast.LENGTH_SHORT).show();
        }
    }
    public void synDeleteCourses(){
        TempRepo tempRepo = new TempRepo(context);
        ArrayList<Integer> courseIDS = tempRepo.getAllSynCourse();
        if(!courseIDS.isEmpty()){
        for (Integer i: courseIDS
             ) {
            deleteCourseID(String.valueOf(i));
        }
        tempRepo.clearCourseTemp();
        }
    }
    public void synDeleteYogaClasses(){
        TempRepo tempRepo = new TempRepo(context);
        ArrayList<Integer> yogaClassIDS = tempRepo.getAllSynClass();
        if(!yogaClassIDS.isEmpty()){
            for (Integer i: yogaClassIDS
                 ) {
                deleteYogaClassID(String.valueOf(i));
            }
        }
        tempRepo.clearYogaClassTemp();
    }

    private void pushCourse(Course c){
        DatabaseReference courseRef = YogaFirebaseDatabase.getInstance(context).db.getReference(CourseRepo.TABLE_NAME).child(String.valueOf(c.getId()));
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", c.getName());
        updates.put("startTime", c.getStartTime());
        updates.put("duration", c.getDuration());
        updates.put("capability", c.getCapability());
        updates.put("pricePerClass", c.getPricePerClass());
        updates.put("description", c.getDescription());
        updates.put("image", c.getImage() != null ? convertToBase64String(c.getImage()) : null); // Convert byte[] to Base64 String
//        updates.put("image", c.getImage() != null ? Arrays.toString(c.getImage()) : null);
        updates.put("typeID", c.getTypeID());
        updates.put("daysOfWeek", c.getDaysOfWeek());

        courseRef.updateChildren(updates);    }

    private void pushClass(YogaClass y){
        DatabaseReference classref = YogaFirebaseDatabase.getInstance(context).db.getReference(YogaClassRepo.TABLE_NAME).child(String.valueOf(y.getId()));
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", y.getName());
        updates.put("teacher", y.getTeacher());
        updates.put("comment", y.getComment());
//        updates.put("image", y.getImage() != null ? convertToBase64String(y.getImage()) : null);
        updates.put("date", y.getDate());
        updates.put("current_capability", 0);
        updates.put("courseID", y.getCourseID());

        classref.updateChildren(updates);
    }

    private void pushType(Type t) {
        DatabaseReference typeRef = YogaFirebaseDatabase.getInstance(context).db.getReference(TypeRepo.TABLE_NAME).child(String.valueOf(t.getId()));
        Map<String, Object> updates = new HashMap<>();
        updates.put("typeName", t.getTypeName());

        typeRef.updateChildren(updates);
    }


    private void deleteCourseID(String id){
            YogaFirebaseDatabase yogaFirebaseDatabase = YogaFirebaseDatabase.getInstance(context);
            yogaFirebaseDatabase.deleteCourseWithID(id);
    }
    private void deleteYogaClassID(String id){
            YogaFirebaseDatabase yogaFirebaseDatabase = YogaFirebaseDatabase.getInstance(context);
            yogaFirebaseDatabase.deleteYogaClassWithID(id);
    }
    private List<Byte> convertToList(byte[] bytes) {
        List<Byte> byteList = new ArrayList<>();
        for (byte b : bytes) {
            byteList.add(b);
        }
        return byteList;
    }
    private String convertToBase64String(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
}
