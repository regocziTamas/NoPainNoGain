package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity(tableName = "exercise")
public class Exercise{



    private String name;
    private String description;

    private ExerciseTarget target;

    @PrimaryKey(autoGenerate = true)
    private Long id;

    public Exercise(String name, String description, ExerciseTarget target) {
        this.name = name;
        this.description = description;
        this.target = target;
    }

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
        return "ID: "+ id + " "+ name;
    }

    public static String toJsonString(Exercise exercise){
        return new Gson().toJson(exercise);
    }

    public static Exercise toExerciseObject(String string){
        return new Gson().fromJson(string,new TypeToken<Exercise>(){}.getType());
    }



}
