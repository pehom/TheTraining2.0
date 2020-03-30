package com.android.pehom.thetraining20.models;

import java.util.List;

public class Schedule {
    private String title;
    private List<TrainingDay> trainingDays;

    public Schedule(String title, List<TrainingDay> trainingDays) {
        this.title = title;
        this.trainingDays = trainingDays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TrainingDay> getTrainingDays() {
        return trainingDays;
    }

    public void setTrainingDays(List<TrainingDay> trainingDays) {
        this.trainingDays = trainingDays;
    }
}
