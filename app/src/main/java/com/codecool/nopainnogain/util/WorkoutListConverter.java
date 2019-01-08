package com.codecool.nopainnogain.util;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Workout;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkoutListConverter {

    private static ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();


    public static List<Workout> convertStringToWorkoutList(String jsonString){
        List<Workout> result = new ArrayList<>();
        try {
            List<Workout> blocks = objectMapper.readValue(jsonString,new TypeReference<List<Workout>>() { });
            result.addAll(blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}
