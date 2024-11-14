package com.sekud.id.network;

import com.google.firebase.firestore.DocumentReference;

public interface ItemCallback {

    void Success(DocumentReference task);

    void Failed(String failed);

    void Failure(String failure);

}
