package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rest extends WorkoutComponent{

    int durationInMilis;


    public Rest(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    public Rest(){}

    public int getDurationInMilis() {
        return durationInMilis;
    }

    public void setDurationInMilis(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    @Override
    public String toString() {
        return durationInMilis/1000 + " seconds of rest";
    }


}
