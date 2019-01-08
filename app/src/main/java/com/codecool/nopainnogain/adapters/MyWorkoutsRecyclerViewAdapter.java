package com.codecool.nopainnogain.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.nopainnogain.App;
import com.codecool.nopainnogain.MainActivity;
import com.codecool.nopainnogain.PlayWorkout;
import com.codecool.nopainnogain.R;
import com.codecool.nopainnogain.WorkoutDetails;
import com.codecool.nopainnogain.model.Workout;

import java.util.List;

public class MyWorkoutsRecyclerViewAdapter extends RecyclerView.Adapter<MyWorkoutsRecyclerViewAdapter.ViewHolder> {

    private int PLAY_WORKOUT_REQUEST_CODE = 1234;

    private List<Workout> workoutList;
    private Context context;
    private boolean allowEditAndDelete;

    public MyWorkoutsRecyclerViewAdapter(List<Workout> workoutList, Context context, boolean allowEditAndDelete){
        this.workoutList = workoutList;
        this.context = context;
        this.allowEditAndDelete = allowEditAndDelete;
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
                if(selectedWorkout.isEmpty()){
                    Toast.makeText(context,"This workout is empty!",Toast.LENGTH_SHORT).show();
                    return;
                }

                final Intent intent = new Intent(holder.imageView.getContext(), PlayWorkout.class);
                intent.putExtra("workout", Workout.toJsonString(selectedWorkout));
                intent.putExtra("startingPage",0);

                if(App.getCurrentWorkout() != null){
                    AlertDialog.Builder builder;
                    builder = new AlertDialog.Builder(context);
                    builder.setTitle("Start New Workout")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MainActivity) context).startActivityForResult(intent,PLAY_WORKOUT_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setMessage("You have an unfinished workout. By starting a new workout, you will lose your progress in the " +
                                    "previous one. Do you want to proceed?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else{
                    ((MainActivity) context).startActivityForResult(intent,PLAY_WORKOUT_REQUEST_CODE);
                }






            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WorkoutDetails.class);
                intent.putExtra("workout",Workout.toJsonString(selectedWorkout));
                intent.putExtra("allowEditAndDelete",allowEditAndDelete);
                context.startActivity(intent);
            }
        });

    }

    private void startWorkout(){

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
