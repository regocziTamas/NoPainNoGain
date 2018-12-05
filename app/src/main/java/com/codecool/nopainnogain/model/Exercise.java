package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Exercise implements Parcelable{

    private static Long idCounter = 0L;

    private String name;
    private String description;

    private ExerciseTarget target;

    private Long id;

    public Exercise(String name, String description, ExerciseTarget target) {
        this.name = name;
        this.description = description;
        this.target = target;
        this.id = idCounter;
        idCounter++;
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

    @Override
    public String toString() {
        return name;
    }

    /*Parcelable stuff below*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(target.name());
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Exercise(Parcel in) {
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        target = ExerciseTarget.valueOf(in.readString());
    }



    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };
}
