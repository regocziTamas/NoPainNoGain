package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class WorkoutBlock implements Parcelable{
    private static Long idCounter = 0L;

    private List<WorkoutComponent> components = new ArrayList<>();
    private Long id;

    public void addComponent(WorkoutComponent component){
        components.add(component);
    }

    public List<WorkoutComponent> getComponents(){
        return components;
    }

    @Override
    public String toString() {
        String toString = "";

        for(WorkoutComponent comp: components){
            toString+=comp.toString()+"\n";
        }
        return toString;
    }

    public Long getId() {
        return id;
    }

    /*Parcelable stuff below*/

    public WorkoutBlock(){
        id = idCounter;
        idCounter++;
    }

    protected WorkoutBlock(Parcel in) {
        id = in.readLong();
        components = in.createTypedArrayList(WorkoutComponent.CREATOR);
    }

    public static final Creator<WorkoutBlock> CREATOR = new Creator<WorkoutBlock>() {
        @Override
        public WorkoutBlock createFromParcel(Parcel in) {
            return new WorkoutBlock(in);
        }

        @Override
        public WorkoutBlock[] newArray(int size) {
            return new WorkoutBlock[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeTypedList(components);
    }
}
