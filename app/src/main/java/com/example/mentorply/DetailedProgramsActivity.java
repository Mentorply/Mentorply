package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mentorply.adapters.MentorProfilesAdapter;
import com.example.mentorply.models.Program;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class DetailedProgramsActivity extends AppCompatActivity {
    //the Program to Display
    Program program;
    //the view objects
    TextView tvProgramName;
    ImageView ivImage;
    TextView tvDescription;
    Button btnPairings;
    //TextView tvCreatedAt;

    private RecyclerView rvMentors;
    public MentorProfilesAdapter adapter;
    protected List<ParseUser> allMentors;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_programs);

        tvProgramName = findViewById(R.id.tvProgramName);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvUserDescription);
        btnPairings = findViewById(R.id.btnPairings);
        //tvCreatedAt = findViewById(R.id.tvCreatedAt);

        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));
        tvProgramName.setText(program.getName());
        tvDescription.setText(program.getDescription());
        //tvCreatedAt.setText(program.getKeyCreatedKey());
        if (program.getImage()!=null){
            Glide.with(this).load(program.getImage().getUrl()).into(ivImage);
        }

        btnPairings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PairingActivity.class);
                i.putExtra(Program.class.getSimpleName(), Parcels.wrap(program));
                startActivity(i);
            }
        });
        /*Date date = program.getCreatedAt();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");
        String stringDate = DateFor.format(date);
        tvCreatedAt.setText(stringDate);
        */
       /* rvMentors = findViewById(R.id.rvMentors);
        adapter = new MentorProfilesAdapter(getApplicationContext(), allMentors);
        rvMentors.setAdapter(adapter);
        rvMentors.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        if (program.getMentors().size()!=0)
            allMentors.addAll(program.getMentors());//this keeps causing an error because it's getting a null pointer reference
        adapter.notifyDataSetChanged();

        */
        //set the information



    }

    private void queryMentors() {

    }
}