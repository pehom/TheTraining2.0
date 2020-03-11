package com.android.pehom.thetraining20;

import android.widget.TextView;

public class TrainingDay {
    private TextView thisDayTextView;
    private boolean isDone;
    private int thisDayNumber;

    public TrainingDay(TextView thisDayTextView, boolean isDone, int thisDayNumber) {
        this.thisDayTextView = thisDayTextView;
        this.isDone = isDone;
        this.thisDayNumber = thisDayNumber;
    }

    public TrainingDay(TextView thisDayTextView, boolean isDone) {
        this.thisDayTextView = thisDayTextView;
        this.isDone = isDone;
    }

    public TextView getThisDayTextView() {
        return thisDayTextView;
    }

    public void setThisDayTextView(TextView thisDayTextView) {
        this.thisDayTextView = thisDayTextView;
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
