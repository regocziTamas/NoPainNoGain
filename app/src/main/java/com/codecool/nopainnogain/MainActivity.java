package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.codecool.nopainnogain.adapters.MainTabAdapter;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.dataaccess.InMemoryDataAccess;
import com.codecool.nopainnogain.model.Workout;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private MainTabAdapter mainTabAdapter;
    private DataAccess dao;
    private Menu menu;

    private int PLAY_WORKOUT_REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean successfulUpdate = getIntent().getBooleanExtra("successfulUpdate",false);
        String snackbarString;
        if(successfulUpdate){
            snackbarString = "All databases have been synced";
        }else{
            snackbarString = "Not all databases could be synced, please try again";
        }

        Snackbar sn = Snackbar.make(findViewById(android.R.id.content),snackbarString,Snackbar.LENGTH_LONG);
        sn.show();

        mainTabAdapter = new MainTabAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mainTabAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        dao = DatabaseDataAccess.getInstance();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.continueInProgressWorkout){
            Intent intent = new Intent(MainActivity.this,PlayWorkout.class);
            intent.putExtra("startingPage",App.getCurrentWorkoutCurrentPage());
            intent.putExtra("workout", Workout.toJsonString(App.getCurrentWorkout()));
            startActivityForResult(intent,PLAY_WORKOUT_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    private void activateContinueWorkoutButton(){
        MenuItem item = menu.getItem(0);
        item.setVisible(true);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        findViewById(R.id.continueInProgressWorkout).startAnimation(anim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(App.getCurrentWorkout() != null){
            activateContinueWorkoutButton();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PLAY_WORKOUT_REQUEST_CODE){
            if(resultCode == RESULT_CANCELED){
                activateContinueWorkoutButton();
            }else{
                App.setCurrentRestTimeLeft(-1L);
                App.setCurrentWorkout(null);
                App.setCurrentWorkoutCurrentPage(-1);
                menu.getItem(0).setVisible(false);
            }
        }else if(requestCode == 100){
            if(resultCode == RESULT_OK ){
                Workout newWorkout = Workout.toWorkoutObject(data.getStringExtra("newWorkout"));
                dao.saveWorkout(newWorkout);

            }

        }
    }


}
