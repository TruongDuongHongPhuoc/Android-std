package com.example.yogaapplicationapp.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.yogaapplicationapp.R;
import com.example.yogaapplicationapp.View.MainActivity;
import com.google.android.material.navigation.NavigationView;

public class SimpleDrawer {
    public static void implementDrawer(Toolbar toolbar, DrawerLayout drawerLayout, ActionBarDrawerToggle drawerToggle, NavigationView navigationView, Context context, Activity activity){
        drawerToggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int i = item.getItemId();
                // Select Course manage in navigation
                if (i == R.id.nav_courseManage) {
                    Toast.makeText(context, "Course selected", Toast.LENGTH_SHORT).show();
                    Intent ix = new Intent(context, MainActivity.class);
                    context.startActivity(ix);
                }
                return false;
            }
        });
    }
}
