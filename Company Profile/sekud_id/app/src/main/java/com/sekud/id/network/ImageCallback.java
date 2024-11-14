package com.sekud.id.network;

import com.google.firebase.storage.ListResult;

public interface ImageCallback {

    void Success(ListResult listResult);

    void Failed(String failed);

    void Failure(String failure);

}
