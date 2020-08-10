package com.example.mentorply.activities.program;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.mentorply.activities.pairing.PairingActivity;
import com.example.mentorply.R;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Program;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class DetailedProgramsActivity extends AppCompatActivity {
    //the Program to Display
    Program program;
    //the view objects
    TextView tvProgramName;
    ImageView ivImage;
    TextView tvDescription;
    TextView tvProgramCode;
    Button btnPairings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_programs);

        tvProgramName = findViewById(R.id.tvProgramName);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvUserDescription);
        tvProgramCode = findViewById(R.id.tvProgramCode);


        btnPairings = findViewById(R.id.btnPairings);

        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));
        tvProgramName.setText(program.getName());
        tvDescription.setText(program.getDescription());
        if (isDirector()){
            tvProgramCode.setText("Program Code: "+ program.getObjectId());
        }


        if (program.getImage() != null) {
            //Glide.with(this).load(program.getImage().getUrl()).circleCrop().into(ivImage);
            Glide
                    .with(this)
                    .load(program.getImage().getUrl())
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE))
                    .centerCrop()
                    .into(ivImage);
        }

        btnPairings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PairingActivity.class);
                Bundle extras = new Bundle();
                i.putExtra(Program.class.getSimpleName(), Parcels.wrap(program));

                startActivity(i);
            }
        });
    }

    private boolean isDirector() {
        final boolean[] director = {false};
        // Define the class we would like to query
        ParseQuery <Membership> query = ParseQuery.getQuery(Membership.class);
    // Define our query conditions
        query.whereEqualTo("program", program);
        query.whereEqualTo("participant", ParseUser.getCurrentUser());
        query.whereEqualTo("role", "director");
    // Execute the find asynchronously
        query.getFirstInBackground(new GetCallback<Membership>() {
            public void done(Membership object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                    tvProgramCode.setVisibility(View.GONE);
                } else {
                    Log.d("score", "Retrieved the object.");
                    director[0] = true;
                }
            }
        });
        return director[0];
    }

}