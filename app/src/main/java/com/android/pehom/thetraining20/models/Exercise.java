package com.android.pehom.thetraining20.models;

import android.util.Log;

public class Exercise {
    private String title;
    private int setsNumber;
    private int set;
    private int setsDone;

    public final static String toStringDivider = ">>";

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
        String s = title + toStringDivider + setsNumber + toStringDivider + set + toStringDivider + setsDone;
        Log.d("mylog", "exercise.toString = " + s);
        return s;
    }

    public static Exercise fromString(String data) {
        Log.d("fromString", "exercise.fromString data = "+data);
        String[] splittedData = data.split(toStringDivider);
        Log.d("fromString", "exercise.fromString splittedData[0] = "+splittedData[0]);
        Log.d("fromString", "exercise.fromString splittedData[1] = "+splittedData[1]);
        Log.d("fromString", "exercise.fromString splittedData[2] = "+splittedData[2]);
        Log.d("fromString", "exercise.fromString splittedData[3] = "+splittedData[3]);
        Exercise exercise = new Exercise(splittedData[0].trim(), Integer.parseInt(splittedData[1].trim())
                , Integer.parseInt(splittedData[2].trim()), Integer.parseInt(splittedData[3].trim()) );
        return exercise;
    }

    public String forPrint () {
        return title + "  " + setsNumber + " X " + set;
    }
}
