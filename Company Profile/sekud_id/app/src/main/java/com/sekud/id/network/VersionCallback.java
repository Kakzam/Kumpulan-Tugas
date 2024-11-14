package com.sekud.id.network;

import com.google.firebase.firestore.DocumentSnapshot;

public interface VersionCallback {

    void Success(DocumentSnapshot task);

    void Failed(String failed);

    void Failure(String failure);

}
