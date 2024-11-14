package com.various.bags.ui;

import android.graphics.Bitmap;

public class Model {

    private String id;
    private String namaItem;
    private String harga;
    private String status;
    private String dilihat;
    private Bitmap gambar;
    private String dataGambar;

    public Model(String id, String namaItem, String harga, String status, String dilihat, Bitmap gambar, String dataGambar) {
        this.id = id;
        this.namaItem = namaItem;
        this.harga = harga;
        this.status = status;
        this.dilihat = dilihat;
        this.gambar = gambar;
        this.dataGambar = dataGambar;
    }

    public String getDataGambar() {
        return dataGambar;
    }

    public String getId() {
        return id;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public String getHarga() {
        return harga;
    }

    public String getStatus() {
        return status;
    }

    public String getDilihat() {
        return dilihat;
    }

    public Bitmap getGambar() {
        return gambar;
    }
}
