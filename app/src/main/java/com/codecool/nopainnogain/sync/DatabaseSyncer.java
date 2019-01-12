package com.codecool.nopainnogain.sync;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.util.ExerciseListConverter;
import com.codecool.nopainnogain.util.WorkoutListConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseSyncer {
    private int ALL_UPDATES_COUNT = 4;

    private String baseUrl = "https://0ba1bd5e.ngrok.io";
    private DatabaseSyncFileHandler fileHandler;
    private Context context;
    private RequestQueue queue;
    private DefaultErrorListener defaultErrorListener;
    private DataAccess dao;
    private int currentUpdatesDone = 0;
    private AllUpdatesDoneCallback callback = null;
    private boolean anUpdateFailed = false;

    public DatabaseSyncer(Context context, DataAccess dao) {
        this.context = context;
        fileHandler = new DatabaseSyncFileHandler(context);
        queue = Volley.newRequestQueue(context);
        defaultErrorListener = new DefaultErrorListener();
        this.dao = dao;
    }


    public void updateExercises(){
        Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/exercise-updates/" + String.valueOf(timestamp);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Exercise> updatedExercises = ExerciseListConverter.convertStringToExerciseList(response);
                if(updatedExercises.isEmpty()){
                    System.out.println("No Exercises to update");
                }else{
                    System.out.println("Updating exercises: " + updatedExercises);
                    for(Exercise exercise: updatedExercises){
                        dao.saveExercise(exercise);
                    }
                }
                currentUpdatesDone++;
                if(currentUpdatesDone == ALL_UPDATES_COUNT){
                    handleAllUpdatesDone();
                }
            }
        }, defaultErrorListener);
        queue.add(request);
    }

    public void updateWorkouts(){
        Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/workout-updates/" + String.valueOf(timestamp);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Workout> updatedWorkouts = WorkoutListConverter.convertStringToWorkoutList(response);
                if(updatedWorkouts.isEmpty()){
                    System.out.println("No Workouts to update");
                }else {
                    System.out.println("Updating workouts: " + updatedWorkouts);
                    for(Workout workout: updatedWorkouts){
                        dao.saveSyncedWorkout(workout);
                    }
                }
                currentUpdatesDone++;
                if(currentUpdatesDone == ALL_UPDATES_COUNT){
                    handleAllUpdatesDone();
                }
            }
        },defaultErrorListener);
        queue.add(request);
    }

    public void updateDeletedWorkouts(){
        final Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/workout-deletes/" + String.valueOf(timestamp);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Long> ids = new ArrayList<>();
                try {
                    ids = new ObjectMapper()
                            .enableDefaultTyping()
                            .readValue(response,new TypeReference<List<Long>>() { });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(ids.isEmpty()){
                    System.out.println("No workout-delete updates");
                }else{
                    System.out.println("Deleting workout with id: " + ids);
                    for(Long id: ids){
                        dao.deleteWorkoutById(id);
                    }
                }
                currentUpdatesDone++;
                if(currentUpdatesDone == ALL_UPDATES_COUNT){
                    handleAllUpdatesDone();
                }
            }
        },defaultErrorListener);

        queue.add(request);
    }

    public void updateDeletedExercises(){
        final Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/exercise-deletes/" + String.valueOf(timestamp);


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Long> ids = new ArrayList<>();
                try {
                    ids = new ObjectMapper()
                            .enableDefaultTyping()
                            .readValue(response,new TypeReference<List<Long>>() { });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(ids.isEmpty()){
                    System.out.println("No exercise-delete updates");
                }else{
                    System.out.println("Deleting exercises with id: " + ids);
                    for(Long id: ids){
                        dao.deleteExerciseById(id);
                    }
                }
                currentUpdatesDone++;
                if(currentUpdatesDone == ALL_UPDATES_COUNT){
                    handleAllUpdatesDone();
                }
            }
        },defaultErrorListener);

        queue.add(request);

    }

    public void syncDatabases(AllUpdatesDoneCallback callback){
        this.callback = callback;
        updateExercises();
        updateWorkouts();
        updateDeletedExercises();
        updateDeletedWorkouts();
    }

    private void handleAllUpdatesDone(){

        if(!anUpdateFailed){
            fileHandler.updateLastUpdatedTimestamp(System.currentTimeMillis() - 15000);
            callback.onAllUpdatesDone();
        }else{
            callback.notAllUpdatesSucceeded();
        }

        currentUpdatesDone = 0;
        anUpdateFailed = false;
        callback = null;
    }

    private class DefaultErrorListener implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {

            error.printStackTrace();
            currentUpdatesDone++;
            anUpdateFailed = true;
            if(currentUpdatesDone == ALL_UPDATES_COUNT){
                handleAllUpdatesDone();
            }
        }
    }
}
