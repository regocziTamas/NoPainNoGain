package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Rest extends WorkoutComponent implements Parcelable{

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

    /*Parceable stuff below*/

    protected Rest(Parcel in) {
        super(in);
        durationInMilis = in.readInt();
        order = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(2);
        super.writeToParcel(dest, flags);
        dest.writeInt(durationInMilis);
        dest.writeInt(order);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Rest> CREATOR = new Creator<Rest>() {
        @Override
        public Rest createFromParcel(Parcel in) {
            return new Rest(in);
        }

        @Override
        public Rest[] newArray(int size) {
            return new Rest[size];
        }
    };
}
