package com.codecool.nopainnogain.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codecool.nopainnogain.ExerciseDetails;
import com.codecool.nopainnogain.R;
import com.codecool.nopainnogain.model.Exercise;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class ExerciseBankRecyclerViewAdapter extends RecyclerView.Adapter<ExerciseBankRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<Exercise> exerciseList;
    private List<Exercise> filteredList;
    private boolean withInfoButton;

    public ExerciseBankRecyclerViewAdapter(Context context, List<Exercise> exerciseList,boolean withInfoButton){
        this.context = context;
        this.exerciseList = exerciseList;
        this.filteredList = this.exerciseList;
        this.withInfoButton = withInfoButton;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(withInfoButton){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_bank_entry_with_info_button,parent,false);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_bank_entry,parent,false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Exercise ex = filteredList.get(position);
        holder.exerciseName.setText(ex.getName());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ExerciseDetails.class);
                intent.putExtra("name",ex.getName());
                intent.putExtra("desc",ex.getDescription());
                view.getContext().startActivity(intent);
            }
        };

        if(withInfoButton){
            holder.infoButton.setOnClickListener(listener);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity parentActivity = ((AppCompatActivity)context);
                    Intent intent = parentActivity.getIntent();
                    intent.putExtra("exerciseId",ex.getId());
                    parentActivity.setResult(Activity.RESULT_OK,intent);
                    parentActivity.finish();

                }
            });
        }else{
            holder.itemView.setOnClickListener(listener);
        }

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void doFilter(String query, String target){
        List<Exercise> temp = new ArrayList<>();
        query = query.toLowerCase();
        if(!target.equals("All")){
            for(Exercise ex: exerciseList){
                String name = ex.getName().toLowerCase();
                if(name.contains(query) && ex.getTarget().toString().toLowerCase().equals(target.toLowerCase())){
                    temp.add(ex);
                }
            }
        }else{
            for(Exercise ex: exerciseList){
                String name = ex.getName().toLowerCase();
                if(name.contains(query)){
                    temp.add(ex);
                }
            }
        }
        filteredList = temp;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView exerciseName;
        View itemView;
        ImageView infoButton;

        public ViewHolder(final View itemView) {
            super(itemView);
            if(withInfoButton){
                this.exerciseName = itemView.findViewById(R.id.exerciseBankEntryWithInfoButton);
            }else {
                this.exerciseName = itemView.findViewById(R.id.exerciseBankEntry);
            }
            this.itemView = itemView;
            this.infoButton = itemView.findViewById(R.id.infoButton);

        }
    }
}
