package com.teknokrat.niskala.menu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.teknokrat.niskala.databinding.ActivityDetailBinding;
import com.teknokrat.niskala.dll.Base;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends Base {

    private ActivityDetailBinding binding;
    private String title = "", deskripsi = "", base = "", id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLog("===============  " + getClass().getSimpleName() + " ===============");
        try {
            binding.back.setOnClickListener(view -> setIntent(DashboardActivity.class));
            if (!getIntent().getStringExtra("DATA_1").isEmpty()){
                if (getIntent().getBooleanExtra("DATA_2", false)){
                    db.collection("niskala").document("ruangan").collection("-").document(getIntent().getStringExtra("DATA_1")).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                id = documentSnapshot.getId();
                                title = documentSnapshot.get("title").toString();
                                deskripsi = documentSnapshot.get("deskripsi").toString();
                                base = documentSnapshot.get("gambar").toString();
                                binding.title.setText(documentSnapshot.get("title").toString());
                                binding.textDeskripsi.setText(documentSnapshot.get("deskripsi").toString());
                                binding.todayAvaiable.setImageBitmap(imageBase64(documentSnapshot.get("gambar").toString()));
                            }).addOnFailureListener(e -> setToast("Silahkan periksa kembali koneksi internet anda"));
                } else {
                    binding.buttonTanggal.setVisibility(View.GONE);
                    binding.buttonSimpan.setVisibility(View.GONE);
                    db.collection("niskala").document("user").collection("-").document(getId()).collection("-").document(getIntent().getStringExtra("DATA_1")).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                id = documentSnapshot.getId();
                                title = documentSnapshot.get("title").toString();
                                deskripsi = documentSnapshot.get("deskripsi").toString();
                                base = documentSnapshot.get("gambar").toString();
                                binding.title.setText(documentSnapshot.get("title").toString());
                                binding.textDeskripsi.setText(documentSnapshot.get("deskripsi").toString());
                                binding.todayAvaiable.setImageBitmap(imageBase64(documentSnapshot.get("gambar").toString()));
                            }).addOnFailureListener(e -> setToast("Silahkan periksa kembali koneksi internet anda"));
                }
            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int[] day_s = new int[1];
            final int[] month_s = new int[1];
            final int[] year_s = new int[1];

            DatePickerDialog datePickerDialog = new DatePickerDialog(DetailActivity.this, (view, year1, month1, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    Calendar currentDate = Calendar.getInstance();
                    if (selectedDate.before(currentDate)) {
                        setToast("Tidak dapat memilih tanggal kemarin");
                    } else {
                        day_s[0] = dayOfMonth;
                        month_s[0] = month;
                        year_s[0] = year;
                    }
            }, year, month, day);

            binding.buttonTanggal.setOnClickListener(view -> datePickerDialog.show());
            binding.buttonSimpan.setOnClickListener(view -> {
                setToast("Proses Sedang Berjalan");

                Map<String, Object> data = new HashMap<>();
                data.put("id", id);
                data.put("title", title);
                data.put("deskripsi", deskripsi);
                data.put("gambar", base);
                data.put("tanggal", day_s[0] + "");
                data.put("bulan", month_s[0] + "");
                data.put("tahun", year_s[0] + "");

                db.collection("niskala").document("user").collection("-").document(getId()).collection("-").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                db.collection("niskala").document("pinjam").collection(day_s[0] + "-" + month_s[0] + "-" + year_s[0]).add(data)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                setToast("Ruangan Berhasil Di Booking");
                                                setIntent(DashboardActivity.class);
                                            }
                                        })
                                        .addOnFailureListener(e -> setToast("Data Gagal Disimpan, Silahkan Periksa Kembali Koneksi Internet Anda"));
                            }
                        })
                        .addOnFailureListener(e -> setToast("Data Gagal Disimpan, Silahkan Periksa Kembali Koneksi Internet Anda"));
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        setIntent(DashboardActivity.class);
    }
}