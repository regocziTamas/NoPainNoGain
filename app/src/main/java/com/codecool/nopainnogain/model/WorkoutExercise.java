package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



public class WorkoutExercise extends WorkoutComponent{

    int reps;
    Exercise exercise;



    public WorkoutExercise(int reps, Exercise exercise) {
        this.reps = reps;
        this.exercise = exercise;
    }

    public WorkoutExercise(){}

    public int getReps() {
        return reps;
    }

    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public String toString() {
        return reps + " x " + exercise.getName();
    }



    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}


