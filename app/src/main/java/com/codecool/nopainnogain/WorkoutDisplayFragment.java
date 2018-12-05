package com.codecool.nopainnogain;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;





public class WorkoutDisplayFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String exerciseName;
    private String reps;

    public WorkoutDisplayFragment() {
        // Required empty public constructor
    }

    public static WorkoutDisplayFragment newInstance(String exerciseName,String reps) {
        WorkoutDisplayFragment fragment = new WorkoutDisplayFragment();
        Bundle args = new Bundle();
        args.putString("exerciseName", exerciseName);
        args.putString("reps", reps);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.exerciseName = getArguments().getString("exerciseName");
        this.reps = getArguments().getString("reps");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_workout_display, container, false);

        TextView exerciseName = view.findViewById(R.id.workoutDisplayExerciseName);
        exerciseName.setText(this.exerciseName);

        TextView reps = view.findViewById(R.id.workoutDisplayReps);
        reps.setText(this.reps + " repetitions of");

        Button doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onFragmentInteraction();
            }
        });
        return view;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
