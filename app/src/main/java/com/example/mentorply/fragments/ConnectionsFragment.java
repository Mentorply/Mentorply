package com.example.mentorply.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mentorply.R;
import com.example.mentorply.activities.pairing.RequestsActivity;
import com.example.mentorply.adapters.ChoicesAdapter;
import com.example.mentorply.adapters.ConnectionsAdapter;
import com.example.mentorply.models.Membership;
import com.example.mentorply.models.Pair;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsFragment extends Fragment {

    public static final String TAG = "ConnectionsFragment";
    private RecyclerView rvConnections;
    public ConnectionsAdapter adapter;
    protected List<ParseUser> allConnections;
    private SwipeRefreshLayout swipeContainer;


    public ConnectionsFragment() {
        // Required empty public constructor
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
        setHasOptionsMenu(true);
        //getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //getActivity().getActionBar().setCustomView(R.layout.);
        rvConnections = view.findViewById(R.id.rvConnections);
        allConnections = new ArrayList<>();
        adapter = new ConnectionsAdapter(getContext(), allConnections);
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
        queryAllPairs();

    }

    public void populateHomeConnections() {
        adapter.clear();
        // ...the data has come back, add new items to your adapter...
        adapter.addAll(allConnections);
        // Now we call setRefreshing(false) to signal refresh has finished
        swipeContainer.setRefreshing(false);
    }

    protected void queryAllPairs() {
        queryToPairs();
        queryFromPairs();
    }

    private void queryFromPairs() {
        ParseQuery<Pair> fromQuery = ParseQuery.getQuery(Pair.class);
        fromQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        fromQuery.whereEqualTo("fromUser", ParseUser.getCurrentUser());
        fromQuery.whereEqualTo("status", "accepted");
        fromQuery.findInBackground(new FindCallback<Pair>() {
            List<ParseUser> connections = new ArrayList<>();

            public void done(List<Pair> pairs, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting mentees", e);
                    return;
                }
                for (Pair pair : pairs) {
                    ParseUser fromUser = pair.getFromUser();
                    fromUser.saveInBackground();
                    connections.add(fromUser);
                }//+program.getObjectId()
                allConnections.addAll(connections);
                adapter.notifyDataSetChanged();
            }
        });



    }

    private void queryToPairs() {
        ParseQuery<Pair> toQuery = ParseQuery.getQuery(Pair.class);
        toQuery.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
        toQuery.whereEqualTo("toUser", ParseUser.getCurrentUser());
        toQuery.whereEqualTo("status", "accepted");
        toQuery.findInBackground(new FindCallback<Pair>() {
            List<ParseUser> connections = new ArrayList<>();

            public void done(List<Pair> pairs, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting mentees", e);
                    return;
                }
                for (Pair pair : pairs) {
                    ParseUser fromUser = pair.getFromUser();
                    fromUser.saveInBackground();
                    connections.add(fromUser);
                }
                allConnections.addAll(connections);
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.requests_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_requests:
                Intent i = new Intent(getContext(), RequestsActivity.class);
                startActivity(i);
                Toast.makeText(getActivity(), "Requests", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // perform query here
//
//                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
//                // see https://code.google.com/p/android/issues/detail?id=24599
//                searchView.clearFocus();
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

}