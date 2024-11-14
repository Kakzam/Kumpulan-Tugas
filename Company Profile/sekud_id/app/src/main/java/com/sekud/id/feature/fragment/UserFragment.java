package com.sekud.id.feature.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.QuerySnapshot;
import com.sekud.id.R;
import com.sekud.id.base.BaseFragment;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.feature.adapter.AdapterItem;
import com.sekud.id.feature.adapter.AdapterUser;
import com.sekud.id.feature.alert.DialogVerifikasiFragment;
import com.sekud.id.feature.alert.LoadingFragment;
import com.sekud.id.model.ModelUser;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment implements AdapterUser.onListener {

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//    public UserFragment() {
//        // Required empty public constructor
//    }
//
//    public static UserFragment newInstance(String param1, String param2) {
//        UserFragment fragment = new UserFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    AdapterUser adapter;
    List<ModelUser> list = new ArrayList<>();

    TextView textProfile;
    ImageView imageSearch, imageAddUser;
    EditText editSearch;
    SwipeRefreshLayout swipe;
    RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadingFragment = new LoadingFragment();
        activity = getActivity();
        context = getContext();
        view = inflater.inflate(R.layout.fragment_user, container, false);
        setInisalisasi();
        return view;
    }

    private void setInisalisasi() {
        textProfile = view.findViewById(R.id.fragment_user_profile);
        imageSearch = view.findViewById(R.id.fragment_user_search);
        imageAddUser = view.findViewById(R.id.fragment_user_add);
        editSearch = view.findViewById(R.id.fragment_user_edit_search);
        swipe = view.findViewById(R.id.fragment_user_swipe);
        recycler = view.findViewById(R.id.fragment_user_recycler);

        textProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLog(editSearch.getText().toString());
            }
        });

        imageAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getData();
    }

    private void getData() {
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterUser(list, UserFragment.this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(String select, int position) {
        if (select.equals("delete")) {
            new RestPresenter().deleteUser(list.get(position).getId(), new RestCallback() {
                @Override
                public void Success(QuerySnapshot task) {
                    setAlerttDialog(StringUtil.WARNING, list.get(position).getName() + "(" + list.get(position).getId() + ") telah berhasil dihapus.");
                    list.remove(position);
                    adapter.notifyDataSetChanged();
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
            Bundle bundle = new Bundle();
            bundle.putString("id", list.get(position).getId());
            bundle.putString("name", list.get(position).getName());
            bundle.putString("username", list.get(position).getUsername());
            bundle.putString("password", list.get(position).getPassword());
            bundle.putString("status", list.get(position).getStatus());

            DialogVerifikasiFragment dialog = new DialogVerifikasiFragment();
            dialog.setArguments(bundle);
            dialog.show(activity.getSupportFragmentManager(), "DialogVerificationFragment");
        }

    }
}