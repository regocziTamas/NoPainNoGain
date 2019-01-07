package com.codecool.nopainnogain.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.util.Collections;
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

        holder.itemView.setOnTouchListener(new DoubleTapHelper(comp));

        if(comp instanceof WorkoutExercise){
            final WorkoutExercise ex = (WorkoutExercise) comp;
            holder.exerciseName.setText(ex.getExercise().getName());
            holder.reps.setText(String.valueOf(ex.getReps()) + " reps");
            holder.editComponent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editBlock.startEditExercise(ex);
                    /*System.out.println("Starting editing of ex with order: " + ex.getOrder());*/
                }
            });
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
        ((EditBlock) context).deleteFromBlock(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onDragAndDropped(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int order1 = viewHolder.getAdapterPosition();
        int order2 = target.getAdapterPosition();


        Collections.swap(components,order1,order2);
        notifyItemMoved(order1,order2);

        ((EditBlock) context).swapTwoComponentsInBlock(order1,order2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView exerciseName;
        TextView reps;
        ImageView editComponent;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            exerciseName = itemView.findViewById(R.id.addBlockEntryExercise);
            reps = itemView.findViewById(R.id.addBlockEntryReps);
            editComponent = itemView.findViewById(R.id.editComponent);
        }
    }

    private class DoubleTapHelper implements View.OnTouchListener{

        private WorkoutComponent component;

        private DoubleTapHelper(WorkoutComponent component){
            this.component = component;
        }

        private GestureDetector gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                notifyItemInserted(components.size());
                ((EditBlock)context).cloneComponentToEnd(component);
                System.out.println("Engem most meghívtak");

                return super.onDoubleTap(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }


    }
}
