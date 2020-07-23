package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {

    ParseUser user;

    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvDescription);

        tvName.setText(user.getUsername());
        //tvDescription.setText(user.getDescri);
        user = ParseUser.getCurrentUser();

        tvName.setText(user.getString("username"));
        tvDescription.setText(user.getString("description"));
        if (user.getParseFile("profilePicture")!=null){
            Glide.with(this).load(user.getParseFile("profilePicture").getUrl()).into(ivProfileImage);
        }
    }
}