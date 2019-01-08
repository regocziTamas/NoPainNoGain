package com.codecool.nopainnogain.sync;

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

    public DatabaseSyncer(Context context) {
        this.context = context;
        fileHandler = new DatabaseSyncFileHandler(context);
    }


    public void updateExercises(final ExerciseUpdateCallback callback){
        Long timestamp = fileHandler.getLastUpdateTimestamp();
        String url = baseUrl + "/exercise-updates/" + String.valueOf(timestamp);
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                fileHandler.updateLastUpdatedTimestamp(System.currentTimeMillis());
                callback.onExerciseUpdate(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);

    }
}
