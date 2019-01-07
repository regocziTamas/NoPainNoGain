package com.codecool.nopainnogain.util;

import android.arch.persistence.room.TypeConverter;

import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class WorkoutBlockListConverter {
    @TypeConverter
    public String fromBlockToString(List<WorkoutBlock> blocks){
        RuntimeTypeAdapterFactory<WorkoutComponent> adapter = RuntimeTypeAdapterFactory
                .of(WorkoutComponent.class)
                .registerSubtype(WorkoutExercise.class)
                .registerSubtype(Rest.class);
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();
        return gson.toJson(blocks);
    }

    @TypeConverter
    public List<WorkoutBlock> fromStringToWorkoutBlockList(String string){
        RuntimeTypeAdapterFactory<WorkoutComponent> adapter = RuntimeTypeAdapterFactory
                .of(WorkoutComponent.class)
                .registerSubtype(WorkoutExercise.class)
                .registerSubtype(Rest.class);
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(adapter).create();
        return gson.fromJson(string, new TypeToken<List<WorkoutBlock>>(){}.getType());
    }
}
