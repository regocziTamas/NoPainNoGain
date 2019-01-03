package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkoutBlock{


    private List<WorkoutExercise> exercises = new ArrayList<>();
    private List<Rest> rests = new ArrayList<>();
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int id) {
        this.order = id;
    }

    public static String toJsonString(WorkoutBlock block){
        return new Gson().toJson(block);
    }

    public static WorkoutBlock toWorkoutBlockObject(String string){
        return new Gson().fromJson(string,new TypeToken<WorkoutBlock>(){}.getType());
    }

    public void reorderComponents(){
        for(int i = 0; i < getComponents().size(); i++){
            WorkoutComponent component = getComponents().get(i);
            if(component instanceof WorkoutExercise){
                ((WorkoutExercise) component).setOrder(i);
            }else if(component instanceof Rest){
                ((Rest) component).setOrder(i);
            }
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
            if(component instanceof  WorkoutExercise){
                WorkoutExercise ex = (WorkoutExercise) component;
                if(ex.getOrder() == order1){
                    ex.setOrder(order2);
                }else if(ex.getOrder() == order2){
                    ex.setOrder(order1);
                }
            }
            else if(component instanceof Rest){
                Rest rest = (Rest) component;
                if(rest.getOrder() == order1){
                    rest.setOrder(order2);
                }else if(rest.getOrder() == order2){
                    rest.setOrder(order1);
                }
            }
        }
    }

    public void deleteComponentByOrder(int i){
        for(WorkoutExercise ex: exercises){
            if(ex.getOrder() == i){
                exercises.remove(ex);
                reorderComponents();
                return;
            }
        }

        for(Rest rest: rests){
            if(rest.getOrder() == i){
                rests.remove(rest);
                reorderComponents();
                return;
            }
        }
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
