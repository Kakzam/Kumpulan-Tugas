package com.various.bags;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.various.bags.databinding.ActivityAddBagsBinding;
import com.various.bags.ui.administrator.BagsFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBagsActivity extends AppCompatActivity {

    ActivityAddBagsBinding binding;
    String image = "";
    FirebaseFirestore ff;

    public static String NAME_ITEM = "NAME_ITEM";
    public static String PRICE = "PRICE";
    public static String STATUS = "STATUS";
    public static String SEE = "SEE";
    public static String IMAGE = "IMAGE";

    public static String BAGS = "BAGS";

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
        ff = FirebaseFirestore.getInstance();

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
                    Map<String, String> data = new HashMap<>();
                    data.put(NAME_ITEM, binding.inputName.getText().toString());
                    data.put(PRICE, binding.inputPrice.getText().toString());
                    data.put(STATUS, binding.inputStatus.getText().toString());
                    data.put(SEE, binding.inputSee.getText().toString());
                    data.put(IMAGE, image);

                    ff.collection(BAGS).add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), AdministratorActivity.class));
                                finish();
                            } else {
                                Toast.makeText(AddBagsActivity.this, "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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