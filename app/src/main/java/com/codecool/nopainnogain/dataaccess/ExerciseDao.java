package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.util.EnumConverter;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Insert
    public void insertExercise(Exercise exercise);

    @Query("SELECT * FROM exercise")
    public List<Exercise> getAllExercises();

    @Query("DELETE FROM exercise")
    public void deleteAllExercises();

    @Query("SELECT * FROM exercise WHERE id LIKE :searchedId")
    public Exercise getExerciseById(long searchedId);



}
