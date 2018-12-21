package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;




public class WorkoutExercise extends WorkoutComponent{

    int reps;
    Exercise exercise;
    int order;


    public WorkoutExercise(int reps, Exercise exercise) {
        this.reps = reps;
        this.exercise = exercise;
    }

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}


