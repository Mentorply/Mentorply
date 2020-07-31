package com.example.mentorply.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentorply.R;
import com.example.mentorply.adapters.ChoicesAdapter;
import com.example.mentorply.adapters.ProgramAdapter;
import com.example.mentorply.models.Affiliation;
import com.example.mentorply.models.Program;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsFragment extends Fragment {

    public static final String TAG = "ConnectionsFragment";
    private RecyclerView rvConnections;
    public ChoicesAdapter adapter;
    protected List<ParseUser> allConnections;
    //public int totalPrograms = 20;
    private SwipeRefreshLayout swipeContainer;


    public ConnectionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ConnectionsFragment newInstance(String param1, String param2) {
        ConnectionsFragment fragment = new ConnectionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connections, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvConnections = view.findViewById(R.id.rvConnections);
        allConnections = new ArrayList<>();
        adapter = new ChoicesAdapter(getContext(), allConnections);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateHomeConnections();
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
        rvConnections.setAdapter(adapter);
        //4. set the layout manager on the recycler view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GridLayoutManager.VERTICAL);
        rvConnections.setLayoutManager(gridLayoutManager);
        //rvPrograms.addItemDecoration(new SpacesItemDecoration(DividerItemDecoration.VERTICAL));
        queryPairs();

    }

    public void populateHomeConnections() {
        adapter.clear();
        // ...the data has come back, add new items to your adapter...
        adapter.addAll(allConnections);
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    protected void queryPairs() {
///*

        ParseQuery<Affiliation> connectionsQuery = ParseQuery.getQuery(Affiliation.class);
        connectionsQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        //programsQuery.include(Program.KEY_NAME);
        connectionsQuery.whereEqualTo("participant", ParseUser.getCurrentUser());
        connectionsQuery.whereNotEqualTo("pair", null);
        connectionsQuery.findInBackground(new FindCallback<Affiliation>() {
            List <ParseUser> connections = new ArrayList<>();
            @Override
            public void done(List<Affiliation> affiliations, ParseException e) {
                if (e!=null){
                    Log.e(TAG, "Issue with getting programs", e);
                    return;
                }
                for (Affiliation affiliation: affiliations){
                    ParseUser connection = affiliation.getPair();
                    connection.saveInBackground();
                    connections.add(connection);
                }//+program.getObjectId()
                allConnections.addAll(connections);
                adapter.notifyDataSetChanged();
            }
        });
    }
}