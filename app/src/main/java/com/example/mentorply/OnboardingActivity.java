package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class OnboardingActivity extends AppCompatActivity {

    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

       // ParseUser.logOut();
      //  ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null


        if (ParseUser.getCurrentUser()!=null){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}