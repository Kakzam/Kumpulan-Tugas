package com.uber.trip;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {

    TextInputEditText eTrip, ePenjelasam, eBiaya, ePesan;
    ImageView image;
    String jpeg = "EMPTY";
    Boolean update = false;
    Other other = new Other();
    DBConfig config;
    Cursor cr;

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
        config = new DBConfig(this, DBConfig.UBER_TRIP, null, DBConfig.DB_VERSION);

        update = Boolean.parseBoolean(getIntent().getStringExtra("update"));
        if (update) {
            other.setLoading(MainActivity3.this);
            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_trip WHERE id = " + getIntent().getStringExtra("select"), null);
            cr.moveToFirst();
            cr.moveToPosition(cr.getCount() - 1);
            other.dissmissLoading();
            eTrip.setText(cr.getString(1));
            ePenjelasam.setText(cr.getString(3));
            eBiaya.setText(cr.getString(4));
            ePesan.setText(cr.getString(5));
            jpeg = cr.getString(2);
            byte[] d = Base64.decode(cr.getString(2), Base64.DEFAULT);
            image.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
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
                    if (update) {
                        config.getWritableDatabase().execSQL(
                                "UPDATE tbl_trip SET " +
                                        "trip = '" + eTrip.getText().toString() + "'," +
                                        "jpeg = '" + jpeg + "'," +
                                        "penjelasan = '" + ePenjelasam.getText().toString() + "'," +
                                        "biaya = '" + eBiaya.getText().toString() + "'," +
                                        "contact = '" + ePesan.getText().toString() + "' " +
                                        "WHERE id = " + getIntent().getStringExtra("select"));

                        other.dissmissLoading();
                        Intent intent = new Intent(MainActivity3.this, MainActivityAdmin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        config.getWritableDatabase().execSQL(
                                "INSERT INTO tbl_trip (trip,jpeg,penjelasan,biaya,contact) VALUES(" +
                                        "'" + eTrip.getText().toString() + "'," +
                                        "'" + jpeg + "'," +
                                        "'" + ePenjelasam.getText().toString() + "'," +
                                        "'" + eBiaya.getText().toString() + "'," +
                                        "'" + ePesan.getText().toString() + "')");
                        other.dissmissLoading();
                        Intent intent = new Intent(MainActivity3.this, MainActivityAdmin.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    other.setToast("Silahkan periksa koneksi internet anda", MainActivity3.this);
                }
            }
        });
    }
}