package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import com.codecool.nopainnogain.adapters.ExerciseBankRecyclerViewAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;

public class SelectNewExercise extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private SearchView searchView;
    private ExerciseBankRecyclerViewAdapter recyclerViewAdapter;
    private DataAccess dao;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_exercise_bank);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.workoutTargetList,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        dao = DatabaseDataAccess.getInstance();
        recyclerViewAdapter = new ExerciseBankRecyclerViewAdapter(this,dao.getAllExercises(),true);

        recyclerView = findViewById(R.id.exerciseBankRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private void handleQueryChange(){
        String query = searchView.getQuery().toString();
        String target = spinner.getSelectedItem().toString();
        recyclerViewAdapter.doFilter(query,target);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        handleQueryChange();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        handleQueryChange();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        handleQueryChange();
        return false;
    }
}
