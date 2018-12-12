package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.WorkoutExercise;

public class AddExercise extends AppCompatActivity {
    private DataAccess dao;
    private TextView exerciseName;
    private EditText reps;
    private Button selectNewExercise;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dao = DatabaseDataAccess.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        exerciseName = findViewById(R.id.addExerciseExerciseName);
        reps = findViewById(R.id.addExerciseReps);
        selectNewExercise = findViewById(R.id.addExerciseSelectNewExercise);
        done = findViewById(R.id.addExerciseDone);

        Intent intent = getIntent();
        long exerciseId = intent.getLongExtra("exerciseId",1L);
        int reps = intent.getIntExtra("exerciseReps",10);

        exerciseName.setText(dao.getExerciseById(exerciseId).getName());
        this.reps.setText(reps);
    }
}
