package com.sekud.id.feature;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sekud.id.R;
import com.sekud.id.base.BaseActivity;
import com.sekud.id.base.BaseFragment;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.feature.adapter.AdapterImage;
import com.sekud.id.model.ModelImage;
import com.sekud.id.network.ImageCallback;
import com.sekud.id.network.ItemCallback;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;
import com.sekud.id.network.UploadImageCallback;
import com.sekud.id.network.VersionCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateItemActivity extends BaseActivity implements AdapterImage.onListener {

    RecyclerView recycler;
    TextInputEditText editTitle, editUsage, editPrice, editAdvantage, editDeficiency;
    CheckBox checkBox;

    Boolean select = false;
    String id = "";
    AdapterImage adapter;
    List<ModelImage> list = new ArrayList<>();

    ActivityResultLauncher<Intent> gallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                setLoading("Upload Image");
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();

//                        Bitmap bitmap = (Bitmap) Objects.requireNonNull(result.getData()).getExtras().get("data");
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                            new RestPresenter().uploadImageItemV2(encodeTobase64(bitmap), "l1vM03sGMDR3DmN2Pxip", new RestCallback() {
                                @Override
                                public void Success(QuerySnapshot task) {
                                    Toast.makeText(CreateItemActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void Failed(String failed) {

                                }

                                @Override
                                public void Failure(String failure) {

                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        list.add(new ModelImage("", imageUri));
//                        setLog("Size : " + list.size());
//                        setRecycler();
//                        new RestPresenter().uploadImageItem(BaseConfig.BASE, BaseConfig.ITEM, UUID.randomUUID().toString(), imageUri, new UploadImageCallback() {
//                            @Override
//                            public void Success(UploadTask.TaskSnapshot taskSnapshot) {
////                                    dismissLoading();
//                                setLog(
//                                        "1 : " + taskSnapshot.getUploadSessionUri().getPath() + "\n" +
//                                                "2 : " + taskSnapshot.getMetadata().getPath() + "\n" +
//                                                "3 : " + taskSnapshot.getMetadata().getName() + "\n" +
//                                                "4 : " + taskSnapshot.getMetadata().getContentType() + "\n" +
//                                                "5 : " + taskSnapshot.getMetadata().getReference().getStorage().getReference().getDownloadUrl().getResult() + "\n" +
//                                                "6 : " + taskSnapshot.getUploadSessionUri().toString() + "\n"
//                                );
//
////                                    Picasso.get().load(taskSnapshot.getMetadata().getReference().getDownloadUrl().getResult()).into(image);
//                            }
//
//                            @Override
//                            public void Failure(String failure) {
//                                setLog(failure);
////                                    dismissLoading();
//                                setAlerttDialog(StringUtil.WARNING, failure);
//                            }
//                        });
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);
        select = Boolean.parseBoolean(getIntent().getStringExtra(new BaseFragment().INTENT_1));
        if (select) id = getIntent().getStringExtra(new BaseFragment().INTENT_2);
        setInisialisasi();
    }

    private void setInisialisasi() {
        recycler = findViewById(R.id.activity_create_item_recycler);
        editTitle = findViewById(R.id.activity_create_item_title_input);
        editUsage = findViewById(R.id.activity_create_item_usage_input);
        editPrice = findViewById(R.id.activity_create_item_price_input);
        editAdvantage = findViewById(R.id.activity_create_item_advantage_input);
        editDeficiency = findViewById(R.id.activity_create_item_deficiency_input);
        checkBox = findViewById(R.id.activity_create_item_check);

        findViewById(R.id.activity_create_item_save_button).setOnClickListener(view -> {
            if (editTitle.getText().toString().isEmpty() &&
                    editUsage.getText().toString().isEmpty() &&
                    editPrice.getText().toString().isEmpty() &&
                    editAdvantage.getText().toString().isEmpty() &&
                    editDeficiency.getText().toString().isEmpty()
            ) {
                setLoading("Sedang Berjalan Upload Data");
                if (!select) {
                    new RestPresenter().addItem(
                            editTitle.getText().toString(),
                            editUsage.getText().toString(),
                            checkBox.isChecked() + "",
                            editPrice.getText().toString(),
                            editAdvantage.getText().toString(),
                            editDeficiency.getText().toString(),
                            new ItemCallback() {
                                @Override
                                public void Success(DocumentReference task) {
                                    addImage(task.getId(), 0);
                                }

                                @Override
                                public void Failed(String failed) {

                                }

                                @Override
                                public void Failure(String failure) {

                                }
                            }
                    );
                } else {
                    new RestPresenter().updateItem(
                            editTitle.getText().toString(),
                            editUsage.getText().toString(),
                            checkBox.isChecked() + "",
                            editPrice.getText().toString(),
                            editAdvantage.getText().toString(),
                            editDeficiency.getText().toString(),
                            id,
                            new ItemCallback() {
                                @Override
                                public void Success(DocumentReference task) {
                                    addImage(task.getId(), 0);
                                }

                                @Override
                                public void Failed(String failed) {

                                }

                                @Override
                                public void Failure(String failure) {

                                }
                            }
                    );
                }
            } else {
                setAlerttDialog(StringUtil.WARNING, "Silahkan periksa semua dengan benar");
            }
        });

        findViewById(R.id.activity_create_item_add_image).setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            gallery.launch(Intent.createChooser(intent, "Select Picture"));
        });

        if (select) setData();
    }

    private void addImage(String getId, int iterator) {
        if (list.get(iterator).getUrl().isEmpty()) {
            new RestPresenter().uploadImageItem(getId, list.get(iterator).getUri(), new UploadImageCallback() {
                @Override
                public void Success(UploadTask.TaskSnapshot taskSnapshot) {
                    setLog(iterator + "");
                    if ((iterator+1) < list.size()) addImage(getId, iterator + 1);
                    else {
                        dismissLoading();
                        setActivity(MainActivity.class);
                    }
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
        } else if ((iterator+1) < list.size()) addImage(getId, iterator + 1);
    }

    private void setData() {
        new RestPresenter().getItem(id, new VersionCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void Success(DocumentSnapshot task) {
                editTitle.setText(task.get("title") + "");
                editUsage.setText(task.get("usage") + "");
                editPrice.setText(task.get("status") + "");
                editAdvantage.setText(task.get("price") + "");
                editDeficiency.setText(task.get("advantage") + "");

                if (Boolean.parseBoolean(task.get("advantage") + "")) checkBox.setChecked(true);

                getUrlImage(task.getId());
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

    private void getUrlImage(String id) {
        new RestPresenter().getImageItem(id, new ImageCallback() {
            @Override
            public void Success(ListResult listResult) {
                getImage(listResult);
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

    private void getImage(ListResult listResult) {
        setLoading("Sedang mengambil gambar");
        for (StorageReference storage : listResult.getItems()) {
            storage.getDownloadUrl().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    list.add(new ModelImage(task.getResult() + "", null));
                } else{
                    setAlerttDialog(StringUtil.WARNING, "Gagal mengambil gambar, silahkan ulangi kembali dan pastikan jaringan anda dalam keadaan bagus");
                }
            });
            adapter.notifyDataSetChanged();
        }
        setRecycler();
    }

    private void setRecycler() {
        dismissLoading();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
//        adapter = new AdapterImage(list, new Preference(this).getLogin(), this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(String select, int position) {
        if (select.equals("delete")){
            setLoading("Sedang menghapus gambar");
            if (!id.isEmpty()) {
                if (!list.get(position).getUrl().isEmpty()){
                    new RestPresenter().deleteImage(list.get(position).getUrl(), new ImageCallback() {
                        @Override
                        public void Success(ListResult listResult) {
                            setAlerttDialog(StringUtil.WARNING, "Gambar " + position + " berhasil dihapus");
                            list.remove(position);
                            setRecycler();
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
                } else {
                    list.remove(position);
                    setRecycler();
                }
            } else {
                list.remove(position);
                setRecycler();
            }
        }
    }
}