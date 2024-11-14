package com.catering.dessert.ui;

import android.annotation.SuppressLint;
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
    TextView textMessage;
    EditText editSearch;
    ImageView imageClear, imageSearch;
    CardView cardSearch;

    List<String> id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> price = new ArrayList<>();
    List<Bitmap> image = new ArrayList<>();
    List<String> imageBase64 = new ArrayList<>();

    List<String> filterId = new ArrayList<>();
    List<String> filterName = new ArrayList<>();
    List<String> filterPrice = new ArrayList<>();
    List<Bitmap> filterImage = new ArrayList<>();
    List<String> filterImageBase64 = new ArrayList<>();

    ItemDessert item;
    Boolean filter = false;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dessert, container, false);

        recycler = view.findViewById(R.id.fragment_dessert_recycler);
        progress = view.findViewById(R.id.fragment_dessert_progressbar);
        textMessage = view.findViewById(R.id.fragment_dessert_message);
        cardSearch = view.findViewById(R.id.fragment_dessert_card);
        editSearch = view.findViewById(R.id.fragment_dessert_edit);
        imageClear = view.findViewById(R.id.fragment_dessert_clear);
        imageSearch = view.findViewById(R.id.fragment_dessert_search);

        imageClear.setOnClickListener(view -> {
            filter = false;
            editSearch.setText("");
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            item = new ItemDessert(name, price, image, DessertFragment.this);
            recycler.setAdapter(item);
        });

        progress.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("catering_dessert").document("dessert").collection("-").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progress.setVisibility(View.GONE);

                if (task.getResult().getDocuments().size() > 0) {

                    id.clear();
                    name.clear();
                    price.clear();
                    image.clear();
                    imageBase64.clear();

                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        id.add(document.getId());
                        name.add(document.get("name").toString());
                        price.add(document.get("price").toString());
                        imageBase64.add(document.get("photo").toString());
                        byte[] decodedString = Base64.decode(document.get("photo").toString(), Base64.DEFAULT);
                        image.add(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recycler.setLayoutManager(layoutManager);
                    item = new ItemDessert(name, price, image, DessertFragment.this);
                    recycler.setAdapter(item);

                    imageSearch.setOnClickListener(view -> {
                        if (!editSearch.getText().toString().isEmpty()) {
                            for (int i = 0; i < id.size(); i++) {
                                if (id.get(i).contains(editSearch.getText().toString())
                                        || name.get(i).contains(editSearch.getText().toString())
                                        || price.get(i).contains(editSearch.getText().toString())) {

                                    filterId.add(id.get(i));
                                    filterName.add(name.get(i));
                                    filterPrice.add(price.get(i));
                                    filterImage.add(image.get(i));
                                    filterImageBase64.add(imageBase64.get(i));
                                    filter = true;
                                }
                            }

                            item = new ItemDessert(filterName, filterPrice, filterImage, DessertFragment.this);
                            recycler.setAdapter(item);
                            item.notifyDataSetChanged();
                        }
                    });

                } else {
                    textMessage.setVisibility(View.VISIBLE);
                    textMessage.setText("Silahkan tambah data dessert");
                    new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
                }
            } else {
                textMessage.setVisibility(View.VISIBLE);
                textMessage.setText("Silahkan periksa koneksi anda,");
                new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
            }
        });

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(int position) {
        if (new Storage(getContext()).getLogin()){
            DialogDessert dialog = new DialogDessert();
            Bundle bundle = new Bundle();

            if (filter) {
                bundle.putString(new DialogDessert().ID, filterId.get(position));
                bundle.putString(new DialogDessert().NAME, filterName.get(position));
                bundle.putInt(new DialogDessert().PRICE, Integer.parseInt(filterPrice.get(position)));
                bundle.putString(new DialogDessert().IMAGE, filterImageBase64.get(position));
                dialog.setArguments(bundle);
                dialog.show(getActivity().getSupportFragmentManager(), "DialogDessert");
            } else {
                bundle.putString(new DialogDessert().ID, id.get(position));
                bundle.putString(new DialogDessert().NAME, name.get(position));
                bundle.putInt(new DialogDessert().PRICE, Integer.parseInt(price.get(position)));
                bundle.putString(new DialogDessert().IMAGE, imageBase64.get(position));
                dialog.setArguments(bundle);
                dialog.show(getActivity().getSupportFragmentManager(), "DialogDessert");
            }
        } else {
            textMessage.setVisibility(View.VISIBLE);
            textMessage.setText("Silahkan login atau registrasi terlebih dahulu dimenu register");
            new Handler().postDelayed(() -> textMessage.setVisibility(View.GONE), 4000);
        }
    }
}