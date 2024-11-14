package com.catering.dessert.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catering.dessert.R;
import com.catering.dessert.Storage;
import com.catering.dessert.ui.item.ItemOrder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements ItemOrder.onListener {

    View view;
    ProgressBar progress;
    TextView textMessage;
    EditText editSearch;
    ImageView imageClear, imageSearch;
    CardView cardSearch;
    RecyclerView recycler;

    List<String> idItem = new ArrayList<>();
    List<String> idOrder = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<Bitmap> image = new ArrayList<>();
    List<String> imageBase64 = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> number = new ArrayList<>();

    List<String> filterIdItem = new ArrayList<>();
    List<String> filterIdOrder = new ArrayList<>();
    List<String> filterName = new ArrayList<>();
    List<String> filterPrice = new ArrayList<>();
    List<Bitmap> filterImage = new ArrayList<>();
    List<String> filterImageBase64 = new ArrayList<>();
    List<String> filterTotal = new ArrayList<>();
    List<String> filterNumber = new ArrayList<>();

    ItemOrder item;
    Boolean filter = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);

        progress = view.findViewById(R.id.fragment_order_progressbar);
        textMessage = view.findViewById(R.id.fragment_order_message);
        editSearch = view.findViewById(R.id.fragment_order_edit);
        imageSearch = view.findViewById(R.id.fragment_order_search);
        imageClear = view.findViewById(R.id.fragment_order_clear);
        cardSearch = view.findViewById(R.id.fragment_order_card);
        recycler = view.findViewById(R.id.fragment_order_recycler);

        if (new Storage(getContext()).getLogin()) {
            FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").document(new Storage(getContext()).getId()).collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().getDocuments().size() > 0) {
                        idItem.clear();
                        idOrder.clear();
                        name.clear();
                        price.clear();
                        image.clear();
                        imageBase64.clear();
                        total.clear();
                        number.clear();

                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            idItem.add(document.get("id").toString());
                            idOrder.add(document.getId());
                            name.add(document.get("name").toString());
                            price.add(document.get("price").toString());
                            total.add(document.get("total").toString());
                            number.add(document.get("number").toString());

                            byte[] decodedString = Base64.decode(document.get("photo").toString(), Base64.DEFAULT);
                            imageBase64.add(document.get("photo").toString());
                            image.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler.setLayoutManager(layoutManager);
                        item = new ItemOrder(idItem, idOrder, name, price, image, imageBase64, total, number, OrderFragment.this);
                        recycler.setAdapter(item);
                    }
                } else {
                    textMessage.setVisibility(View.VISIBLE);
                    textMessage.setText("Silahkan periksa koneksi internet anda");
                    new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                }
            });

            imageSearch.setOnClickListener(view -> {
                filter = true;
                filterIdItem.clear();
                filterIdOrder.clear();
                filterName.clear();
                filterPrice.clear();
                filterImage.clear();
                filterImageBase64.clear();
                filterNumber.clear();
                filterTotal.clear();

                for (int i = 0; i < idItem.size(); i++) {
                    if (idItem.get(i).contains(editSearch.getText().toString())
                            || idOrder.get(i).contains(editSearch.getText().toString())
                            || name.get(i).contains(editSearch.getText().toString())
                            || price.get(i).contains(editSearch.getText().toString())
                            || total.get(i).contains(editSearch.getText().toString())
                            || number.get(i).contains(editSearch.getText().toString())
                            || imageBase64.get(i).contains(editSearch.getText().toString())
                    ) {
                        filterIdItem.add(idItem.get(i));
                        filterIdOrder.add(idOrder.get(i));
                        filterName.add(name.get(i));
                        filterPrice.add(price.get(i));
                        filterImage.add(image.get(i));
                        filterImageBase64.add(imageBase64.get(i));
                        filterTotal.add(total.get(i));
                        filterNumber.add(number.get(i));
                    }
                }

                item = new ItemOrder(filterIdItem, filterIdOrder, filterName, filterPrice, filterImage, filterImageBase64, filterTotal, filterNumber, OrderFragment.this);
                recycler.setAdapter(item);
            });

            imageClear.setOnClickListener(view -> {
                editSearch.setText("");
                filter = false;
                item = new ItemOrder(idItem, idOrder, name, price, image, imageBase64, total, number, OrderFragment.this);
                recycler.setAdapter(item);
            });

        } else {
            recycler.setVisibility(View.GONE);
            cardSearch.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onClick(int position) {
        progress.setVisibility(View.VISIBLE);
        textMessage.setVisibility(View.VISIBLE);
        if (filter) {
            FirebaseFirestore.getInstance()
                    .collection("catering_dessert")
                    .document("user")
                    .collection("-")
                    .document(new Storage(getContext()).getId())
                    .collection("-")
                    .document(filterIdOrder.get(position))
                    .delete()
                    .addOnCompleteListener(task -> {
                        progress.setVisibility(View.GONE);
                        new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                        if (task.isSuccessful()) {
                            textMessage.setText("anda berhasil menghapus " + filterName.get(position));
                            filterIdItem.remove(position);
                            filterIdOrder.remove(position);
                            filterName.remove(position);
                            filterPrice.remove(position);
                            filterImage.remove(position);
                            filterImageBase64.remove(position);
                            filterNumber.remove(position);
                            filterTotal.remove(position);

                            item.notifyDataSetChanged();
                        } else {
                            textMessage.setText("anda gagal menghapus " + filterName.get(position));
                        }
                    });
        } else {
            FirebaseFirestore.getInstance()
                    .collection("catering_dessert")
                    .document("user")
                    .collection("-")
                    .document(new Storage(getContext()).getId())
                    .collection("-")
                    .document(idOrder.get(position))
                    .delete()
                    .addOnCompleteListener(task -> {
                        progress.setVisibility(View.GONE);
                        new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                        if (task.isSuccessful()) {
                            textMessage.setText("anda berhasil menghapus " + name.get(position));
                            idItem.remove(position);
                            idOrder.remove(position);
                            name.remove(position);
                            price.remove(position);
                            image.remove(position);
                            imageBase64.remove(position);
                            total.remove(position);
                            number.remove(position);
                            item.notifyDataSetChanged();
                        } else {
                            textMessage.setText("anda gagal menghapus " + name.get(position));
                        }
                    });
        }
    }
}