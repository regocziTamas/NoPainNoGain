package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codecool.nopainnogain.util.DatabaseHelper;
import com.codecool.nopainnogain.util.SerializationUtility;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@DatabaseTable(tableName = "workout")
public class Workout implements Parcelable{


    @DatabaseField
    private String title;
    @ForeignCollectionField(eager = true, maxEagerLevel = 4)
    private ForeignCollection<WorkoutBlock> blocks;
    @DatabaseField(generatedId = true, columnName = "workout_id")
    private Long id;


    public Workout(String title) {
        this.title = title;
    }

    public Workout(){

    }


    public void addBlock(WorkoutBlock block){
        blocks.add(block);
    }

    public List<WorkoutBlock> getBlocksForListing(){
        ArrayList<WorkoutBlock> temp = new ArrayList<>(blocks);
        return temp;
    }

    public List<WorkoutComponent> getBlocksForWorkoutDisplay(){

        Iterator<WorkoutBlock> it = blocks.iterator();
        List<WorkoutComponent> temp = new ArrayList<>();

        while(it.hasNext()){
             temp.addAll(it.next().getComponents());
        }

        return temp;
    }

    @Override
    public String toString() {
        String toString = title + "\n";

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

    public static Workout newInstance(String title){
        return DatabaseHelper.getNewWorkout(new Workout(title));
    }

    /*public void replaceBlockById(Long id, WorkoutBlock newBlock){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).getId().equals(id)){
                blocks.set(i,newBlock);
            }
        }

    }*/

    /*Parcelable stuff below*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        try{
            parcel.writeString(SerializationUtility.toString((Serializable)blocks));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    protected Workout(Parcel in) {
        id = in.readLong();
        title = in.readString();
        try{
            blocks = (ForeignCollection<WorkoutBlock>) SerializationUtility.fromString(in.readString());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
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
