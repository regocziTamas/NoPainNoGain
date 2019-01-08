package com.codecool.nopainnogain.util;

import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExerciseListConverter {

    private static ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();


    public static List<Exercise> convertStringToExerciseList(String jsonString){
        List<Exercise> result = new ArrayList<>();
        try {
            List<Exercise> blocks = objectMapper.readValue(jsonString,new TypeReference<List<Exercise>>() { });
            result.addAll(blocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
