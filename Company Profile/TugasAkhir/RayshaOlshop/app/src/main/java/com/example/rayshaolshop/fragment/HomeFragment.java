package com.example.rayshaolshop.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.rayshaolshop.DBConfig;
import com.example.rayshaolshop.Model.CategoryModel;
import com.example.rayshaolshop.Model.NewProductsModel;
import com.example.rayshaolshop.Model.PopularProductsModel;
import com.example.rayshaolshop.R;
import com.example.rayshaolshop.activity.ShowAllActivity;
import com.example.rayshaolshop.adapter.CategoryAdapter;
import com.example.rayshaolshop.adapter.NewProductsAdapter;
import com.example.rayshaolshop.adapter.PopularProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //
    TextView catshowAll, popularShowAll, newProductShowAll;

    //deklarasi linear layout;
    LinearLayout linearLayout;

    //deklarasi "progress"
    ProgressDialog progressDialog;

    // deklarasi RecycleView
    RecyclerView catRecycleview, newProductRecycleview, popularRecycleview;

    //category recycleview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //new products Recycleview
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    //popular products Recycleview
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    //Firebase
    DBConfig config;
    Cursor cr;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        config = new DBConfig(getContext(), DBConfig.RAYSHA_OLSHOP, null, DBConfig.DB_VERSION);
        progressDialog = new ProgressDialog(getActivity());
        catRecycleview = root.findViewById(R.id.rec_category);
        newProductRecycleview = root.findViewById(R.id.new_product_rec);
        popularRecycleview = root.findViewById(R.id.popular_rec);
        catshowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);
        newProductShowAll = root.findViewById(R.id.newProducts_see_all);

        catshowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        catshowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });


        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        //image slide
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        //inisialisasi image slider
        slideModels.add(new SlideModel(R.drawable.banner1, "Diskon Sepatu", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Diskon Parfume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70% OFF", ScaleTypes.CENTER_CROP));

        //Menampilkan image slider
        imageSlider.setImageList(slideModels);

        //inisialisasi Progeress dialog
        progressDialog.setTitle("Selamat Datang di Raysha Shop");
        progressDialog.setMessage("Tunggu sebentar...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Categori
        catRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecycleview.setAdapter(categoryAdapter);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_category", null);
        cr.moveToFirst();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            categoryModelList.add(new CategoryModel(cr.getString(1), cr.getString(2), cr.getString(3)));
            categoryAdapter.notifyDataSetChanged();
        }

        //New Products
        newProductRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecycleview.setAdapter(newProductsAdapter);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_new_products", null);
        cr.moveToFirst();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            newProductsModelList.add(new NewProductsModel(cr.getString(1), cr.getString(2), cr.getString(3), cr.getInt(4), cr.getString(5)));
            newProductsAdapter.notifyDataSetChanged();
        }

        //Popular Products
        popularRecycleview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularRecycleview.setAdapter(popularProductsAdapter);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_popular_product", null);
        cr.moveToFirst();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            popularProductsModelList.add(new PopularProductsModel(cr.getString(1), cr.getString(2), cr.getString(3), cr.getInt(4), cr.getString(5)));
            popularProductsAdapter.notifyDataSetChanged();
        }

        linearLayout.setVisibility(View.VISIBLE);
        progressDialog.dismiss();

        return root;
    }
}