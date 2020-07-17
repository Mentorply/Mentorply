package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mentorply.models.Program;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailedProgramsActivity extends AppCompatActivity {
    //the Post to Display
    Program program;
    //the view objects
    TextView tvProgramName;
    ImageView ivImage;
    TextView tvDescription;
    //TextView tvCreatedAt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_programs);

        tvProgramName = findViewById(R.id.tvProgramName);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        //tvCreatedAt = findViewById(R.id.tvCreatedAt);

        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));
        tvProgramName.setText(program.getName());
        tvDescription.setText(program.getDescription());
        //tvCreatedAt.setText(program.getKeyCreatedKey());
        if (program.getImage()!=null){
            Glide.with(this).load(program.getImage().getUrl()).into(ivImage);
        }
        /*Date date = program.getCreatedAt();
        SimpleDateFormat DateFor = new SimpleDateFormat("dd MMMM yyyy");
        String stringDate = DateFor.format(date);
        tvCreatedAt.setText(stringDate);
        */

        //set the information



    }
}