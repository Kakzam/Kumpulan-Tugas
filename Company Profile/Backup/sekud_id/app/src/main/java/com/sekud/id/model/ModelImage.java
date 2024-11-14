package com.sekud.id.model;

import android.net.Uri;

public class ModelImage {

    private String url;
    private Uri uri;

    public ModelImage(String url, Uri uri) {
        this.url = url;
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public Uri getUri() {
        return uri;
    }
}
