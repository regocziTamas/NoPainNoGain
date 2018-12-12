package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;

import java.util.List;

public class DatabaseDataAccess implements DataAccess {

    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;
    private WorkoutDatabase database;
    private static DatabaseDataAccess instance = null;

    private DatabaseDataAccess(Context applicationContext){
        database = Room.databaseBuilder(applicationContext ,WorkoutDatabase.class,"nopainnogain").allowMainThreadQueries().build();
        exerciseDao = database.exerciseDao();
        workoutDao = database.workoutDao();

        workoutDao.deleteAllWorkouts();
        exerciseDao.deleteAllExercises();
    }

    public static void initializeDataAccess(Context applicationContext){
        if(instance != null){
            throw new RuntimeException("Already initialized, use getInstance() method");
        }else{
            instance = new DatabaseDataAccess(applicationContext);
        }
    }

    public static DatabaseDataAccess getInstance(){
        if(instance == null){
            throw new RuntimeException("DatabaseDataAccess instance not initialized, please use initializeDataAccess() method first");
        }else{
            return instance;
        }
    }


    @Override
    public List<Workout> getAllWorkouts() {
        return workoutDao.getAllWorkouts();
    }

    @Override
    public Workout findWorkoutById(long id) {
        return workoutDao.getWorkoutById(id);
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exerciseDao.getAllExercises();
    }

    @Override
    public void saveWorkout(Workout workout) {
        long id = workoutDao.insertWorkout(workout);
        if(id == -1){
            workoutDao.updateWorkout(workout);
        }
    }

    @Override
    public Exercise getExerciseById(long id) {
        return exerciseDao.getExerciseById(id);
    }

    @Override
    public void saveExercise(Exercise exercise) {
        exerciseDao.insertExercise(exercise);
    }


}
