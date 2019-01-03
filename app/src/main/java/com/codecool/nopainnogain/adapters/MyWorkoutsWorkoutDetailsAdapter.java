package com.codecool.nopainnogain.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import java.util.Collections;
import java.util.List;

public class MyWorkoutsWorkoutDetailsAdapter extends RecyclerView.Adapter<MyWorkoutsWorkoutDetailsAdapter.ViewHolder> {

    List<WorkoutBlock> workoutBlocks;
    boolean editable;
    Context context;
    PopupMenu popup;


    public MyWorkoutsWorkoutDetailsAdapter(Context context, boolean editable){
        this.workoutBlocks = new ArrayList<>();
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

    public void newDataset(List<WorkoutBlock> blocks){
        workoutBlocks.clear();
        workoutBlocks.addAll(blocks);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyWorkoutsWorkoutDetailsAdapter.ViewHolder holder, int position) {
        final WorkoutBlock currentBlock = workoutBlocks.get(position);
        holder.blockTitle.setText("Exercise "+ (++position));

        StringBuilder exercisesString = new StringBuilder();
        for(WorkoutComponent component: currentBlock.getComponents()){
            exercisesString.append(component.toString()).append("\n");
        }

        holder.exercises.setText(exercisesString.toString());

        if(editable){
            ImageView editButton = holder.itemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EditBlock.class);
                    intent.putExtra("block",WorkoutBlock.toJsonString(currentBlock));
                    ((CreateNewWorkout) context).startEditBlockActivity(intent);
                }
            });
            ImageView popupMenu = holder.itemView.findViewById(R.id.popupMenuButton);
            popupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu(v, currentBlock);
                }
            });
        }
    }

    private void showMenu(View v, WorkoutBlock block) {
        popup = new PopupMenu(context, v);
        final int blockOrder = block.getOrder();
        System.out.println("Clicked block number: " + blockOrder);
        popup.setOnMenuItemClickListener(null);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.deleteBlock){
                    showDeleteConfirmationDialog(blockOrder);
                    return true;
                }else if(menuItem.getItemId() == R.id.moveDown){
                    if(blockOrder == workoutBlocks.size()-1){
                        return false;
                    }else{
                        handleSwap(blockOrder,+1);
                        return true;
                    }

                }else if(menuItem.getItemId() == R.id.moveUp){
                    if(blockOrder == 0){
                        return false;
                    }else{
                        handleSwap(blockOrder,-1);
                        return true;
                    }
                }else{
                    return false;
                }
            }

        });
        popup.inflate(R.menu.block_popover_menu);
        popup.show();
    }



    private void handleSwap(int blockOrder, int direction){
        ((CreateNewWorkout) context).swapTwoBlocksInDisplayedWorkout(blockOrder,blockOrder+direction);
        Collections.swap(workoutBlocks,blockOrder+direction,blockOrder);

        for(int i = 0; i < workoutBlocks.size();i++){
            workoutBlocks.get(i).setOrder(i);
        }

        notifyItemMoved(blockOrder, blockOrder+direction);
    }


    private void showDeleteConfirmationDialog(final int blockOrder){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Block")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        workoutBlocks.remove(blockOrder);
                        notifyItemRemoved(blockOrder);
                        ((CreateNewWorkout)context).deleteBlockFromDisplayedWorkout(blockOrder);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setMessage("Are you sure you want to delete this block?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void addEmptyBlockToWorkout(WorkoutBlock newBlock){
        workoutBlocks.add(newBlock);
        notifyItemInserted(workoutBlocks.size()-1);
    }

    @Override
    public int getItemCount() {
        return workoutBlocks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView blockTitle;
        TextView exercises;
        View itemView;

        ViewHolder(View itemView) {
            super(itemView);
            blockTitle = itemView.findViewById(R.id.myWorkoutsWorkoutBlockTitle);
            exercises = itemView.findViewById(R.id.exercises);
            this.itemView = itemView;
        }
    }


}
