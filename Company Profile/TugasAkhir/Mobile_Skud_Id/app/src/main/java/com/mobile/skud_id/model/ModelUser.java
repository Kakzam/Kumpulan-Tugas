package com.mobile.skud_id.model;

public class ModelUser {

    private String id;
    private String username;
    private String password;
    private String phone;
    private String image;
    private String permission;

    public ModelUser(String id, String username, String password, String phone, String image, String permission) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.image = image;
        this.permission = permission;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }

    public String getPermission() {
        return permission;
    }
}
