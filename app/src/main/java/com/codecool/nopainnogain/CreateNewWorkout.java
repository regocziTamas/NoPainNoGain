package com.codecool.nopainnogain;

import android.content.Intent;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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
        textView = findViewById(R.id.createNewWorkout);

        this.workout = getIntent().getParcelableExtra("workout");
        if(workout.getBlocksForListing().size() == 0){
            textView.setText("Click on the plus icon to add a new block");
        }else{
            inflateView(workout.getTitle());
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
                inflateView(workout.getTitle());
                layoutInitialized = true;
            }
            adapter.addEmptyBlockToWorkout();
            adapter.notifyItemInserted(adapter.getItemCount());
        }
        return super.onOptionsItemSelected(item);
    }

    private void inflateView(String text){
        LinearLayout layout = findViewById(R.id.createNewWorkoutRoot);
        layout.removeViewAt(0);

        LinearLayout.LayoutParams editTextLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        editTextLayout.setMarginStart(20);
        editTextLayout.setMarginEnd(20);
        editTextLayout.setMargins(20,40,40,20);

        this.editText = new EditText(this);
        editText.setLayoutParams(editTextLayout);
        editText.setText(text);
        editText.setSingleLine();
        editText.setSelection(editText.getText().length());



        this.recyclerView = new RecyclerView(this);
        adapter = new MyWorkoutsWorkoutDetailsAdapter(this,workout,true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        layout.addView(editText);
        layout.addView(recyclerView);
    }

    public void startEditBlockActivity(Intent intent){
        startActivityForResult(intent,REQUEST_CODE_EDIT_BLOCK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK ){
            if(requestCode == REQUEST_CODE_EDIT_BLOCK){
                WorkoutBlock block = data.getParcelableExtra("newBlock");
                workout.replaceBlockById(block.getId(),block);
                adapter.notifyDataSetChanged();
            }
        }
    }



    @Override
    public void finish() {
        System.out.println(workout);
        Intent returnIntent = getIntent();
        returnIntent.putExtra("newWorkout",workout);
        setResult(RESULT_OK,returnIntent);
        super.finish();
    }
}
