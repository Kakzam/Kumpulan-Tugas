package com.sekud.id.feature.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.sekud.id.R;
import com.sekud.id.base.BaseFragment;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.feature.CreateItemActivity;
import com.sekud.id.feature.DetailItemActivity;
import com.sekud.id.feature.adapter.AdapterItem;
import com.sekud.id.feature.alert.LoadingFragment;
import com.sekud.id.model.ModelItem;
import com.sekud.id.network.ImageCallback;
import com.sekud.id.network.ItemCallback;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends BaseFragment implements AdapterItem.onListener {

    EditText textSearch;
    SwipeRefreshLayout swipe;
    RecyclerView recycler;
    List<ModelItem> list = new ArrayList(), filter = new ArrayList<>();
    AdapterItem adapter;
    int size = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        context = getContext();
        activity = getActivity();
        loadingFragment = new LoadingFragment();
        setInisialisasi();
        return view;
    }

    private void setInisialisasi() {
        textSearch = view.findViewById(R.id.fragment_item_edit_search);
        recycler = view.findViewById(R.id.fragment_item_recycler);
        swipe = view.findViewById(R.id.fragment_item_swipe);
        swipe.setOnRefreshListener(this::getData);

        if (new Preference(context).getLogin())
            view.findViewById(R.id.fragment_item_add).setVisibility(View.VISIBLE);

        view.findViewById(R.id.fragment_item_search).setOnClickListener(view -> {

            String search = textSearch.getText().toString();

            for (ModelItem item : list) {
                if (search.contains(item.getId()) ||
                        search.contains(item.getStatus()) ||
                        search.contains(item.getAdvantage()) ||
                        search.contains(item.getDeficiency()) ||
                        search.contains(item.getPrice()) ||
                        search.contains(item.getTitle()) ||
                        search.contains(item.getUrl()) ||
                        search.contains(item.getUsage())) {
                    filter.add(item);
                }
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            adapter = new AdapterItem(filter, new Preference(context).getLogin(), ItemFragment.this);
            recycler.setAdapter(adapter);
        });

        view.findViewById(R.id.fragment_item_add).setOnClickListener(view -> {
            setActivity(CreateItemActivity.class, "false", "");
        });

        getData();
    }

    private void getData() {
        filter.clear();
        setLoading("Sedang mengunduh data");
        new RestPresenter().getAllItem(new RestCallback() {
            @Override
            public void Success(QuerySnapshot task) {
                list.clear();
                for (QueryDocumentSnapshot document : task) {
//                    new RestPresenter().getImageItem(document.getId(), new ImageCallback() {
//                        @Override
//                        public void Success(ListResult listResult) {
//                            if (listResult.getPrefixes().size() > 0) {
//                                list.add(new ModelItem(
//                                        document.getId(),
//                                        document.get("title") + "",
//                                        document.get("usage") + "",
//                                        document.get("status") + "",
//                                        document.get("price") + "",
//                                        document.get("advantage") + "",
//                                        document.get("deficiency") + "",
//                                        listResult.getPrefixes().get(0).getDownloadUrl().getResult().toString()
//                                ));
//
//                                setLog(listResult.getPrefixes().get(0).getDownloadUrl().getResult().toString());
//                            } else {


                    list.add(new ModelItem(
                            document.getId(),
                            document.get("title") + "",
                            document.get("usage") + "",
                            document.get("status") + "",
                            document.get("price") + "",
                            document.get("advantage") + "",
                            document.get("deficiency") + "",
                            ""
                    ));
//                            }
//                        }
//
//                        @Override
//                        public void Failed(String failed) {
//                            setLog(failed);
//                        }
//
//                        @Override
//                        public void Failure(String failure) {
//                            setLog(failure);
//                        }
//                    });
                }
                setRecycler();
            }

            @Override
            public void Failed(String failed) {
                dismissLoading();
                setLog(failed);
            }

            @Override
            public void Failure(String failure) {
                dismissLoading();
                setLog(failure);
            }
        });
    }

    private void setRecycler() {
        dismissLoading();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterItem(list, new Preference(context).getLogin(), ItemFragment.this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(String select, int position) {
        String id = filter.size() > 0 ? filter.get(position).getId() : list.get(position).getId();
        if (select.equals("delete")) {
            new RestPresenter().getImageItem(id, new ImageCallback() {
                @Override
                public void Success(ListResult listResult) {
                    size = listResult.getPrefixes().size();
                    for (StorageReference storage : listResult.getPrefixes()) {
                        new RestPresenter().deleteImage(storage.getDownloadUrl().getResult().toString(), new ImageCallback() {
                            @Override
                            public void Success(ListResult listResult) {
                                size -= 1;
                                if (size == 0)
                                    new RestPresenter().deleteItem(id, new ItemCallback() {
                                        @Override
                                        public void Success(DocumentReference task) {
                                            setAlerttDialog(StringUtil.WARNING, "Berhasil menghapus " + id);
                                            getData();
                                        }

                                        @Override
                                        public void Failed(String failed) {
                                            setLog(failed);
                                            setAlerttDialog(StringUtil.WARNING, failed);
                                        }

                                        @Override
                                        public void Failure(String failure) {
                                            setLog(failure);
                                            setAlerttDialog(StringUtil.WARNING, failure);
                                        }
                                    });
                            }

                            @Override
                            public void Failed(String failed) {
                                setLog(failed);
                            }

                            @Override
                            public void Failure(String failure) {
                                setLog(failure);
                            }
                        });
                    }
                }

                @Override
                public void Failed(String failed) {
                    setLog(failed);
                }

                @Override
                public void Failure(String failure) {
                    setLog(failure);
                }
            });

        } else if (select.equals("update")) {
            setActivity(CreateItemActivity.class, "true", id);
        } else if (select.equals("buy")) {
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + "+6285218566153" + "&text=" + "Meu beli " + id);
            Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(sendIntent);
        } else if (select.equals("detail")) {
            setActivity(DetailItemActivity.class, id, "");
        }
    }
}