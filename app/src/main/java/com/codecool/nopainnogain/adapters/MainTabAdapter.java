package com.codecool.nopainnogain.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codecool.nopainnogain.ExerciseBank;
import com.codecool.nopainnogain.MyWorkouts;

public class MainTabAdapter extends FragmentPagerAdapter {

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return MyWorkouts.newInstance();
        }else{
            return ExerciseBank.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
