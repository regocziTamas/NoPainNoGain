package com.codecool.nopainnogain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codecool.nopainnogain.adapters.MyWorkoutsRecyclerViewAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.ExerciseTarget;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutExercise;


public class MyWorkouts extends Fragment {

    private int REQUEST_CODE_NEW_WORKOUT = 100;

    private DataAccess dao;
    private MyWorkoutsRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    public MyWorkouts() {
        // Required empty public constructor
    }


    public static MyWorkouts newInstance() {
        MyWorkouts fragment = new MyWorkouts();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dao = DatabaseDataAccess.getInstance();
        adapter = new MyWorkoutsRecyclerViewAdapter(dao.getAllWorkouts(),getContext());
        recyclerView = view.findViewById(R.id.myWorkoutsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        floatingActionButton = view.findViewById(R.id.addNewWorkoutButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CreateNewWorkout.class);
                Workout newWorkout = new Workout("New Workout");
                System.out.println("get blocks for listing: " + newWorkout.getBlocksForListing());
                intent.putExtra("workout",Workout.toJsonString(newWorkout));
                startActivityForResult(intent,REQUEST_CODE_NEW_WORKOUT);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_workouts, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == -1){
            if(requestCode == REQUEST_CODE_NEW_WORKOUT){
                Workout newWorkout = Workout.toWorkoutObject(data.getStringExtra("newWorkout"));
                dao.saveWorkout(newWorkout);
                adapter.addToDataSet(newWorkout);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
