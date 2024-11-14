package com.uti.psqlite;

public class MahasiswaModel {
//    buat variabel
    String npm, nama, jurusan;

//    buat method
//    method "setXXX" >> untuk isi data
//    method "getXXX" >> untuk ambil data

    void setNPM(String npm)
    {
        this.npm = npm;
    }

    void setNama(String nama)
    {
        this.nama = nama;
    }

    void setJurusan(String jurusan)
    {
        this.jurusan = jurusan;
    }

    String getNPM() {
        return npm;
    }
    String getNama() {
        return nama;
    }
    String getJurusan() {
        return jurusan;
    }
}
