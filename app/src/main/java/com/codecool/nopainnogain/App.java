package com.codecool.nopainnogain;

import android.app.Application;
import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.model.Exercise;
import com.codecool.nopainnogain.model.ExerciseTarget;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutExercise;


import java.util.Arrays;


public class App extends Application {

    DataAccess dao;

    @Override
    public void onCreate() {
        super.onCreate();
        Exercise ex1 = new Exercise("Push-up", "You do some push-ups, it is easy", ExerciseTarget.Chest);
        Exercise ex2 = new Exercise("Barbell Curl", "Stand up with your torso upright while holding a barbell at a shoulder-width grip. The palm of your hands should be facing forward and the elbows should be close to the torso. This will be your starting position.", ExerciseTarget.Biceps);
        Exercise ex3 = new Exercise("Bench Dip", "For this exercise you will need to place a bench behind your back. With the bench perpendicular to your body, and while looking away from it, hold on to the bench on its edge with the hands fully extended, separated at shoulder width. The legs will be extended forward, bent at the waist and perpendicular to your torso. This will be your starting position.", ExerciseTarget.Triceps);
        Exercise ex4 = new Exercise("Regular Grip Front Lat Pulldown", "Sit down on a pull-down machine with a wide bar attached to the top pulley. Make sure that you adjust the knee pad of the machine to fit your height. These pads will prevent your body from being raised by the resistance attached to the bar.", ExerciseTarget.Back);
        Exercise ex5 = new Exercise("Ab Roller", "Slowly roll the ab roller straight forward, stretching your body into a straight position. Tip: Go down as far as you can without touching the floor with your body. Breathe in during this portion of the movement.", ExerciseTarget.Abs);
        Exercise ex6 = new Exercise("Barbell Full Squat", "This exercise is best performed inside a squat rack for safety purposes. To begin, first set the bar on a rack just above shoulder level. Once the correct height is chosen and the bar is loaded, step under the bar and place the back of your shoulders (slightly below the neck) across it.", ExerciseTarget.Quads);
        Exercise ex7 = new Exercise("Standing Military Press", "Start by placing a barbell that is about chest high on a squat rack. Once you have selected the weights, grab the barbell using a pronated (palms facing forward) grip. Make sure to grip the bar wider than shoulder width apart from each other.", ExerciseTarget.Shoulders);
        Exercise ex8 = new Exercise("Seated Calf Raise", "Place your lower thighs under the lever pad, which will need to be adjusted according to the height of your thighs. Now place your hands on top of the lever pad in order to prevent it from slipping forward.", ExerciseTarget.Calves);
        Exercise ex9 = new Exercise("Cable Wrist Curl", "Use your arms to grab the cable bar with a narrow to shoulder width supinated grip (palms up) and bring them up so that your forearms are resting against the top of your thighs. Your wrists should be hanging just beyond your knees.", ExerciseTarget.Forearms);


        DatabaseDataAccess.initializeDataAccess(getApplicationContext());
        dao = DatabaseDataAccess.getInstance();

        for (Exercise e : Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9)) {
            dao.saveExercise(e);
        }

        System.out.println(dao.getAllExercises());

        WorkoutExercise wex1 = new WorkoutExercise(10, dao.getAllExercises().get(0));
        WorkoutExercise wex2 = new WorkoutExercise(10, dao.getAllExercises().get(1));
        WorkoutExercise wex3 = new WorkoutExercise(10, dao.getAllExercises().get(2));
        WorkoutExercise wex4 = new WorkoutExercise(10, dao.getAllExercises().get(3));

        Rest rest1 = new Rest(5000);

        WorkoutBlock wb1 = new WorkoutBlock();
        wb1.addComponent(rest1);
        wb1.addComponent(wex1);
        wb1.addComponent(rest1);
        wb1.addComponent(wex1);
        wb1.addComponent(rest1);
        wb1.addComponent(wex1);
        wb1.addComponent(rest1);
        wb1.addComponent(wex1);
        wb1.addComponent(rest1);

        WorkoutBlock wb2 = new WorkoutBlock();
        wb2.addComponent(wex2);
        wb2.addComponent(rest1);
        wb2.addComponent(wex2);
        wb2.addComponent(rest1);
        wb2.addComponent(wex2);
        wb2.addComponent(rest1);
        wb2.addComponent(wex2);
        wb2.addComponent(rest1);

        Workout workout1 = new Workout("Chest and Biceps workout");

        workout1.addBlock(wb1);
        workout1.addBlock(wb2);

        dao.saveWorkout(workout1);

        /*WorkoutExercise wex3 = new WorkoutExercise(15, ex3);
        WorkoutExercise wex4 = new WorkoutExercise(15, ex4);*/

        WorkoutBlock wb3 = new WorkoutBlock();
        wb3.addComponent(wex3);
        wb3.addComponent(rest1);
        wb3.addComponent(wex3);
        wb3.addComponent(rest1);
        wb3.addComponent(wex3);
        wb3.addComponent(rest1);
        wb3.addComponent(wex3);
        wb3.addComponent(rest1);

        WorkoutBlock wb4 = new WorkoutBlock();
        wb4.addComponent(wex4);
        wb4.addComponent(rest1);
        wb4.addComponent(wex4);
        wb4.addComponent(rest1);
        wb4.addComponent(wex4);
        wb4.addComponent(rest1);
        wb4.addComponent(wex4);
        wb4.addComponent(rest1);

        Workout workout2 = new Workout("Good ol' triceps and back workout");

        workout2.addBlock(wb3);
        workout2.addBlock(wb4);

        dao.saveWorkout(workout2);

        System.out.println(workout1);
        System.out.println(workout2);


    }
}
