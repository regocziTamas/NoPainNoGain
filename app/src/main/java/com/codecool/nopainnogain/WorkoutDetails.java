package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codecool.nopainnogain.adapters.MyWorkoutsWorkoutDetailsAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.Workout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WorkoutDetails extends AppCompatActivity {

    private DataAccess dao;
    private TextView workoutTitle;
    private RecyclerView recyclerView;
    private MyWorkoutsWorkoutDetailsAdapter adapter;
    private Workout selectedWorkout;
    private int REQUEST_CODE_EDIT_WORKOUT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        dao = DatabaseDataAccess.getInstance();

        Intent intent = getIntent();
        selectedWorkout = Workout.toWorkoutObject(intent.getStringExtra("workout"));


        workoutTitle = findViewById(R.id.workoutDetailsTitle);
        workoutTitle.setText(selectedWorkout.getTitle());

        adapter = new MyWorkoutsWorkoutDetailsAdapter(this,selectedWorkout,false);

        recyclerView = findViewById(R.id.workoutDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.do_workout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.doWorkoutButton){
            Intent intent = new Intent(WorkoutDetails.this,PlayWorkout.class);
            intent.putExtra("workout",Workout.toJsonString(selectedWorkout));
            startActivity(intent);
        }else if(id == R.id.editWorkout){
            Intent intent = new Intent(WorkoutDetails.this,CreateNewWorkout.class);
            intent.putExtra("workout",Workout.toJsonString(selectedWorkout));
            startActivityForResult(intent,REQUEST_CODE_EDIT_WORKOUT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_EDIT_WORKOUT){
                Workout workout = Workout.toWorkoutObject(data.getStringExtra("newWorkout"));
                adapter.newDataset(workout);
                selectedWorkout = workout;
                forceRedrawRecyclerview();
                dao.saveWorkout(workout);
            }
        }
    }

    private void forceRedrawRecyclerview(){
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(null);
        recyclerView.getRecycledViewPool().clear();
        recyclerView.swapAdapter(adapter,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



}
