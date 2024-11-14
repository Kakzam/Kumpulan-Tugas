package com.kost.ku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kost.ku.test.Input;

import java.util.ArrayList;
import java.util.List;

public class KostInputActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<String> user = new ArrayList<>();
    List<String> gambar = new ArrayList<>();
    List<String> kost = new ArrayList<>();
    List<String> alamat = new ArrayList<>();
    List<String> jenis = new ArrayList<>();
    List<String> fasilitas = new ArrayList<>();
    List<String> phone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_input);
        recyclerView = findViewById(R.id.list);

        findViewById(R.id.tambah_kost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(intent);
            }
        });

        FirebaseFirestore.getInstance().collection("local").document("kost").collection("space").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0){
                        for (DocumentSnapshot d : task.getResult()) {
                            user.add(d.getId());
                            gambar.add(d.get("gambar").toString());
                            kost.add(d.get("kost").toString());
                            alamat.add(d.get("alamat").toString());
                            jenis.add(d.get("jenis").toString());
                            fasilitas.add(d.get("fasilitas").toString());
                            phone.add(d.get("phone").toString());
                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(new Input(getApplicationContext(), user, gambar, kost, alamat, jenis, fasilitas, phone));
                    }
                } else
                    new Tambah().ToastShort("Silahkan periksa koneksi internet anda", getApplicationContext());
            }
        });
    }
}