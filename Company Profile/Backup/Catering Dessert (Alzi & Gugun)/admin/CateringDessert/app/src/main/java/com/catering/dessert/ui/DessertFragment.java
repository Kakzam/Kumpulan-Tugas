package com.catering.dessert.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.catering.dessert.ui.item.ItemDessert;
import com.catering.dessert.ui.sheet.DialogDessert;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DessertFragment extends Fragment implements ItemDessert.onListener {

    View view;
    RecyclerView recycler;
    ProgressBar progress;
    TextView textAdd, textMessage;
    EditText editSearch;
    ImageView imageClear, imageSearch;
    CardView cardSearch;

    List<String> id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<Bitmap> image = new ArrayList<>();

    List<String> filterId = new ArrayList<>();
    List<String> filterName = new ArrayList<>();
    List<String> filterPrice = new ArrayList<>();
    List<Bitmap> filterImage = new ArrayList<>();

    ItemDessert item;
    Boolean filter = true;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dessert, container, false);

        recycler = view.findViewById(R.id.fragment_dessert_recycler);
        progress = view.findViewById(R.id.fragment_dessert_progressbar);
        textAdd = view.findViewById(R.id.fragment_dashboard_dessert_add);
        textMessage = view.findViewById(R.id.fragment_dessert_message);
        cardSearch = view.findViewById(R.id.fragment_dessert_card);
        editSearch = view.findViewById(R.id.fragment_dessert_edit);
        imageClear = view.findViewById(R.id.fragment_dessert_clear);
        imageSearch = view.findViewById(R.id.fragment_dessert_search);

        imageClear.setOnClickListener(view -> {
            editSearch.setText("");
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            item = new ItemDessert(name, price, image, DessertFragment.this);
            recycler.setAdapter(item);
        });

        textAdd.setOnClickListener(view -> new DialogDessert().show(getActivity().getSupportFragmentManager(), "DialogDessert"));

        if (new Storage(getContext()).getLogin()) {
            progress.setVisibility(View.VISIBLE);
            FirebaseFirestore.getInstance().collection("catering_dessert").document("dessert").collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progress.setVisibility(View.GONE);

                    if (task.getResult().getDocuments().size() > 0) {

                        id.clear();
                        name.clear();
                        price.clear();
                        image.clear();

                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            id.add(document.getId());
                            name.add(document.get("name").toString());
                            price.add(document.get("price").toString());
                            byte[] decodedString = Base64.decode(document.get("photo").toString(), Base64.DEFAULT);
                            image.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recycler.setLayoutManager(layoutManager);
                        item = new ItemDessert(name, price, image, DessertFragment.this);
                        recycler.setAdapter(item);

                        imageSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!editSearch.getText().toString().isEmpty()) {
                                    for (int i = 0; i < id.size(); i++) {
                                        if (id.get(i).contains(editSearch.getText().toString())
                                                || name.get(i).contains(editSearch.getText().toString())
                                                || price.get(i).contains(editSearch.getText().toString())) {

                                            filterId.add(id.get(i));
                                            filterName.add(name.get(i));
                                            filterPrice.add(price.get(i));
                                            filterImage.add(image.get(i));
                                            filter = false;
                                        }
                                    }

                                    item = new ItemDessert(filterName, filterPrice, filterImage, DessertFragment.this);
                                    recycler.setAdapter(item);
                                    item.notifyDataSetChanged();
                                }
                            }
                        });

                    } else {
                        textMessage.setVisibility(View.VISIBLE);
                        textMessage.setText("Silahkan tambah data dessert");
                    }
                } else {
                    textMessage.setVisibility(View.VISIBLE);
                    textMessage.setText("Silahkan periksa koneksi anda,");
                }
            });
        } else {
            textAdd.setVisibility(View.GONE);
            recycler.setVisibility(View.GONE);
            cardSearch.setVisibility(View.GONE);
        }

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(int position) {
        progress.setVisibility(View.VISIBLE);
        textMessage.setVisibility(View.VISIBLE);
        if (filter) {
            FirebaseFirestore.getInstance().collection("catering_dessert").document("dessert").collection("-").document(filterId.get(position)).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progress.setVisibility(View.GONE);
                    textMessage.setText("Anda berhasil menghapus " + filterName.get(position));
                    filterId.remove(position);
                    filterName.remove(position);
                    filterPrice.remove(position);
                    filterImage.remove(position);
                    item.notifyDataSetChanged();
                } else textMessage.setText("Silahkan tekan tombol simpan kembali");
            });
        } else {
            FirebaseFirestore.getInstance().collection("catering_dessert").document("dessert").collection("-").document(id.get(position)).delete().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progress.setVisibility(View.GONE);
                    textMessage.setText("Anda berhasil menghapus " + name.get(position));
                    id.remove(position);
                    name.remove(position);
                    price.remove(position);
                    image.remove(position);
                    item.notifyDataSetChanged();
                } else textMessage.setText("Silahkan tekan tombol simpan kembali");
            });
        }
    }
}