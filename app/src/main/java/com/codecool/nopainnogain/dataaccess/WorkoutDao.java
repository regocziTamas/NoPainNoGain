package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert
    public void insertWorkout(Workout workout);

    @Query("SELECT * FROM workout")
    public List<Workout> getAllWorkouts();

    @Query("DELETE FROM workout")
    public void deleteAllWorkouts();
}
