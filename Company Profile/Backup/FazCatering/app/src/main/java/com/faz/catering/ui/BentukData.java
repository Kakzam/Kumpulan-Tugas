package com.faz.catering.ui;

public class BentukData {

    private String catering;
    private String nama;
    private String harga;
    private String deskripsi;
    private String lokasi;
    private String phone;
    private String gambar;

    public BentukData(String catering, String nama, String harga, String deskripsi, String lokasi, String phone, String gambar) {
        this.catering = catering;
        this.nama = nama;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.phone = phone;
        this.gambar = gambar;
    }

    public String getCatering() {
        return catering;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getPhone() {
        return phone;
    }

    public String getGambar() {
        return gambar;
    }
}
