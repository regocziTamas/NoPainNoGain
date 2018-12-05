package com.codecool.nopainnogain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "rest")
public class Rest extends WorkoutComponent implements Parcelable{

    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true, columnName = "rest_id")
    long id;
    @DatabaseField
    int durationInMilis;
    @DatabaseField(foreign = true)
    WorkoutBlock containingBlock;
    @DatabaseField
    int order;

    public Rest(int durationInMilis) {
        this.durationInMilis = durationInMilis;
    }

    public Rest(){};

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /*Parceable stuff below*/

    protected Rest(Parcel in) {
        super(in);
        durationInMilis = in.readInt();
        id = in.readLong();
        order = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(2);
        super.writeToParcel(dest, flags);
        dest.writeInt(durationInMilis);
        dest.writeLong(id);
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
