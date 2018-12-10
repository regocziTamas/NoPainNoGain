package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class WorkoutBlock implements Parcelable{


    private List<WorkoutExercise> exercises = new ArrayList<>();
    private List<Rest> rests = new ArrayList<>();
    private Long id;

    public void addComponent(WorkoutComponent component){

        if(component instanceof WorkoutExercise){
            WorkoutExercise temp = (WorkoutExercise) component;
            exercises.add(temp);
        } else if( component instanceof Rest){
            Rest temp = (Rest) component;
            rests.add(temp);
        }

    }

    public List<WorkoutComponent> getComponents(){
        List<WorkoutComponent> temp = new ArrayList<>();
        temp.addAll(exercises);
        temp.addAll(rests);
        return temp;
    }



    @Override
    public String toString() {
        String toString = "";

        for(WorkoutComponent comp: getComponents()){
            toString+=comp.toString()+"\n";
        }
        return toString;
    }

    public Long getId() {
        return id;
    }

    /*Parcelable stuff below*/

    public WorkoutBlock(){

    }

    protected WorkoutBlock(Parcel in) {
        id = in.readLong();
        exercises = in.createTypedArrayList(WorkoutExercise.CREATOR);
        rests = in.createTypedArrayList(Rest.CREATOR);
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
        parcel.writeTypedList(exercises);
        parcel.writeTypedList(rests);
    }
}
