package com.codecool.nopainnogain.util;

import android.arch.persistence.room.TypeConverter;

import com.codecool.nopainnogain.model.WorkoutBlock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class WorkoutBlockListConverter {
    @TypeConverter
    public String fromBlockToString(List<WorkoutBlock> blocks){
        Gson gson = new Gson();
        return gson.toJson(blocks);
    }

    @TypeConverter
    public List<WorkoutBlock> fromStringToWorkoutBlockList(String string){
        Gson gson = new Gson();
        return gson.fromJson(string, new TypeToken<List<WorkoutBlock>>(){}.getType());
    }
}
