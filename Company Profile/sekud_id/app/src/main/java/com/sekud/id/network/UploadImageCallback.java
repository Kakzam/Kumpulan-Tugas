package com.sekud.id.network;

import com.google.firebase.storage.UploadTask;

public interface UploadImageCallback {

    void Success(UploadTask.TaskSnapshot taskSnapshot);

    void Failed(String failed);

    void Failure(String failure);

}
