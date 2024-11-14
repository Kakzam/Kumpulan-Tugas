package com.shop.amku.other;

import android.graphics.Bitmap;

public class Shop {

    private String id;
    private String nama;
    private String status;
    private String harga;
    private String deskripsi;
    private String dilihat;
    private Bitmap photo;

    public Shop(String id, String nama, String status, String harga, String deskripsi, String dilihat, Bitmap photo) {
        this.id = id;
        this.nama = nama;
        this.status = status;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.dilihat = dilihat;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }

    public String getHarga() {
        return harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getDilihat() {
        return dilihat;
    }

    public Bitmap getPhoto() {
        return photo;
    }

}
