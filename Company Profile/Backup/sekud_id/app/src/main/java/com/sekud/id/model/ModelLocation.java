package com.sekud.id.model;

public class ModelLocation {

    private String id;
    private String location;
    private String title;

    public ModelLocation(String id, String location, String title) {
        this.id = id;
        this.location = location;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }
}
