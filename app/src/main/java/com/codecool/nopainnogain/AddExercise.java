package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.WorkoutExercise;

public class AddExercise extends AppCompatActivity {
    private DataAccess dao;
    private TextView exerciseName;
    private EditText repsEditText;
    private Button selectNewExercise;
    private Button done;
    private Exercise selectedExercise;
    private int SELECT_NEW_EXERCISE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dao = DatabaseDataAccess.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseName = findViewById(R.id.addExerciseExerciseName);
        repsEditText = findViewById(R.id.addExerciseReps);
        selectNewExercise = findViewById(R.id.addExerciseSelectNewExercise);
        done = findViewById(R.id.addExerciseDone);

        Intent intent = getIntent();
        String exerciseJson = intent.getStringExtra("exercise");

        int reps = intent.getIntExtra("reps",10);

        /*System.out.println("On arrival: " + exercise + " Reps: " + reps);*/

        if(exerciseJson == null){
            Exercise defaultExercise = dao.getAllExercises().get(0);
            selectedExercise = defaultExercise;
            exerciseName.setText(defaultExercise.getName());
            this.repsEditText.setText(String.valueOf(10));
        }else{
            Exercise exercise = Exercise.toExerciseObject(exerciseJson);
            selectedExercise = exercise;
            exerciseName.setText(exercise.getName());
            repsEditText.setText(String.valueOf(reps));
        }


        selectNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddExercise.this,SelectNewExercise.class);
                startActivityForResult(intent,SELECT_NEW_EXERCISE);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                intent.putExtra("exercise",Exercise.toJsonString(selectedExercise));
                intent.putExtra("reps",Integer.valueOf(repsEditText.getText().toString()));
                /*System.out.println("On departure: " + selectedExercise + " Reps: " + repsEditText.getText().toString());*/
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_NEW_EXERCISE){
                Long id = data.getLongExtra("exerciseId", -1L);
                Exercise exercise = dao.getExerciseById(id);
                exerciseName.setText(exercise.getName());
                selectedExercise = exercise;
            }
        }
    }
}
