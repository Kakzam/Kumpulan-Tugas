package com.uber.trip;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    TextInputEditText eTrip, ePenjelasam, eBiaya, ePesan;
    ImageView image;
    String jpeg = "EMPTY";
    Boolean update = false;
    Other other = new Other();

    ActivityResultLauncher<Intent> png = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            image.setImageBitmap(bitmap);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            MainActivity3.this.jpeg = Base64.encodeToString(b, Base64.DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        eTrip = findViewById(R.id.trip);
        ePenjelasam = findViewById(R.id.penjelasan);
        eBiaya = findViewById(R.id.biaya);
        ePesan = findViewById(R.id.pesan);
        image = findViewById(R.id.jpeg);

        update = Boolean.parseBoolean(getIntent().getStringExtra("update"));
        if (update) {
            other.setLoading(MainActivity3.this);

            FirebaseFirestore.getInstance()
                    .collection("collection")
                    .document("trip")
                    .collection("collection")
                    .document(getIntent().getStringExtra("select"))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            other.dissmissLoading();
                            eTrip.setText(task.getResult().get("trip").toString());
                            ePenjelasam.setText(task.getResult().get("penjelasan").toString());
                            eBiaya.setText(task.getResult().get("biaya").toString());
                            ePesan.setText(task.getResult().get("contact").toString());
                            jpeg = task.getResult().get("jpeg").toString();
                            byte[] d = Base64.decode(task.getResult().get("jpeg").toString(), Base64.DEFAULT);
                            image.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
                        }
                    });
        }

        findViewById(R.id.pilih).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                png.launch(Intent.createChooser(intent, "Pahawang"));
            }
        });

        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!jpeg.equals("EMPTY")
                        && !eTrip.getText().toString().isEmpty()
                        && !ePenjelasam.getText().toString().isEmpty()
                        && !eBiaya.getText().toString().isEmpty()
                        && !ePesan.getText().toString().isEmpty()
                ) {
                    other.setLoading(MainActivity3.this);
                    Map<String, String> map = new HashMap<>();
                    map.put("trip", eTrip.getText().toString());
                    map.put("jpeg", jpeg);
                    map.put("penjelasan", ePenjelasam.getText().toString());
                    map.put("biaya", eBiaya.getText().toString());
                    map.put("contact", ePesan.getText().toString());

                    if (update) {
                        FirebaseFirestore.getInstance()
                                .collection("collection")
                                .document("trip")
                                .collection("collection")
                                .document(getIntent().getStringExtra("select"))
                                .set(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        other.dissmissLoading();
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(MainActivity3.this, MainActivityAdmin.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            other.setToast("Silahkan periksa koneksi internet anda", MainActivity3.this);
                                        }
                                    }
                                });
                    } else {
                        FirebaseFirestore.getInstance()
                                .collection("collection")
                                .document("trip")
                                .collection("collection")
                                .add(map)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        other.dissmissLoading();
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(MainActivity3.this, MainActivityAdmin.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            other.setToast("Silahkan periksa koneksi internet anda", MainActivity3.this);
                                        }
                                    }
                                });
                    }
                } else {
                    other.setToast("Silahkan periksa koneksi internet anda", MainActivity3.this);
                }
            }
        });
    }
}