package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

@Entity(tableName = "exercise")
public class Exercise{



    private String name;
    private String description;

    private ExerciseTarget target;

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private static ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();

    public Exercise(String name, String description, ExerciseTarget target) {
        this.name = name;
        this.description = description;
        this.target = target;
    }

    public Exercise(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExerciseTarget getTarget() {
        return target;
    }

    public void setTarget(ExerciseTarget target) {
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public static String toJsonString(Exercise exercise){
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(exercise);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static Exercise toExerciseObject(String string){
        Exercise fromJson = null;
        try {
            fromJson = objectMapper.readValue(string,Exercise.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fromJson;
    }



}
