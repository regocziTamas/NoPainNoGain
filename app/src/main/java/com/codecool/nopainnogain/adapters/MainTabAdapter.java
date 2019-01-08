package com.codecool.nopainnogain.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codecool.nopainnogain.ExerciseBank;
import com.codecool.nopainnogain.MyWorkouts;
import com.codecool.nopainnogain.WorkoutStore;

public class MainTabAdapter extends FragmentPagerAdapter {

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return MyWorkouts.newInstance();
        }else if(position == 1){
            return ExerciseBank.newInstance();
        }else {
            return WorkoutStore.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
