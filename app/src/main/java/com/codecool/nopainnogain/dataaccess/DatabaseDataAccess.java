package com.codecool.nopainnogain.dataaccess;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.sync.AllUpdatesDoneCallback;
import com.codecool.nopainnogain.sync.DatabaseSyncer;

import java.util.List;

public class DatabaseDataAccess implements DataAccess {

    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;
    private WorkoutDao syncedWorkoutDao;
    private WorkoutDatabase database;
    private WorkoutDatabase syncedDatabase;
    private DatabaseSyncer syncer;
    private static DatabaseDataAccess instance = null;

    private DatabaseDataAccess(Context applicationContext){
        database = Room.databaseBuilder(applicationContext ,WorkoutDatabase.class,"nopainnogain").allowMainThreadQueries().build();
        syncedDatabase = Room.databaseBuilder(applicationContext,WorkoutDatabase.class,"nopainnogainsync").allowMainThreadQueries().build();
        exerciseDao = syncedDatabase.exerciseDao();
        syncedWorkoutDao = syncedDatabase.workoutDao();
        workoutDao = database.workoutDao();
        syncer = new DatabaseSyncer(applicationContext,this);

        /*workoutDao.deleteAllWorkouts();
        exerciseDao.deleteAllExercises();
        syncedWorkoutDao.deleteAllWorkouts();*/
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

    public void syncAllDatabases(AllUpdatesDoneCallback callback){
        syncer.syncDatabases(callback);
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
    public List<Workout> getAllSyncedWorkouts() {
        return syncedWorkoutDao.getAllWorkouts();
    }

    @Override
    public Workout findSyncedWorkoutById(long id) {
        return syncedWorkoutDao.getWorkoutById(id);
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
    public void saveSyncedWorkout(Workout workout){
        long id = syncedWorkoutDao.insertWorkout(workout);
        if(id == -1){
            syncedWorkoutDao.updateWorkout(workout);
        }
    }

    @Override
    public void deleteExerciseById(Long id) {
        exerciseDao.deleteExerciseById(id);
    }

    @Override
    public void deleteWorkoutById(Long id) {
        workoutDao.deleteWorkout(id);
    }



    @Override
    public Exercise getExerciseById(long id) {
        return exerciseDao.getExerciseById(id);
    }

    @Override
    public void saveExercise(Exercise exercise) {
        long id = exerciseDao.insertExercise(exercise);
        if(id == -1){
            exerciseDao.updateExercise(exercise);
        }
    }

    @Override
    public Exercise getExerciseByName(String name) {
        return exerciseDao.getExerciseByName(name);
    }


}
