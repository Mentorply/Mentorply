package com.example.mentorply.activities.program;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mentorply.R;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Program;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class JoinProgramActivity extends AppCompatActivity {

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
                    Toast.makeText(JoinProgramActivity.this, "Program Code cannot be empty", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(JoinProgramActivity.this, "Program Name: "+program.getName(), Toast.LENGTH_SHORT).show();
                    Membership membership = new Membership();
                    membership.setParticipant(ParseUser.getCurrentUser());
                    membership.setProgram(program);
                    if (!toggleButtonState) { //mentee
                        membership.setRole("mentee");
                    }
                    else{//mentor
                        membership.setRole("mentor");
                    }
                    program.addMembership(membership);
                    program.saveInBackground();
                    ParseUser.getCurrentUser().add("membership", membership);
                    ParseUser.getCurrentUser().saveInBackground();

                }
                else
                {
                    if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                    {
                        //object doesn't exist
                        Toast.makeText(JoinProgramActivity.this, "The program does not exist", Toast.LENGTH_SHORT).show();
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