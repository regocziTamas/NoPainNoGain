package com.codecool.nopainnogain;

import android.app.Application;

import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.codecool.nopainnogain.sync.DatabaseSyncer;
import com.codecool.nopainnogain.sync.ExerciseDeleteUpdateCallback;
import com.codecool.nopainnogain.sync.ExerciseUpdateCallback;
import com.codecool.nopainnogain.sync.WorkoutDeleteUpdateCallback;
import com.codecool.nopainnogain.sync.WorkoutUpdateCallback;
import com.codecool.nopainnogain.util.ExerciseListConverter;
import com.codecool.nopainnogain.util.WorkoutListConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class App extends Application {

    DataAccess dao;
    private static Workout currentWorkout = null;
    private static Integer currentWorkoutCurrentPage = -1;
    private static Long currentRestTimeLeft = -1L;

    public static Long getCurrentRestTimeLeft() {
        return currentRestTimeLeft;
    }

    public static void setCurrentRestTimeLeft(Long currentRestTimeLeft) {
        App.currentRestTimeLeft = currentRestTimeLeft;
    }

    public static Workout getCurrentWorkout() {
        return currentWorkout;
    }

    public static void setCurrentWorkout(Workout currentWorkout) {
        App.currentWorkout = currentWorkout;
    }

    public static int getCurrentWorkoutCurrentPage() {
        return currentWorkoutCurrentPage;
    }

    public static void setCurrentWorkoutCurrentPage(int currentWorkoutCurrentPage) {
        App.currentWorkoutCurrentPage = currentWorkoutCurrentPage;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseDataAccess.initializeDataAccess(getApplicationContext());
        dao = DatabaseDataAccess.getInstance();
    }


}
