package com.companyprofile.alzigugun.another;

public class ModelBerita {
    String id;
    String judul;
    String berita;

    public ModelBerita(String id, String judul, String berita) {
        this.id = id;
        this.judul = judul;
        this.berita = berita;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getBerita() {
        return berita;
    }
}
