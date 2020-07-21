package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mentorply.models.Program;
import com.google.android.material.snackbar.Snackbar;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ProgramCodeActivity extends AppCompatActivity {

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
                    if (!toggleButtonState) {
                        //program.getMentees().
                        //seeIfUserIsInProgram(ParseUser.getCurrentUser(), program.getMentees().getQuery());
                        program.addMentee(program.getMentees(), ParseUser.getCurrentUser());
                        program.saveInBackground();
                    }
                    else{
                        program.addMentor(program.getMentors(), ParseUser.getCurrentUser());
                        program.saveInBackground();

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

    private boolean seeIfUserIsInProgram(ParseUser currentUser, ParseQuery query) {
        query.whereEqualTo("objectId", currentUser);
        final boolean[] inProgram = {false};
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (e==null){
                    //user is in the program
                    Toast.makeText(ProgramCodeActivity.this, "The user is already in this program", Toast.LENGTH_SHORT).show();
                    inProgram[0] = true;

                }
            }
        });
        return inProgram[0];
    }

}