package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentorply.models.Program;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class ProgramCodeActivity extends AppCompatActivity {

    EditText etProgramCode;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_code);

        etProgramCode = findViewById(R.id.etProgramCode);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String programCode = etProgramCode.getText().toString();
                if (programCode.isEmpty()){
                    Toast.makeText(ProgramCodeActivity.this, "Program Code cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                validateProgram(programCode);
            }
        });
    }

    private void validateProgram(String programCode) {
        // Specify which class to query
        ParseQuery<Program> query = ParseQuery.getQuery(Program.class);
        // Define our query conditions
        query.whereEqualTo("objectId", programCode);
        query.getFirstInBackground(new GetCallback<Program>()
        {
            public void done(Program program, ParseException e)
            {
                if(e == null)
                {
                    //object exists
                    Toast.makeText(ProgramCodeActivity.this, "The program exists!", Toast.LENGTH_SHORT).show();
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
}