package com.companyprofile.ristianingsih.model;

public class History {

    private int year;
    private String History;

    public History(int year, String history) {
        this.year = year;
        History = history;
    }

    public int getYear() {
        return year;
    }

    public String getHistory() {
        return History;
    }
}
