package com.example.yogaapplicationapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yogaapplicationapp.Model.Course;
import com.example.yogaapplicationapp.Model.CourseRepo;
import com.example.yogaapplicationapp.Model.YogaClass;
import com.example.yogaapplicationapp.Model.YogaClassRepo;
import com.example.yogaapplicationapp.R;
import com.example.yogaapplicationapp.ViewModel.SimpleDrawer;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class YogaClassActivity extends AppCompatActivity {
    // Search fragment
    EditText search;
    Button btn_Search;
    //recycular_classes
    YogaClassRepo yogaClassRepo = new YogaClassRepo(this);
    ArrayList<YogaClass> yogaClasses = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Button btnAddClass;

    //Variable for Drawer
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    //adapter
    ClassAdapter classAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_yoga_class);

        //Mapping search
        search = findViewById(R.id.et_search);
        btn_Search = findViewById(R.id.btn_search);
        //Handling Drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.nav_view);

        SimpleDrawer.implementDrawer(toolbar,drawerLayout,drawerToggle,navigationView,YogaClassActivity.this,this);
        //check search
        Intent intent = getIntent();
        String teacherName = intent.getStringExtra("Search");
        if(teacherName != null){
            yogaClasses = yogaClassRepo.searchByTeacherName(teacherName);
        }else{
            yogaClasses = yogaClassRepo.getAllYogaClasses();
        }

        // others
         classAdapter = new ClassAdapter(yogaClasses,yogaClassRepo,YogaClassActivity.this);
        Toast.makeText(YogaClassActivity.this,String.valueOf(yogaClasses.size()),Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recycular_classes);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(classAdapter);

        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(YogaClassActivity.this, "Search", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(YogaClassActivity.this, YogaClassActivity.class);
                intent.putExtra("Search", search.getText().toString());
                startActivity(intent);
            }
        });

        btnAddClass = findViewById(R.id.btnAddClass);

        btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(YogaClassActivity.this,CreateYogaClass.class);
                startActivity(i);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateView();
//    }

    public void updateView() {
        // Update the adapter's data
        classAdapter.setYogaClasses(yogaClasses);
        // Notify the adapter that the data has changed
        classAdapter.notifyDataSetChanged();
    }

}