package com.flow.giftforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flow.giftforyou.ui.UtilString;
import com.flow.giftforyou.ui.ubah.AddBucketActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class BucketActivity extends AppCompatActivity {

    List<DocumentSnapshot> list = new ArrayList<>();
    RecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);

        view = findViewById(R.id.activity_masuk_view);

        new UtilString().e().collection(getIntent().getStringExtra(UtilString.BUCKET)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.addAll(task.getResult().getDocuments());

                    if (list.size() == 0)
                        Toast.makeText(getApplicationContext(), "Data masih kosong", Toast.LENGTH_LONG).show();

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    view.setLayoutManager(layoutManager);
                    view.setAdapter(new GambarActivity(list, getIntent().getStringExtra(UtilString.LOGIN), getIntent().getStringExtra(UtilString.BUCKET), BucketActivity.this, BucketActivity.this));
                } else
                    Toast.makeText(getApplicationContext(), "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });

        if (!getIntent().getStringExtra(UtilString.LOGIN).equals(UtilString.UBAH))
            findViewById(R.id.activity_masuk_daftar).setVisibility(View.GONE);
        findViewById(R.id.activity_masuk_daftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBucketActivity.class);
                intent.putExtra(UtilString.BUCKET, getIntent().getStringExtra(UtilString.BUCKET));
                intent.putExtra(UtilString.LOGIN, getIntent().getStringExtra(UtilString.LOGIN));
                startActivity(intent);
                finish();
            }
        });
    }
}