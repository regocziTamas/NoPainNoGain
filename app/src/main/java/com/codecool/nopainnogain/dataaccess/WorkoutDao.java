package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertWorkout(Workout workout);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public int updateWorkout(Workout workout);

    @Query("SELECT * FROM workout")
    public List<Workout> getAllWorkouts();

    @Query("DELETE FROM workout")
    public void deleteAllWorkouts();

    @Query("SELECT * FROM workout WHERE id LIKE :searchedId")
    public Workout getWorkoutById(Long searchedId);

    @Query("DELETE FROM workout WHERE id LIKE :id")
    public void deleteWorkout(Long id);
}
