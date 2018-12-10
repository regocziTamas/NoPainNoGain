package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;




public class WorkoutExercise extends WorkoutComponent implements Parcelable{

    int reps;
    Exercise exercise;


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
        return reps + " x " + exercise.toString();
    }



    /*Parcelable stuff below*/

    protected WorkoutExercise(Parcel in) {
        super(in);
        reps = in.readInt();
        exercise = in.readParcelable(Exercise.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(1);
        super.writeToParcel(dest, flags);
        dest.writeInt(reps);
        dest.writeParcelable(exercise, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WorkoutExercise> CREATOR = new Creator<WorkoutExercise>() {
        @Override
        public WorkoutExercise createFromParcel(Parcel in) {
            return new WorkoutExercise(in);
        }

        @Override
        public WorkoutExercise[] newArray(int size) {
            return new WorkoutExercise[size];
        }
    };




}


