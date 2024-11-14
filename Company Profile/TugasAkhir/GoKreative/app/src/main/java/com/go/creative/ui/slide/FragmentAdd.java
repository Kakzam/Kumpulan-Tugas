package com.go.creative.ui.slide;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.go.creative.other.DBConfig;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.go.creative.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FragmentAdd extends BottomSheetDialogFragment {

    TextInputEditText inputNama, inputHarga, inputTerjual;
    ImageView imageView;
    TextView textSave;
    ProgressBar progress;
    String image;
    DBConfig config;

    ActivityResultLauncher<Intent> open = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            imageView.setImageBitmap(bitmap);
                            FragmentAdd.this.image = Base64.encodeToString(b, Base64.DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        config = new DBConfig(getContext(), DBConfig.GO_CREATIVE, null, DBConfig.DB_VERSION);
        inputNama = view.findViewById(R.id.fragment_add_name);
        inputHarga = view.findViewById(R.id.fragment_add_harga);
        inputTerjual = view.findViewById(R.id.fragment_add_sell);
        imageView = view.findViewById(R.id.fragment_add_image);
        textSave = view.findViewById(R.id.fragment_add_save);
        progress = view.findViewById(R.id.fragment_add_progress);

        imageView.setOnClickListener(view1 -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            open.launch(Intent.createChooser(intent, "Pilih Gambar"));
        });

        textSave.setOnClickListener(view1 -> {
            progress.setVisibility(View.VISIBLE);
            config.getWritableDatabase().execSQL(
                    "INSERT INTO tbl_desain (nama, harga, terjual, gambar) VALUES(" +
                            "'" + inputNama.getText().toString() + "'," +
                            "'" + inputHarga.getText().toString() + "'," +
                            "'" + inputTerjual.getText().toString() + "'," +
                            "'" + image + "')");
            progress.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Kamu berhasil nambah data", Toast.LENGTH_LONG).show();
            dismiss();
        });

        return view;
    }

}