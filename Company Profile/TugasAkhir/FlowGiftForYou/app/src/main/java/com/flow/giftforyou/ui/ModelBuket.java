package com.flow.giftforyou.ui;

public class ModelBuket {

    private String id;
    private String judul;
    private String deskripsi;
    private String wa;
    private String jpeg;

    public ModelBuket(String id, String judul, String deskripsi, String wa, String jpeg) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.wa = wa;
        this.jpeg = jpeg;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getWa() {
        return wa;
    }

    public String getJpeg() {
        return jpeg;
    }
}
