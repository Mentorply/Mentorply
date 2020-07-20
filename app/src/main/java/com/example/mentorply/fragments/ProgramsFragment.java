package com.example.mentorply.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mentorply.CreateProgramActivity;
import com.example.mentorply.LoginActivity;
import com.example.mentorply.OnboardingActivity;
import com.example.mentorply.ProgramCodeActivity;
import com.example.mentorply.R;
import com.example.mentorply.adapters.ProgramAdapter;
import com.example.mentorply.models.Program;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgramsFragment extends Fragment {

    public static final String TAG = "ProgramsFragment";
    private RecyclerView rvPrograms;
    public ProgramAdapter adapter;
    protected List <Program> allPrograms;
    //public int totalPrograms = 20;
    private SwipeRefreshLayout swipeContainer;

    public ProgramsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programs, container, false);
    }
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        rvPrograms = view.findViewById(R.id.rvPrograms);
        allPrograms = new ArrayList<>();
        adapter = new ProgramAdapter(getContext(), allPrograms);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateHomePrograms();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //Steps to use the recycler view:
        //0. create layout for one row in the list
        //1. create the adapter
        //2. create the data source
        //3. set the adapter on the recycler view
        rvPrograms.setAdapter(adapter);
        //4. set the layout manager on the recycler view
        rvPrograms.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPrograms();


    }

    public void populateHomePrograms() {
        adapter.clear();
        // ...the data has come back, add new items to your adapter...
        adapter.addAll(allPrograms);
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    protected void queryPrograms() {
        ParseQuery<Program> query = ParseQuery.getQuery(Program.class);
        query.include(Program.KEY_NAME);
        //query.setLimit(totalPrograms);
        query.addDescendingOrder(Program.KEY_NAME);
        query.findInBackground(new FindCallback<Program>() {
            @Override
            public void done(List<Program> programs, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Issue with getting programs", e);
                    return;
                }
                for (Program program: programs){
                    Log.i(TAG, "Program: "+program.getName()+", Description: "+program.getDescription()+ ", Program Code: ");
                }
                allPrograms.addAll(programs);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.programs_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                Intent i = new Intent(getContext(), CreateProgramActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Create", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add:
                Intent j = new Intent(getContext(), ProgramCodeActivity.class);
                startActivity(j);
                //finish();
                Toast.makeText(getActivity(), "Add by Code", Toast.LENGTH_SHORT).show();
                break;
            /*default:
                fragment = new ProfileFragment();
                break;
             */
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void querySpecificProgram(String programCode) {
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
                    Toast.makeText(getActivity(), "The program exists!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(e.getCode() == ParseException.OBJECT_NOT_FOUND)
                    {
                        //object doesn't exist
                        Toast.makeText(getActivity(), "The program does not exist", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //unknown error, debug
                    }
                }
            }
        });

    }*/

   /* private String openDialog() {
       AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
       builder.setTitle("Title");
       final String[] m_Text = new String[1];
       // Set up the input
       final EditText input = new EditText(getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
       input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
       builder.setView(input);

       // Set up the buttons
       builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               m_Text[0] = input.getText().toString();
           }
       });
       builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
           }
       });

       builder.show();
       return m_Text[0];
   }*/
}