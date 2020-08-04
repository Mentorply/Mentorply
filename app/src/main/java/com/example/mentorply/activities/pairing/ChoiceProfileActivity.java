package com.example.mentorply.activities.pairing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mentorply.R;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Pair;
import com.example.mentorply.models.Program;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class ChoiceProfileActivity extends AppCompatActivity {

    public static final String TAG = "ChoiceProfileActivity";

    ParseUser user;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvDescription;
    Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_choice);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvDescription = findViewById(R.id.tvUserDescription);
        btnRequest = findViewById(R.id.btnRequest);

        user = Parcels.unwrap(getIntent().getParcelableExtra(ParseUser.class.getSimpleName()));
        tvName.setText(user.getString("username"));
        tvDescription.setText(user.getString("description"));
        if (user.getParseFile("profilePicture") != null) {
            Glide.with(this).load(user.getParseFile("profilePicture").getUrl()).into(ivProfileImage);
        }

        if (!isPair())
            btnRequest.setEnabled(true);
        else
            btnRequest.setEnabled(false);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair pair = new Pair();
                pair.setStatus("pending");
                pair.setFromUser(ParseUser.getCurrentUser());
                pair.setToUser(user);
                pair.saveInBackground();
                btnRequest.setEnabled(false);
                //program.addPair(pair);
            }
        });
    }

    private boolean isPair() {
        final boolean[] connected = {false};
        // Define the class we would like to query
        ParseQuery<Pair> query1 = ParseQuery.getQuery(Pair.class);
        // Define our query conditions
        query1.whereEqualTo("fromUser", ParseUser.getCurrentUser().getObjectId());
        query1.whereEqualTo("toUser", user.getObjectId());
        query1.getFirstInBackground(new GetCallback<Pair>() {
            public void done(Pair pair, ParseException e) {
                if (pair != null) {
                    connected[0] = true;
                }
//                else {
//
//                }
            }

        });

        ParseQuery<Pair> query2 = ParseQuery.getQuery(Pair.class);
        query2.whereEqualTo("toUser", ParseUser.getCurrentUser().getObjectId());
        query2.whereEqualTo("fromUser", user.getObjectId());
        query2.getFirstInBackground(new GetCallback<Pair>() {
            public void done(Pair pair, ParseException e) {
                if (pair != null) {
                    connected[0] = true;
                }
//                else {
//
//                }
            }
        });

//
//
//        // Execute the find asynchronously
//        ParseQuery<Membership> mainQuery = ParseQuery.or(queries);
//        mainQuery.findInBackground(new FindCallback<Membership>() {
//            public void done(List<Membership> memberships, ParseException e) {
//                if (e == null) { connected[0] = true;
//                    // Access the array of results here
//                } else {
//                    Log.d("item", "Error: " + e.getMessage());
//                }
//            }
//        });
        return connected[0];
    }
}