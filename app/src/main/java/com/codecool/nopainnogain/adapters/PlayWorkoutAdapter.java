package com.codecool.nopainnogain.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codecool.nopainnogain.PlayWorkout;
import com.codecool.nopainnogain.RestDisplayFragment;
import com.codecool.nopainnogain.WorkoutDisplayFragment;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;

import java.util.ArrayList;
import java.util.List;

public class PlayWorkoutAdapter extends FragmentPagerAdapter {

    private List<WorkoutComponent> components;
    private List<Fragment> fragments = new ArrayList<>();

    public PlayWorkoutAdapter(List<WorkoutComponent> components, FragmentManager fm){
        super(fm);
        this.components = components;
        populateFragmentList();
    }

    private void populateFragmentList(){
        for(WorkoutComponent comp: components){
            if(comp instanceof WorkoutExercise){
                WorkoutExercise ex = (WorkoutExercise) comp;
                fragments.add(WorkoutDisplayFragment.newInstance(ex.getExercise().getName(),String.valueOf(ex.getReps())));
            }else if(comp instanceof Rest){
                Rest rest = (Rest) comp;
                fragments.add(RestDisplayFragment.newInstance(rest.getDurationInMilis()));
            }
        }
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


}
