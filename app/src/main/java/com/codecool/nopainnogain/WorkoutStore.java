package com.codecool.nopainnogain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codecool.nopainnogain.adapters.MyWorkoutsRecyclerViewAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.model.Workout;

import java.util.List;


public class WorkoutStore extends Fragment {

    private DataAccess dao;
    private MyWorkoutsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    public WorkoutStore() {
        // Required empty public constructor
    }


    public static WorkoutStore newInstance() {
        WorkoutStore fragment = new WorkoutStore();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dao = DatabaseDataAccess.getInstance();
        recyclerView = view.findViewById(R.id.workoutStoreRecyclerView);


        List<Workout> workoutList = dao.getAllSyncedWorkouts();
        if(workoutList.isEmpty()){
            Snackbar snackbar = Snackbar.make(view,"No workouts found in the synced database",Snackbar.LENGTH_SHORT);
            snackbar.show();
        }


        adapter = new MyWorkoutsRecyclerViewAdapter(workoutList,getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_workout_store, container, false);
    }




}
