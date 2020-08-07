package com.example.mentorply.activities.pairing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mentorply.R;
import com.example.mentorply.activities.accounts.LoginActivity;
import com.example.mentorply.models.ChatRoom;
import com.example.mentorply.models.Pair;
import com.example.mentorply.models.User;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class AcceptRequestActivity extends AppCompatActivity {

    public static final String TAG = "AcceptRequestActivity";

    Pair pair;
    ParseUser user;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDescription;
    Button btnAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvUserDescription);
        btnAccept = findViewById(R.id.btnRequest);

        pair = Parcels.unwrap(getIntent().getParcelableExtra(Pair.class.getSimpleName()));
        user = pair.getFromUser();
        tvName.setText(user.getString("username"));
        tvDescription.setText(user.getString("description"));
        if (user.getParseFile("profilePicture") != null) {
            Glide.with(this).load(user.getParseFile("profilePicture").getUrl()).into(ivProfileImage);
        }

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pair.setStatus("accepted");
                pair.saveInBackground();
                ParseUser.getCurrentUser().add("pairs", pair);
                user.add("pairs", pair);
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setPair(pair);
                chatRoom.saveInBackground();
                Intent i = new Intent(AcceptRequestActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}