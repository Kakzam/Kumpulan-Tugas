package com.catering.dessert.ui.sheet;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.catering.dessert.Storage;
import com.catering.dessert.ui.item.ItemDessert;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.catering.dessert.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DialogDessert extends BottomSheetDialogFragment {

    View view;
    ProgressBar progress;
    TextView textMessage, textSave;
    TextInputEditText editPrice, editName, editTotal;
    EditText editNumber;
    ImageView imageView;

    int number = 1;
    public String ID = "ID";
    public String NAME = "NAME";
    public String PRICE = "PRICE";
    public String IMAGE = "IMAGE";

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_dessert, container, false);

        String id = getArguments().getString(ID);
        String name = getArguments().getString(NAME);
        int price = getArguments().getInt(PRICE);
        String image = getArguments().getString(IMAGE);

        progress = view.findViewById(R.id.fragment_dialog_dessert_progressbar);
        textMessage = view.findViewById(R.id.fragment_dialog_dessert_message);
        textSave = view.findViewById(R.id.fragment_dialog_dessert_save);
        editName = view.findViewById(R.id.fragment_dialog_dessert_name);
        editPrice = view.findViewById(R.id.fragment_dialog_dessert_price);
        editTotal = view.findViewById(R.id.fragment_dialog_dessert_total);
        imageView = view.findViewById(R.id.fragment_dialog_dessert_image);
        editNumber = view.findViewById(R.id.dialog_dessert_edit);

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        editName.setText(name);
        editPrice.setText(new ItemDessert(null, null, null, null).getCurrency(price));
        editTotal.setText(new ItemDessert(null, null, null, null).getCurrency(price));

        view.findViewById(R.id.dialog_dessert_plus).setOnClickListener(view -> {
            number += 1;
            editNumber.setText("" + number);
            editTotal.setText(new ItemDessert(null, null, null, null).getCurrency((number * price)));
        });

        view.findViewById(R.id.dialog_dessert_minus).setOnClickListener(view -> {
            number -= 1;
            if (number < 0) {
                number = 0;
            } else {
                editNumber.setText("" + number);
                editTotal.setText(new ItemDessert(null, null, null, null).getCurrency((number * price)));
            }
        });

        textSave.setOnClickListener(view -> {
            if (number > 0) {
                progress.setVisibility(View.VISIBLE);
                Map<String, String> map = new HashMap<>();
                map.put("photo", image);
                map.put("name", name);
                map.put("price", price + "");
                map.put("number", number + "");
                map.put("total", (price * number) + "");
                map.put("id", id);

                FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").document(new Storage(getContext()).getId()).collection("-").add(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progress.setVisibility(View.GONE);
                        textMessage.setText("Anda berhasil menambahkan makanan " + name + " silahkan lihat dimenu order");
                        dismiss();
                    } else textMessage.setText("Silahkan tekan tombol simpan kembali");
                });
            } else textMessage.setText("Silahkan isi semua dengan benar, dan pastikan ada fotonya");
        });

        return view;
    }

}