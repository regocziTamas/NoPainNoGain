package com.codecool.nopainnogain.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WorkoutExercise.class, name = "WorkoutExercise"),
        @JsonSubTypes.Type(value = Rest.class, name = "Rest")
})
public class WorkoutComponent {

    int order;

    public WorkoutComponent(){}

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
