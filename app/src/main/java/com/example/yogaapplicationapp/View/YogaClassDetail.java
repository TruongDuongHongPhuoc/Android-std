package com.example.yogaapplicationapp.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.YogaClass;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.R;
import com.example.yogaapplicationapp.ViewModel.ImageHandler;

public class YogaClassDetail extends AppCompatActivity {

    TextView classNameDetail, teacherName, classDateDetail, classDescriptionDetail, classCapabilityDetail, classCourseBelongDetail;
    ImageView classImage;
    Button btnUpdate, btnDelete;
    YogaClassRepo yogaRepo;
    int classID;
    YogaClass yogaClass;
    CourseRepo courseRepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yoga_class_detail);

        // Mapping
        classNameDetail = findViewById(R.id.classNameDetail);
        teacherName = findViewById(R.id.classTeacherDetail);
        classDateDetail = findViewById(R.id.classDateDetail);
        classDescriptionDetail = findViewById(R.id.classDescriptionDetail);
        classCapabilityDetail = findViewById(R.id.classCapabilityDetail);
        classCourseBelongDetail = findViewById(R.id.classCourseBelongDetail);
        classImage = findViewById(R.id.classImageDetail);
        btnUpdate = findViewById(R.id.btn_updateClassDetail);
        btnDelete = findViewById(R.id.btnDeleteClassDetail);
        // Repo
        yogaRepo = new YogaClassRepo(this);
        courseRepo = new CourseRepo(this);
        // Get Intent and Class ID
        Bundle extra = getIntent().getExtras();
        classID = extra.getInt("classID", -1);
        Toast.makeText(this, "class id: " + classID, Toast.LENGTH_SHORT).show();
        if (classID != -1) {
            yogaClass = yogaRepo.getYogaClassWithID(classID);
        }

        // Set value
        if (yogaClass != null) {
            classNameDetail.setText("Class name: "+yogaClass.getName());
            teacherName.setText("Teacher name: "+yogaClass.getTeacher());
            classDateDetail.setText("Date: "+yogaClass.getDate());
            classDescriptionDetail.setText("Comment: "+yogaClass.getComment());
            classCapabilityDetail.setText("capability" + String.valueOf(yogaClass.getCurrentCapability()));
            classCourseBelongDetail.setText("Course: " + courseRepo.getCourseWithID(yogaClass.getCourseID()).getName());
            // Assuming you have a method to convert byte[] to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(yogaClass.getImage(), 0, yogaClass.getImage().length);
            classImage.setImageBitmap(bitmap);
            //update button
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(YogaClassDetail.this,CreateYogaClass.class);
                    i.putExtra("classID",classID);
                    startActivity(i);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteNotifyDialogue();
                }
            });
        }
    }
    private void showDeleteNotifyDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ARE YOU SURE TO DELETE");

        String message = "class name: " + classNameDetail.getText()+ "\n" +
                "teacher name: " + teacherName.getText() + "\n"+
                "date: " + classDateDetail.getText() + "\n" +
                "course: " + classCourseBelongDetail.getText()  + "\n"+
                "comment: " + classDescriptionDetail.getText() + "\n";
        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent ix = new Intent(YogaClassDetail.this,YogaClassActivity.class);
                startActivity(ix);
                yogaRepo.deleteYogaClass(classID);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}