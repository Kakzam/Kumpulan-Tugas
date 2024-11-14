package com.companyprofile.ristianingsih.tampilan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.companyprofile.ristianingsih.R;
import com.companyprofile.ristianingsih.lain.AdapterHistory;
import com.companyprofile.ristianingsih.model.History;

import java.util.ArrayList;
import java.util.List;

public class AwardsActivity extends AppCompatActivity {

    Button buttonAction;
    EditText editTitle, editLink;
    RecyclerView recycler;

    AdapterHistory adapter;
    List<History> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awards);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, UtamaActivity.class);
        startActivity(intent);
        finish();
    }
}