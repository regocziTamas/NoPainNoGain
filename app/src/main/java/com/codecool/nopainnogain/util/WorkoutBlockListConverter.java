package com.codecool.nopainnogain.util;

import android.arch.persistence.room.TypeConverter;

import com.codecool.nopainnogain.model.WorkoutBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutBlockListConverter {

    private ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();

    @TypeConverter
    public String fromBlockToString(List<WorkoutBlock> blocks){
        String jsonString = null;
        try {
            jsonString = objectMapper.writerFor(new TypeReference<List<WorkoutBlock>>() { }).writeValueAsString(blocks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    @TypeConverter
    public List<WorkoutBlock> fromStringToWorkoutBlockList(String string){
        List<WorkoutBlock> result = new ArrayList<>();

        try {
            List<WorkoutBlock> blocks = objectMapper.readValue(string,new TypeReference<List<WorkoutBlock>>() { });
            result.addAll(blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
