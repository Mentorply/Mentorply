package com.example.mentorply;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import com.example.mentorply.adapters.ChoicesAdapter;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Program;
import com.example.mentorply.models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
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
       final ParseQuery<Membership> query = ParseQuery.getQuery(Membership.class);
       query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
       //programsQuery.include(Program.KEY_NAME);
       query.whereEqualTo("program", program);


       ParseQuery<Membership> subQuery = ParseQuery.getQuery("Membership");
       subQuery.whereEqualTo("participant", ParseUser.getCurrentUser());
       subQuery.getFirstInBackground(new GetCallback<Membership>() {
           public void done(Membership object, ParseException e) {
               if (object == null) {
                   Log.d("score", "The getFirst request failed.");
               } else {
                   Log.d("score", "Retrieved the object.");
                   if (object.getRole().equals("mentee"))
                       query.whereEqualTo("role", "mentor");
                   else
                       query.whereEqualTo("role", "mentee");
               }
           }
       });


//*/
       query.findInBackground(new FindCallback<Membership>() {
           List <ParseUser> participants = new ArrayList<ParseUser>();
           @Override
           public void done(List<Membership> memberships, ParseException e) {
               if (e!=null){
                   Log.e(TAG, "Issue with getting programs", e);
                   return;
               }
               for (Membership membership: memberships){
                   ParseUser participant = membership.getParticipant();
                   participant.saveInBackground();
                   participants.add(participant);
               }//+program.getObjectId()
               users.addAll(participants);
               adapter.notifyDataSetChanged();
           }
       });
   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here

                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}