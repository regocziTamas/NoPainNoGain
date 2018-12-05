package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "workoutexercise")
public class WorkoutExercise extends WorkoutComponent implements Parcelable{

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "workoutExerciseId")
    long id;
    @DatabaseField
    int reps;
    @DatabaseField(canBeNull = false,foreign = true,foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 10)
    Exercise exercise;
    @DatabaseField(foreign = true)
    WorkoutBlock containingBlock;
    @DatabaseField
    int order;


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
        return reps + " x " + exercise.toString();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /*Parcelable stuff below*/

    protected WorkoutExercise(Parcel in) {
        super(in);
        id = in.readLong();
        order = in.readInt();
        reps = in.readInt();
        exercise = in.readParcelable(Exercise.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(1);
        super.writeToParcel(dest, flags);
        dest.writeLong(id);
        dest.writeInt(order);
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


