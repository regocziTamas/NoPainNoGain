package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkoutBlock{


    private List<WorkoutExercise> exercises = new ArrayList<>();
    private List<Rest> rests = new ArrayList<>();
    private List<WorkoutComponent> components = new ArrayList<>();
    private int order;
    private static ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping();

    public WorkoutBlock(){

    }

    public void addComponent(WorkoutComponent component){
        component.setOrder(components.size());
        components.add(component);
    }

    public void replaceExerciseByOrder(int order, WorkoutExercise newExercise){
        WorkoutExercise toDelete = null;
        for(WorkoutExercise ex: exercises){
            if(ex.getOrder() == order){
                toDelete = ex;
            }
        }
        if (toDelete != null){
            /*System.out.println("replace happening");*/
            /*System.out.println(toDelete);
            System.out.println(newExercise);*/
            exercises.remove(toDelete);
            exercises.add(order,newExercise);
        }


    }

    public List<WorkoutComponent> getComponents(){
        Collections.sort(components,new WorkoutComponentComparator());
        return components;
    }


    @Override
    public String toString() {
        String toString = "";

        for(WorkoutComponent comp: getComponents()){
            toString+=comp.toString()+"\n";
        }
        return toString;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int id) {
        this.order = id;
    }

    public static String toJsonString(WorkoutBlock block){
        String string = null;
        try {
            string = objectMapper.writeValueAsString(block);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }

    public static WorkoutBlock toWorkoutBlockObject(String string){
        WorkoutBlock block = null;
        try {
            block = objectMapper.readValue(string,WorkoutBlock.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return block;
    }

    public void reorderComponents(){
        for(int i = 0; i < getComponents().size(); i++){
            WorkoutComponent component = getComponents().get(i);
            component.setOrder(i);
        }
    }

    public void cloneComponent(WorkoutComponent component){
        if(component instanceof WorkoutExercise){
            WorkoutExercise ex = (WorkoutExercise) component;
            addComponent(new WorkoutExercise(ex.getReps(),ex.getExercise()));
        }else if(component instanceof Rest){
            Rest rest = (Rest) component;
            addComponent(new Rest(rest.getDurationInMilis()));
        }
    }

    public void swapTwoComponents(int order1, int order2){
        for(WorkoutComponent component: getComponents()){
            if(component.getOrder() == order1){
                component.setOrder(order2);
            }else if(component.getOrder() == order2){
                component.setOrder(order1);
            }
        }
    }

    public void deleteComponentByOrder(int i){
        for(WorkoutComponent component: components){
            if(component.getOrder() == i){
                components.remove(component);
                reorderComponents();
            }
        }

    }

    class WorkoutComponentComparator implements java.util.Comparator<WorkoutComponent>{

        @Override
        public int compare(WorkoutComponent t1, WorkoutComponent t2) {
            return t1.getOrder() - t2.getOrder();
        }
    }
}
