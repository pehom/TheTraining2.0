package com.android.pehom.thetraining20.models;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TrainingDay {
  //  private TextView thisDayTextView;
    private List<Exercise> exercises;
    private int thisDayNumber;
    private boolean isDone;

    private static String toStringDivider = "!@#";

    public TrainingDay() {
    }

    public TrainingDay(List<Exercise> exercises, int thisDayNumber, boolean isDone) {
        this.exercises = exercises;
        this.thisDayNumber = thisDayNumber;
        this.isDone = isDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getThisDayNumber() {
        return thisDayNumber;
    }

    public void setThisDayNumber(int thisDayNumber) {
        this.thisDayNumber = thisDayNumber;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        String s="";

        s+=Converter.exercisesToString(exercises) + toStringDivider + thisDayNumber + toStringDivider+String.valueOf(isDone);
        return s;
    }

    public static  TrainingDay fromString(String data){
        String[] splittedData = data.split(toStringDivider);
        Log.d("fromString", "TrainingDay.fromString data = " + data );
        for(int j=0; j<splittedData.length;j++){
            Log.d("fromString", "TrainingDay.fromString splittedData["+j+"] = " + splittedData[j] );
        }
        List<Exercise> exercises = Converter.exercisesFromString(splittedData[0].trim());

        int thisDayNumber = Integer.parseInt(splittedData[1].trim());
        boolean isDone = Boolean.parseBoolean(splittedData[2]);
        return new TrainingDay(exercises, thisDayNumber, isDone);
    }
}
