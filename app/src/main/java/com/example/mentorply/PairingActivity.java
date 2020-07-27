package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.mentorply.adapters.ChoicesAdapter;
import com.example.mentorply.models.Affiliation;
import com.example.mentorply.models.Program;
import com.example.mentorply.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PairingActivity extends AppCompatActivity {

    public static final String TAG = "PairingActivity";


    Program program;
    RecyclerView rvChoices;
    List<ParseUser> users;
    ChoicesAdapter adapter;
    //EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairing);

        //Find the recycler view
        rvChoices = findViewById(R.id.rvChoices);
        //Init the list of tweets and adapter
        program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));
        users = new ArrayList<>();
        adapter = new ChoicesAdapter(this, users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //Recycler view setup: layout manager and the adapter
        rvChoices.setLayoutManager(layoutManager);
        rvChoices.setAdapter(adapter);
        queryParseUsers();

    }

   protected void queryParseUsers() {
       ParseQuery<Affiliation> menteesQuery = ParseQuery.getQuery(Affiliation.class);
       menteesQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
       //programsQuery.include(Program.KEY_NAME);
       menteesQuery.whereEqualTo("program", program);
       menteesQuery.whereEqualTo("role", "mentee");
//*/
       menteesQuery.findInBackground(new FindCallback<Affiliation>() {
           List <ParseUser> mentees = new ArrayList<ParseUser>();
           @Override
           public void done(List<Affiliation> affiliations, ParseException e) {
               if (e!=null){
                   Log.e(TAG, "Issue with getting programs", e);
                   return;
               }
               for (Affiliation affiliation: affiliations){
                   ParseUser mentee = affiliation.getParticipant();
                   mentee.saveInBackground();
                   mentees.add(mentee);
               }//+program.getObjectId()
               users.addAll(mentees);
               adapter.notifyDataSetChanged();
           }
       });

   }
}