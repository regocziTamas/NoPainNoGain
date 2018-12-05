package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codecool.nopainnogain.adapters.MyWorkoutsWorkoutDetailsAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.Workout;

public class WorkoutDetails extends AppCompatActivity {

    private DataAccess dao;
    private TextView workoutTitle;
    private RecyclerView recyclerView;
    private MyWorkoutsWorkoutDetailsAdapter adapter;
    private Workout selectedWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        Intent intent = getIntent();
        selectedWorkout = intent.getParcelableExtra("workout");


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
            intent.putExtra("workout",selectedWorkout);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
