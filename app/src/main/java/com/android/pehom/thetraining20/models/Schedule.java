package com.android.pehom.thetraining20.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String title;
    private List<Exercise> exercises;
    private List<TrainingDay> trainingDays;
    private int daysCompleted;

    public Schedule() {
    }

    public Schedule(String title, List<Exercise> exercises, List<TrainingDay> trainingDays, int daysCompleted) {
        this.title = title;
        this.exercises = exercises;
        this.trainingDays = trainingDays;
        this.daysCompleted = daysCompleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public int getDaysCompleted() {
        return daysCompleted;
    }

    public void setDaysCompleted(int daysCompleted) {
        this.daysCompleted = daysCompleted;
    }

    public List<TrainingDay> getTrainingDays() {
        return trainingDays;
    }

    public void setTrainingDays(List<TrainingDay> trainingDays) {
        this.trainingDays = trainingDays;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i=0; i< exercises.size(); i++){
            s+=exercises.get(i).toString() + "(.)(.)";
        }
        String r = title + "(.)(.)" + s + daysCompleted;
        Log.d("mylog", "Schedule.toString = " + r);
        return r;
    }

    public static Schedule fromString(String data) {
        Schedule schedule = new Schedule();
        String[] splittedData = data.split("(.)(.)");
        schedule.setTitle(splittedData[0].trim());
        schedule.setDaysCompleted(Integer.parseInt(splittedData[splittedData.length-1].trim()));
        List<Exercise> exercises = new ArrayList<>();
        for (int i=1;i<splittedData.length-1;i++) {
            if (splittedData[i] !=null) {
                Exercise exercise = Exercise.fromString(splittedData[i].trim());
                exercises.add(exercise);
            }
        }
        schedule.setExercises(exercises);
        return schedule;
    }
}
