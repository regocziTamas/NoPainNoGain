package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rest extends WorkoutComponent{

    int durationInMilis;
    int order;

    public Rest(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    public int getDurationInMilis() {
        return durationInMilis;
    }

    public void setDurationInMilis(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return durationInMilis/1000 + " seconds of rest";
    }


}
