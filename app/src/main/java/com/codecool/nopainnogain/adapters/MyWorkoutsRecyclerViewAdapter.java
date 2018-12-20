package com.codecool.nopainnogain.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.nopainnogain.PlayWorkout;
import com.codecool.nopainnogain.R;
import com.codecool.nopainnogain.WorkoutDetails;
import com.codecool.nopainnogain.model.Workout;
import com.google.gson.Gson;

import java.util.List;

public class MyWorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<MyWorkoutsRecyclerViewAdapter.ViewHolder> {

    private List<Workout> workoutList;
    private Context context;

    public MyWorkoutsRecyclerViewAdapter(List<Workout> workoutList, Context context){
        this.workoutList = workoutList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyWorkoutsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_workouts_entry,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyWorkoutsRecyclerViewAdapter.ViewHolder holder, int position) {
        final Workout selectedWorkout = workoutList.get(position);
        holder.workoutTitle.setText(selectedWorkout.getTitle());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.imageView.getContext(), PlayWorkout.class);
                intent.putExtra("workout", Workout.toJsonString(selectedWorkout));
                holder.imageView.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WorkoutDetails.class);
                intent.putExtra("workout",Workout.toJsonString(selectedWorkout));
                view.getContext().startActivity(intent);
            }
        });

    }

    public void updateDataSet(List<Workout> workoutList){
        this.workoutList = workoutList;
        notifyDataSetChanged();
    }

    public void addToDataSet(Workout workout){
        workoutList.add(workout);
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView workoutTitle;
        public ImageView imageView;
        public View itemView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.workoutTitle = itemView.findViewById(R.id.myWorkoutsWorkoutTitle);
            this.imageView = itemView.findViewById(R.id.entryPlayButton);
            this.itemView = itemView;


        }


    }
}
