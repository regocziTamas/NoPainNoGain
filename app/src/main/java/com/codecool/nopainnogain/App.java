package com.codecool.nopainnogain;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.codecool.nopainnogain.dataaccess.ExerciseDao;
import com.codecool.nopainnogain.dataaccess.WorkoutDao;
import com.codecool.nopainnogain.dataaccess.WorkoutDatabase;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.ExerciseTarget;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class App extends Application {

    private static WorkoutDatabase db;
    private static ExerciseDao exerciseDao;
    private static WorkoutDao workoutDao;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("BAAAAZZZDMEEEEEEEEEEEEEEEG");
        Exercise ex1 = new Exercise("Push-up", "You do some push-ups, it is easy", ExerciseTarget.Chest);
        Exercise ex2 = new Exercise("Barbell Curl", "Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.", ExerciseTarget.Biceps);
        Exercise ex3 = new Exercise("Bench Dip", "For this exercise you will need to place a bench behind your back. With the bench perpendicular to your body, and while looking away from it, hold on to the bench on its edge with the hands fully extended, separated at shoulder width. The legs will be extended forward, bent at the waist and perpendicular to your torso. This will be your starting position.", ExerciseTarget.Triceps);
        Exercise ex4 = new Exercise("Regular Grip Front Lat Pulldown", "Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.", ExerciseTarget.Back);
        Exercise ex5 = new Exercise("Ab Roller", "Slowly roll the ab roller straight forward, stretching your body into a straight position. Tip: Go down as far as you can without touching the floor with your body. Breathe in during this portion of the movement.", ExerciseTarget.Abs);
        Exercise ex6 = new Exercise("Barbell Full Squat", "This exercise is best performed inside a squat rack for safety purposes. To begin, first set the bar on a rack just above shoulder level. Once the correct height is chosen and the bar is loaded, step under the bar and place the back of your shoulders (slightly below the neck) across it.", ExerciseTarget.Quads);
        Exercise ex7 = new Exercise("Standing Military Press", "Start by placing a barbell that is about chest high on a squat rack. Once you have selected the weights, grab the barbell using a pronated (palms facing forward) grip. Make sure to grip the bar wider than shoulder width apart from each other.", ExerciseTarget.Shoulders);
        Exercise ex8 = new Exercise("Seated Calf Raise", "Place your lower thighs under the lever pad, which will need to be adjusted according to the height of your thighs. Now place your hands on top of the lever pad in order to prevent it from slipping forward.", ExerciseTarget.Calves);
        Exercise ex9 = new Exercise("Cable Wrist Curl", "Use your arms to grab the cable bar with a narrow to shoulder width supinated grip (palms up) and bring them up so that your forearms are resting against the top of your thighs. Your wrists should be hanging just beyond your knees.",ExerciseTarget.Forearms);

        Rest rest = new Rest(1000);

        db = Room.databaseBuilder(getApplicationContext(),WorkoutDatabase.class,"nopainnogain").allowMainThreadQueries().build();

        exerciseDao = db.exerciseDao();
        workoutDao = db.workoutDao();

        workoutDao.deleteAllWorkouts();
        exerciseDao.deleteAllExercises();


        exerciseDao.insertExercise(ex1);

        List<Exercise> list = exerciseDao.getAllExercises();

        WorkoutExercise wex1 = new WorkoutExercise(10,ex1);
        WorkoutExercise wex2 = new WorkoutExercise(10,ex2);

        WorkoutBlock block = new WorkoutBlock();

        block.addComponent(rest);
        block.addComponent(wex1);
        block.addComponent(wex2);

        Workout newWorkout = new Workout("Eddki edzés");
        newWorkout.addBlock(block);
        newWorkout.addBlock(block);

        workoutDao.insertWorkout(newWorkout);


        System.out.println(workoutDao.getAllWorkouts());

        /*List<WorkoutBlock> listblock = new ArrayList<>();*//*
        listblock.add(block);
        listblock.add(block);*/

        /*Gson gson = new Gson();
        String string = gson.toJson(listblock);

        List<WorkoutBlock> fromJson = gson.fromJson(string, new TypeToken<List<WorkoutBlock>>(){}.getType());
        System.out.println("Kabbe " + fromJson);*/




    }
}
