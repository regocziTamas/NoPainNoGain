package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.util.EnumConverter;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertExercise(Exercise exercise);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public int updateExercise(Exercise exercise);

    @Query("SELECT * FROM exercise")
    public List<Exercise> getAllExercises();

    @Query("DELETE FROM exercise")
    public void deleteAllExercises();

    @Query("SELECT * FROM exercise WHERE id LIKE :searchedId")
    public Exercise getExerciseById(long searchedId);

    @Query("SELECT * FROM exercise WHERE name Like :searchedName")
    public Exercise getExerciseByName(String searchedName);

    @Query("DELETE FROM exercise WHERE id LIKE :id")
    public void deleteExerciseById(Long id);



}
