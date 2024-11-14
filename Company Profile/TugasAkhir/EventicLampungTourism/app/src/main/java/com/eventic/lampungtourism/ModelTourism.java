package com.eventic.lampungtourism;

public class ModelTourism {

    private String id;
    private String judul;
    private String description;
    private String price;
    private String type;
    private String image;
    private String image2;

    public ModelTourism(String id, String judul, String description, String price, String type, String image, String image2) {
        this.id = id;
        this.judul = judul;
        this.description = description;
        this.price = price;
        this.type = type;
        this.image = image;
        this.image2 = image2;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getImage2() {
        return image2;
    }
}
