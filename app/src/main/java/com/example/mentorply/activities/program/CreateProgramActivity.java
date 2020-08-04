package com.example.mentorply.activities.program;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentorply.R;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Program;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class CreateProgramActivity extends AppCompatActivity {

    public static final String TAG = "CreateProgramActivity";
    EditText etProgramName;
    EditText etDescription;
    Button btnUploadImage;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_program);

        etProgramName = findViewById(R.id.etProgramName);
        etDescription = findViewById(R.id.etDescription);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnConfirm = findViewById(R.id.btnConfirm);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String programName = etProgramName.getText().toString();
                if (programName.isEmpty()){
                    Toast.makeText(CreateProgramActivity.this, "Program Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String description = etDescription.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(CreateProgramActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                createProgram(programName, description, currentUser);
                //program.saveInBackground();
            }
        });
    }

    private void createProgram(String programName, String description, ParseUser currentUser) {
        final Program program = new Program();
        program.setName(programName);
        program.setDescription(description);
        final Membership membership = new Membership();
        membership.setRole("director");
        membership.setProgram(program);
        membership.setParticipant(currentUser);
        membership.saveInBackground();
        program.saveInBackground();
        // program.setDirector(ParseUser.getCurrentUser());
        program.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Error while saving ", e);
                    Toast.makeText(CreateProgramActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "Program creation was successful!!");
                etProgramName.setText("");
                etDescription.setText("");
                program.addMembership(membership);
                program.saveInBackground();
                ParseUser.getCurrentUser().add("membership", membership);
                ParseUser.getCurrentUser().saveInBackground();


            }
        });

    }
}