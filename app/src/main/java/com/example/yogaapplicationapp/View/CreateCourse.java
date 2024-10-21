package com.example.yogaapplicationapp.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.Type;
import com.example.yogaapplicationapp.Model.TypeRepo;
import com.example.yogaapplicationapp.R;
import com.example.yogaapplicationapp.ViewModel.ImageHandler;
import com.example.yogaapplicationapp.ViewModel.synchronizeData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
// CREATE AND UPDATE AS WELL
public class CreateCourse extends AppCompatActivity {
    //variable
    int totalDurationInMinutes;
    String selectedDaysString;
    int courseID;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    //UI fragment
    private TextView errorText;
    private EditText editTextCourseName;
    private EditText editTextStartTime;
    private EditText editTextDuration;
    private EditText editTextCapability;
    private EditText editTextPricePerClass;
    private EditText editTextDescription;
    private ImageView imageViewCourseImage;
    private Spinner spinnerType;
    private EditText editTextDaysOfWeek;
    private Button buttonCreateCourse;
    private Button buttonSelectImage;
    //Repository
    private CourseRepo courseRepo;
    private TypeRepo typeRepo;
    private synchronizeData syn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        //Repo
        typeRepo = new TypeRepo(CreateCourse.this);
        courseRepo = new CourseRepo(this);
        syn = new synchronizeData(this);
        //Mapping
        errorText = findViewById(R.id.tv_CreateCourseError);
        errorText.setVisibility(View.GONE);

        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextCapability = findViewById(R.id.editTextCapability);
        editTextPricePerClass = findViewById(R.id.editTextPricePerClass);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageViewCourseImage = findViewById(R.id.imageViewCourseImage);
        spinnerType = findViewById(R.id.spinnerType);
        //Load types to spinner
        loadTypesIntoSpinner();
        //continue mapping
        editTextDaysOfWeek = findViewById(R.id.editTextDaysOfWeek);
        buttonCreateCourse = findViewById(R.id.buttonCreateCourse);
//        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        //Checking if create or update
        Intent intent = getIntent();
        courseID = intent.getIntExtra("courseID", -1);
        if (courseID != -1) {
//            Task task = db.getTask(taskId);
//            editTextTaskName.setText(task.getName());
//            checkBoxCompleted.setChecked(task.isCompleted());
//            expiryDate = task.getExpiryDate();
            Course c = courseRepo.getCourseWithID(courseID);
            buttonCreateCourse.setText("UPDATE COURSE: " + courseID);
            editTextCourseName.setText(c.getName());
        }

        //On click events
        imageViewCourseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        buttonCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isError()) {
                    DisplayCourseDetailToDialogue();
                }else {
                    errorText.setVisibility(View.VISIBLE);
                }
                }
        });
        editTextDaysOfWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDaysDialog();
            }
        });
        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPickTime(CreateCourse.this);
            }
        });
        editTextDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDurationPickerDialog();
            }
        });
    }
    // take picture by camera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    // take the picture result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageViewCourseImage.setImageBitmap(imageBitmap);
        }
    }
    //Dispaly all course information in dialogue
    private void DisplayCourseDetailToDialogue() {
        String name = editTextCourseName.getText().toString();
        String startTime = editTextStartTime.getText().toString();
        int capability = Integer.parseInt(editTextCapability.getText().toString());
        float pricePerClass = Float.parseFloat(editTextPricePerClass.getText().toString());
        String description = editTextDescription.getText().toString();
        byte[] image = imageViewToByte(imageViewCourseImage);
        String selectedTypeName = (String) spinnerType.getSelectedItem();
        String daysOfWeek = editTextDaysOfWeek.getText().toString();
        int typeID = getTypeIDByName(selectedTypeName);
        showCourseDetailsDialog(name,startTime,totalDurationInMinutes,capability,pricePerClass,description,image,typeID,daysOfWeek);
        totalDurationInMinutes = -1;
        selectedDaysString = "";
    }
    // turn image to byte[] type
    private byte[] imageViewToByte(ImageView imageView) {
        return ImageHandler.imageViewToByte(imageView);
    }

    //after create or edit show information agains
    private void showCourseDetailsDialog(String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(courseID != -1) {
                createCourse(builder, name, startTime, duration, capability, pricePerClass, description, image, typeID, daysOfWeek);
        }else {
            updateCourse(builder,name,startTime,duration,capability,pricePerClass,description,image,typeID,daysOfWeek);
        }
    }
    private void createCourse(AlertDialog.Builder builder, String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek){
        builder.setTitle("Course Details");
        String message = "Name: " + name + "\n" +
                "Start Time: " + startTime + "\n" +
                "Duration: " + duration + " minutes\n" +
                "Capability: " + capability + "\n" +
                "Price Per Class: " + pricePerClass + "\n" +
                "Description: " + description + "\n" +
                "Type ID: " + typeID + "\n" +
                "Days of the Week: " + daysOfWeek;

        builder.setMessage(message);

        // Add the image if available
        if (image != null && image.length > 0) {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            builder.setView(imageView);
        }

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                courseRepo.createCourse(name, startTime, duration, capability, pricePerClass, description, image, typeID, daysOfWeek);
                syn.Synchronize();
                Intent intent = new Intent(CreateCourse.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updateCourse(AlertDialog.Builder builder, String name, String startTime, int duration, int capability, float pricePerClass, String description, byte[] image, int typeID, String daysOfWeek){
        builder.setTitle("Updated course detail");
        String message = "Name: " + name + "\n" +
                "Start Time: " + startTime + "\n" +
                "Duration: " + duration + " minutes\n" +
                "Capability: " + capability + "\n" +
                "Price Per Class: " + pricePerClass + "\n" +
                "Description: " + description + "\n" +
                "Type ID: " + typeID + "\n" +
                "Days of the Week: " + daysOfWeek;
        builder.setMessage(message);

        // Add the image if available
        if (image != null && image.length > 0) {
            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
            builder.setView(imageView);
        }

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                courseRepo.updateCourse(courseID,name, startTime, duration, capability, pricePerClass, description, image, typeID, daysOfWeek);
                syn.Synchronize();
                Intent intent = new Intent(CreateCourse.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    //get tpye name return id of the type
    private int getTypeIDByName(String typeName) {
        ArrayList<Type> types = typeRepo.getAllType();
        for (Type type : types) {
            if (type.getTypeName().equals(typeName)) {
                return type.getId();
            }
        }
        return -1; // Return an invalid ID if not found
    }
    // load types into spinner
    private void loadTypesIntoSpinner() {
        ArrayList<Type> types = typeRepo.getAllType();
        ArrayList<String> typeNames = new ArrayList<>();
        for (Type type : types) {
            typeNames.add(type.getTypeName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }
    // show the custome days dialogue choose monday or tuesday or ....
    private void showCustomDaysDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pickday_dialogue, null);

        // Initialize checkboxes
        CheckBox monday = dialogView.findViewById(R.id.checkbox_monday);
        CheckBox tuesday = dialogView.findViewById(R.id.checkbox_tuesday);
        CheckBox wed = dialogView.findViewById(R.id.checkbox_wednesday);
        CheckBox thurs = dialogView.findViewById(R.id.checkbox_thursday);
        CheckBox fri = dialogView.findViewById(R.id.checkbox_friday);
        CheckBox sat = dialogView.findViewById(R.id.checkbox_saturday);
        // Repeat for other days
        CheckBox sunday = dialogView.findViewById(R.id.checkbox_sunday);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Days")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<String> selectedDays = new ArrayList<>();
                        if (monday.isChecked()) selectedDays.add("Monday");
                        if (tuesday.isChecked()) selectedDays.add("Tuesday");
                        if (wed.isChecked()) selectedDays.add("Wednesday");
                        if (thurs.isChecked()) selectedDays.add("Thursday");
                        if (fri.isChecked()) selectedDays.add("Friday");
                        if (sat.isChecked()) selectedDays.add("Saturday");
                        if (sunday.isChecked()) selectedDays.add("Sunday");
                        selectedDaysString = TextUtils.join(", ", selectedDays);
                        editTextDaysOfWeek.setText(selectedDaysString);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    // show picktime calendar
    public void showPickTime(Context context){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minuteOfHour) -> editTextStartTime.setText(hourOfDay + ":" + minuteOfHour+ "HOUR"),
                hour, minute, true);
        timePickerDialog.show();
    }
    // show duration picker to pick duration for the course
    private void showDurationPickerDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.timedurationpicker);
        dialog.setTitle("Pick Duration");

        final NumberPicker hourPicker = dialog.findViewById(R.id.hourPicker);
        final NumberPicker minutePicker = dialog.findViewById(R.id.minutePicker);
        Button setButton = dialog.findViewById(R.id.btn_setTimeRange);

        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setMinValue(0);

        setButton.setOnClickListener(v -> {
            int hours = hourPicker.getValue();
            int minutes = minutePicker.getValue();
            totalDurationInMinutes = (hours*60) + minutes;
            editTextDuration.setText(hours + " hours " + minutes + " minutes");
            dialog.dismiss();
        });
        dialog.show();
    }
    private Boolean isError(){
        Boolean check = false;
        errorText.setText("");
        if (editTextCourseName.getText() == null || editTextCourseName.getText().toString().trim().isEmpty()) {
            errorText.append("Course name cannot be null or empty, ");
            check = true;
        }
        if (editTextStartTime.getText() == null || editTextStartTime.getText().toString().trim().isEmpty()) {
            errorText.append("Start time cannot be null or empty, ");
            check = true;
        }
        if (editTextDuration.getText() == null || editTextDuration.getText().toString().trim().isEmpty()) {
            errorText.append("Duration cannot be null or empty, ");
            check = true;
        }
        if (editTextCapability.getText() == null || editTextCapability.getText().toString().trim().isEmpty()) {
            errorText.append("Capability cannot be null or empty, ");
            check = true;
        }
        if (editTextPricePerClass.getText() == null || editTextPricePerClass.getText().toString().trim().isEmpty()) {
            errorText.append("Price per class cannot be null or empty, ");
            check = true;
        }
        if (editTextDescription.getText() == null || editTextDescription.getText().toString().trim().isEmpty()) {
            errorText.append("Description cannot be null or empty, ");
            check = true;
        }
        if (editTextDaysOfWeek.getText() == null || editTextDaysOfWeek.getText().toString().trim().isEmpty()) {
            errorText.append("Days of the week cannot be null or empty, ");
            check = true;
        }
        return check;
    }
}