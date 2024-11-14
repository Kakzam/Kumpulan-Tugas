package com.go.creative.ui.primer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.go.creative.R;
import com.go.creative.other.primer.ItemSosmed;
import com.go.creative.ui.slide.FragmentSosmed;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    TextView buttonAdd;
    RecyclerView rc;
    ItemSosmed itemSosmed;

    List<String> key = new ArrayList<>();
    List<String> sosmed = new ArrayList<>();
    List<String> akun = new ArrayList<>();
    List<String> link = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        buttonAdd = view.findViewById(R.id.fragment_contact_add_sosmed);
        rc = view.findViewById(R.id.fragment_contact_recycler_view);

        buttonAdd.setOnClickListener(view1 -> new FragmentSosmed().show(getActivity().getSupportFragmentManager(), "openn"));

        FirebaseFirestore.getInstance().collection("go_creative").document("sosmed").collection("-").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                key.clear();
                sosmed.clear();
                akun.clear();
                link.clear();

                for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                    Log.v("Go", task.getResult().getDocuments().get(i).getId());

                    key.add(task.getResult().getDocuments().get(i).getId());
                    sosmed.add(task.getResult().getDocuments().get(i).get("sosmed").toString());
                    akun.add(task.getResult().getDocuments().get(i).get("nama").toString());
                    link.add(task.getResult().getDocuments().get(i).get("link").toString());
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                rc.setLayoutManager(layoutManager);
                itemSosmed = new ItemSosmed(key, sosmed, akun, link, getContext());
                rc.setAdapter(itemSosmed);
            } else
                Toast.makeText(getContext(), "Kamu gagal mengambil data", Toast.LENGTH_LONG).show();
        });

        return view;
    }
}