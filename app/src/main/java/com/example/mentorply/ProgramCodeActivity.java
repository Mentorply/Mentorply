package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mentorply.models.Affiliation;
import com.example.mentorply.models.Program;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProgramCodeActivity extends AppCompatActivity {

    public static final String TAG = "ProgramCodeActivity";
    EditText etProgramCode;
    ToggleButton btnRole;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_code);

        etProgramCode = findViewById(R.id.etProgramCode);
        btnRole = findViewById(R.id.btnRole);
        btnSubmit = findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String programCode = etProgramCode.getText().toString();
                if (programCode.isEmpty()){
                    Toast.makeText(ProgramCodeActivity.this, "Program Code cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean toggleButtonState = btnRole.isChecked(); // false is mentee and true is mentor
                validateProgram(programCode, toggleButtonState);
            }
        });
    }

    private void validateProgram(String programCode, final boolean toggleButtonState) {
        // Specify which class to query
        ParseQuery<Program> query = ParseQuery.getQuery(Program.class);
        // Define our query conditions
        query.whereEqualTo("objectId", programCode);
        query.getFirstInBackground(new GetCallback<Program>()
        {
            public void done(Program program, ParseException e)
            {
                if (e == null)
                {
                    //object exists
                    Toast.makeText(ProgramCodeActivity.this, "Program Name: "+program.getName(), Toast.LENGTH_SHORT).show();
                    if (!toggleButtonState) { //mentee
                        //if (seeIfUserIsInProgram(ParseUser.getCurrentUser(), program)){
                        Affiliation affiliation = new Affiliation();
                        affiliation.setParticipant(ParseUser.getCurrentUser());
                        affiliation.setProgram(program);
                        affiliation.setRole("mentee");
                        program.addAffiliation(affiliation);
                        //program.removeMentor(ParseUser.getCurrentUser());
                        program.saveInBackground();
                        //}
                    }
                    else{//mentor
                        Affiliation affiliation = new Affiliation();
                        affiliation.setParticipant(ParseUser.getCurrentUser());
                        affiliation.setProgram(program);
                        affiliation.setRole("mentor");
                        program.addAffiliation(affiliation);
                        // if (seeIfUserIsInProgram(ParseUser.getCurrentUser(), program)){
                            //program.addMentor(ParseUser.getCurrentUser());
                            //program.removeMentee(ParseUser.getCurrentUser());
                            program.saveInBackground();
                       //}
                    }
                }
                else
                {
                    if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                    {
                        //object doesn't exist
                        Toast.makeText(ProgramCodeActivity.this, "The program does not exist", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //unknown error, debug
                    }
                }
            }
        });

    }
/*
    private boolean seeIfUserIsInProgram(ParseUser currentUser, Program program) {
        ArrayList<Affiliation> ar =  program.getAffiliations();
        //ar.remove
        programsQuery.findInBackground(new FindCallback<Affiliation>() {
            List <Program> programs = new ArrayList<Program>();
            @Override
            public void done(List<Affiliation> affiliations, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Issue with getting programs", e);
                    return;
                }
                for (Affiliation affiliation: affiliations){
                    Program program = affiliation.getProgram();
                    program.saveInBackground();
                    Log.i(TAG, "Program: "+program.getName()+", Description: "+program.getDescription());
                }//+program.getObjectId()
                allPrograms.addAll(programs);
                adapter.notifyDataSetChanged();
            }
        });
    }
//*/
}