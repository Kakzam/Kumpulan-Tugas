package com.sekud.id.model;

public class ModelUser {

    private String id;
    private String name;
    private String username;
    private String password;
    private String status;
    private String image;

    public ModelUser(String id, String name, String username, String password, String status, String image) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.status = status;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }
}
