package com.companyprofile.ristianingsih.tampilan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.companyprofile.ristianingsih.R;
import com.companyprofile.ristianingsih.lain.AdapterHistory;
import com.companyprofile.ristianingsih.lain.Preference;
import com.companyprofile.ristianingsih.model.History;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity implements AdapterHistory.onListener {

    Button buttonAction;
    EditText editYear, editHistory;
    RecyclerView recycler;

    AdapterHistory adapter;
    List<History> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        buttonAction = findViewById(R.id.button_action_);
        editHistory = findViewById(R.id.history);
        editYear = findViewById(R.id.year);
        recycler = findViewById(R.id.recycler);

        if (new Preference(this).getLogin())
            findViewById(R.id.button_action).setVisibility(View.VISIBLE);

        findViewById(R.id.button_action).setOnClickListener(view -> {
            findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
            findViewById(R.id.button_action).setVisibility(View.GONE);
            editYear.setText("");
            editHistory.setText("");
            buttonAction.setText("Simpan");
        });

        buttonAction.setOnClickListener(view -> {
            if (!editYear.getText().toString().isEmpty() && !editHistory.getText().toString().isEmpty()) {
                if (editYear.getText().toString().length() == 4 && editHistory.getText().toString().length() > 10) {
                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, String> map = new HashMap<>();
                    map.put("year", editYear.getText().toString());
                    map.put("sejarah", editHistory.getText().toString());

                    db.collection("ristia-ningsih").document("filosofi-visi-misi").collection("history").document(editYear.getText().toString()).set(map).addOnCompleteListener(task -> {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            findViewById(R.id.button_action).setVisibility(View.VISIBLE);
                            findViewById(R.id.ll_edit).setVisibility(View.GONE);
                            settingLoad();
                            new AlertDialog.Builder(this).setMessage("Filosofi, Visi, Misi berhasil diubah!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            }).create().show();
                        } else
                            new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    }).addOnFailureListener(e1 -> {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    });
                } else {
                    new AlertDialog.Builder(this).setMessage("Silahkan periksa tahun harus 4 digit, dan sejarah harus minimal 10 digit").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                }
            } else {
                findViewById(R.id.ll_edit).setVisibility(View.GONE);
                findViewById(R.id.button_action).setVisibility(View.VISIBLE);
            }
        });

        settingLoad();
    }

    private void settingLoad() {
        findViewById(R.id.progress).setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ristia-ningsih").document("filosofi-visi-misi").collection("history").orderBy("year", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    list.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(new History(Integer.parseInt(document.getData().get("year") + ""), document.getData().get("sejarah") + ""));
                    }
                    setRecy();
                } catch (NullPointerException e) {
                    findViewById(R.id.button_action).setVisibility(View.GONE);
                    findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
                }
            } else new AlertDialog.Builder(this)
                    .setMessage("Periksa koneksi anda.")
                    .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();
        });
    }

    private void setRecy() {
        try {
            findViewById(R.id.progress).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            adapter = new AdapterHistory(list, new Preference(this).getLogin(), this);
            recycler.setAdapter(adapter);
        } catch (NullPointerException e) {
            findViewById(R.id.progress).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            adapter = new AdapterHistory(list, false, this);
            recycler.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UtamaActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(String select, int position) {
        if (select.equals("update")) {
            findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
            editYear.setText(list.get(position).getYear() + "");
            editHistory.setText(list.get(position).getHistory());
            buttonAction.setText("Ubah");
        } else if (select.equals("delete")) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("ristia-ningsih").document("filosofi-visi-misi").collection("history").document(list.get(position).getYear() + "").delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    new AlertDialog.Builder(this).setMessage("Filosofi, Visi, Misi berhasil dihapus!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }).create().show();
                    settingLoad();
                } else new AlertDialog.Builder(this)
                        .setMessage("Periksa koneksi anda.")
                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create()
                        .show();
            });
        }
    }
}