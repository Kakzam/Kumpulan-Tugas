package com.companyprofile.ristianingsih.model;

public class Awards {

    private String title;
    private String link;

    public Awards(String title, String link) {
        this.link = title;
        this.title = link;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}
