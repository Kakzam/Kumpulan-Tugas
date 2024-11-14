package com.example.tugasakhir;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.tugasakhir.databinding.ActivityRumahBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RumahActivity extends BaseActivity {

    ActivityRumahBinding binding;
    private Calendar calendar;
    private ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRumahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        binding.editTextDate.setText(currentDate);

        dataList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("barang").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        dataList.add(documentSnapshot.getString("nama"));
                    }
                }).addOnFailureListener(e -> setToast("Periksa Koneksi Internet Anda"));

        if (!dataList.isEmpty()) {
            binding.editTextBarang.setText(dataList.get(0));
        }

        binding.tombol.setOnClickListener(view -> {
            if (binding.check.isChecked()){
                if (binding.editTextBarang.getText().toString().isEmpty()) {
                    setToast("Silahkan Pilih Data Barang");
                } else if (binding.jumlah.getText().toString().isEmpty()) {
                    setToast("Silahkan Isi Jumlah");
                } else {
                    setToast("Proses Sedang Berjalan");

                    Map<String, Object> user = new HashMap<>();
                    user.put("tanggal", binding.editTextDate.getText().toString());
                    user.put("nama", binding.editTextBarang.getText().toString());
                    user.put("jumlah", binding.jumlah.getText().toString());

                    FirebaseFirestore.getInstance().collection("data")
                            .document(getId())
                            .collection("Rumah")
                            .add(user)
                            .addOnSuccessListener(documentReference -> {
                                setToast("Menunggu Konfirmasi");
                                finish();
                            })
                            .addOnFailureListener(e -> {setToast("Periksa Koneksi Internet Anda");});
                }
            } else {
                setToast("Anda belum setuju dengan ketentuan yang berlaku");
            }
        });
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String selectedDate = dateFormat.format(calendar.getTime());
            binding.editTextDate.setText(selectedDate);
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    public void showDataListDialog(View view) {
        if (dataList.isEmpty()) {
            return; // Jika daftar data kosong, tampilkan pesan atau tindakan lainnya
        }

        final CharSequence[] items = dataList.toArray(new CharSequence[dataList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Data");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String selectedItem = items[item].toString();
                binding.editTextBarang.setText(selectedItem);
                dialog.dismiss();
            }
        });
        builder.show();
    }
}