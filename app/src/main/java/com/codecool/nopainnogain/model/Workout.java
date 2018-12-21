package com.codecool.nopainnogain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity(tableName = "workout")
public class Workout{


    private String title;
    private List<WorkoutBlock> blocks = new ArrayList<>();
    @PrimaryKey(autoGenerate = true)
    private Long id;


    public Workout(String title) {

        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addBlock(WorkoutBlock block){
        block.setOrder(blocks.size());
        blocks.add(block);
    }

    public List<WorkoutBlock> getBlocksForListing(){
        Collections.sort(blocks, new WorkoutBlockComparator());
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

    public boolean isEmpty(){
        boolean empty = true;
        for(WorkoutBlock block: blocks){
            if(!block.getComponents().isEmpty()){
                empty = false;
                break;
            }
        }
        return empty;
    }

    public List<WorkoutBlock> getBlocks() {
        return getBlocksForListing();
    }

    public void setBlocks(List<WorkoutBlock> blocks) {
        this.blocks = blocks;
    }

    public static String toJsonString(Workout workout){
        return new Gson().toJson(workout);
    }

    public static Workout toWorkoutObject(String string){
        return new Gson().fromJson(string,new TypeToken<Workout>(){}.getType());
    }

    @Override
    public String toString() {
        String toString = "ID: " + id + "\n";

        for(WorkoutBlock block: blocks){
            toString += block.toString();
        }

        return toString;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void replaceBlockById(int id, WorkoutBlock newBlock){
        for(int i = 0; i < blocks.size(); i++){
            if(blocks.get(i).getOrder() == id){
                blocks.set(i,newBlock);
            }
        }
    }



    class WorkoutBlockComparator implements Comparator<WorkoutBlock> {

        @Override
        public int compare(WorkoutBlock block, WorkoutBlock t1) {
            return block.getOrder() - t1.getOrder();
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
