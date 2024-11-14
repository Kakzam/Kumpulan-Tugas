package com.companyprofile.ristianingsih.tampilan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.companyprofile.ristianingsih.R;
import com.companyprofile.ristianingsih.lain.Preference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FilosofiActivity extends AppCompatActivity {

    EditText editFilosofi, editVisi, editMisi;
    TextView textFilosofi, textVisi, textMisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filosofi);

        editFilosofi = findViewById(R.id.edit_filosofi);
        editVisi = findViewById(R.id.edit_visi);
        editMisi = findViewById(R.id.edit_misi);
        textFilosofi = findViewById(R.id.text_filosofi);
        textVisi = findViewById(R.id.text_visi);
        textMisi = findViewById(R.id.text_misi);

        if (new Preference(this).getLogin())
            findViewById(R.id.button_edit).setVisibility(View.VISIBLE);

        findViewById(R.id.button_edit).setOnClickListener(view -> {
            findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
            findViewById(R.id.button_edit).setVisibility(View.GONE);
        });

        findViewById(R.id.button_save).setOnClickListener(view -> {
            if (editFilosofi.getText().toString().isEmpty() ||
                    editVisi.getText().toString().isEmpty() ||
                    editMisi.getText().toString().isEmpty()) {
                findViewById(R.id.ll_edit).setVisibility(View.GONE);
                findViewById(R.id.button_edit).setVisibility(View.VISIBLE);
            } else {
                if (editFilosofi.getText().toString().length() > 10 ||
                        editVisi.getText().toString().length() > 10 ||
                        editMisi.getText().toString().length() > 10) {
                    findViewById(R.id.button_edit).setVisibility(View.VISIBLE);
                    new AlertDialog.Builder(this).setMessage("Tidak ada yang boleh kosong").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                } else {
                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, String> map = new HashMap<>();
                    map.put("filosofi", editFilosofi.getText().toString());
                    map.put("visi", editVisi.getText().toString());
                    map.put("misi", editMisi.getText().toString());
                    db.collection("ristia-ningsih").document("filosofi-visi-misi").set(map).addOnCompleteListener(task -> {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            findViewById(R.id.button_edit).setVisibility(View.VISIBLE);
                            findViewById(R.id.ll_edit).setVisibility(View.GONE);
                            setLoadData();
                            new AlertDialog.Builder(this).setMessage("Filosofi, Visi, Misi berhasil diubah!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            }).create().show();
                        } else
                            new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    }).addOnFailureListener(e1 -> {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    });
                }
            }
        });

        setLoadData();
    }

    private void setLoadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ristia-ningsih").document("filosofi-visi-misi").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    textFilosofi.setText(task.getResult().get("filosofi").toString());
                    textVisi.setText(task.getResult().get("visi").toString());
                    textMisi.setText(task.getResult().get("misi").toString());

                    editFilosofi.setText(task.getResult().get("filosofi").toString());
                    editVisi.setText(task.getResult().get("visi").toString());
                    editMisi.setText(task.getResult().get("misi").toString());
                } catch (NullPointerException e) {
                    findViewById(R.id.button_edit).setVisibility(View.GONE);
                    findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
                }
            } else new AlertDialog.Builder(this)
                    .setMessage("Periksa koneksi anda.")
                    .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UtamaActivity.class);
        startActivity(intent);
        finish();
    }
}