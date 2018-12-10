package com.codecool.nopainnogain.util;

import android.arch.persistence.room.TypeConverter;
import com.codecool.nopainnogain.model.ExerciseTarget;

public class EnumConverter {
    @TypeConverter
    public String enumToString(ExerciseTarget target){
        return target.name();
    }

    @TypeConverter
    public ExerciseTarget stringToEnum(String string){
        return ExerciseTarget.valueOf(string);
    }
}
