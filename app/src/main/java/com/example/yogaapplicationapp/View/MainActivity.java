package com.example.yogaapplicationapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.R;
import com.example.yogaapplicationapp.ViewModel.NetWorkMonitor;
import com.example.yogaapplicationapp.ViewModel.SimpleDrawer;
import com.example.yogaapplicationapp.ViewModel.synchronizeData;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    CourseRepo courseRepo;
    YogaClassRepo yogaClassRepo;
    ArrayList<Course> courses = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Button btnAddCourse;
    Button startClass;
    Button btn_syn;
    //Variable for Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    synchronizeData syn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        //set default theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        // preparing
        courseRepo = new CourseRepo(MainActivity.this);
        courses = courseRepo.getAllCourses();
        yogaClassRepo = new YogaClassRepo(MainActivity.this);
        syn = new synchronizeData( MainActivity.this);

        //Handling Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);

        SimpleDrawer.implementDrawer(toolbar,drawerLayout,drawerToggle,navigationView,MainActivity.this,this);
        NetWorkMonitor.implementCheckInternet(this,MainActivity.this);


        CourseAdapter courseAdapter = new CourseAdapter(courses,courseRepo,MainActivity.this);
        Toast.makeText(MainActivity.this,String.valueOf(courses.size()),Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(courseAdapter);

        startClass = findViewById(R.id.btnClassMain);
        startClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,YogaClassActivity.class);
                startActivity(i);
            }
        });
        btnAddCourse = findViewById(R.id.btnAddCourse);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,CreateCourse.class);
                startActivity(i);
            }
        });
//        btn_syn = findViewById(R.id.btn_syni);
//        btn_syn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                syn.Synchronize();
//            }
//        });
    }
}