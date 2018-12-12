package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

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



}
