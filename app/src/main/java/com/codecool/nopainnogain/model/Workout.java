package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Workout implements Parcelable{
    private static Long idCounter = 0L;


    private String title;
    private List<WorkoutBlock> blocks = new ArrayList<>();
    private Long id;


    public Workout(String title) {
        id = idCounter;
        idCounter++;
        this.title = title;
    }


    public void addBlock(WorkoutBlock block){
        blocks.add(block);
    }

    public List<WorkoutBlock> getBlocksForListing(){
        return blocks;
    }

    public List<WorkoutComponent> getBlocksForWorkoutDisplay(){
        List<WorkoutComponent> components = new ArrayList<>();

        for(int i = 0; i < blocks.size(); i++){
            /*if(i == 0){
                components.add(new WorkoutStart());
            }else{
                components.add(new WorkoutBlockStart());
            }*/
            components.addAll(blocks.get(i).getComponents());
        }
        return components;
    }

    @Override
    public String toString() {
        String toString = "\n";

        for(WorkoutBlock block: blocks){
            toString += block.toString();
        }

        return toString;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void replaceBlockById(Long id, WorkoutBlock newBlock){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).getId().equals(id)){
                blocks.set(i,newBlock);
            }
        }
    }

    /*Parcelable stuff below*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeTypedList(blocks);
    }

    protected Workout(Parcel in) {
        id = in.readLong();
        title = in.readString();
        blocks = in.createTypedArrayList(WorkoutBlock.CREATOR);
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };
}
