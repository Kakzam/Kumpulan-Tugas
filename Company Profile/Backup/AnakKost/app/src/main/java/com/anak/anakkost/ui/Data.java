package com.anak.anakkost.ui;

public class Data {

    private String judul;
    private String harga;
    private String deskripsi;
    private String lokasi;
    private String gambar;
    private String delete;

    public Data(String judul, String harga, String deskripsi, String lokasi, String gambar, String delete) {
        this.judul = judul;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.gambar = gambar;
        this.delete = delete;
    }

    public String getJudul() {
        return judul;
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

    public String getGambar() {
        return gambar;
    }

    public String getDelete() {
        return delete;
    }
}
