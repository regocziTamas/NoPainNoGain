package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.codecool.nopainnogain.util.DatabaseHelper;
import com.codecool.nopainnogain.util.SerializationUtility;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorkoutBlock implements Parcelable{

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "workout_block_id")
    private Long id;
    @ForeignCollectionField(eager = true, maxEagerLevel = 4)
    private ForeignCollection<WorkoutExercise> exercises;
    @ForeignCollectionField(eager = true, maxEagerLevel = 4)
    private ForeignCollection<Rest> rests;
    @DatabaseField
    private int order;




    public void addComponent(WorkoutComponent component){

        if(component instanceof WorkoutExercise){
            WorkoutExercise temp = (WorkoutExercise) component;
            temp.setOrder(getNextOrder());
            exercises.add(temp);
        }else if(component instanceof Rest){
            Rest temp = (Rest) component;
            temp.setOrder(getNextOrder());
            rests.add(temp);
        }
    }

    public List<WorkoutComponent> getComponents(){
        List<WorkoutComponent> temp = new ArrayList<>();
        temp.addAll(exercises);
        temp.addAll(rests);
        Collections.sort(temp,new WorkoutComponentComparator());
        return temp;
    }

    private int getNextOrder(){
        return exercises.size() + rests.size();
    }

    public static WorkoutBlock newInstance(){
        return DatabaseHelper.getNewWorkoutBlock();
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

    public WorkoutBlock(){

    }

    class WorkoutComponentComparator implements java.util.Comparator<WorkoutComponent>{

        @Override
        public int compare(WorkoutComponent t1, WorkoutComponent t2) {
            int t1Order;
            if(t1 instanceof WorkoutExercise){
                t1Order = ((WorkoutExercise) t1).getOrder();
            } else{
                t1Order = ((Rest) t1).getOrder();
            }
            int t2Order;
            if(t2 instanceof WorkoutExercise){
                t2Order = ((WorkoutExercise) t2).getOrder();
            } else{
                t2Order = ((Rest) t2).getOrder();
            }
            return t1Order - t2Order;
        }
    }


    /*Parcelable stuff below*/

    @SuppressWarnings("unchecked")
    protected WorkoutBlock(Parcel in) {
        id = in.readLong();
        order = in.readInt();
        try{
            exercises = (ForeignCollection<WorkoutExercise>) SerializationUtility.fromString(in.readString());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        try{
            rests = (ForeignCollection<Rest>) SerializationUtility.fromString(in.readString());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
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
        parcel.writeInt(order);
        if(exercises instanceof Serializable){
            try{
                parcel.writeString(SerializationUtility.toString((Serializable)exercises));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if(rests instanceof Serializable){
            try{
                parcel.writeString(SerializationUtility.toString((Serializable)rests));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
