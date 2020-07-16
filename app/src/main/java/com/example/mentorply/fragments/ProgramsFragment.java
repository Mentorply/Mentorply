package com.example.mentorply.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mentorply.R;
import com.example.mentorply.adapters.ProgramAdapter;
import com.example.mentorply.models.Program;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                    Log.i(TAG, "Program: "+program.getName()+", Description: "+program.getDescription());
                }
                allPrograms.addAll(programs);
                adapter.notifyDataSetChanged();
            }
        });
    }
}