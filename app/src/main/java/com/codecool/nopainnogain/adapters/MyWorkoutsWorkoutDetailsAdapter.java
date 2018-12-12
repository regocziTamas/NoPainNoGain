package com.codecool.nopainnogain.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codecool.nopainnogain.CreateNewWorkout;
import com.codecool.nopainnogain.EditBlock;
import com.codecool.nopainnogain.R;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.ExerciseTarget;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

public class MyWorkoutsWorkoutDetailsAdapter extends RecyclerView.Adapter<MyWorkoutsWorkoutDetailsAdapter.ViewHolder> {

    List<WorkoutBlock> workoutBlocks;
    boolean editable;
    Context context;
    private int REQUEST_CODE_EDIT_BLOCK = 101;


    public MyWorkoutsWorkoutDetailsAdapter(Context context, Workout workout, boolean editable){
        this.workoutBlocks = workout.getBlocksForListing();
        this.editable = editable;
        this.context = context;
    }

    @NonNull
    @Override
    public MyWorkoutsWorkoutDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout view;
        if(editable){
            view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_workouts_workout_details_entry_editable,parent,false);

        }else{
            view = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_workouts_workout_detail_entry,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyWorkoutsWorkoutDetailsAdapter.ViewHolder holder, int position) {
        final WorkoutBlock currentBlock = workoutBlocks.get(position);
        holder.blockTitle.setText("Exercise "+ (++position));
        CardView cardView = (CardView) ((RelativeLayout)holder.itemView).getChildAt(0);
        LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
        clearLinearLayout(linearLayout);

        for(WorkoutComponent component: currentBlock.getComponents()){
            TextView text = new TextView(linearLayout.getContext());
            text.setText(component.toString());
            System.out.println(component.toString());
            text.setTextSize(15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(35,10,0,0);
            text.setLayoutParams(params);
            linearLayout.addView(text);
        }

        if(editable){
            ImageView editButton = holder.itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditBlock.class);
                    /*currentBlock.addComponent(new WorkoutExercise(10,new Exercise("Pushup","djfdf", ExerciseTarget.Forearms)));
                    currentBlock.addComponent(new Rest(1000));*/
                    intent.putExtra("block",WorkoutBlock.toJsonString(currentBlock));
                    ((CreateNewWorkout) context).startEditBlockActivity(intent);
                }
            });
        }
    }

    private void clearLinearLayout(LinearLayout layout){
        for(int i = 0; i < layout.getChildCount(); i++){
            if(i != 0){
                layout.removeViewAt(i);
            }
        }
    }

    public void addEmptyBlockToWorkout(){
        workoutBlocks.add(new WorkoutBlock());
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return workoutBlocks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView blockTitle;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            blockTitle = itemView.findViewById(R.id.myWorkoutsWorkoutBlockTitle);
            this.itemView = itemView;
        }
    }


}
