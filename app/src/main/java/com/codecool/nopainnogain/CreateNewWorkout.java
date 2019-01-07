package com.codecool.nopainnogain;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Handler;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codecool.nopainnogain.adapters.MyWorkoutsWorkoutDetailsAdapter;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;

public class CreateNewWorkout extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private RecyclerView recyclerView;
    private MyWorkoutsWorkoutDetailsAdapter adapter;
    private Workout workout;
    private boolean layoutInitialized = false;
    private int REQUEST_CODE_EDIT_BLOCK = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_workout);

        workout = Workout.toWorkoutObject(getIntent().getStringExtra("workout"));
        textView = findViewById(R.id.createNewWorkout);
        recyclerView = findViewById(R.id.createNewWorkoutRecyclerView);
        editText = findViewById(R.id.workoutName);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyWorkoutsWorkoutDetailsAdapter(this,true);
        recyclerView.setAdapter(adapter);
        adapter.newDataset(workout.getBlocksForListing());
        recyclerView.requestFocus();

        System.out.println(workout);

        editText.setText(workout.getTitle());

        if(workout.getBlocksForListing().size() == 0){
            textView.setText("Click on the plus icon to add a new block");
            recyclerView.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
        }else{
            initializeActivityWithWorkout();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_new_workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if(!layoutInitialized){
                initializeActivityWithWorkout();
                layoutInitialized = true;
            }
            WorkoutBlock newBlock = new WorkoutBlock();
            workout.addBlock(newBlock);
            adapter.addEmptyBlockToWorkout(newBlock);
            item.setEnabled(false);
            enableButtonAgain(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void enableButtonAgain(final MenuItem item){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                item.setEnabled(true);
            }
        },500);
    }

    private void initializeActivityWithWorkout(){
        textView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.VISIBLE);
        forceRedrawRecyclerview();
    }


    public void startEditBlockActivity(Intent intent){
        startActivityForResult(intent,REQUEST_CODE_EDIT_BLOCK);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ){
            if(requestCode == REQUEST_CODE_EDIT_BLOCK){
                WorkoutBlock block = WorkoutBlock.toWorkoutBlockObject(data.getStringExtra("newBlock"));
                workout.replaceBlockById(block.getOrder(),block);
                adapter.newDataset(workout.getBlocksForListing());
                adapter.notifyItemChanged(block.getOrder(),block);
            }
        }
    }

    private void forceRedrawRecyclerview(){
        recyclerView.setLayoutManager(null);
        recyclerView.setAdapter(null);
        recyclerView.getRecycledViewPool().clear();
        recyclerView.swapAdapter(adapter,false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }



    @Override
    public void finish() {
        Intent returnIntent = getIntent();
        workout.setTitle(editText.getText().toString());
        returnIntent.putExtra("newWorkout",Workout.toJsonString(workout));
        setResult(RESULT_OK,returnIntent);
        super.finish();
    }

    public void deleteBlockFromDisplayedWorkout(int order){
        workout.deleteBlockByOrder(order);
    }

    public void swapTwoBlocksInDisplayedWorkout(int order1,int order2){
        workout.swapBlocks(order1,order2);
    }



}
