package com.various.bags;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.various.bags.databinding.ActivityAddBagsBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddBagsActivity extends AppCompatActivity {

    ActivityAddBagsBinding binding;
    String image = "";
    DBConfig config;

    ActivityResultLauncher<Intent> inputTas = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            image = Base64.encodeToString(b, Base64.DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        config = new DBConfig(this, DBConfig.VARIOUS_BAGS, null, DBConfig.DB_VERSION);

        /* Select Bags New */
        binding.inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                inputTas.launch(Intent.createChooser(intent, "Tas Baru"));
            }
        });

        /* Verification Bags New */
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.inputName.getText().toString().isEmpty()
                        && !binding.inputPrice.getText().toString().isEmpty()
                        && !binding.inputStatus.getText().toString().isEmpty()
                        && !binding.inputSee.getText().toString().isEmpty()
                        && !image.isEmpty()
                ) {
                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_bags (nama_item,price,status,see,image) VALUES(" +
                                    "'" + binding.inputName.getText().toString() + "'," +
                                    "'" + binding.inputPrice.getText().toString() + "'," +
                                    "'" + binding.inputStatus.getText().toString() + "'," +
                                    "'" + binding.inputSee.getText().toString() + "'," +
                                    "'" + image + "')");

                    startActivity(new Intent(getApplicationContext(), AdministratorActivity.class));
                    finish();
                } else if (binding.inputName.getText().toString().isEmpty()) {
                    Toast.makeText(AddBagsActivity.this, "Silahkan isi nama barang", Toast.LENGTH_SHORT).show();
                } else if (binding.inputPrice.getText().toString().isEmpty()) {
                    Toast.makeText(AddBagsActivity.this, "Silahkan isi harga barang", Toast.LENGTH_SHORT).show();
                } else if (binding.inputStatus.getText().toString().isEmpty()) {
                    Toast.makeText(AddBagsActivity.this, "Silahkan isi status barang", Toast.LENGTH_SHORT).show();
                } else if (binding.inputSee.getText().toString().isEmpty()) {
                    Toast.makeText(AddBagsActivity.this, "Silahkan isi dilihat barang", Toast.LENGTH_SHORT).show();
                } else if (image.isEmpty()) {
                    Toast.makeText(AddBagsActivity.this, "Silahkan isi gambar barang", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), AdministratorActivity.class));
        finish();
    }
}