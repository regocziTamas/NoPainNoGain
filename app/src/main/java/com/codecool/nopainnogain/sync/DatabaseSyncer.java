package com.codecool.nopainnogain.sync;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class DatabaseSyncer {

    String baseUrl = "https://fc8eccdc.ngrok.io";
    DatabaseSyncFileHandler fileHandler;
    Context context;
    RequestQueue queue;
    DefaultErrorListener defaultErrorListener;

    public DatabaseSyncer(Context context) {
        this.context = context;
        fileHandler = new DatabaseSyncFileHandler(context);
        queue = Volley.newRequestQueue(context);
        defaultErrorListener = new DefaultErrorListener();
    }


    public void updateExercises(final ExerciseUpdateCallback callback){
        Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/exercise-updates/" + String.valueOf(timestamp);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fileHandler.updateLastUpdatedTimestamp(System.currentTimeMillis());
                callback.onExerciseUpdate(response);
            }
        }, defaultErrorListener);
        queue.add(request);
    }

    public void updateWorkouts(final WorkoutUpdateCallback callback){
        Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/workout-updates/" + String.valueOf(timestamp);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fileHandler.updateLastUpdatedTimestamp(System.currentTimeMillis());
                callback.onWorkoutUpdate(response);
            }
        },defaultErrorListener);
        queue.add(request);
    }

    private class DefaultErrorListener implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    }
}
