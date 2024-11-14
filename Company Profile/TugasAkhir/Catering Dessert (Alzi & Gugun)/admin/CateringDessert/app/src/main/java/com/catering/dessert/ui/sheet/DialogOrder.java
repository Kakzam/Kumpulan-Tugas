package com.catering.dessert.ui.sheet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catering.dessert.R;
import com.catering.dessert.ui.item.ItemDialogOrder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DialogOrder extends BottomSheetDialogFragment implements ItemDialogOrder.onListener {

    View view;
    ProgressBar progress;
    TextView textMessage;
    RecyclerView recycler;
    ItemDialogOrder item;

    List<String> id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<Bitmap> photo = new ArrayList<>();
    List<String> number = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> price = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_order, container, false);

        progress = view.findViewById(R.id.dialog_order_progressbar);
        textMessage = view.findViewById(R.id.dialog_order_message);
        recycler = view.findViewById(R.id.dialog_order_recycler);

        FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").document(getArguments().getString("ID")).collection("-").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progress.setVisibility(View.GONE);

                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                    id.add(document.getId());
                    name.add(document.get("name").toString());
                    number.add(document.get("number").toString());
                    total.add(document.get("total").toString());
                    price.add(document.get("price").toString());
                    byte[] decodedString = Base64.decode(document.get("photo").toString(), Base64.DEFAULT);
                    photo.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recycler.setLayoutManager(layoutManager);
                item = new ItemDialogOrder(id, name, photo, number, total, price, DialogOrder.this);
                recycler.setAdapter(item);

            } else {
                textMessage.setText("Silahkan tekan tombol simpan kembali");
                textMessage.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onClick(int position) {
        progress.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").document(getArguments().getString("ID")).collection("-").document(id.get(position)).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progress.setVisibility(View.GONE);
                textMessage.setText(name.get(position) + " telah selesai.");
                id.remove(position);
                name.remove(position);
                photo.remove(position);
                number.remove(position);
                total.remove(position);
                price.remove(position);
                item.notifyDataSetChanged();
            } else {
                textMessage.setText("Silahkan tekan tombol simpan kembali");
                textMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}