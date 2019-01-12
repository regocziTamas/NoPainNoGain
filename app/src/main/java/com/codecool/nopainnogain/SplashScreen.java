package com.codecool.nopainnogain;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codecool.nopainnogain.dataaccess.DataAccess;
import com.codecool.nopainnogain.dataaccess.DatabaseDataAccess;
import com.codecool.nopainnogain.model.Rest;
import com.codecool.nopainnogain.model.Workout;
import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutExercise;
import com.codecool.nopainnogain.sync.AllUpdatesDoneCallback;

public class SplashScreen extends AppCompatActivity {

    private DatabaseDataAccess dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setTheme(R.style.AppTheme_NoActionBar);
        final Intent intent = new Intent(SplashScreen.this,MainActivity.class);

        dao = DatabaseDataAccess.getInstance();
        dao.syncAllDatabases(new AllUpdatesDoneCallback() {
            @Override
            public void onAllUpdatesDone() {
                intent.putExtra("successfulUpdate", true);
                /*addTestWorkouts();
                addMoreTestWorkouts();*/
                startActivity(intent);
                finish();
            }

            @Override
            public void notAllUpdatesSucceeded() {
                intent.putExtra("successfulUpdate", false);
                startActivity(intent);
                finish();
            }
        });

    }

    private void addTestWorkouts(){
        WorkoutExercise wex1 = new WorkoutExercise(10,dao.getAllExercises().get(4));
        WorkoutExercise wex2 = new WorkoutExercise(10,dao.getAllExercises().get(5));
        WorkoutExercise wex3 = new WorkoutExercise(10,dao.getAllExercises().get(6));

        Rest rest1 = new Rest(3000);
        Rest rest2 = new Rest(3000);

        WorkoutBlock wb1 = new WorkoutBlock();
        wb1.addComponent(wex1);
        wb1.addComponent(rest1);
        wb1.addComponent(wex2);
        wb1.addComponent(rest2);
        wb1.addComponent(wex3);

        Workout testWorkout = new Workout("Short Test Workout");
        testWorkout.addBlock(wb1);

        dao.saveWorkout(testWorkout);

    }

    private void addMoreTestWorkouts(){
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
        /*wb1.addComponent(rest5);*/


        WorkoutBlock wb2 = new WorkoutBlock();
        wb2.addComponent(wex6);
        wb2.addComponent(rest7);
        wb2.addComponent(wex7);
        wb2.addComponent(rest8);
        wb2.addComponent(wex8);
        wb2.addComponent(rest9);
        wb2.addComponent(wex9);
        /*wb2.addComponent(rest10);*/

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
//        wb3.addComponent(rest14);

        WorkoutBlock wb4 = new WorkoutBlock();
        wb4.addComponent(wex14);
        wb4.addComponent(rest15);
        wb4.addComponent(wex15);
        wb4.addComponent(rest16);
        wb4.addComponent(wex16);
        wb4.addComponent(rest17);
        wb4.addComponent(wex17);
        /*wb4.addComponent(rest18);*/

        Workout workout2 = new Workout("Good ol' triceps and back workout");

        workout2.addBlock(wb3);
        workout2.addBlock(wb4);

        dao.saveWorkout(workout2);

    }
}
