package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.util.EnumConverter;
import com.codecool.nopainnogain.util.WorkoutBlockListConverter;

@Database(entities = {Exercise.class, Workout.class}, version = 2)
@TypeConverters({EnumConverter.class, WorkoutBlockListConverter.class})
public abstract class WorkoutDatabase extends RoomDatabase {

    public abstract ExerciseDao exerciseDao();

    public abstract WorkoutDao workoutDao();

}
