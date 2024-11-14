package com.catering.dessert.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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

import com.catering.dessert.DBConfig;
import com.catering.dessert.R;
import com.catering.dessert.Storage;
import com.catering.dessert.ui.item.ItemDessert;
import com.catering.dessert.ui.sheet.DialogDessert;

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
    DBConfig config;
    Cursor cr;

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
        config = new DBConfig(getContext(), DBConfig.CATERING_DESSERT, null, DBConfig.DB_VERSION);
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
            id.clear();
            name.clear();
            price.clear();
            image.clear();

            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_dessert", null);
            cr.moveToFirst();

            for (int count = 0; count < cr.getCount(); count++) {
                cr.moveToPosition(count);

                Log.v("ZAM", cr.getString(0));
                Log.v("ZAM", cr.getString(1));
                Log.v("ZAM", cr.getString(2));
                Log.v("ZAM", cr.getString(3));
                id.add(cr.getString(0));
                name.add(cr.getString(2));
                price.add(cr.getString(3));
                byte[] decodedString = Base64.decode(cr.getString(1), Base64.DEFAULT);
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
            config.getWritableDatabase().execSQL("DELETE FROM tbl_dessert WHERE id = '" + filterId.get(position) + "'");
            progress.setVisibility(View.GONE);
            textMessage.setText("Anda berhasil menghapus " + filterName.get(position));
            filterId.remove(position);
            filterName.remove(position);
            filterPrice.remove(position);
            filterImage.remove(position);
            item.notifyDataSetChanged();
        } else {
            config.getWritableDatabase().execSQL("DELETE FROM tbl_dessert WHERE id = '" + id.get(position) + "'");
            progress.setVisibility(View.GONE);
            textMessage.setText("Anda berhasil menghapus " + filterName.get(position));
            id.remove(position);
            name.remove(position);
            price.remove(position);
            image.remove(position);
            item.notifyDataSetChanged();
        }
    }
}