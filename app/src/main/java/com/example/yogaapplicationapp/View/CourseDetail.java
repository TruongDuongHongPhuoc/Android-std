package com.example.yogaapplicationapp.View;

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
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.R;

public class CourseDetail extends AppCompatActivity {
    CourseRepo courseRepo = new CourseRepo(this);
    YogaClassRepo yogaClassRepo = new YogaClassRepo(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Mapping
        ImageView courseImage = findViewById(R.id.courseDetailImage);
        TextView courseName = findViewById(R.id.courseDetailName);
        TextView courseStartTime = findViewById(R.id.courseDetailStartTime);
        TextView courseDuration = findViewById(R.id.courseDetailDuration);
        TextView courseCapability = findViewById(R.id.courseDetailCapability);
        TextView coursePrice = findViewById(R.id.courseDetailPrice);
        TextView courseDescription = findViewById(R.id.courseDetailDescription);
        TextView courseDaysOfWeek = findViewById(R.id.courseDetailDaysOfWeek);
        Button updateCourse = findViewById(R.id.btnUpdateCourse);
        Button deleteCourse = findViewById(R.id.btnDeleteCourse);

        //get intent ID
        Bundle extra = getIntent().getExtras();
        int courseID = extra.getInt("courseID");
        Toast.makeText(this,"Course ID: "+ courseID,Toast.LENGTH_SHORT).show();
        Course c =courseRepo.getCourseWithID(courseID);

        // Set data to views
        if(c != null){
            courseName.setText(c.getName());
            courseStartTime.setText("Start Time: " + c.getStartTime());
            courseDuration.setText("Duration: " + c.getDuration() + " hours");
            courseCapability.setText("Capability: " + c.getCapability() + " students");
            coursePrice.setText("Price per Class: " + c.getPricePerClass() + "$");
            courseDescription.setText("Description: " + c.getDescription());
            courseDaysOfWeek.setText("Days of the Week: " + c.getDaysOfWeek());

            // Assuming you have a method to convert byte[] to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(c.getImage(), 0, c.getImage().length);
            courseImage.setImageBitmap(bitmap);
        }else {
            courseName.setText("Name not found");
        }
        // onlick event
        updateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CourseDetail.this,CreateCourse.class);
                i.putExtra("courseID",courseID);
                startActivity(i);
            }
        });
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CourseDetail.this, MainActivity.class);
                courseRepo.deleteCourse(courseID);
                yogaClassRepo.deleteYogaClassesByCourseID(courseID);
                startActivity(i);
            }
        });
    }
}