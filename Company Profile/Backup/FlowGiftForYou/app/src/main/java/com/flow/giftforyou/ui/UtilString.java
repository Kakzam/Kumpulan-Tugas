package com.flow.giftforyou.ui;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UtilString {

    public static String PENDAFTARAN = "PENDAFTARAN_";
    public static String USERNAME = PENDAFTARAN + "USERNAME";
    public static String PASSWORD = PENDAFTARAN + "PASSWORD";
    public static String NAMA = PENDAFTARAN + "NAMA";

    public static String BUCKET = "BUCKET_";
    public static String HANTARAN = "HANTARAN_";
    public static String FRAME = "FRAME_";

    public static String JPEG = "JPEG";
    public static String JUDUL = "JUDUL";
    public static String DESKRIPSI = "DESKRIPSI";
    public static String WA = "WA";

    public static String UBAH = "UBAH";
    public static String LOGIN = "LOGIN";
    public static String GAGAL = "GAGAL";

    public FirebaseFirestore e() {
        return FirebaseFirestore.getInstance();
    }

    public String check(List<DocumentSnapshot> list, String id, String sandi) {
        String select = GAGAL;
        int position = list.size();

        if (position > 0) for (int item = 0; item < position; item++) {
            if (list.get(item).get(USERNAME).toString().equals(id)
                    && list.get(item).get(PASSWORD).toString().equals(sandi)) {
                if (list.get(item).get(LOGIN).toString().equals(LOGIN)) {
                    select = LOGIN;
                }

                if (list.get(item).get(LOGIN).toString().equals(UBAH)) {
                    select = UBAH;
                }
            }
        }

        return select;
    }

    public void deleteData(String collection, String id, Context context) {
        e().collection(collection).document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Anda berhasil menghapus", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context, "Anda gagal menghapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
