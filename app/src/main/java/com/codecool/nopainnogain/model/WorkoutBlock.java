package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutBlock implements Parcelable{


    private List<WorkoutExercise> exercises = new ArrayList<>();
    private List<Rest> rests = new ArrayList<>();
    private Long id;
    private int order;

    public void addComponent(WorkoutComponent component){

        if(component instanceof WorkoutExercise){
            WorkoutExercise temp = (WorkoutExercise) component;
            temp.setOrder(getNextOrder());
            exercises.add(temp);
        } else if( component instanceof Rest){
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /*Parcelable stuff below*/

    public WorkoutBlock(){

    }

    protected WorkoutBlock(Parcel in) {
        id = in.readLong();
        exercises = in.createTypedArrayList(WorkoutExercise.CREATOR);
        rests = in.createTypedArrayList(Rest.CREATOR);
        order = in.readInt();
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
        parcel.writeInt(order);
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
}
