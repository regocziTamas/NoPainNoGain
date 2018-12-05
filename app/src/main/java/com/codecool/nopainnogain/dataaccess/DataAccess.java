package com.codecool.nopainnogain.dataaccess;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;

import java.util.List;

public interface DataAccess {

    public List<Workout> getAllWorkouts();

    public Workout findWorkoutById(long id);

    public List<Exercise> getAllExercises();

    public void saveWorkout(Workout workout);

    public Exercise getExerciseById(long id);

}
