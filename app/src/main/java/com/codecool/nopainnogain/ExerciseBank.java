package com.codecool.nopainnogain;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.codecool.nopainnogain.adapters.ExerciseBankRecyclerViewAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;


public class ExerciseBank extends Fragment implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener {



    private Spinner spinner;
    private SearchView searchView;
    private ExerciseBankRecyclerViewAdapter recyclerViewAdapter;
    private DataAccess dao;
    private RecyclerView recyclerView;

    public ExerciseBank() {
        // Required empty public constructor
    }

    public static ExerciseBank newInstance() {
        ExerciseBank fragment = new ExerciseBank();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_exercise_bank, container, false);

        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.workoutTargetList,
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dao = new InMemoryDataAccess();
        recyclerViewAdapter = new ExerciseBankRecyclerViewAdapter(this.getContext(),dao.getAllExercises());

        recyclerView = view.findViewById(R.id.exerciseBankRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recyclerViewAdapter);
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
}
