package com.android.pehom.thetraining20.models;

import android.widget.TextView;

import java.util.List;

public class TrainingDay {
    private List<Exercise> exercises;
    private boolean isDone;
    private int thisDayNumber;

    public TrainingDay(List<Exercise> exercises, boolean isDone, int thisDayNumber) {
        this.exercises = exercises;
        this.isDone = isDone;
        this.thisDayNumber = thisDayNumber;
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
}
