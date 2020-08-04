package com.example.mentorply.activities.pairing;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentorply.R;
import com.example.mentorply.adapters.ChoicesAdapter;
import com.example.mentorply.adapters.RequestsAdapter;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Pair;
import com.example.mentorply.models.Program;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class RequestsActivity extends AppCompatActivity {
    public static final String TAG = "RequestsActivity";


    //Program program;
    RecyclerView rvRequests;
    List<Pair> pairs;
    RequestsAdapter adapter;
    //EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        //Find the recycler view
        rvRequests = findViewById(R.id.rvRequests);
        //Init the list of tweets and adapter
        //program = Parcels.unwrap(getIntent().getParcelableExtra(Program.class.getSimpleName()));
        pairs = new ArrayList<>();
        adapter = new RequestsAdapter(this, pairs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //Recycler view setup: layout manager and the adapter
        rvRequests.setLayoutManager(layoutManager);
        rvRequests.setAdapter(adapter);
        queryPairs();//getCurrentRoleToQuery()

    }

    // String currentRole
    protected void queryPairs() {
        final ParseQuery<Pair> query = ParseQuery.getQuery(Pair.class);
        query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //programsQuery.include(Program.KEY_NAME);
        query.whereEqualTo("status", "pending");
        query.whereEqualTo("toUser", ParseUser.getCurrentUser());
        //query.whereEqualTo("role", currentRole);

        query.findInBackground(new FindCallback<Pair>() {
            List <Pair> results = new ArrayList<Pair>();
            @Override
            public void done(List<Pair> pairz, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Issue with getting programs", e);
                    return;
                }
                for (Pair pair: pairz){
                    results.add(pair);
                }//+program.getObjectId()
                pairs.addAll(results);
                adapter.notifyDataSetChanged();
            }
        });
    }


}
