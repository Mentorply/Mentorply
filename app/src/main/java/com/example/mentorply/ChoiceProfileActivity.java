package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import java.util.ArrayList;
import java.util.List;

public class ChoiceProfileActivity extends AppCompatActivity {

    public static final String TAG = "ChoiceProfileActivity";

    ParseUser user;
    Program program;

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
        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));

        tvName.setText(user.getString("username"));
        tvDescription.setText(user.getString("description"));
        if (user.getParseFile("profilePicture")!=null){
            Glide.with(this).load(user.getParseFile("profilePicture").getUrl()).into(ivProfileImage);
        }

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair pair = new Pair();
                if (isCurrentUserMentor()==false){
                    pair.setMentee(ParseUser.getCurrentUser());
                    pair.setMentor(user);
                }
                else{
                    pair.setMentee(user);
                    pair.setMentor(ParseUser.getCurrentUser());
                }
                pair.setStatus("pending");
                pair.saveInBackground();
                program.addPair(pair);
            }
        });
    }

    protected boolean isCurrentUserMentor() {
        final ParseQuery<Membership> query = ParseQuery.getQuery(Membership.class);
        //final String[] role = {""};
        final boolean[] isMentor = {true};
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //programsQuery.include(Program.KEY_NAME);
        query.whereEqualTo("program", program);
        query.whereEqualTo("participant", ParseUser.getCurrentUser());
        query.getFirstInBackground(new GetCallback<Membership>() {
            public void done(Membership membership, ParseException e) {
                if (membership == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    if (membership.getRole().equals("mentee"))
                        isMentor[0] = false;
                }
            }

        });
        return isMentor[0];
    }
}