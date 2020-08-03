package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mentorply.models.Program;

import org.parceler.Parcels;

public class DetailedProgramsActivity extends AppCompatActivity {
    //the Program to Display
    Program program;
    //the view objects
    TextView tvProgramName;
    ImageView ivImage;
    TextView tvDescription;
    Button btnPairings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_programs);

        tvProgramName = findViewById(R.id.tvProgramName);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvUserDescription);
        btnPairings = findViewById(R.id.btnPairings);

        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));
        tvProgramName.setText(program.getName());
        tvDescription.setText(program.getDescription());
        if (program.getImage() != null) {
            Glide.with(this).load(program.getImage().getUrl()).into(ivImage);
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
}