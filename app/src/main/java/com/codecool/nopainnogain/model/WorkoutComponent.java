package com.codecool.nopainnogain.model;


import android.os.Parcel;
import android.os.Parcelable;

public abstract class WorkoutComponent implements Parcelable {
    String placeholder = "placeholder";

    public WorkoutComponent(){}



    /*Parcelable stuff below*/

    protected WorkoutComponent(Parcel in) {
        placeholder = in.readString();
    }

    public static final Creator<WorkoutComponent> CREATOR = new Creator<WorkoutComponent>() {
        @Override
        public WorkoutComponent createFromParcel(Parcel in) {
            int type = in.readInt();
            if (type == 1){
                return new WorkoutExercise(in);
            }else if(type == 2){
                return new Rest(in);
            }
            return null;
        }

        @Override
        public WorkoutComponent[] newArray(int size) {
            return new WorkoutComponent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(placeholder);
    }

    @Override
    public String toString() {
        return placeholder;
    }
}
