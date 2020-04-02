package com.android.pehom.thetraining20.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Converter {
    private static String exercisesToStringDivider = "&&&*";
    private static String trainingDaysToStringDivider = "%%%%";

    public static String exercisesToString (List<Exercise> exercises){
        String s = "";
        for (int i=0;i<exercises.size();i++) {
            s+=exercises.get(i).toString() + exercisesToStringDivider;
        }
        return s;
    }

    public static List<Exercise> exercisesFromString (String data) {
        String[] splittedData = data.split(exercisesToStringDivider);
        List<Exercise> exercises = new ArrayList<>();
        for (int i=0; i< splittedData.length-1; i++){
            Exercise exercise = Exercise.fromString(splittedData[i].trim());
            exercises.add(exercise);
        }
        return exercises;
    }

    public static String trainingDaysToString (List<TrainingDay> trainingDays) {
        String s = "";
        for (int i=0;i<trainingDays.size();i++) {
            s+=trainingDays.get(i).toString() + trainingDaysToStringDivider;
        }
        return s;
    }

    public static List<TrainingDay> trainingDaysFromString (String data) {
        String[] splittedData = data.split(trainingDaysToStringDivider);
        Log.d("fromString", "trainingDaysFromString data ="+ data);
        for (int i=0;i < splittedData.length; i++) {
            Log.d("fromString", "trainingDaysFromString splittedData["+i+"] =" + splittedData[i]);
        }
        List<TrainingDay> trainingDays = new ArrayList<>();
        for (int i=0; i< splittedData.length; i++){
            TrainingDay trainingDay = TrainingDay.fromString(splittedData[i].trim());
            trainingDays.add(trainingDay);
        }
        return trainingDays;
    }
}
