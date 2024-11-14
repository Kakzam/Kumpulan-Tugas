package com.mobile.skud_id.model;

public class ModelLocation {

    private String id;
    private String title;
    private String maps;

    public ModelLocation(String id, String title, String maps) {
        this.id = id;
        this.title = title;
        this.maps = maps;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMaps() {
        return maps;
    }
}
