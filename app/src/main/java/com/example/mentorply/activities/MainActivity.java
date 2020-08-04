package com.example.mentorply.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mentorply.R;
import com.example.mentorply.fragments.ConnectionsFragment;
import com.example.mentorply.fragments.ProgramsFragment;
import com.example.mentorply.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//this will be the class where we have the bottom navigation view and where we can get access to the main aspects of the project
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_programs:
                        fragment = new ProgramsFragment();
                        break;
                    case R.id.action_pairs:
                        fragment = new ConnectionsFragment();
                        break;
                    case R.id.action_settings:
                    default:
                        fragment = new SettingsFragment();
                        break;
                }
               fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
     bottomNavigationView.setSelectedItemId(R.id.action_programs);
    }

}