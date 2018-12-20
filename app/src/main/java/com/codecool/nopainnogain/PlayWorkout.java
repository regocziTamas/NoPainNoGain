package com.codecool.nopainnogain;


import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentTransaction;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    public PlayWorkout(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_workout);

        Workout workout = Workout.toWorkoutObject(getIntent().getStringExtra("workout"));
        componentList = workout.getBlocksForWorkoutDisplay();
        currentPage = 0;

        adapter = new PlayWorkoutAdapter(componentList,getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        final CustomOnPageChangeListener customOnPageChangeListener = new CustomOnPageChangeListener();
        mViewPager.addOnPageChangeListener(customOnPageChangeListener);

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                customOnPageChangeListener.onPageSelected(0);
            }
        });
    }


    @Override
    public void onFragmentInteraction() {
        mViewPager.setCurrentItem(++currentPage,true);
    }

    @Override
    public void onTimesUp() {
        mViewPager.setCurrentItem(++currentPage,true);
    }

    @Override
    public WorkoutComponent getNextExercise() {
        return componentList.get(mViewPager.getCurrentItem());
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
