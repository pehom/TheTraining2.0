package com.android.pehom.thetraining20.models;

public class Exercise {
    private String title;
    private int setsNumber;
    private int set;


    public Exercise(String title, int setsNumber, int set) {
        this.title = title;
        this.setsNumber = setsNumber;
        this.set = set;
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
}
