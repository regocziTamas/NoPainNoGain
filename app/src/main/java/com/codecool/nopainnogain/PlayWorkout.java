package com.codecool.nopainnogain;


import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.codecool.nopainnogain.adapters.PlayWorkoutAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlockStart;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.codecool.nopainnogain.util.NonSwipeableViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PlayWorkout extends AppCompatActivity implements WorkoutDisplayFragment.OnFragmentInteractionListener, RestDisplayFragment.OnRestTimeUpListener {


    private NonSwipeableViewPager mViewPager;
    private PlayWorkoutAdapter adapter;
    private int currentPage;
    private List<WorkoutComponent> componentList;
    private Workout workout;

    public PlayWorkout(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_workout);

        workout = Workout.toWorkoutObject(getIntent().getStringExtra("workout"));
        componentList = workout.getBlocksForWorkoutDisplay();
        currentPage = getIntent().getIntExtra("startingPage",0);

        App.setCurrentWorkout(workout);
        App.setCurrentWorkoutCurrentPage(currentPage);


        adapter = new PlayWorkoutAdapter(componentList,getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        final CustomOnPageChangeListener customOnPageChangeListener = new CustomOnPageChangeListener();
        mViewPager.addOnPageChangeListener(customOnPageChangeListener);

        if(currentPage != 0){
            mViewPager.post(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem(currentPage,false);
                }
            });
        }else{
            mViewPager.post(new Runnable() {
                @Override
                public void run() {
                    customOnPageChangeListener.onPageSelected(currentPage);

                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_workout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.abortWorkout){
            abortConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void abortConfirmationDialog(){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Abort Workout")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        App.setCurrentWorkout(null);
                        App.setCurrentWorkoutCurrentPage(-1);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setMessage("Are you sure you want to abort this workout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onFragmentInteraction() {
        mViewPager.setCurrentItem(++currentPage,true);
        if(currentPage == componentList.size()){
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public void onTimesUp() {
        mViewPager.setCurrentItem(++currentPage,true);
        if(currentPage == componentList.size()){
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    public WorkoutComponent getNextExercise() {
        int index = mViewPager.getCurrentItem()+2;
        if(index < componentList.size()){
            return componentList.get(index);
        }else{
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        App.setCurrentWorkoutCurrentPage(currentPage);
        setResult(RESULT_CANCELED);
        finish();
    }

    class CustomOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            if(adapter.getItem(position) instanceof RestDisplayFragment){
                RestDisplayFragment restDisplayFragment = (RestDisplayFragment) adapter.getItem(position);
                restDisplayFragment.handleCountdown();
            }
        }
    }
}
