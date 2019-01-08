package com.codecool.nopainnogain;


import android.animation.ValueAnimator;
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

import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecool.nopainnogain.adapters.PlayWorkoutAdapter;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.codecool.nopainnogain.util.NonSwipeableViewPager;


import java.util.List;

public class PlayWorkout extends AppCompatActivity implements WorkoutDisplayFragment.OnExerciseDoneListener, RestDisplayFragment.OnRestTimeUpListener {


    private NonSwipeableViewPager mViewPager;
    private PlayWorkoutAdapter adapter;
    private int currentPage;
    private int originalPageNumbering;
    private int originalWorkoutComponentCount;
    private CustomOnPageChangeListener customOnPageChangeListener;
    private List<WorkoutComponent> componentList;
    private Workout workout;
    private boolean continued;
    private ProgressBar overallProgress;
    private TextView numericProgressDisplay;

    public PlayWorkout(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_workout);

        workout = Workout.toWorkoutObject(getIntent().getStringExtra("workout"));
        originalWorkoutComponentCount = workout.getBlocksForWorkoutDisplay().size();

        overallProgress = findViewById(R.id.workoutProgressBar);
        numericProgressDisplay = findViewById(R.id.numericProgress);

        numericProgressDisplay.setText("0/"+String.valueOf(originalWorkoutComponentCount));

        currentPage = getIntent().getIntExtra("startingPage",0);
        originalPageNumbering = currentPage;
        if(currentPage == 0){
            continued = false;
        }else{
            continued = true;
        }
        componentList = workout.getBlocksForWorkoutDisplay().subList(currentPage,workout.getBlocksForWorkoutDisplay().size());
        currentPage = 0;

        App.setCurrentWorkout(workout);
        App.setCurrentWorkoutCurrentPage(currentPage);


        adapter = new PlayWorkoutAdapter(componentList,getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        customOnPageChangeListener = new CustomOnPageChangeListener();
        mViewPager.addOnPageChangeListener(customOnPageChangeListener);


        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                customOnPageChangeListener.onPageSelected(currentPage);

            }
        });



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
                        App.setCurrentRestTimeLeft(-1L);
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
    public void onExerciseDone() {
        mViewPager.setCurrentItem(++currentPage,true);
        originalPageNumbering++;
        numericProgressDisplay.setText(getNumericProgressString());
        setOverallProgressBar();
        if(currentPage == componentList.size()){
            finishActivityWithDelay(500);
        }
    }

    @Override
    public void onTimesUp() {
        mViewPager.setCurrentItem(++currentPage,true);
        originalPageNumbering++;
        numericProgressDisplay.setText(getNumericProgressString());
        setOverallProgressBar();
        if(currentPage == componentList.size()){
            finishActivityWithDelay(500);
        }
    }

    private void finishActivityWithDelay(long delay){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setResult(RESULT_OK);
                finish();
            }
        },delay);
    }

    private String getNumericProgressString(){
        return String.valueOf(originalPageNumbering) + "/" + String.valueOf(originalWorkoutComponentCount);
    }

    private void setOverallProgressBar(){
        double percentage = (double) originalPageNumbering /(double) originalWorkoutComponentCount;
        double maxWidth = overallProgress.getWidth();
        double currentWidth = maxWidth * percentage;
        System.out.println(percentage);
        percentage = percentage * 100D;


        ValueAnimator animator = ValueAnimator.ofInt(overallProgress.getProgress(),(int)percentage);
        animator.setInterpolator(new LinearInterpolator());
        animator.setStartDelay(0);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                overallProgress.setProgress(value);
            }
        });

        animator.start();




    }

    @Override
    public WorkoutComponent getNextExercise() {
        int index = currentPage+1;
        for(int i = index; i < componentList.size(); i++){
            if(componentList.get(i) instanceof WorkoutExercise){
                return componentList.get(i);
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        App.setCurrentWorkoutCurrentPage(originalPageNumbering);
        if(adapter.getItem(currentPage) instanceof RestDisplayFragment){
            RestDisplayFragment currentRest = (RestDisplayFragment) adapter.getItem(currentPage);
            App.setCurrentRestTimeLeft(currentRest.getCurrentTimeLeft());
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    class CustomOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener{
        @Override
        public void onPageSelected(int position) {
            if(adapter.getItem(position) instanceof RestDisplayFragment){
                RestDisplayFragment restDisplayFragment = (RestDisplayFragment) adapter.getItem(position);
                if(position == 0 && continued){
                    restDisplayFragment.continueRest(App.getCurrentRestTimeLeft());
                }else{
                    restDisplayFragment.handleCountdown(1);
                }
            }
        }
    }
}
