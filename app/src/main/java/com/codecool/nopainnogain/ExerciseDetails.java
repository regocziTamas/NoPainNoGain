package com.codecool.nopainnogain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ExerciseDetails extends AppCompatActivity {

    String name;
    String desc;
    TextView nameTextView;
    TextView descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);

        name = getIntent().getStringExtra("name");
        desc = getIntent().getStringExtra("desc");

        nameTextView = findViewById(R.id.exerciseDetailsTitle);
        descTextView = findViewById(R.id.exerciseDetailsDesc);

        nameTextView.setText(name);
        descTextView.setText(desc);
    }
}
