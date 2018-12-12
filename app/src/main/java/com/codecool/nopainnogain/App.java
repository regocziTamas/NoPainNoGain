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
import com.google.gson.Gson;


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

        WorkoutExercise wex2 = new WorkoutExercise(10, dao.getAllExercises().get(0));
        WorkoutExercise wex3 = new WorkoutExercise(10, dao.getAllExercises().get(0));
        WorkoutExercise wex4 = new WorkoutExercise(10, dao.getAllExercises().get(0));
        WorkoutExercise wex5 = new WorkoutExercise(10, dao.getAllExercises().get(0));
        WorkoutExercise wex6 = new WorkoutExercise(10, dao.getAllExercises().get(2));
        WorkoutExercise wex7 = new WorkoutExercise(10, dao.getAllExercises().get(2));
        WorkoutExercise wex8 = new WorkoutExercise(10, dao.getAllExercises().get(2));
        WorkoutExercise wex9 = new WorkoutExercise(10, dao.getAllExercises().get(2));
        WorkoutExercise wex10 = new WorkoutExercise(10, dao.getAllExercises().get(3));
        WorkoutExercise wex11 = new WorkoutExercise(10, dao.getAllExercises().get(3));
        WorkoutExercise wex12 = new WorkoutExercise(10, dao.getAllExercises().get(3));
        WorkoutExercise wex13 = new WorkoutExercise(10, dao.getAllExercises().get(3));
        WorkoutExercise wex14 = new WorkoutExercise(10, dao.getAllExercises().get(4));
        WorkoutExercise wex15 = new WorkoutExercise(10, dao.getAllExercises().get(4));
        WorkoutExercise wex16 = new WorkoutExercise(10, dao.getAllExercises().get(4));
        WorkoutExercise wex17 = new WorkoutExercise(10, dao.getAllExercises().get(4));

        Rest rest2 = new Rest(5000);
        Rest rest3 = new Rest(5000);
        Rest rest4 = new Rest(5000);
        Rest rest5 = new Rest(5000);
        Rest rest7 = new Rest(5000);
        Rest rest8 = new Rest(5000);
        Rest rest9 = new Rest(5000);
        Rest rest10 = new Rest(5000);
        Rest rest11 = new Rest(5000);
        Rest rest12 = new Rest(5000);
        Rest rest13 = new Rest(5000);
        Rest rest14 = new Rest(5000);
        Rest rest15 = new Rest(5000);
        Rest rest16 = new Rest(5000);
        Rest rest17 = new Rest(5000);
        Rest rest18 = new Rest(5000);


        WorkoutBlock wb1 = new WorkoutBlock();
        wb1.addComponent(wex2);
        wb1.addComponent(rest2);
        wb1.addComponent(wex3);
        wb1.addComponent(rest3);
        wb1.addComponent(wex4);
        wb1.addComponent(rest4);
        wb1.addComponent(wex5);
        wb1.addComponent(rest5);

        System.out.println(wb1);

        WorkoutBlock wb2 = new WorkoutBlock();
        wb2.addComponent(wex6);
        wb2.addComponent(rest7);
        wb2.addComponent(wex7);
        wb2.addComponent(rest8);
        wb2.addComponent(wex8);
        wb2.addComponent(rest9);
        wb2.addComponent(wex9);
        wb2.addComponent(rest10);

        Workout workout1 = new Workout("Chest and Biceps workout");

        workout1.addBlock(wb1);
        workout1.addBlock(wb2);

        dao.saveWorkout(workout1);

        WorkoutBlock wb3 = new WorkoutBlock();
        wb3.addComponent(wex10);
        wb3.addComponent(rest11);
        wb3.addComponent(wex11);
        wb3.addComponent(rest12);
        wb3.addComponent(wex12);
        wb3.addComponent(rest13);
        wb3.addComponent(wex13);
        wb3.addComponent(rest14);

        WorkoutBlock wb4 = new WorkoutBlock();
        wb4.addComponent(wex14);
        wb4.addComponent(rest15);
        wb4.addComponent(wex15);
        wb4.addComponent(rest16);
        wb4.addComponent(wex16);
        wb4.addComponent(rest17);
        wb4.addComponent(wex17);
        wb4.addComponent(rest18);

        Workout workout2 = new Workout("Good ol' triceps and back workout");

        workout2.addBlock(wb3);
        workout2.addBlock(wb4);

        dao.saveWorkout(workout2);



    }
}
