package com.codecool.nopainnogain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.codecool.nopainnogain.model.Rest;

public class AddRest extends AppCompatActivity {

    NumberPicker minutes;
    NumberPicker seconds;
    int initialDuration;
    Button setButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rest);
        Intent intent = getIntent();
        initialDuration = intent.getIntExtra("initialDuration",1000);


        minutes = findViewById(R.id.minutes);
        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        minutes.setOnValueChangedListener(new CustomOnValueChangedListener());

        seconds = findViewById(R.id.seconds);
        if(initialDuration<=60000){
            seconds.setMinValue(1);
        }else{
            seconds.setMinValue(0);
        }
        seconds.setMaxValue(59);
        seconds.setFormatter(new CustomFormatter());

        setInitialValues();

        setButton = findViewById(R.id.setRestButton);
        setButton.setOnClickListener(new CustomOnClickListener());

    }

    private void setInitialValues() {
        int milis = initialDuration;

        int seconds = (milis / 1000) % 60 ;
        int minutes = ((milis / (1000*60)) % 60);

        System.out.println(seconds);
        System.out.println(minutes);

        this.minutes.setValue(minutes);
        this.seconds.setValue(seconds);


    }

    private int getNewValue(){
        return minutes.getValue()*60000+seconds.getValue()*1000;
    }

    class CustomFormatter implements NumberPicker.Formatter{

        @Override
        public String format(int i) {
            if(i < 10){
                return "0" + String.valueOf(i);
            }else{
                return String.valueOf(i);
            }
        }
    }

    class CustomOnValueChangedListener implements NumberPicker.OnValueChangeListener{

        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            if(i1 > 0){
                seconds.setMinValue(0);
            }else{
                seconds.setMinValue(1);
            }
        }
    }

    class CustomOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            intent.putExtra("newDuration",getNewValue());
            setResult(RESULT_OK,intent);
            finish();
        }
    }




}
