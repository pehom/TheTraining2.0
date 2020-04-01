package com.android.pehom.thetraining20.models;

import android.widget.TextView;

import java.util.List;

public class TrainingDay {
  //  private TextView thisDayTextView;
    private List<Exercise> exercises;
    private int thisDayNumber;
    private boolean isDone;

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
}
