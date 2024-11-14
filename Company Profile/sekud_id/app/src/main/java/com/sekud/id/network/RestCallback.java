package com.sekud.id.network;

import com.google.firebase.firestore.QuerySnapshot;

public interface RestCallback {

    void Success(QuerySnapshot task);

    void Failed(String failed);

    void Failure(String failure);

}
