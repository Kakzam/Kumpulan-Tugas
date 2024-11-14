package com.sekud.id.feature;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.sekud.id.R;
import com.sekud.id.base.BaseActivity;
import com.sekud.id.base.Preference;
import com.sekud.id.feature.adapter.AdapterImage;
import com.sekud.id.model.ModelImage;
import com.sekud.id.network.ImageCallback;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;
import com.sekud.id.network.VersionCallback;

import java.util.ArrayList;
import java.util.List;

public class DetailItemActivity extends BaseActivity implements AdapterImage.onListener {

    RecyclerView recycler;
    TextView textTitle, textUsage, textStatus, textPrice, textAdvantage, textDeficiency, textBuy;
    ImageView imageEdit;

    AdapterImage adapter;
    List<ModelImage> list = new ArrayList<>();
    List<String> listS = new ArrayList<>();
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        id = getIntent().getStringExtra(INTENT_1);
        setInisialisasi();
    }

    private void setInisialisasi() {
        recycler = findViewById(R.id.activity_detail_item_recycler);
        textTitle = findViewById(R.id.activity_detail_item_title);
        textUsage = findViewById(R.id.activity_detail_item_usage);
        textStatus = findViewById(R.id.activity_detail_item_status);
        textPrice = findViewById(R.id.activity_detail_item_price);
        textAdvantage = findViewById(R.id.activity_detail_item_advantage);
        textDeficiency = findViewById(R.id.activity_detail_item_deficiency);
        textBuy = findViewById(R.id.activity_detail_item_buy);
        imageEdit = findViewById(R.id.activity_detail_item_edit);

        if (new Preference(this).getLogin()) textBuy.setVisibility(View.GONE);
        else imageEdit.setVisibility(View.GONE);

        textBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + "+6285218566153" + "&text=" + "Meu beli " + id);
                Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(sendIntent);
            }
        });

        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivity(CreateItemActivity.class, "true", id);
            }
        });

        getData();
    }

    private void getData() {
        setLoading("Sedang mengunduh data");
        new RestPresenter().getItem(id, new VersionCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void Success(DocumentSnapshot task) {
                textTitle.setText(task.get("title") + "");
                textUsage.setText(task.get("usage") + "");
                textStatus.setText(task.get("status") + "");
                textPrice.setText(task.get("price") + "");
                textAdvantage.setText(task.get("advantage") + "");
                textDeficiency.setText(task.get("deficiency") + "");
                getImage();
            }

            @Override
            public void Failed(String failed) {
                setLog(failed);
            }

            @Override
            public void Failure(String failure) {
                setLog(failure);
            }
        });
    }

    private void getImage() {
        new RestPresenter().getImageItemV2(id, new RestCallback() {
            @Override
            public void Success(QuerySnapshot task) {
                for (DocumentSnapshot document : task.getDocuments()){
                    listS.add(document.get("image").toString());
                }
                setRecycler();
            }

            @Override
            public void Failed(String failed) {

            }

            @Override
            public void Failure(String failure) {

            }
        });
//        new RestPresenter().getImageItem(id, new ImageCallback() {
//            @Override
//            public void Success(ListResult listResult) {
//                for (StorageReference storage : listResult.getPrefixes()) {
//                    list.add(new ModelImage(storage.getDownloadUrl().getResult().toString(), null));
//                }
//                setRecycler();
//            }
//
//            @Override
//            public void Failed(String failed) {
//                setLog(failed);
//            }
//
//            @Override
//            public void Failure(String failure) {
//                setLog(failure);
//            }
//        });
    }

    private void setRecycler() {
        dismissLoading();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterImage(listS, new Preference(this).getLogin(), this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        setActivity(MainActivity.class);
    }

    @Override
    public void onClick(String select, int position) {

    }
}