package com.codecool.nopainnogain.util;

import android.content.Context;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.codecool.nopainnogain.R;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String DATABASE_NAME = "workout.db";
    private static final int DATABASE_VERSION = 4;

    private Dao<Exercise,Long> exerciseDao = null;
    private Dao<WorkoutExercise,Long> workoutExerciseDao = null;
    private Dao<Rest,Long> restDao = null;
    private Dao<WorkoutBlock,Long> workoutBlockDao = null;

    private static Dao<WorkoutExercise,Long> staticWexDao = null;
    private static Dao<WorkoutBlock,Long> staticWorkoutBlockDao = null;
    private static Dao<Exercise,Long> staticExerciseDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        try {
            staticWexDao = getDao(WorkoutExercise.class);
            staticWorkoutBlockDao = getDao(WorkoutBlock.class);
            staticExerciseDao = getDao(Exercise.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Exercise.class);
            TableUtils.createTable(connectionSource, WorkoutExercise.class);
            TableUtils.createTable(connectionSource, Rest.class);
            TableUtils.createTable(connectionSource, WorkoutBlock.class);
        }catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try{
            TableUtils.dropTable(connectionSource,Exercise.class,true);
            TableUtils.dropTable(connectionSource,WorkoutExercise.class,true);
            TableUtils.dropTable(connectionSource,Rest.class,true);
            TableUtils.dropTable(connectionSource,WorkoutBlock.class,true);
        }catch (SQLException e){
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + i + " to new "
                    + i1, e);
        }
    }

    public Dao<Exercise,Long> getExercisesDao() throws SQLException{
        if(exerciseDao == null){
            exerciseDao = getDao(Exercise.class);
        }
        return exerciseDao;
    }

    public Dao<WorkoutExercise,Long> getWorkoutExerciseDao() throws SQLException{
        if(workoutExerciseDao == null){
            workoutExerciseDao = getDao(WorkoutExercise.class);
        }

        return workoutExerciseDao;
    }

    public Dao<Rest,Long> getRestDao() throws SQLException{
        if(restDao == null){
            restDao = getDao(Rest.class);
        }
        return restDao;
    }

    public Dao<WorkoutBlock,Long> getWorkoutBlockDao() throws SQLException{
        if(workoutBlockDao == null){
            workoutBlockDao = getDao(WorkoutBlock.class);
        }
        return workoutBlockDao;
    }

    public static Dao<WorkoutExercise,Long> getStaticWexDao(){
        return staticWexDao;
    }

    public static Dao<Exercise,Long> getStaticExDao(){
        return staticExerciseDao;
    }

    public static WorkoutBlock getNewWorkoutBlock(){
        WorkoutBlock newBlock = new WorkoutBlock();
        WorkoutBlock newBlockAfterSQL = null;
        try {
            staticWorkoutBlockDao.create(newBlock);
            Long id = staticWorkoutBlockDao.extractId(newBlock);
            newBlockAfterSQL = staticWorkoutBlockDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newBlockAfterSQL;


    }




    public void clearTables(){
        try{
            TableUtils.clearTable(getConnectionSource(),Exercise.class);
            TableUtils.clearTable(getConnectionSource(),WorkoutExercise.class);
            TableUtils.clearTable(getConnectionSource(),WorkoutBlock.class);
            TableUtils.clearTable(getConnectionSource(),Rest.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
