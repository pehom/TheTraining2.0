package com.android.pehom.thetraining20.models;

public class Exercise {
    private String title;
    private int rowCount;

    public Exercise(String title, int rowCount) {
        this.title = title;
        this.rowCount = rowCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
