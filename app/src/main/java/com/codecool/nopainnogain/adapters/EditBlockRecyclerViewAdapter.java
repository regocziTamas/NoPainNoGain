package com.codecool.nopainnogain.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecool.nopainnogain.EditBlock;
import com.codecool.nopainnogain.R;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.codecool.nopainnogain.util.DragAndDropSwipeHelper;

import java.util.List;

public class EditBlockRecyclerViewAdapter extends RecyclerView.Adapter<EditBlockRecyclerViewAdapter.ViewHolder> implements DragAndDropSwipeHelper.DragAndDropSwipeHelperListener {

    Context context;
    List<WorkoutComponent> components;
    EditBlock editBlock;

    public EditBlockRecyclerViewAdapter(Context context, List<WorkoutComponent> components, EditBlock editBlock){
        this.context = context;
        this.components = components;
        this.editBlock = editBlock;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_block_component_entry,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutComponent comp = components.get(position);
        if(comp instanceof WorkoutExercise){
            WorkoutExercise ex = (WorkoutExercise) comp;
            holder.exerciseName.setText(ex.getExercise().getName());
            holder.reps.setText(String.valueOf(ex.getReps()) + " reps");
        }else if(comp instanceof Rest){
            final Rest rest = (Rest) comp;
            holder.exerciseName.setText(String.valueOf(rest.getDurationInMilis()/1000) + " seconds of rest");
            holder.editComponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editBlock.startEditRest(rest);
                }
            });
        }

    }

    public void addComponentManually(WorkoutComponent component){
        components.add(component);
    }

    @Override
    public int getItemCount() {
        return components.size();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        components.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView reps;
        ImageView editComponent;

        public ViewHolder(View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.addBlockEntryExercise);
            reps = itemView.findViewById(R.id.addBlockEntryReps);
            editComponent = itemView.findViewById(R.id.editComponent);
        }
    }
}
