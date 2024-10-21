package com.example.yogaapplicationapp.View;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.YogaClass;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.R;
import com.example.yogaapplicationapp.ViewModel.ImageHandler;
import com.example.yogaapplicationapp.ViewModel.synchronizeData;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateYogaClass extends AppCompatActivity {
    //variable
    public static final int PICK_IMAGE = 2;
    //UI Fragment
    EditText className, classDescription;
    EditText teacherName,classDate;
    Spinner courseBelong;
    ImageView classImage;
    ArrayList<Course> courses;
    int courseIDSelected;
    Button createButton;
    int yogaclassID;
    TextView errorText;

    //repo
    YogaClassRepo yogaRepo = new YogaClassRepo(this);
    CourseRepo courseRepo = new CourseRepo(this);
    synchronizeData syn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_yoga_class);
        //Mapping
        errorText = findViewById(R.id.tv_createYogaError);
        errorText.setVisibility(View.GONE);

        className = findViewById(R.id.classNameInput);
        teacherName = findViewById(R.id.classTeacherNameInput);
        classDate = findViewById(R.id.classDateInput);
        courseBelong = findViewById(R.id.classCourseBelongInput);
        classImage = findViewById(R.id.classImageInput);
        classDescription = findViewById(R.id.classDescriptionInput);
        createButton = findViewById(R.id.classSaveButtonInput);
        //Load courses to Spinner
        courses = loadCourseIntoSpinner();
        //Synchronize initial
        syn = new synchronizeData(this);
        // check if is udpate
        Intent intent = getIntent();
        yogaclassID = intent.getIntExtra("classID", -1);
        if(yogaclassID != -1){
            className.setText("update: "+yogaRepo.getYogaClassWithID(yogaclassID).getName());
        }
        //On Click event
        classImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        classDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        courseBelong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Course c = courses.get(i);
                courseIDSelected = c.getId();
                Toast.makeText(CreateYogaClass.this,"SELECTED Course ID"+ courseIDSelected,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isError()) {
                    showYogaClassDetailDialogue();
                }else{
                    errorText.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void showYogaClassDetailDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("yoga class Details");
        // add image to dialogue
        byte[] image = ImageHandler.imageViewToByte(classImage);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        // Scale image to 480x480
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(scaledBitmap);
        builder.setView(imageView);
        String message = "class name: " + className.getText()+ "\n" +
                "teacher name: " + teacherName.getText() + "\n"+
                "date: " + classDate.getText() + "\n" +
                "course: " + courseBelong.getSelectedItem()  + "\n"+
                "comment: " + classDescription.getText() + "\n";
        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // yes button
                if(yogaclassID != -1){
                    updateYogaClass(yogaclassID);
                    syn.Synchronize();
                    returnToActivity();
                }else {
                    createYogaClass();
                    syn.Synchronize();
                    returnToActivity();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void createYogaClass(){
        String yogaName = className.getText().toString();
        String teachName = teacherName.getText().toString();
        String descriptoin = classDescription.getText().toString();
        byte[] image = ImageHandler.imageViewToByte(classImage);
        String date = classDate.getText().toString();
        String selectedCouseName = (String) courseBelong.getSelectedItem();
        int courseID = GetIDofCourseWithName(selectedCouseName);
        yogaRepo.createYogaClass(yogaName,teachName,descriptoin,image,date,0,courseID);
        Toast.makeText(CreateYogaClass.this,"Create Yoga class success",Toast.LENGTH_SHORT).show();
    }
    private void updateYogaClass(int id){
        String yogaName = className.getText().toString();
        String teachName = teacherName.getText().toString();
        String descriptoin = classDescription.getText().toString();
        byte[] image = ImageHandler.imageViewToByte(classImage);
        String date = classDate.getText().toString();
        String selectedCouseName = (String) courseBelong.getSelectedItem();
        int courseID = GetIDofCourseWithName(selectedCouseName);
        yogaRepo.updateYogaClass(id,yogaName,teachName,descriptoin,image,date,0,courseID);
        Toast.makeText(CreateYogaClass.this,"Create Yoga class success",Toast.LENGTH_SHORT).show();
    }
    private ArrayList<Course> loadCourseIntoSpinner() {
        ArrayList<Course> courses = courseRepo.getAllCourses();
        ArrayList<String> typeNames = new ArrayList<>();
        for (Course course : courses) {
            typeNames.add(course.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseBelong.setAdapter(adapter);
        return courses;
    }
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            classImage.setImageURI(selectedImage);
        }
    }
    public int GetIDofCourseWithName(String name){
        ArrayList<Course> courses = courseRepo.getAllCourses();
        for (Course c: courses
             ) {
            if(c.getName().equals(name)){
                return c.getId();
            }
        }
        return -1;
    }
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        String dayOfWeek =  getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
                        Course c = courseRepo.getCourseWithID(courseIDSelected);
                        if(c.getDaysOfWeek().contains(dayOfWeek)){
                            Toast.makeText(CreateYogaClass.this, "YOUR INPUT IS VALID", Toast.LENGTH_SHORT).show();
                            int Date = calendar.get(Calendar.DAY_OF_MONTH);
                            int Month = calendar.get(Calendar.MONTH);
                            int Year = calendar.get(Calendar.YEAR);
                            classDate.setText("Date: " +String.valueOf(Date) + "/"+ Month + "/" + Year);
                        }else{
                            Toast.makeText(CreateYogaClass.this, "YOUR INPUT IS NOT VALID", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private String getDayOfWeek(int day) {
        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
            default:
                return "";
        }
    }
    private void returnToActivity(){
        Intent i = new Intent(CreateYogaClass.this, YogaClassActivity.class);
        startActivity(i);
    }
    private Boolean isError(){
        boolean check = false;
        errorText.setText("");

        if (className.getText() == null || className.getText().toString().trim().isEmpty()) {
            errorText.append("Class name cannot be null or empty, ");
            check = true;
        }
        if (teacherName.getText() == null || teacherName.getText().toString().trim().isEmpty()) {
            errorText.append("Teacher name cannot be null or empty, ");
            check = true;
        }
        if (classDate.getText() == null || classDate.getText().toString().trim().isEmpty()) {
            errorText.append("Class date cannot be null or empty, ");
            check = true;
        }
        if (classImage.getDrawable() == null) {
            errorText.append("Class image cannot be null, ");
            check = true;
        }
        if (classDescription.getText() == null || classDescription.getText().toString().trim().isEmpty()) {
            errorText.append("Class description cannot be null or empty, ");
            check = true;
        }

        return check;
    }

}