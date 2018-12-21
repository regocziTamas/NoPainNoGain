package com.codecool.nopainnogain;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codecool.nopainnogain.model.WorkoutComponent;
import com.codecool.nopainnogain.model.WorkoutExercise;

import java.util.Locale;


public class RestDisplayFragment extends Fragment {

    private OnRestTimeUpListener mListener;
    private long duration;
    private TextView timeLeft;
    private CountDownTimer timer;
    private ProgressBar progressBar;
    private TextView nextExerciseTextView;

    public RestDisplayFragment() {
        // Required empty public constructor
    }


    public static RestDisplayFragment newInstance(int durationInMilis) {
        RestDisplayFragment fragment = new RestDisplayFragment();
        Bundle args = new Bundle();
        args.putInt("duration",durationInMilis);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.duration = getArguments().getInt("duration");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rest_display, container, false);

        this.timeLeft = view.findViewById(R.id.restDuration);
        this.progressBar = view.findViewById(R.id.timeLeft);

        nextExerciseTextView = view.findViewById(R.id.nextExercise);
        WorkoutComponent component = mListener.getNextExercise();
        if(component instanceof WorkoutExercise && component != null){
            WorkoutExercise ex = (WorkoutExercise) component;
            nextExerciseTextView.setText("Coming up next:\n"+ ex.getReps() + " repetitions of " + ex.getExercise());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRestTimeUpListener) {
            mListener = (OnRestTimeUpListener) context;
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


    public interface OnRestTimeUpListener {
        void onTimesUp();
        WorkoutComponent getNextExercise();
    }

    public void handleCountdown(){
        timeLeft.setText(String.valueOf(duration/1000));
        startAnimation();
        timer = new CountDownTimer(duration,10) {
            @Override
            public void onTick(long l) {
                float currentTime = l/1000F;
                timeLeft.setText(String.format(Locale.ENGLISH,"%.2f",currentTime));
            }

            @Override
            public void onFinish() {
                timeLeft.setText("0");
                mListener.onTimesUp();
            }
        };

        timer.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer != null){
            timer.cancel();
        }
    }


    private void startAnimation() {
        int width = progressBar.getWidth();
        progressBar.setMax(width);

        ValueAnimator animator = ValueAnimator.ofInt(width, 0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setStartDelay(0);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                progressBar.setProgress(value);
            }
        });

        animator.start();
    }
}
