package com.catering.dessert.ui.sheet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.catering.dessert.DBConfig;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.catering.dessert.R;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DialogDessert extends BottomSheetDialogFragment {

    View view;
    ProgressBar progress;
    TextView textImage, textMessage, textSave;
    TextInputEditText editPrice, editName;
    ImageView imageView;
    DBConfig config;

    String photo;
    ActivityResultLauncher<Intent> gallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap image = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            photo = Base64.encodeToString(b, Base64.DEFAULT);
                            imageView.setImageBitmap(image);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_dessert, container, false);
        config = new DBConfig(getContext(), DBConfig.CATERING_DESSERT, null, DBConfig.DB_VERSION);
        progress = view.findViewById(R.id.fragment_dialog_dessert_progressbar);
        textImage = view.findViewById(R.id.fragment_dialog_dessert_add);
        textMessage = view.findViewById(R.id.fragment_dialog_dessert_message);
        textSave = view.findViewById(R.id.fragment_dialog_dessert_save);
        editName = view.findViewById(R.id.fragment_dialog_dessert_name);
        editPrice = view.findViewById(R.id.fragment_dialog_dessert_price);
        imageView = view.findViewById(R.id.fragment_dialog_dessert_image);

        textImage.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            gallery.launch(Intent.createChooser(intent, "Select Picture"));
        });

        textSave.setOnClickListener(view -> {
            if (!editName.getText().toString().isEmpty() && !editPrice.getText().toString().isEmpty() && !photo.isEmpty()) {
                progress.setVisibility(View.VISIBLE);
                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_dessert (photo,name,price) VALUES(" +
                                "'" + photo.replace("\n", "") + "'," +
                                "'" + editName.getText().toString() + "'," +
                                "'" + editPrice.getText().toString() + "')");
                progress.setVisibility(View.GONE);
                textMessage.setText("Anda berhasil menambahkan makanan " + editName.getText().toString());
                dismiss();
            } else textMessage.setText("Silahkan isi semua dengan benar, dan pastikan ada fotonya");
        });

        return view;
    }

}