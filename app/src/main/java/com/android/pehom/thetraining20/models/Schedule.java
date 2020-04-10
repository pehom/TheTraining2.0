package com.android.pehom.thetraining20.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private String title;
    private List<Exercise> exercises;
    private List<TrainingDay> trainingDays;
    private int daysCompleted;

    private static String toStringDivider = ".I.";

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
        String r = title + toStringDivider + Converter.exercisesToString(exercises) + toStringDivider
                + Converter.trainingDaysToString(trainingDays) + toStringDivider+ daysCompleted;
        Log.d("mylog", "Schedule.toString = " + r);
        return r;
    }

    public static Schedule fromString(String data) {
        Log.d("scheduleFromString", "data = "+data);

        Schedule schedule = new Schedule();
        String[] splittedData = data.split(toStringDivider);
        if (splittedData.length == 4) {
            Log.d("scheduleFromString", "splittedData[0] = " + splittedData[0]);
            Log.d("scheduleFromString", "splittedData[1] = " + splittedData[1]);
            Log.d("scheduleFromString", "splittedData[2] = " + splittedData[2]);
            Log.d("scheduleFromString", "splittedData[3] = " + splittedData[3]);


            schedule.setTitle(splittedData[0].trim());

            schedule.setDaysCompleted(Integer.parseInt(splittedData[3].trim()));
            List<Exercise> exercises = Converter.exercisesFromString(splittedData[1]);
            List<TrainingDay> trainingDays = Converter.trainingDaysFromString(splittedData[2]);
            schedule.setExercises(exercises);
            schedule.setTrainingDays(trainingDays);
        }
        return schedule;
    }
}
