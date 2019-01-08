package com.codecool.nopainnogain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.codecool.nopainnogain.model.Workout;


public class WorkoutDetails extends AppCompatActivity {

    private DataAccess dao;
    private TextView workoutTitle;
    private RecyclerView recyclerView;
    private MyWorkoutsWorkoutDetailsAdapter adapter;
    private Workout selectedWorkout;
    private int REQUEST_CODE_EDIT_WORKOUT = 101;
    private int PLAY_WORKOUT_REQUEST_CODE = 1234;
    private boolean allowEditAndDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        dao = DatabaseDataAccess.getInstance();

        Intent intent = getIntent();
        selectedWorkout = Workout.toWorkoutObject(intent.getStringExtra("workout"));
        allowEditAndDelete = intent.getBooleanExtra("allowEditAndDelete", true);

        System.out.println(selectedWorkout);

        workoutTitle = findViewById(R.id.workoutDetailsTitle);
        workoutTitle.setText(selectedWorkout.getTitle());

        adapter = new MyWorkoutsWorkoutDetailsAdapter(this,false);
        adapter.newDataset(selectedWorkout.getBlocksForListing());

        recyclerView = findViewById(R.id.workoutDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(allowEditAndDelete){
            getMenuInflater().inflate(R.menu.do_workout_menu,menu);
        }else{
            getMenuInflater().inflate(R.menu.do_workout_menu_edit_delete_disabled,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.doWorkoutButton){
            Intent intent = new Intent(WorkoutDetails.this,PlayWorkout.class);
            intent.putExtra("workout",Workout.toJsonString(selectedWorkout));
            intent.putExtra("startingPage",0);
            startActivityForResult(intent,PLAY_WORKOUT_REQUEST_CODE);
        }else if(id == R.id.editWorkout){
            Intent intent = new Intent(WorkoutDetails.this,CreateNewWorkout.class);
            intent.putExtra("workout",Workout.toJsonString(selectedWorkout));
            startActivityForResult(intent,REQUEST_CODE_EDIT_WORKOUT);
        }else if(id == R.id.deleteWorkout){
            showDeleteConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    

    private void showDeleteConfirmationDialog(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Workout")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dao.deleteWorkout(selectedWorkout);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setMessage("Are you sure you want to delete this workout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_EDIT_WORKOUT){
                Workout workout = Workout.toWorkoutObject(data.getStringExtra("newWorkout"));
                adapter.newDataset(workout.getBlocksForListing());
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
