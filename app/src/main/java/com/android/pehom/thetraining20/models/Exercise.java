package com.android.pehom.thetraining20.models;

import android.util.Log;

public class Exercise {
    private String title;
    private int setsNumber;
    private int set;
    private int setsDone;

    public Exercise(){

    }

    public Exercise(String title, int setsNumber, int set, int setsDone) {
        this.title = title;
        this.setsNumber = setsNumber;
        this.set = set;
        this.setsDone = setsDone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getSetsNumber() {
        return setsNumber;
    }

    public void setSetsNumber(int setsNumber) {
        this.setsNumber = setsNumber;
    }

    public int getSetsDone() {
        return setsDone;
    }

    public void setSetsDone(int setsDone) {
        this.setsDone = setsDone;
    }

    @Override
    public String toString() {
        String s = title + ">>" + setsNumber + ">>" + set + ">>" + setsDone;
        Log.d("mylog", "exercise.toString = " + s);
        return s;
    }

    public static Exercise fromString(String data) {
        String[] splittedData = data.split(">>");
        Exercise exercise = new Exercise(splittedData[0].trim(), Integer.parseInt(splittedData[1].trim())
                , Integer.parseInt(splittedData[2].trim()), Integer.parseInt(splittedData[3].trim()) );
        return exercise;
    }
}
